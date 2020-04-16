package org.harshdev.goosbook

class ApplicationRunner {
    private static final String SNIPER_ID = "sniper"
    private static final String SNIPER_PASSWORD = "sniper"
    public static final String XMPP_HOSTNAME = "localhost"
    private AuctionSniperDriver driver



    void startBiddingIn(FakeAuctionServer auction) {

        Thread thread = new Thread("Test Application") {
            @Override
            public void run() {
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
}
