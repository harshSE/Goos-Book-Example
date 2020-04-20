package org.harshdev.goosbook.auctionsniper.ui

import org.harshdev.goosbook.SniperSnapShot
import org.harshdev.goosbook.Main
import org.harshdev.goosbook.SniperState

import javax.swing.*
import java.awt.*

class MainWindow extends JFrame{

    static String APPLICATION_TITLE = "Auction Sniper"
    private final SniperTableModel snipers
    private String SNIPER_TABLE_NAME = "sniper status"


    MainWindow(SniperTableModel snipers){
        super(APPLICATION_TITLE)
        this.snipers = snipers
        setName(Main.MAIN_WINDOW_NAME)
        fillContentPane(makeSniperTable())
        pack()
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE)
        setVisible(true)
    }


    private JTable makeSniperTable() {
        final JTable table = new JTable(snipers)
        table.setName(SNIPER_TABLE_NAME)
        table
    }

    private void fillContentPane(JTable sniperTable) {
        Container pane = getContentPane();
        pane.setLayout(new BorderLayout())
        pane.add(new JScrollPane(sniperTable), BorderLayout.CENTER)
    }

    void sniperStatusChange(SniperSnapShot auctionState) {
        snipers.sniperStateChanged(auctionState)
    }
}
