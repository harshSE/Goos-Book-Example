package org.harshdev.goosbook

import spock.lang.Specification

class AuctionSniperEndToEndTest extends Specification {

    private final FakeAuctionServer auction = new FakeAuctionServer("item-54321")
    private final ApplicationRunner application = new ApplicationRunner();

    def "sniper joins auction until auction close"() {

        when:
        auction.startSellingItem()
        application.startBiddingIn(auction)
        auction.hasReceivedJoinRequestFromSniper()
        auction.announceClosed()


        then:
        application.showSniperHasLostAuction();

    }

    def cleanup() {
        application.stop();
        auction.stop()
    }


}
