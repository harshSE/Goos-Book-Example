package org.harshdev.goosbook


import org.jivesoftware.smack.chat2.Chat
import org.jivesoftware.smack.chat2.ChatManager
import org.jivesoftware.smack.chat2.IncomingChatMessageListener
import org.jivesoftware.smack.packet.Message
import org.jivesoftware.smack.tcp.XMPPTCPConnection
import org.jivesoftware.smack.tcp.XMPPTCPConnectionConfiguration
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.function.Executable
import org.jxmpp.jid.EntityBareJid

import static org.harshdev.goosbook.api.MessageAssert.assertThat
import static org.jivesoftware.smack.chat2.ChatManager.getInstanceFor
import static org.junit.jupiter.api.Assertions.assertNotNull

class FakeAuctionServer {
    private static final String AUCTION_RESOURCE = "auction"
    private static final String AUCTION_PASSWORD = "auction"
    private static final String ITEM_ID_AS_LOGIN = "auction-%s"
    private static final String XMPP_HOSTNAME = "localhost"
    private final MessageListener singleMessageListener = new MessageListener();
    private String itemId
    private XMPPTCPConnection connection
    private Chat currentChat;

    FakeAuctionServer(String itemId) {
        this.itemId = itemId
        XMPPTCPConnectionConfiguration configuration = XMPPTCPConnectionConfiguration.builder()
                .setUsernameAndPassword(String.format(ITEM_ID_AS_LOGIN, itemId), AUCTION_PASSWORD)
                .setResource(AUCTION_RESOURCE)
                .setXmppDomain("harshdev.com")
                .setHost(XMPP_HOSTNAME)
                .build();
        this.connection = new XMPPTCPConnection(configuration)
    }

    void startSellingItem() {

        connection.connect().login()

        ChatManager chatManager = getInstanceFor(connection);

        chatManager.addIncomingListener(new IncomingChatMessageListener() {
            @Override
            void newIncomingMessage(EntityBareJid from, Message message, Chat chat) {
                currentChat = chat
                singleMessageListener.processMessage(message, chat)
            }
        })

    }

    void hasReceivedJoinRequestFromSniper(String bidder) {
        Message message = singleMessageListener.message()
        assertNotNull(message, "Message not received")
        Assertions.assertAll(
                { assertThat(message).from(bidder) } as Executable,
                { assertThat(message).command("JOIN") } as Executable,
        )
    }

    void announceClosed() {
        currentChat.send("SOLVersion: 1.1; Event: CLOSE;")
    }

    String getItemId() {
        return itemId
    }

    void stop() {
        if(connection.isConnected()) {
            connection.disconnect()
        }
    }

    void reportPrice(int biddingPrice, int increment, String currentBidder) {
        String message = "SOLVersion: 1.1; Event: PRICE; CurrentPrice: ${biddingPrice}; Increment: ${increment}; Bidder: ${currentBidder};"
        currentChat.send(message)
    }

    void hasReceivedBid(int biddingPrice, String bidder) {
        Message message = singleMessageListener.message()
        Assertions.assertAll(
                { assertThat(message).from(bidder) } as Executable,
                { assertThat(message).command("BID") } as Executable,
                { assertThat(message).price(biddingPrice) } as Executable,
        )
    }

    XMPPTCPConnection getConnection() {
        connection
    }
}
