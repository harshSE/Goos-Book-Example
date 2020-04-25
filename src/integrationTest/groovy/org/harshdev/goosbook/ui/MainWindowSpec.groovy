package org.harshdev.goosbook.ui

import com.objogate.wl.swing.probe.ValueMatcherProbe
import org.hamcrest.Matchers
import org.harshdev.goosbook.AuctionSniperDriver
import org.harshdev.goosbook.auctionsniper.ui.MainWindow
import org.harshdev.goosbook.auctionsniper.ui.SniperTableModel
import org.harshdev.goosbook.auctionsniper.ui.UserEventListener
import spock.lang.Specification

import static org.hamcrest.Matchers.equalTo

class MainWindowSpec extends Specification{

    private MainWindow mainWindow
    private SniperTableModel model
    private AuctionSniperDriver driver;


    def setup(){
        model = Mock()
        driver = new AuctionSniperDriver(1000)
    }

    def "make user request when join button click"() {
        given:
        ValueMatcherProbe<String> buttonProb =
                new ValueMatcherProbe<>(equalTo("item-test"), "Join request")

        mainWindow = new MainWindow(model, new UserEventListener() {
            @Override
            void joinAuction(String item) {
                buttonProb.setReceivedValue(item)
            }
        })
        when:
        driver.statBiddingIn("item-test")

        then:
        driver.check(buttonProb)
    }

    def cleanup() {
        driver.dispose()
        mainWindow.dispose()
    }


}
