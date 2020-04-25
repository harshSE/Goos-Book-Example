package org.harshdev.goosbook.auctionsniper.ui


import org.harshdev.goosbook.SniperListener
import org.harshdev.goosbook.SniperSnapShot
import org.harshdev.goosbook.SniperState

import javax.swing.table.AbstractTableModel

class SniperTableModel extends AbstractTableModel implements SniperListener{
    private final static Map<SniperState, String> STATUS_TEXT = [(SniperState.JOINING): "Joining",
                                                                 (SniperState.BIDDING): "Bidding",
                                                                 (SniperState.WINNING): "Winning",
                                                                 (SniperState.LOST): "Lost",
                                                                 (SniperState.WON): "Won"]
    private HashMap<String,Integer> itemToSnapShot;
    private List<SniperSnapShot> snapShots

    SniperTableModel() {
        itemToSnapShot = [:]
        snapShots = []
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
        return snapShots.size()
    }

    @Override
    Object getValueAt(int rowIndex, int columnIndex) {
        SniperSnapShot snapShot = snapShots[rowIndex]
        Column column = Column.at(columnIndex);
        column.valueIn(snapShot)
    }

    static String getText(SniperState state) {
        STATUS_TEXT.get(state)
    }

    @Override
    synchronized void sniperStateChanged(SniperSnapShot snapShot) {

        Integer index = itemToSnapShot[snapShot.getItem()]
        if(index == null) {
            addNewRow(snapShot)
        } else {
            updateRow(index, snapShot)
        }
    }

    private void updateRow(int index, SniperSnapShot snapShot) {
        snapShots.set(index, snapShot)
        fireTableRowsUpdated(index, index)
    }

    private void addNewRow(SniperSnapShot snapShot) {
        int index
        snapShots.add(snapShot)
        index = snapShots.size() - 1
        itemToSnapShot[snapShot.getItem()] = index;
        fireTableRowsInserted(index, index)
    }

}
