package org.harshdev.goosbook

import spock.lang.Specification

class SniperStateSpec extends Specification {

    def "sniper lost when closed in joining state"() {
        expect:
        SniperState.JOINING.whenAuctionClosed() == SniperState.LOST
    }

    def "sniper lost when closed in bidding state"() {
        expect:
        SniperState.BIDDING.whenAuctionClosed() == SniperState.LOST
    }

    def "sniper won when closed in winning state"() {
        expect:
        SniperState.WINNING.whenAuctionClosed() == SniperState.WON
    }
}
