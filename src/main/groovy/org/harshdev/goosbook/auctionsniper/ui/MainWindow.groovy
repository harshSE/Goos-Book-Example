package org.harshdev.goosbook.auctionsniper.ui

import org.harshdev.goosbook.Main
import org.harshdev.goosbook.SniperSnapShot

import javax.swing.*
import java.awt.*
import java.awt.event.ActionEvent
import java.awt.event.ActionListener

class MainWindow extends JFrame{

    static String APPLICATION_TITLE = "Auction Sniper"
    static String NEW_ITEM_ID_NAME = "New Auction Item"
    static String JOIN_BUTTON_NAME = "Join Auction Btn"
    private final SniperTableModel snipers
    private String SNIPER_TABLE_NAME = "sniper status"
    private UserEventListener userEventListener


    MainWindow(SniperTableModel sniperTableModel, UserEventListener userEventListener) {
        super(APPLICATION_TITLE)
        this.snipers = sniperTableModel
        setName(Main.MAIN_WINDOW_NAME)
        fillContentPane(makeSniperTable(sniperTableModel), makeControls())
        pack()
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE)
        setVisible(true)
        this.userEventListener = userEventListener
    }

    private JPanel makeControls() {
        JPanel controls = new JPanel(new FlowLayout())
        final JTextField itemIdField = new JTextField()
        itemIdField.setColumns(25)
        itemIdField.setName(NEW_ITEM_ID_NAME)
        controls.add(itemIdField)

        JButton joinAuctionButton = new JButton("Join Auction")
        joinAuctionButton.setName(JOIN_BUTTON_NAME)
        controls.add(joinAuctionButton)

        joinAuctionButton.addActionListener(new ActionListener() {
            @Override
            void actionPerformed(ActionEvent e) {
                userEventListener.joinAuction(itemIdField.getText())
            }
        })
        controls
    }


    private JTable makeSniperTable(SniperTableModel snipers) {
        final JTable table = new JTable(snipers)
        table.setName(SNIPER_TABLE_NAME)
        table
    }

    private void fillContentPane(JTable sniperTable, JPanel panel) {
        Container pane = getContentPane()
        pane.setLayout(new BorderLayout())
        pane.add(panel, BorderLayout.PAGE_START)
       pane.add(new JScrollPane(sniperTable), BorderLayout.CENTER)
    }

    void sniperStatusChange(SniperSnapShot auctionState) {
        snipers.sniperStateChanged(auctionState)
    }
}
