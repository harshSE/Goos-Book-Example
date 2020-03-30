package org.harshdev.goosbook

import org.jivesoftware.smack.ConnectionConfiguration
import org.jivesoftware.smack.XMPPConnection
import org.jivesoftware.smack.tcp.XMPPTCPConnection
import org.jivesoftware.smack.tcp.XMPPTCPConnectionConfiguration

public class FakeAuctionServer {
    private String itemId

    public FakeAuctionServer(String itemId) {
        this.itemId = itemId
        XMPPTCPConnectionConfiguration configuration = XMPPTCPConnectionConfiguration.builder()
                .setUsernameAndPassword("")
                .setResource()
                .setXmppDomain()
                .build();
        this.connection = new XMPPTCPConnection(configuration)
    }

    public void startSellingItem() {

    }

    public void hasReceivedJoinRequestFromSniper() {

    }

public void announceClosed() {

    }

    public String getItemId() {
        return itemId
    }
}
