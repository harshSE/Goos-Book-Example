package org.harshdev.goosbook.auctionsniper.ui

import org.harshdev.goosbook.SniperSnapShot
import org.harshdev.goosbook.SniperState
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

    def "set sniper values in column"() {
        when:
        model.sniperStateChanged(new SniperSnapShot(item,100, 110, SniperState.BIDDING))

        then:
        1* listener.tableChanged(_)
        then:
        model.getValueAt(0, Column.ITEM.ordinal()) == item
        model.getValueAt(0, Column.LAST_PRICE.ordinal()) == 100
        model.getValueAt(0, Column.LAST_BID.ordinal()) == 110
        model.getValueAt(0, Column.SNIPER_STATE.ordinal()) == "Bidding"

    }

    def "setsUpColumnHeadings" () {
        expect:
        for (Column column: Column.values()) {
            column.name ==  model.getColumnName(column.ordinal())
        }
    }
}
