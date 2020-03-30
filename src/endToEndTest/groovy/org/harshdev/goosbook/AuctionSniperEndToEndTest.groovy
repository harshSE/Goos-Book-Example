package org.harshdev.goosbook

import spock.lang.Specification

public class AuctionSniperEndToEndTest extends Specification {

    private final FakeAuctionServer auction = new FakeAuctionServer("item-54321")
    private final ApplicationRunner application = new ApplicationRunner();

    def "sniper joins auction until auction close"() {
        auction.startSellingItem()
        application.startBiddingIn(auction)
        auction.hasReceivedJoinRequestFromSniper()
        auction.announceClosed()
        application.showSniperHasLostAuction();
        
    }

    def cleanup() {
        application.stop();
        auction.stop()
    }


}
