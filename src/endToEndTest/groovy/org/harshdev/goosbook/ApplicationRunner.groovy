package org.harshdev.goosbook

public class ApplicationRunner {
    private static final String SNIPER_ID = "sniper"
    private static final String SNIPER_PASSWORD = "sniper"
    private AuctionSniperDriver driver;

    public void startBiddingIn(FakeAuctionServer auction) {
        Thread thread =  new Thread("Test Application") {
                @Override
                public void run() {
                    try {
                        Main.main(XMPP_HOSTNAME, SNIPER_ID, SNIPER_PASSWORD, auction.getItemId())
                    } catch(Exception ex) {
                        ex.printStackTrace()
                    }
                }
        };

        thread.setDaemon(true)
        thread.start()
        driver = new AuctionSniperDriver(1000)
        driver.showSniperStatus(STATUS_JOINING)
    }

    public void showSniperHasLostAuction() {
        driver.showSniperStatus(STATUS_CLOSED)
    }

    public void stop(){
        if(Objects.nonNull(driver)) {
            driver.dispose()
        }
    }
}
