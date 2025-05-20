package CryptoUserInfo.panels;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.text.NumberFormat;
import java.util.Locale;

public class WalletSummaryPanel extends JPanel {
    public WalletSummaryPanel() {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setBorder(new EmptyBorder(10, 20, 20, 20));

        JLabel totalBalance = new JLabel("Total Balance: " + formatCurrency(9849));
        totalBalance.setFont(new Font("Arial", Font.BOLD, 22));
        totalBalance.setForeground(new Color(0, 128, 0));
        add(totalBalance);
        add(Box.createRigidArea(new Dimension(0, 20)));

        JPanel coinListPanel = new JPanel(new GridLayout(0, 2, 15, 10));
        coinListPanel.setBorder(BorderFactory.createTitledBorder("Your Coins"));

        coinListPanel.add(createCoinLabel("Bitcoin", 5228, "resources/icons/bitcoin.png"));
        coinListPanel.add(createCoinLabel("Ethereum", 4621, "resources/icons/ethereum.png"));
        coinListPanel.add(createCoinLabel("Tether (ERC20)", 0, "resources/icons/usdt.png"));
        coinListPanel.add(createCoinLabel("Litecoin", 0, "resources/icons/litecoin.png"));

        add(coinListPanel);
    }

    private JPanel createCoinLabel(String name, double value, String iconPath) {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JLabel icon = new JLabel(new ImageIcon(iconPath));
        JLabel label = new JLabel(name + ": " + formatCurrency(value));
        label.setFont(new Font("Arial", Font.PLAIN, 14));
        panel.add(icon);
        panel.add(label);
        return panel;
    }

    private String formatCurrency(double value) {
        NumberFormat formatter = NumberFormat.getCurrencyInstance(Locale.US);
        return formatter.format(value);
    }
}
