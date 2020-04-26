package org.harshdev.goosbook.auctionsniper.xmpp


import org.harshdev.goosbook.FakeAuctionServer
import org.jivesoftware.smack.tcp.XMPPTCPConnection
import spock.lang.Specification

import java.util.concurrent.CountDownLatch

import static java.util.concurrent.TimeUnit.SECONDS
import static org.junit.jupiter.api.Assertions.assertTrue

class AuctionHouseSpec extends Specification{
    private static final String SNIPER_XMPP_ID = "sniper@harshdev.com/auction"
    private static final String SNIPER_ID = "sniper"
    private static final String SNIPER_PASSWORD = "sniper"
    private static final String XMPP_HOSTNAME = "localhost"
    private String item = "item-54321"
    private FakeAuctionServer server
    private XMPPAuction auction
    private XMPPTCPConnection connection
    private AuctionHouse auctionHouse

    def setup(){
        server = new FakeAuctionServer(item)
        server.startSellingItem()

        auctionHouse = new AuctionHouse(SNIPER_ID, SNIPER_PASSWORD, XMPP_HOSTNAME)
        auctionHouse.connect()
        auction = this.auctionHouse.auctionFor(item)
    }

    def "receives events from auction server after joining"() {

        given:
        CountDownLatch latch = new CountDownLatch(1)

        auction.addEventListener(new AuctionEventListener(latch))

        auction.join()

        server.hasReceivedJoinRequestFromSniper(SNIPER_XMPP_ID)

        when:
        server.announceClosed()

        then:
        assertTrue(latch.await(2, SECONDS))
    }

    def cleanup() {
        server.stop()
        auctionHouse.stop()
    }

    private static class AuctionEventListener implements org.harshdev.goosbook.auctionsniper.AuctionEventListener {
        private final CountDownLatch latch

        AuctionEventListener(CountDownLatch latch) {
            this.latch = latch
        }

        @Override
        void auctionClosed() {
            latch.countDown()
        }

        @Override
        void currentPrice(int price, int increment, PriceSource priceSource) {
            throw new UnsupportedOperationException("currentPrice not supported")
        }
    }
}
