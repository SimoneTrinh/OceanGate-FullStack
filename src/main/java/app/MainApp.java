package app;

import ui.components.InfoSymbol;
import ui.components.TopNav;

import javax.swing.*;
import java.awt.*;

public class MainApp {
    public static void main(String[] args) {
        JFrame frame = new JFrame("Mixed Layout Demo");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1400, 900);


        JPanel mainPanel = new JPanel(new BorderLayout());

        InfoSymbol infoSymbol = new InfoSymbol(new BorderLayout());

        infoSymbol.setPreferredSize(new Dimension(100, 50));
        infoSymbol.setBackground(Color.RED);
        TopNav topNav = new TopNav(new BorderLayout());
        topNav.setPreferredSize(new Dimension(100, 50));
        topNav.setBackground(Color.CYAN);

//        LeftPanel leftPanel = new LeftPanel();
//
//        mainPanel.add(leftPanel, BorderLayout.CENTER); // leftPanel == rest of space
//        mainPanel.add(rightPanel, BorderLayout.EAST);
//        mainPanel.add(bottomPanel, BorderLayout.SOUTH);

        mainPanel.add(topNav, BorderLayout.NORTH);
        mainPanel.add(infoSymbol, BorderLayout.SOUTH);

        frame.add(mainPanel);
        frame.setVisible(true);
//        SwingUtilities.invokeLater(() -> {
//            CryptoTradingPlatformUI ui = new CryptoTradingPlatformUI();
//            ui.setVisible(true);
//        });
    }

}
