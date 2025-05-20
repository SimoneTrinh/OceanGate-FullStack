package CryptoUserInfo;

import CryptoUserInfo.components.UIUtils;
import CryptoUserInfo.panels.UserWalletPanel;
import CryptoUserInfo.panels.*;
import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class CryptoUserInfo extends JPanel {
    public CryptoUserInfo(ActionListener backToTradingPanel) {
        setLayout(new BorderLayout());

        // ==== Top Bar chứa nút Back + User Overview ====
        JPanel topBar = new JPanel(new MigLayout("insets 10 10 10 10, fill", "[grow][right]"));

        JButton backButton = UIUtils.createStyledButton("← Back to Trading");
        backButton.addActionListener(backToTradingPanel);

        UserOverviewPanel userOverview = new UserOverviewPanel();

        topBar.add(backButton, "align left");
        topBar.add(userOverview, "align right");

        add(topBar, BorderLayout.NORTH);

        // Tab navigation
        JTabbedPane tabbedPane = new JTabbedPane();
        tabbedPane.setFont(new Font("Arial", Font.BOLD, 14));

        tabbedPane.addTab("User Wallet", new UserWalletPanel());
        tabbedPane.addTab("User Settings", new UserSettingsPanel());

        add(tabbedPane, BorderLayout.CENTER);
    }

    public static void main(String[] args) {
//        SwingUtilities.invokeLater(() -> new CryptoUserInfo().setVisible(true));
    }
}
