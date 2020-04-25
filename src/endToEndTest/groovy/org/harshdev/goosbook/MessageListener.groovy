package org.harshdev.goosbook


import org.jivesoftware.smack.chat2.Chat
import org.jivesoftware.smack.packet.Message

import java.util.concurrent.ArrayBlockingQueue
import java.util.concurrent.TimeUnit

class MessageListener {

    private ArrayBlockingQueue<Message> messages = new ArrayBlockingQueue<>(1);

    void processMessage(Message message, Chat chat) {
        messages << message
    }


    Message message() {
        def message = messages.poll(5, TimeUnit.SECONDS)
        message
    }
}
