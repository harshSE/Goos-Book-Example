package org.harshdev.goosbook.auctionsniper.ui

import org.harshdev.goosbook.auctionsniper.Item
import org.harshdev.goosbook.auctionsniper.Main
import org.harshdev.goosbook.auctionsniper.SniperPortfolio

import javax.swing.*
import java.awt.*
import java.awt.event.ActionEvent
import java.util.concurrent.CopyOnWriteArrayList

class MainWindow extends JFrame{

    static final String APPLICATION_TITLE = "Auction Sniper"
    static final String NEW_ITEM_ID_NAME = "New Auction Item"
    static final String NEW_ITEM_STOP_PRICE = "New Auction Item Stop Price"
    static final String JOIN_BUTTON_NAME = "Join Auction Btn"
    static final String SNIPER_TABLE_NAME = "sniper status"

    private final java.util.List<UserEventListener> userEventListeners


    MainWindow(SniperPortfolio sniperPortfolio) {
        super(APPLICATION_TITLE)
        setName(Main.MAIN_WINDOW_NAME)
        fillContentPane(makeSniperTable(sniperPortfolio), makeControls())
        pack()
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE)
        setFocusable(true)
        setVisible(true)
        toFront()
        setAlwaysOnTop(true)
        this.userEventListeners = [] as CopyOnWriteArrayList
    }

    private JPanel makeControls() {
        JPanel controls = new JPanel(new FlowLayout())
        JTextField itemIdField = createItemTextField()
        controls.add(itemIdField)
        JTextField stopPriceTextField = createStopPriceTextField()
        controls.add(stopPriceTextField)

        JButton joinAuctionButton = new JButton("Join Auction")
        joinAuctionButton.setName(JOIN_BUTTON_NAME)
        controls.add(joinAuctionButton)

        joinAuctionButton.addActionListener((ActionEvent e) -> notifyAll(itemIdField, stopPriceTextField))

        controls
    }

    private java.util.List<UserEventListener> notifyAll(JTextField itemIdField, JTextField stopPriceTextField) {
        for (it in userEventListeners) {
            Item item = new Item(itemIdField.getText(), Integer.parseInt(stopPriceTextField.getText()))
            it.joinAuction(item)
        }
    }

    private JTextField createItemTextField() {
        final JTextField itemIdField = new JTextField()
        itemIdField.setColumns(25)
        itemIdField.setName(NEW_ITEM_ID_NAME)
        itemIdField
    }

    private JTextField createStopPriceTextField() {
        final JTextField itemIdField = new JTextField()
        itemIdField.setColumns(25)
        itemIdField.setName(NEW_ITEM_STOP_PRICE)
        itemIdField
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
