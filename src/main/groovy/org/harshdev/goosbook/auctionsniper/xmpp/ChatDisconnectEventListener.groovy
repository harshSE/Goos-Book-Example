package org.harshdev.goosbook.auctionsniper.xmpp

import org.jivesoftware.smack.chat2.IncomingChatMessageListener

interface ChatDisconnectEventListener {
    public void disconnect(IncomingChatMessageListener listener)
}