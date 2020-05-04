package org.harshdev.goosbook

import com.objogate.wl.swing.AWTEventQueueProber
import com.objogate.wl.swing.driver.*
import com.objogate.wl.swing.gesture.GesturePerformer

import javax.swing.*
import javax.swing.table.JTableHeader

import static com.objogate.wl.swing.matcher.IterableComponentsMatcher.matching
import static com.objogate.wl.swing.matcher.JLabelTextMatcher.withLabelText
import static java.lang.String.valueOf
import static org.hamcrest.Matchers.equalTo

class AuctionSniperDriver extends JFrameDriver {

    private static final String NEW_ITEM_ID_NAME = "New Auction Item"
    private static final String NEW_STOP_PRICE_NAME = "New Auction Item Stop Price"
    private static final String JOIN_BUTTON_NAME = "Join Auction Btn"
    private static final String MAIN_WINDOW_NAME = "Sniper"

    AuctionSniperDriver(int timeout) {
        super(new GesturePerformer(),
                JFrameDriver.topLevelFrame(
                        ComponentDriver.named(MAIN_WINDOW_NAME),
                        showingOnScreen()),
                new AWTEventQueueProber(timeout, 100))

    }

    void showSniperStatus(String item, int lastPrice, int lastBid, String status) {
        new JTableDriver(this).hasRow(matching(
                withLabelText(item),
                withLabelText(valueOf(lastPrice)),
                withLabelText(valueOf(lastBid)),
                withLabelText(equalTo(status))))
    }

    void hasColumnTitles() {
        JTableHeaderDriver headers = new JTableHeaderDriver(this, JTableHeader.class);
        headers.hasHeaders(matching(
                withLabelText("Item"),
                withLabelText("Last Price"),
                withLabelText("Last Bid"),
                withLabelText("State")))

    }

    void statBiddingIn(String item) {
        this.statBiddingIn(item, Integer.MAX_VALUE)
    }

    void statBiddingIn(String item, int price) {
        itemField(NEW_ITEM_ID_NAME).replaceAllText(item)
        itemField(NEW_STOP_PRICE_NAME).replaceAllText(String.valueOf(price))
        bidButton().click()
    }

    private JTextFieldDriver itemField(String fieldName) {
        JTextFieldDriver newItemField = new JTextFieldDriver(this, JTextField.class, named(fieldName))
        newItemField.focusWithMouse()
        newItemField

    }

    private JButtonDriver bidButton() {
        new JButtonDriver(this, JButton.class, named(JOIN_BUTTON_NAME))
    }


}
