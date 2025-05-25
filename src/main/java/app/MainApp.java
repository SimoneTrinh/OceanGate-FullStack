package app;

import CryptoUserInfo.CryptoUserInfo;
import chart.ChartHosting;
import com.formdev.flatlaf.FlatLaf;
import com.formdev.flatlaf.FlatLightLaf;
import login.AuthDialog;
import services.BinanceStreaming;
import services.DBManager;
import ui.components.center.MainCenterPanel;
import ui.components.left.MainLeftPanel;
import ui.components.top.MainTopPanel;
import utils.Constants;
import utils.LocalStorage;

import javax.swing.*;
import javax.swing.plaf.FontUIResource;
import java.awt.*;
import java.io.File;
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

        DBManager.connect();

//        UIManager.put("defaultFont", new FontUIResource("Inter", Font.PLAIN, 13));


        try {
            // Load custom font from file
            Font customFont = Font.createFont(Font.TRUETYPE_FONT, new File("Binance_PLEX.ttf"))
                    .deriveFont(12f); // Set default size

            // Register the font in the graphics environment
            GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
            ge.registerFont(customFont);


        } catch (FontFormatException | IOException e) {
            e.printStackTrace();
        }

//        FlatLaf.registerCustomDefaultsSource("themes"); // thư mục chứa FlatLaf.properties
        FlatLightLaf.setup();
        // Login
        SwingUtilities.invokeLater(() -> {
            AuthDialog authDialog = new AuthDialog(this, () -> {

                this.setVisible(true);
            });

            authDialog.setVisible(true);

//            FlatLaf.registerCustomDefaultsSource((String) null); // xóa custom source
//            FlatLightLaf.setup();
            ChartHosting chart = new ChartHosting();
            try {
                chart.init();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

            BinanceStreaming.getInstance().connect();

            // CardLayout switch panels
            cardLayout = new CardLayout();
            mainContentPanel = new JPanel(cardLayout);
            add(mainContentPanel, BorderLayout.CENTER);

            // 1. Main app
            JPanel tradingPanel = createTradingPanel();

            // 2. User menu
            CryptoUserInfo userInfo = new CryptoUserInfo(e -> cardLayout.show(mainContentPanel, "TRADING"));

            mainContentPanel.add(tradingPanel, "TRADING");
            mainContentPanel.add(userInfo, "USER_MENU");

            // Default
            cardLayout.show(mainContentPanel, "TRADING");
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

