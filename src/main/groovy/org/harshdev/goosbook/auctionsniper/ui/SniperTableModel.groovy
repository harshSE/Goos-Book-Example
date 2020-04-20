package org.harshdev.goosbook.auctionsniper.ui

import org.harshdev.goosbook.SniperListener
import org.harshdev.goosbook.SniperSnapShot
import org.harshdev.goosbook.SniperState

import javax.swing.table.AbstractTableModel

import static org.harshdev.goosbook.SniperState.JOINING

class SniperTableModel extends AbstractTableModel implements SniperListener{
    private final static Map<SniperState, String> STATUS_TEXT = [(SniperState.JOINING): "Joining",
                                                                 (SniperState.BIDDING): "Bidding",
                                                                 (SniperState.WINNING): "Winning",
                                                                 (SniperState.LOST): "Lost",
                                                                 (SniperState.WON): "Won"]
    private SniperSnapShot snapShot;

    SniperTableModel(String item) {
        snapShot = SniperSnapShot.joining(item)
    }

    @Override
    int getColumnCount() {
        return 4
    }

    @Override
    String getColumnName(int column) {
        return Column.at(column).name
    }

    @Override
    int getRowCount() {
        return 1
    }

    @Override
    Object getValueAt(int rowIndex, int columnIndex) {
        Column column = Column.at(columnIndex);
        column.valueIn(snapShot)
    }

    static String getText(SniperState state) {
        STATUS_TEXT.get(state)
    }

    @Override
    void sniperStateChanged(SniperSnapShot snapShot) {
        this.snapShot = snapShot
        fireTableRowsUpdated(0,0)
    }
}
