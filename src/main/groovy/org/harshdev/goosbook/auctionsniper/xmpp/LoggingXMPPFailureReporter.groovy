package org.harshdev.goosbook.auctionsniper.xmpp

import java.nio.file.Path

class LoggingXMPPFailureReporter {

    private final PrintWriter writer;

    LoggingXMPPFailureReporter(PrintWriter writer) {
        this.writer = writer
    }

    static LoggingXMPPFailureReporter create(Path path){
        PrintWriter writer = new PrintWriter(new FileWriter(path.toString()))
        new LoggingXMPPFailureReporter(writer)
    }

    void canNotTranslateMessage(String auctionId, String message, Throwable exception) {
        writer.println("<$auctionId> could not translate message: $message because ${exception.getMessage()}")
        writer.flush()
    }
}
