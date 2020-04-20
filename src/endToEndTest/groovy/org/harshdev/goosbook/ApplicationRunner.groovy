package org.harshdev.goosbook

class ApplicationRunner {
    private static final String SNIPER_ID = "sniper"
    private static final String SNIPER_PASSWORD = "sniper"
    static final String XMPP_HOSTNAME = "localhost"
    static String SNIPER_XMPP_ID = "sniper@harshdev.com/auction"
    private AuctionSniperDriver driver



    void startBiddingIn(FakeAuctionServer auction) {

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
        driver.showSniperStatus(SniperStatus.STATUS_JOINING)
    }

    void showSniperHasLostAuction() {
        driver.showSniperStatus(SniperStatus.STATUS_LOST)
    }

    void stop() {
        if (Objects.nonNull(driver)) {
            driver.dispose()
        }
        Main.stop()
    }

    void hasShownSniperIsBidding() {
        driver.showSniperStatus(SniperStatus.STATUS_BIDDING)
    }

    void hasShownSniperIsWinning() {
        driver.showSniperStatus(SniperStatus.STATUS_WINNING)
    }

    void showsSnipeHasWonTheAuction() {
        driver.showSniperStatus(SniperStatus.STATUS_WIN)
    }
}
