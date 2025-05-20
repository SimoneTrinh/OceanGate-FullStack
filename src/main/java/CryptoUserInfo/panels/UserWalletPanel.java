package CryptoUserInfo.panels;

import javax.swing.*;
import java.awt.*;

public class UserWalletPanel extends JPanel {
    public UserWalletPanel() {
        setLayout(new BorderLayout());

        // Top: User Info
        add(new UserOverviewPanel(), BorderLayout.NORTH);

        // Center: Wallet Summary
        add(new WalletSummaryPanel(), BorderLayout.CENTER);

        // Bottom: Transaction History
        add(new TransactionHistoryPanel(), BorderLayout.SOUTH);
    }
}
