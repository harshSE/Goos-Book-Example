package org.harshdev.goosbook.auctionsniper.xmpp

import spock.lang.Specification

class LoggingXMPPFailureReporterSpec extends Specification {

    private LoggingXMPPFailureReporter reporter
    private PrintWriter writer

    def setup() {
        writer = Mock()
        reporter = new LoggingXMPPFailureReporter(this.writer)
    }

    def "log server when not able to translate message"() {

        when:
        reporter.canNotTranslateMessage("test", "bad message", new Exception("not able to parse message"))

        then:
        1* writer.println("<test> could not translate message: bad message because not able to parse message")
        1* writer.flush()
    }
}
