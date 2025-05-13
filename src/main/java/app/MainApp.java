package app;

import chart.ChartHosting;
import com.formdev.flatlaf.FlatLightLaf;
import services.BinanceStreaming;
import ui.components.center.MainCenterPanel;
import ui.components.left.MainLeftPanel;
import ui.components.top.MainTopPanel;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;

public class MainApp {
    public static void main(String[] args) {
        FlatLightLaf.setup();

        ChartHosting chart = new ChartHosting();
        try {
            chart.init();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        BinanceStreaming.getInstance().connect();

        JFrame frame = new JFrame("Mixed Layout Demo");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1400, 900);

        MainTopPanel mainTopPanel = new MainTopPanel(new BorderLayout());
        MainLeftPanel mainLeftPanel = new MainLeftPanel(new BorderLayout());
        MainCenterPanel mainCenterPanel = new MainCenterPanel(new BorderLayout());

        frame.add(mainTopPanel, BorderLayout.NORTH);
        frame.add(mainLeftPanel, BorderLayout.WEST);
        frame.add(mainCenterPanel, BorderLayout.CENTER);

        frame.setVisible(true);

    }

}
