package org.harshdev.goosbook.auctionsniper.xmpp


import org.jivesoftware.smack.chat2.Chat
import org.jivesoftware.smack.chat2.IncomingChatMessageListener
import org.jivesoftware.smack.packet.Message
import org.jxmpp.jid.EntityBareJid;

class XMPPMessageIncomingMessageListener implements IncomingChatMessageListener{

        private AuctionMessageTranslator translator
        private Chat chat

        XMPPMessageIncomingMessageListener(AuctionMessageTranslator translator, Chat chat) {
            this.translator = translator
            this.chat = chat
        }

        @Override
        void newIncomingMessage(EntityBareJid from, Message message, Chat chat) {
            if(this.chat != chat) {
                return
            }

            this.translator.processMessage(message)
        }
    }