package org.harshdev.goosbook

import org.harshdev.goosbook.Main.SniperStateDisplayer
import spock.lang.PendingFeature
import spock.lang.Specification

import static org.harshdev.goosbook.AuctionEventListener.PriceSource.FromOtherBidder
import static org.harshdev.goosbook.AuctionEventListener.PriceSource.FromSniper

class AuctionSniperSpec extends Specification{

    private SniperListener sniperListener;
    private Auction auction
    private AuctionSniper auctionSniper;

    def setup() {
        sniperListener = Mock()
        auction = Mock()
        auctionSniper = new AuctionSniper(sniperListener, auction)
    }

    def "bid higher by incremented price when new price received from other bidder" () {
        when:
        auctionSniper.currentPrice(100, 7, FromOtherBidder)

        then:
        1* auction.bid(107)
    }

    def "report bidding when new price received from other bidder" () {
        when:
        auctionSniper.currentPrice(100, 7, FromOtherBidder)

        then:
        1* sniperListener.bidding()
    }

    def "report winning when new price received from sniper" () {
        when:
        auctionSniper.currentPrice(100, 7, FromSniper)

        then:
        1* sniperListener.winning()
    }


    def "report loss if auction close immediately"() {
        when:

        auctionSniper.auctionClosed()

        then:
        1* sniperListener.sniperLost()

    }

    def "report loss if auction close while bidding"() {
        given:
        SniperState sniperState = Spy(new SniperState())
        auctionSniper = new AuctionSniper(sniperState, auction)

        when:

        auctionSniper.currentPrice(100, 10, AuctionEventListener.PriceSource.FromOtherBidder)

        auctionSniper.auctionClosed()

        then:
        sniperState.state == SniperState.State.BIDDING

        then:
        1* sniperState.sniperLost()


    }

    def "report won if auction closes when winning"() {
        given:
        SniperState sniperState = Spy(new SniperState())
        auctionSniper = new AuctionSniper(sniperState, auction)

        when:

        auctionSniper.currentPrice(100, 10, AuctionEventListener.PriceSource.FromSniper)

        auctionSniper.auctionClosed()

        then:
        sniperState.state == SniperState.State.WINNING

        then:
        1* sniperState.sniperWon()
    }


    static class  SniperState implements SniperListener {

        static enum State {
            WINNING,
            BIDDING;
        }

        State state;

        @Override
        void sniperLost() {

        }

        @Override
        void bidding() {
            this.state = State.BIDDING
        }

        @Override
        void winning() {
            this.state = State.WINNING
        }

        @Override
        void sniperWon() {

        }
    }
}
