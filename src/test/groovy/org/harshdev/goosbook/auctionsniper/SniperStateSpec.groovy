package org.harshdev.goosbook.auctionsniper

import org.harshdev.goosbook.auctionsniper.SniperState
import spock.lang.Specification
import spock.lang.Unroll

class SniperStateSpec extends Specification {

    @Unroll
    def 'sniper #tostate when closed in #fromstate state'() {
        expect:
        fromstate.whenAuctionClosed() == tostate

        where:
        fromstate || tostate
        SniperState.JOINING  || SniperState.LOST
        SniperState.BIDDING  || SniperState.LOST
        SniperState.LOSING   || SniperState.LOST
        SniperState.WINNING  || SniperState.WON
        SniperState.FAILED   || SniperState.FAILED
    }
}
