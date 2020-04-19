package org.harshdev.goosbook

import spock.lang.Specification

class AuctionSniperSpec extends Specification{

    private SniperListener sniperListener;
    private Auction auction
    private AuctionSniper auctionSniper;

    def setup() {
        sniperListener = Mock()
        auction = Mock()
        auctionSniper = new AuctionSniper(sniperListener, auction)
    }

    def "report loss when auction closes" () {
        when:
        auctionSniper.auctionClosed()

        then:
        1* sniperListener.sniperLost()
    }

    def "bid higher by incremented price when new price received" () {
        when:
        auctionSniper.currentPrice(100, 7)

        then:
        1* auction.bid(107)
    }

    def "report bidding when new price received" () {
        when:
        auctionSniper.currentPrice(100, 7)

        then:
        1* sniperListener.bidding()
    }
}
