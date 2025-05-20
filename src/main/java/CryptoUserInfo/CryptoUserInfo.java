package CryptoUserInfo;

import CryptoUserInfo.panels.UserWalletPanel;
import com.formdev.flatlaf.FlatLightLaf;
import CryptoUserInfo.panels.*;

import javax.swing.*;
import java.awt.*;

public class CryptoUserInfo extends JPanel {
    public CryptoUserInfo() {
        setLayout(new BorderLayout());

        // Tab navigation
        JTabbedPane tabbedPane = new JTabbedPane();
        tabbedPane.setFont(new Font("Arial", Font.BOLD, 14));

        tabbedPane.addTab("User Wallet", new UserWalletPanel());
        tabbedPane.addTab("User Settings", new UserSettingsPanel());

        add(tabbedPane, BorderLayout.CENTER);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new CryptoUserInfo().setVisible(true));
    }
}
