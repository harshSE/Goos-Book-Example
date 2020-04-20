package org.harshdev.goosbook


import spock.lang.Specification

class AuctionSniperEndToEndTest extends Specification {

    private final FakeAuctionServer auction = new FakeAuctionServer("item-54321")
    private final ApplicationRunner application = new ApplicationRunner();

    def "sniper joins auction until auction close"() {

        when:
        auction.startSellingItem()
        application.startBiddingIn(auction)
        auction.hasReceivedJoinRequestFromSniper(ApplicationRunner.SNIPER_XMPP_ID)
        auction.announceClosed()

        then:
        application.showSniperHasLostAuction();


    }

    def "sniper makes a higher bid but loses"() {
        when:
        auction.startSellingItem()
        application.startBiddingIn(auction)
        auction.hasReceivedJoinRequestFromSniper(ApplicationRunner.SNIPER_XMPP_ID)
        auction.reportPrice(1000, 98, "other bidder")

        application.hasShownSniperIsBidding()

        auction.hasReceivedBid(1098, ApplicationRunner.SNIPER_XMPP_ID)

        auction.announceClosed()

        then:
        application.showSniperHasLostAuction()

    }

    def "sniper wins an auction by bidding higher" () {

        when:
         auction.startSellingItem()
        application.startBiddingIn(auction)
        auction.hasReceivedJoinRequestFromSniper(ApplicationRunner.SNIPER_XMPP_ID)
        auction.reportPrice(1000, 98, "other bidder")

        application.hasShownSniperIsBidding(1000, 1098)

        auction.hasReceivedBid(1098, ApplicationRunner.SNIPER_XMPP_ID)

        auction.reportPrice(1098, 97, ApplicationRunner.SNIPER_XMPP_ID)
        application.hasShownSniperIsWinning(1098)

        auction.announceClosed()

        then:
        application.showsSnipeHasWonTheAuction(1098)

    }



    def cleanup() {
        application.stop();
        auction.stop()
    }


}
