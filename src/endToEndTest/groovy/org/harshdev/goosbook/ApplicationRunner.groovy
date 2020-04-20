package org.harshdev.goosbook

import org.harshdev.goosbook.auctionsniper.ui.MainWindow

class ApplicationRunner {
    private static final String SNIPER_ID = "sniper"
    private static final String SNIPER_PASSWORD = "sniper"
    static final String XMPP_HOSTNAME = "localhost"
    static String SNIPER_XMPP_ID = "sniper@harshdev.com/auction"
    private AuctionSniperDriver driver
    private String item


    void startBiddingIn(FakeAuctionServer auction) {
        this.item = auction.getItemId()

        Thread thread = new Thread("Test Application") {
            @Override
            void run() {
                try {
                    Main.main(ApplicationRunner.this.XMPP_HOSTNAME, SNIPER_ID, SNIPER_PASSWORD, auction.getItemId())
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
        driver.showSniperStatus("Joining")
    }

    void showSniperHasLostAuction() {
        driver.showSniperStatus("Lost")
    }

    void stop() {
        if (Objects.nonNull(driver)) {
            driver.dispose()
        }
        Main.stop()
    }

    @Deprecated
    void hasShownSniperIsBidding() {
        driver.showSniperStatus("Bidding")
    }

    void hasShownSniperIsBidding(int lastPrice, int lastBid) {
        driver.showSniperStatus(item,lastPrice, lastBid, "Bidding")
    }

    @Deprecated
    void hasShownSniperIsWinning() {
        driver.showSniperStatus("Winning")
    }

    void hasShownSniperIsWinning(int winningBid) {
        driver.showSniperStatus(item,winningBid, winningBid,"Winning")
    }

    @Deprecated
    void showsSnipeHasWonTheAuction() {
        driver.showSniperStatus("Won")
    }

    void showsSnipeHasWonTheAuction(int lastPrice) {
        driver.showSniperStatus(item,lastPrice, lastPrice, "Won")
    }
}
