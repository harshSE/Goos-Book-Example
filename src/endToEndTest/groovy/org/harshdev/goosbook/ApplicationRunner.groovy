package org.harshdev.goosbook

import org.harshdev.goosbook.auctionsniper.Main
import org.harshdev.goosbook.auctionsniper.ui.MainWindow

class ApplicationRunner {
    private static final String SNIPER_ID = "sniper"
    private static final String SNIPER_PASSWORD = "sniper"
    static final String XMPP_HOSTNAME = "localhost"
    static String SNIPER_XMPP_ID = "sniper@harshdev.com/auction"
    private AuctionSniperDriver driver

    ApplicationRunner() {
        driver = startSniper()
    }

    void startBiddingIn(FakeAuctionServer... auctions) {
        for(auction in auctions) {
            startBiddingInWithStopPrice(auction, Integer.MAX_VALUE)
        }
    }

    private AuctionSniperDriver startSniper() {
        Thread thread = new Thread("Test Application") {
            @Override
            void run() {
                try {
                    Main.main(ApplicationRunner.this.XMPP_HOSTNAME, SNIPER_ID, SNIPER_PASSWORD)
                } catch (Exception ex) {
                    ex.printStackTrace()
                }
            }
        }
        thread.setDaemon(true)
        thread.start()
        driver = new AuctionSniperDriver(1000)
        driver.hasTitle(MainWindow.APPLICATION_TITLE)
        driver.hasColumnTitles()
        driver
    }

    void stop() {
        if (Objects.nonNull(driver)) {
            driver.dispose()
        }
        Main.stop()
    }

    void hasShownSniperIsBidding(FakeAuctionServer auction, int lastPrice, int lastBid) {
        driver.showSniperStatus(auction.getItemId(),lastPrice, lastBid, "Bidding")
    }

    void showSniperHasLostAuction(FakeAuctionServer auction, int lastPrice, int lastBid) {
        driver.showSniperStatus(auction.getItemId(), lastPrice,lastBid,"Lost")
    }

    void hasShownSniperIsWinning(FakeAuctionServer auction, int winningBid) {
        driver.showSniperStatus(auction.getItemId(),winningBid, winningBid,"Winning")
    }

    void showsSnipeHasWonTheAuction(FakeAuctionServer auction, int lastPrice) {
        driver.showSniperStatus(auction.getItemId(),lastPrice, lastPrice, "Won")
    }

    void startBiddingInWithStopPrice(FakeAuctionServer auction, int stopPrice) {
        String item = auction.getItemId()
        Thread.sleep(100)
        driver.statBiddingIn(item, stopPrice)
        driver.showSniperStatus(item, 0,0,"Joining")
    }

    void hasShownSniperIsLosing(FakeAuctionServer auction, int lastPrice, int lastBid) {
        driver.showSniperStatus(auction.getItemId(),lastPrice, lastBid, "Losing")
    }
}
