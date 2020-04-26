package org.harshdev.goosbook.auctionsniper.ui

import org.harshdev.goosbook.auctionsniper.Main
import org.harshdev.goosbook.auctionsniper.SniperPortfolio

import javax.swing.*
import java.awt.*
import java.awt.event.ActionEvent
import java.util.concurrent.CopyOnWriteArrayList

class MainWindow extends JFrame{

    static final String APPLICATION_TITLE = "Auction Sniper"
    static final String NEW_ITEM_ID_NAME = "New Auction Item"
    static final String JOIN_BUTTON_NAME = "Join Auction Btn"
    static final String SNIPER_TABLE_NAME = "sniper status"

    private final java.util.List<UserEventListener> userEventListeners


    MainWindow(SniperPortfolio sniperPortfolio) {
        super(APPLICATION_TITLE)
        setName(Main.MAIN_WINDOW_NAME)
        fillContentPane(makeSniperTable(sniperPortfolio), makeControls())
        pack()
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE)
        setVisible(true)
        this.userEventListeners = [] as CopyOnWriteArrayList
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

        joinAuctionButton.addActionListener((ActionEvent e) -> userEventListeners.each {it.joinAuction(itemIdField.getText())})
        controls
    }

    void addUserEventListener(UserEventListener listener) {
        userEventListeners << listener
    }



    private JTable makeSniperTable(SniperPortfolio portfolio) {
        SniperTableModel model = new SniperTableModel()
        portfolio.addPortfolioListener(model)
        final JTable table = new JTable(model)
        table.setName(SNIPER_TABLE_NAME)
        table
    }

    private void fillContentPane(JTable sniperTable, JPanel panel) {
        Container pane = getContentPane()
        pane.setLayout(new BorderLayout())
        pane.add(panel, BorderLayout.PAGE_START)
       pane.add(new JScrollPane(sniperTable), BorderLayout.CENTER)
    }

}
