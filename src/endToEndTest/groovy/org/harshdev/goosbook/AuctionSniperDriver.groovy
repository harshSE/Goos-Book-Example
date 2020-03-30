package org.harshdev.goosbook

import com.objogate.wl.swing.AWTEventQueueProber
import com.objogate.wl.swing.driver.JFrameDriver
import com.objogate.wl.swing.driver.JLabelDriver
import com.objogate.wl.swing.gesture.GesturePerformer

import javax.swing.JFrame

public class AuctionSniperDriver extends JFrameDriver{

    AuctionSniperDriver(int timeout) {
        super(new GesturePerformer(),
                JFrameDriver.topLevelFrame(
                        name(Main.TOP_WINDOW_NAME),
                        showingOnScreen()),
                new AWTEventQueueProber(timeout, 100))

    }

    void showSniperStatus(SniperStatus status) {
        new JLabelDriver(this, name(Main.SNIPER_STATUS_NAME)).hasText(equalTo(status))
    }
}
