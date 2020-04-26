package org.harshdev.goosbook.auctionsniper.ui

import com.objogate.wl.swing.probe.ValueMatcherProbe
import org.harshdev.goosbook.AuctionSniperDriver
import org.harshdev.goosbook.auctionsniper.SniperPortfolio
import spock.lang.Specification

import static org.hamcrest.Matchers.equalTo

class MainWindowSpec extends Specification{

    private MainWindow mainWindow
    private SniperPortfolio portfolio
    private AuctionSniperDriver driver;


    def setup(){
        portfolio = Mock()
        driver = new AuctionSniperDriver(1000)

    }

    def "make user request when join button click"() {
        given:
        ValueMatcherProbe<String> buttonProb =
                new ValueMatcherProbe<>(equalTo("item-test"), "Join request")

        mainWindow = new MainWindow(portfolio)

        mainWindow.addUserEventListener((String item) -> buttonProb.setReceivedValue(item))

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
