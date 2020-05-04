package org.harshdev.goosbook.auctionsniper

import spock.lang.Specification

import static org.harshdev.goosbook.auctionsniper.AuctionEventListener.PriceSource.FromOtherBidder
import static org.harshdev.goosbook.auctionsniper.AuctionEventListener.PriceSource.FromSniper
import static org.harshdev.goosbook.auctionsniper.SniperState.*

class AuctionSniperSpec extends Specification {

    private SniperListener sniperListener;
    private Auction auction
    private AuctionSniper auctionSniper;
    private String item = "test-item"

    def setup() {
        sniperListener = Mock()
        auction = Mock()

        auctionSniper = new AuctionSniper(new Item(item, Integer.MAX_VALUE), auction)
        auctionSniper.addSniperListener(sniperListener)
    }

    def "bid higher by incremented price when new price received from other bidder"() {
        when:
        auctionSniper.currentPrice(100, 7, FromOtherBidder)

        then:
        1 * auction.bid(107)
    }

    def "report bidding when new price received from other bidder"() {
        def lastPrice = 100
        def increment = 7
        def lastBid = 107
        when:
        auctionSniper.currentPrice(lastPrice, increment, FromOtherBidder)

        then:
        1 * sniperListener.sniperStateChanged(new SniperSnapShot(item, lastPrice, lastBid, BIDDING))
    }

    def "report winning when new price received from sniper"() {
        when:
        auctionSniper.currentPrice(100, 7, FromSniper)

        then:
        1 * sniperListener.sniperStateChanged(new SniperSnapShot(item, 100, 100, WINNING))
    }


    def "report loss if auction close immediately"() {
        when:
        auctionSniper.auctionClosed()

        then:
        1 * sniperListener.sniperStateChanged(new SniperSnapShot(item, 0, 0, SniperState.LOST))

    }

    def "report loss if auction close while bidding"() {
        when:

        auctionSniper.currentPrice(100, 10, FromOtherBidder)

        auctionSniper.auctionClosed()

        then:
        1 * sniperListener.sniperStateChanged(new SniperSnapShot(item, 100, 110, BIDDING))

        then:
        1 * sniperListener.sniperStateChanged(new SniperSnapShot(item, 100, 110, SniperState.LOST))
    }

    def "report won if auction closes when winning"() {
        when:
        auctionSniper.currentPrice(100, 10, AuctionEventListener.PriceSource.FromSniper)

        auctionSniper.auctionClosed()

        then:
        1 * sniperListener.sniperStateChanged(new SniperSnapShot(item, 100, 100, WINNING))
        then:
        1 * sniperListener.sniperStateChanged(new SniperSnapShot(item, 100, 100, SniperState.WON))
    }

    def "report losing and stop further bid when new price is higher than stop price"() {
        given:
        auctionSniper = new AuctionSniper(new Item(item, 110), auction)
        auctionSniper.addSniperListener(sniperListener)
        when:

        auctionSniper.currentPrice(100, 10, FromOtherBidder)

        auctionSniper.currentPrice(111, 10, FromOtherBidder)

        then:
        1 * sniperListener.sniperStateChanged(new SniperSnapShot(item, 100, 110, BIDDING))
        1 * auction.bid(110)

        then:
        1 * sniperListener.sniperStateChanged(new SniperSnapShot(item, 111, 110, LOSING))
        0 * auction.bid(_)

    }
}
