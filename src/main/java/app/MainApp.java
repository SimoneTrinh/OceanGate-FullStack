package app;

import chart.ChartHosting;
import com.formdev.flatlaf.FlatLightLaf;
import login.AuthDialog;
import user_menu.UserMenu;
import services.BinanceStreaming;
import ui.components.center.MainCenterPanel;
import ui.components.left.MainLeftPanel;
import ui.components.top.MainTopPanel;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.util.concurrent.atomic.AtomicBoolean;

public class MainApp extends JFrame {
    private CardLayout cardLayout;
    private JPanel mainContentPanel;
    public MainApp() {
        // Setup giao diện app
        setTitle("Crypto Trading App");
        setSize(1200, 800);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Setup giao diện Trading
        FlatLightLaf.setup();

        ChartHosting chart = new ChartHosting();
        try {
            chart.init();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        BinanceStreaming.getInstance().connect();

        // CardLayout để chuyển đổi giữa các giao diện
        cardLayout = new CardLayout();
        mainContentPanel = new JPanel(cardLayout);
        add(mainContentPanel, BorderLayout.CENTER);

        // 1. Giao diện Trading chính
        JPanel tradingPanel = createTradingPanel();

        // 2. Giao diện UserMenu
        UserMenu userMenu = new UserMenu();

        // Add các màn hình vào card
        mainContentPanel.add(tradingPanel, "TRADING");
        mainContentPanel.add(userMenu, "USER_MENU");

        // Mặc định hiển thị trading
        cardLayout.show(mainContentPanel, "TRADING");

        AtomicBoolean loginSuccess = new AtomicBoolean(false);

        // Login
        SwingUtilities.invokeLater(() -> {
            AuthDialog authDialog = new AuthDialog(this, () -> {
                loginSuccess.set(true);
                this.setVisible(true);
            });

            authDialog.setVisible(true);

            if (!loginSuccess.get()) {
                System.exit(0);
            }
        });
    }

    private JPanel createTradingPanel() {
        JPanel mainTradingFrame = new JPanel(new BorderLayout());

        MainTopPanel mainTopPanel = new MainTopPanel(new BorderLayout(), e -> cardLayout.show(mainContentPanel, "USER_MENU"));
        MainLeftPanel mainLeftPanel = new MainLeftPanel(new BorderLayout());
        MainCenterPanel mainCenterPanel = new MainCenterPanel(new BorderLayout());


        mainTradingFrame.add(mainTopPanel, BorderLayout.NORTH);
        mainTradingFrame.add(mainLeftPanel, BorderLayout.WEST);
        mainTradingFrame.add(mainCenterPanel, BorderLayout.CENTER);

        return mainTradingFrame;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new MainApp().setVisible(false));
    }
}

