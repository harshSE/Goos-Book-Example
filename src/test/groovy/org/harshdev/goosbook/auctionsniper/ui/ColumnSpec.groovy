package org.harshdev.goosbook.auctionsniper.ui

import org.harshdev.goosbook.auctionsniper.SniperSnapShot
import org.harshdev.goosbook.auctionsniper.SniperState
import spock.lang.Specification

class ColumnSpec extends Specification {



    def "#column column map to #field of snapshot"() {
        given:
        SniperSnapShot snapShot = new SniperSnapShot("test-item", 100,10, SniperState.BIDDING)

        when:
        Object val = column.valueIn(snapShot)

        then:

        val == value

        where:
        column              | field         || value
        Column.ITEM         | "Item"              || "test-item"
        Column.LAST_PRICE   | "Last price"  || 100
        Column.LAST_BID     | "Last price"  || 10
        Column.SNIPER_STATE | "Last price"  || "Bidding"

    }



}
