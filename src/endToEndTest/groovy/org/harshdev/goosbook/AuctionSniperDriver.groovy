package org.harshdev.goosbook

import com.objogate.wl.swing.AWTEventQueueProber
import com.objogate.wl.swing.driver.ComponentDriver
import com.objogate.wl.swing.driver.JFrameDriver
import com.objogate.wl.swing.driver.JLabelDriver
import com.objogate.wl.swing.gesture.GesturePerformer

import static org.hamcrest.Matchers.equalTo

class AuctionSniperDriver extends JFrameDriver{

    AuctionSniperDriver(int timeout) {
        super(new GesturePerformer(),
                JFrameDriver.topLevelFrame(
                        ComponentDriver.named(Main.MAIN_WINDOW_NAME),
                        showingOnScreen()),
                new AWTEventQueueProber(timeout, 100))
    }

    void showSniperStatus(SniperStatus status) {
        new JLabelDriver(this, named(Main.SNIPER_STATUS_NAME)).hasText(equalTo(status))
    }
}
