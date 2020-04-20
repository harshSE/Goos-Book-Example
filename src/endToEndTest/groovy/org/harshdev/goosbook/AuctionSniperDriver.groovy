package org.harshdev.goosbook

import com.objogate.wl.swing.AWTEventQueueProber
import com.objogate.wl.swing.driver.ComponentDriver
import com.objogate.wl.swing.driver.JFrameDriver
import com.objogate.wl.swing.driver.JTableDriver
import com.objogate.wl.swing.driver.JTableHeaderDriver
import com.objogate.wl.swing.gesture.GesturePerformer

import javax.swing.table.JTableHeader

import static com.objogate.wl.swing.matcher.IterableComponentsMatcher.matching
import static com.objogate.wl.swing.matcher.JLabelTextMatcher.withLabelText
import static java.lang.String.valueOf
import static org.hamcrest.Matchers.equalTo

class AuctionSniperDriver extends JFrameDriver{

    AuctionSniperDriver(int timeout) {
        super(new GesturePerformer(),
                JFrameDriver.topLevelFrame(
                        ComponentDriver.named(Main.MAIN_WINDOW_NAME),
                        showingOnScreen()),
                new AWTEventQueueProber(timeout, 100))
    }

    @Deprecated
    void showSniperStatus(String status) {
        new JTableDriver(this).hasCell(withLabelText(equalTo(status)))
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
}
