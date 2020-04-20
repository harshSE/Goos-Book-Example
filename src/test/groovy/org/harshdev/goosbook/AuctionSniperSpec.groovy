package org.harshdev.goosbook


import spock.lang.Specification

import static org.harshdev.goosbook.AuctionEventListener.PriceSource.FromOtherBidder
import static org.harshdev.goosbook.AuctionEventListener.PriceSource.FromSniper
import static org.harshdev.goosbook.SniperState.BIDDING
import static org.harshdev.goosbook.SniperState.WINNING

class AuctionSniperSpec extends Specification{

    private SniperListener sniperListener;
    private Auction auction
    private AuctionSniper auctionSniper;
    private String item = "test-item"

    def setup() {
        sniperListener = Mock()
        auction = Mock()

        auctionSniper = new AuctionSniper(item,sniperListener, auction)
    }

    def "bid higher by incremented price when new price received from other bidder" () {
        when:
        auctionSniper.currentPrice(100, 7, FromOtherBidder)

        then:
        1* auction.bid(107)
    }

    def "report bidding when new price received from other bidder" () {
        def lastPrice = 100
        def increment = 7
        def lastBid = 107
        when:
        auctionSniper.currentPrice(lastPrice, increment, FromOtherBidder)

        then:
        1* sniperListener.sniperStateChanged(new SniperSnapShot(item, lastPrice, lastBid, BIDDING))
    }

    def "report winning when new price received from sniper" () {
        when:
        auctionSniper.currentPrice(100, 7, FromSniper)

        then:
        1* sniperListener.sniperStateChanged(new SniperSnapShot(item, 100, 100, WINNING))
    }


    def "report loss if auction close immediately"() {
        when:
        auctionSniper.auctionClosed()

        then:
        1* sniperListener.sniperStateChanged(new SniperSnapShot(item, 0, 0, SniperState.LOST))

    }

    def "report loss if auction close while bidding"() {
        when:

        auctionSniper.currentPrice(100, 10, AuctionEventListener.PriceSource.FromOtherBidder)

        auctionSniper.auctionClosed()

        then:
        1* sniperListener.sniperStateChanged(new SniperSnapShot(item, 100, 110, BIDDING))
        1* sniperListener.sniperStateChanged(new SniperSnapShot(item, 100, 110, SniperState.LOST))
    }

    def "report won if auction closes when winning"() {
        when:
        auctionSniper.currentPrice(100, 10, AuctionEventListener.PriceSource.FromSniper)

        auctionSniper.auctionClosed()

        then:
        1* sniperListener.sniperStateChanged(new SniperSnapShot(item, 100, 100, WINNING))
        1* sniperListener.sniperStateChanged(new SniperSnapShot(item, 100, 100, SniperState.WON))
    }
}
