package org.harshdev.goosbook.auctionsniper.ui

import com.objogate.wl.swing.probe.ValueMatcherProbe
import org.harshdev.goosbook.AuctionSniperDriver
import org.harshdev.goosbook.auctionsniper.Item
import org.harshdev.goosbook.auctionsniper.SniperPortfolio
import spock.lang.Specification

import static org.hamcrest.Matchers.equalTo

class MainWindowSpec extends Specification{

    private MainWindow mainWindow
    private SniperPortfolio portfolio
    private AuctionSniperDriver driver;


    def setup(){
        portfolio = Mock()
        mainWindow = new MainWindow(portfolio)
        driver = new AuctionSniperDriver(1000)

    }

    def "make user request when join button click"() {
        given:
        ValueMatcherProbe<String> buttonProb =
                new ValueMatcherProbe<>(equalTo("item-test"), "Join request")


        ValueMatcherProbe<String> priceProb =
                new ValueMatcherProbe<>(equalTo(String.valueOf(1100)), "Join request without stop price")

        mainWindow.addUserEventListener([
                joinAuction: { Item item ->
                    buttonProb.setReceivedValue(item.getItemId())
                    priceProb.setReceivedValue(String.valueOf(item.getStopPrice()))
                }
        ] as UserEventListener)

        when:
        driver.statBiddingIn("item-test", 1100)

        then:
        driver.check(priceProb)
        driver.check(buttonProb)
    }

    def "make user request when join button click and stop price is empty"() {
        given:
        ValueMatcherProbe<String> buttonProb =
                new ValueMatcherProbe<>(equalTo("item-test"), "Join request without stop price")

        ValueMatcherProbe<String> priceProb =
                new ValueMatcherProbe<>(equalTo(String.valueOf(Integer.MAX_VALUE)), "Join request without stop price")

        mainWindow.addUserEventListener([
                joinAuction: { Item item ->
                    buttonProb.setReceivedValue(item.getItemId())
                    priceProb.setReceivedValue(String.valueOf(item.getStopPrice()))
                }
        ] as UserEventListener)

        when:
        driver.statBiddingIn("item-test")


        then:
        driver.check(priceProb)
        driver.check(buttonProb)

    }

    def cleanup() {
        driver.dispose()
        mainWindow.dispose()
    }


}
