package org.harshdev.goosbook.auctionsniper.ui

import org.harshdev.goosbook.Main
import org.harshdev.goosbook.SniperStatus

import javax.swing.JFrame
import javax.swing.JLabel
import javax.swing.border.LineBorder
import java.awt.Color

class MainWindow extends JFrame{
    private static final String SNIPER_STATUS_NAME = "sniper status"
    private static final JLabel sniperStatus = createLabel(SniperStatus.STATUS_JOINING)
    MainWindow(){
        super("Auction Sniper")
        setName(Main.MAIN_WINDOW_NAME)
        add(sniperStatus)
        pack()
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE)
        setVisible(true)
    }


    private static JLabel createLabel(SniperStatus initialTest) {
        JLabel result =  new JLabel(initialTest.name)
        result.setName(SNIPER_STATUS_NAME)
        result.setBorder(new LineBorder(Color.BLACK))
        return result
    }

    void showStatus(SniperStatus status) {
        sniperStatus.setText(status.name);
    }
}
