package app;

import ui.components.center.MainCenterPanel;
import ui.components.left.MainLeftPanel;
import ui.components.top.MainTopPanel;

import javax.swing.*;
import java.awt.*;

public class MainApp {
    public static void main(String[] args) {
        JFrame frame = new JFrame("Mixed Layout Demo");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1400, 900);

        MainTopPanel mainTopPanel = new MainTopPanel(new BorderLayout());
        MainLeftPanel mainLeftPanel = new MainLeftPanel(new BorderLayout());
        MainCenterPanel mainCenterPanel = new MainCenterPanel(new BorderLayout());

//        InfoSymbolPanel infoSymbolPanel = new InfoSymbolPanel(new BorderLayout());
//
//        infoSymbolPanel.setPreferredSize(new Dimension(100, 50));
//        infoSymbolPanel.setBackground(Color.RED);
//        TopNavPanel topNavPanel = new TopNavPanel(new BorderLayout());
//        topNavPanel.setPreferredSize(new Dimension(100, 50));
//        topNavPanel.setBackground(Color.CYAN);

//        LeftPanel leftPanel = new LeftPanel();
//
//        mainPanel.add(leftPanel, BorderLayout.CENTER); // leftPanel == rest of space
//        mainPanel.add(rightPanel, BorderLayout.EAST);
//        mainPanel.add(bottomPanel, BorderLayout.SOUTH);

//        mainPanel.add(topNavPanel, BorderLayout.NORTH);
//        mainPanel.add(infoSymbolPanel, BorderLayout.SOUTH);

        frame.add(mainTopPanel, BorderLayout.NORTH);
        frame.add(mainLeftPanel, BorderLayout.WEST);
        frame.add(mainCenterPanel, BorderLayout.CENTER);

        frame.setVisible(true);
//        SwingUtilities.invokeLater(() -> {
//            CryptoTradingPlatformUI ui = new CryptoTradingPlatformUI();
//            ui.setVisible(true);
//        });
    }

}
