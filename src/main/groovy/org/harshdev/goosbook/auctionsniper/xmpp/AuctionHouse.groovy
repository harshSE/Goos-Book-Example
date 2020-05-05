package org.harshdev.goosbook.auctionsniper.xmpp

import org.harshdev.goosbook.auctionsniper.Announcer
import org.harshdev.goosbook.auctionsniper.AuctionEventListener
import org.jivesoftware.smack.chat2.Chat
import org.jivesoftware.smack.chat2.ChatManager
import org.jivesoftware.smack.chat2.IncomingChatMessageListener
import org.jivesoftware.smack.tcp.XMPPTCPConnection
import org.jivesoftware.smack.tcp.XMPPTCPConnectionConfiguration
import org.jxmpp.jid.impl.JidCreate

import java.nio.file.Paths

import static org.jivesoftware.smack.chat2.ChatManager.getInstanceFor

class AuctionHouse {
    static final String AUCTION_RESOURCE = "auction"
    private XMPPTCPConnection connection
    private final String userName
    private final String password
    private final String hostName

    AuctionHouse(String userName, String password, String hostName) {
        this.hostName = hostName
        this.password = password
        this.userName = userName
    }

    void connect(){
        XMPPTCPConnectionConfiguration configuration = XMPPTCPConnectionConfiguration.builder()
                .setUsernameAndPassword(userName, password)
                .setResource(AUCTION_RESOURCE)
                .setXmppDomain("harshdev.com")
                .setHost(hostName)
                .build()
        connection = new XMPPTCPConnection(configuration)
        connection.connect().login()
    }

    XMPPAuction auctionFor(String item) {
        String jid = createAuctionIdForXmpp(item, connection)

        ChatManager chatManager = getInstanceFor(connection)

        Chat chat = chatManager.chatWith(JidCreate.entityBareFrom(jid))

        Announcer<AuctionEventListener> eventListener = Announcer.to(AuctionEventListener.class)

        XMPPAuction auction = new XMPPAuction(chat, eventListener)

        addMessageListener(chatManager, chat, connection,eventListener.announce())



        auction
    }

    private void addMessageListener(ChatManager chatManager,
                                    Chat chat,
                                    XMPPTCPConnection connection,
                                    AuctionEventListener eventListener) {


        String user = connection.getUser().toString()
        AuctionMessageTranslator translator = new AuctionMessageTranslator(user, LoggingXMPPFailureReporter.create(Paths.get("report.log")))

        //FIXME adding lister for each auction will cause memory leak as message listener never release even though chat ended i.e auction closed.
        IncomingChatMessageListener listener = new XMPPMessageIncomingMessageListener(translator, chat)
        chatManager.addIncomingListener(listener)

        DisconnectEventListener disconnectEventListener = new DisconnectEventListener((IncomingChatMessageListener messageListener) -> chatManager.removeIncomingListener(messageListener), listener)
        translator.addEventListener(disconnectEventListener)
        translator.addEventListener(eventListener)
    }


    private GString createAuctionIdForXmpp(String itemId, XMPPTCPConnection connection) {
        "auction-${itemId}@${connection.getXMPPServiceDomain()}/${AUCTION_RESOURCE}"
    }

    void stop() {
        if(connection.isConnected()) {
            connection.disconnect()
        }
    }
}
