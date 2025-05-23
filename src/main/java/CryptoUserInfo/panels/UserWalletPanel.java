package CryptoUserInfo.panels;

import javax.swing.*;
import java.awt.*;

public class UserWalletPanel extends JPanel {
    public UserWalletPanel() {
        setLayout(new BorderLayout());

        // Sidebar trái: Tổng số dư + Coin list
        JPanel leftSidebar = new JPanel();
        leftSidebar.setLayout(new BorderLayout());
        leftSidebar.setPreferredSize(new Dimension(300, 0));
        leftSidebar.add(new TotalBalancePanel(), BorderLayout.NORTH);
        leftSidebar.add(new CoinListPanel(), BorderLayout.CENTER);

        // Bảng giao dịch bên phải
        JPanel rightPanel = new TransactionHistoryPanel();

        add(leftSidebar, BorderLayout.WEST);
        add(rightPanel, BorderLayout.CENTER);
    }
}
