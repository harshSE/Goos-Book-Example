package org.harshdev.goosbook

import org.jivesoftware.smack.AbstractXMPPConnection
import org.jivesoftware.smack.chat2.Chat
import org.jivesoftware.smack.chat2.IncomingChatMessageListener
import org.jivesoftware.smack.packet.Message
import org.jivesoftware.smack.tcp.XMPPTCPConnection
import org.jivesoftware.smack.tcp.XMPPTCPConnectionConfiguration
import org.jxmpp.jid.EntityBareJid
import org.jxmpp.jid.impl.JidCreate

import static org.jivesoftware.smack.chat2.ChatManager.getInstanceFor

class FakeAuctionServer {
    private static final String AUCTION_RESOURCE = "auction"
    private static final String AUCTION_PASSWORD = "auction"
    private static final String ITEM_ID_AS_LOGIN = "auction-%s"
    private final SingleMessageListener singleMessageListener = new SingleMessageListener();
    private String itemId
    private XMPPTCPConnection connection
    private Chat currentChat;

    FakeAuctionServer(String itemId) {
        this.itemId = itemId
        XMPPTCPConnectionConfiguration configuration = XMPPTCPConnectionConfiguration.builder()
                .setUsernameAndPassword(String.format(ITEM_ID_AS_LOGIN, itemId), AUCTION_PASSWORD)
                .setResource(AUCTION_RESOURCE)
                .setXmppDomain("harshdev.com")
                .setHost("goosbook.harshdev.com")
                .setHost(ApplicationRunner.XMPP_HOSTNAME)
                .build();
        this.connection = new XMPPTCPConnection(configuration)
    }

    void startSellingItem() {
        this.connection.connect().login()
        def chatManager = getInstanceFor(connection);
        chatManager.addIncomingListener(new IncomingChatMessageListener() {
            @Override
            void newIncomingMessage(EntityBareJid from, Message message, Chat chat) {
                currentChat = chat;
                singleMessageListener.processMessage(message, chat);
            }
        })

    }

    void hasReceivedJoinRequestFromSniper() {
        singleMessageListener.receiveAMessage()
    }

    void announceClosed() {
        currentChat.send(new Message())
    }

    String getItemId() {
        return itemId
    }

    void stop() {
        if(connection.isConnected()) {
            connection.disconnect()
        }
    }
}
