package org.harshdev.goosbook.auctionsniper.ui


import org.harshdev.goosbook.auctionsniper.AuctionSniper
import org.harshdev.goosbook.auctionsniper.SniperSnapShot
import org.harshdev.goosbook.auctionsniper.SniperState
import spock.lang.Specification

import javax.swing.event.TableModelListener

class SniperTableModelSpec extends Specification {

    private SniperTableModel model = new SniperTableModel()
    private TableModelListener listener
    private String item = "test-item"

    def setup() {
        listener = Mock()
        model.addTableModelListener(listener)
    }

    def "has enough column "() {
        expect:
        model.getColumnCount() == 4
    }

    def "has no of rows equals to snapshot"() {

        when:
        model.sniperStateChanged(SniperSnapShot.joining("item1"))
        model.sniperStateChanged(SniperSnapShot.joining("item2"))
        model.sniperStateChanged(SniperSnapShot.joining("item3"))

        then:
        model.getRowCount() == 3

    }

    def "set sniper values for row in column"() {
        when:
        model.sniperStateChanged(new SniperSnapShot("item-1",100, 110, SniperState.BIDDING))
        model.sniperStateChanged(new SniperSnapShot("item-2",1, 10, SniperState.JOINING))
        model.sniperStateChanged(new SniperSnapShot("item-3",500, 50, SniperState.WINNING))

        then:
        3* listener.tableChanged(_)
        then:
        verifyAll {
            model.getValueAt(0, Column.ITEM.ordinal()) == "item-1"
            model.getValueAt(0, Column.LAST_PRICE.ordinal()) == 100
            model.getValueAt(0, Column.LAST_BID.ordinal()) == 110
            model.getValueAt(0, Column.SNIPER_STATE.ordinal()) == "Bidding"

            model.getValueAt(1, Column.ITEM.ordinal()) == "item-2"
            model.getValueAt(1, Column.LAST_PRICE.ordinal()) == 1
            model.getValueAt(1, Column.LAST_BID.ordinal()) == 10
            model.getValueAt(1, Column.SNIPER_STATE.ordinal()) == "Joining"

            model.getValueAt(2, Column.ITEM.ordinal()) == "item-3"
            model.getValueAt(2, Column.LAST_PRICE.ordinal()) == 500
            model.getValueAt(2, Column.LAST_BID.ordinal()) == 50
            model.getValueAt(2, Column.SNIPER_STATE.ordinal()) == "Winning"
        }
    }

    def "update sniper values on status change"() {
        when:
        model.sniperStateChanged(new SniperSnapShot("item-1",100, 110, SniperState.BIDDING))
        model.sniperStateChanged(new SniperSnapShot("item-2",1, 10, SniperState.JOINING))
        model.sniperStateChanged(new SniperSnapShot("item-3",500, 50, SniperState.WINNING))

        model.sniperStateChanged(new SniperSnapShot("item-2",11, 11, SniperState.LOST))
        model.sniperStateChanged(new SniperSnapShot("item-3",5000, 500, SniperState.WON))

        then:
        5* listener.tableChanged(_)
        then:
        verifyAll {
            model.getValueAt(0, Column.ITEM.ordinal()) == "item-1"
            model.getValueAt(0, Column.LAST_PRICE.ordinal()) == 100
            model.getValueAt(0, Column.LAST_BID.ordinal()) == 110
            model.getValueAt(0, Column.SNIPER_STATE.ordinal()) == "Bidding"

            model.getValueAt(1, Column.ITEM.ordinal()) == "item-2"
            model.getValueAt(1, Column.LAST_PRICE.ordinal()) == 11
            model.getValueAt(1, Column.LAST_BID.ordinal()) == 11
            model.getValueAt(1, Column.SNIPER_STATE.ordinal()) == "Lost"

            model.getValueAt(2, Column.ITEM.ordinal()) == "item-3"
            model.getValueAt(2, Column.LAST_PRICE.ordinal()) == 5000
            model.getValueAt(2, Column.LAST_BID.ordinal()) == 500
            model.getValueAt(2, Column.SNIPER_STATE.ordinal()) == "Won"
        }
    }

    def "add sniper value when auction sniper added" () {
        given:
        SniperSnapShot joining = SniperSnapShot.joining("test-item")
        AuctionSniper sniper = Mock()
        sniper.getSnapShot() >> joining

        when:
        model.addSniper(sniper)

        then:
        model.size() == 1
        model.getValueAt(0, Column.ITEM.ordinal()) == "test-item"
        model.getValueAt(0, Column.LAST_PRICE.ordinal()) == 0
        model.getValueAt(0, Column.LAST_BID.ordinal()) == 0
        model.getValueAt(0, Column.SNIPER_STATE.ordinal()) == "Joining"
    }

    def "setsUpColumnHeadings" () {
        expect:
        for (Column column: Column.values()) {
            column.name ==  model.getColumnName(column.ordinal())
        }
    }
}
