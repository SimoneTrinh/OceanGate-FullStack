package org.example;

import chart.ChartHosting;
import services.BinanceStreaming;
import services.DBManager;
import ui.components.center.MainCenterPanel;
import ui.components.left.MainLeftPanel;
import ui.components.top.MainTopPanel;
import utils.Constants;
import utils.LocalStorage;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;


public class Main extends JFrame {
    public Main() {
        setTitle("Crypto Trading App");
        setSize(1200, 800);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setTitle("Crypto Trading App");
        setSize(1200, 800);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
//        FlatLightLaf.setup();

        DBManager.connect();
        Constants.LOCAL_STORAGE.put(LocalStorage.USER_ID, "1");
        ChartHosting chart = new ChartHosting();
        try {
            chart.init();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        BinanceStreaming.getInstance().connect();
        JPanel tradingPanel = createTradingPanel();
        add(tradingPanel);
    }

    private JPanel createTradingPanel() {
        JPanel mainTradingFrame = new JPanel(new BorderLayout());

        MainTopPanel mainTopPanel = new MainTopPanel(new BorderLayout(), e -> System.out.println("Hehe"));
        MainLeftPanel mainLeftPanel = new MainLeftPanel(new BorderLayout());
        MainCenterPanel mainCenterPanel = new MainCenterPanel(new BorderLayout());


        mainTradingFrame.add(mainTopPanel, BorderLayout.NORTH);
        mainTradingFrame.add(mainLeftPanel, BorderLayout.WEST);
        mainTradingFrame.add(mainCenterPanel, BorderLayout.CENTER);

        return mainTradingFrame;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new Main().setVisible(true); // ẩn frame cho đến khi login thành công
        });
    }
}
