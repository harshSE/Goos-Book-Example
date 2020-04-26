package org.harshdev.goosbook


import spock.lang.Specification

class AuctionSniperEndToEndTest extends Specification {

    private final FakeAuctionServer item54321Auction = new FakeAuctionServer("item-54321")
    private final FakeAuctionServer item65432Auction = new FakeAuctionServer("item-65432")
    private final ApplicationRunner application = new ApplicationRunner();

    def "sniper joins auction until auction close"() {

        when:
        item54321Auction.startSellingItem()
        application.startBiddingIn(item54321Auction)
        item54321Auction.hasReceivedJoinRequestFromSniper(ApplicationRunner.SNIPER_XMPP_ID)
        item54321Auction.announceClosed()

        then:
        application.showSniperHasLostAuction(item54321Auction, 0, 0);


    }

    def "sniper makes a higher bid but loses"() {
        when:
        item54321Auction.startSellingItem()
        application.startBiddingIn(item54321Auction)
        item54321Auction.hasReceivedJoinRequestFromSniper(ApplicationRunner.SNIPER_XMPP_ID)
        item54321Auction.reportPrice(1000, 98, "other bidder")

        application.hasShownSniperIsBidding(item54321Auction,1000, 1098)

        item54321Auction.hasReceivedBid(1098, ApplicationRunner.SNIPER_XMPP_ID)

        item54321Auction.announceClosed()

        then:
        application.showSniperHasLostAuction(item54321Auction, 1000, 1098)

    }

    def "sniper wins an auction by bidding higher" () {

        when:
         item54321Auction.startSellingItem()
        application.startBiddingIn(item54321Auction)
        item54321Auction.hasReceivedJoinRequestFromSniper(ApplicationRunner.SNIPER_XMPP_ID)
        item54321Auction.reportPrice(1000, 98, "other bidder")
        application.hasShownSniperIsBidding(item54321Auction,1000, 1098)

        item54321Auction.hasReceivedBid(1098, ApplicationRunner.SNIPER_XMPP_ID)

        item54321Auction.reportPrice(1098, 97, ApplicationRunner.SNIPER_XMPP_ID)
        application.hasShownSniperIsWinning(item54321Auction, 1098)

        item54321Auction.announceClosed()

        then:
        application.showsSnipeHasWonTheAuction(item54321Auction, 1098)

    }


    def "sniper bids for multiple items" () {
        when:

        item54321Auction.startSellingItem()
        item65432Auction.startSellingItem()

        application.startBiddingIn(item54321Auction, item65432Auction)

        then:
        item54321Auction.hasReceivedJoinRequestFromSniper(ApplicationRunner.SNIPER_XMPP_ID)
        item65432Auction.hasReceivedJoinRequestFromSniper(ApplicationRunner.SNIPER_XMPP_ID)

        item54321Auction.reportPrice(1000, 98, "other bidder")
        application.hasShownSniperIsBidding(item54321Auction,1000, 1098)

        item65432Auction.reportPrice(500, 50, "other bidder for 54321")
        application.hasShownSniperIsBidding(item65432Auction,500, 550)

        item54321Auction.reportPrice(1098, 97, ApplicationRunner.SNIPER_XMPP_ID)
        item65432Auction.reportPrice(550, 25, ApplicationRunner.SNIPER_XMPP_ID)

        application.hasShownSniperIsWinning(item54321Auction, 1098)
        application.hasShownSniperIsWinning(item65432Auction, 550)

        item54321Auction.announceClosed()
        item65432Auction.announceClosed()

        then:

        application.showsSnipeHasWonTheAuction(item54321Auction, 1098)
        application.showsSnipeHasWonTheAuction(item65432Auction, 550)
    }

    def cleanup() {
        application.stop();
        item54321Auction.stop()
        item65432Auction.stop()
    }





}
