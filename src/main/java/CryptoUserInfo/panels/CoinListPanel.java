package CryptoUserInfo.panels;

import CryptoUserInfo.components.IconResources;

import javax.swing.*;
import java.awt.*;
import java.util.LinkedHashMap;
import java.util.Map;

public class CoinListPanel extends JPanel {
    public CoinListPanel() {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
//        setBackground(Color.WHITE);
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Map coin name => [USD value, market price, quantity]
        Map<String, String[]> coins = new LinkedHashMap<>();
        coins.put("Bitcoin", new String[]{"5 228$", "68 527,50 $", "0,07 BTC"});
        coins.put("Ethereum", new String[]{"4 621$", "3 926,25 $", "1,18 ETH"});
        coins.put("XRP", new String[]{"0$", "1 $", "0 USDT"});

        for (Map.Entry<String, String[]> entry : coins.entrySet()) {
            String name = entry.getKey();
            String[] values = entry.getValue(); // [walletValue, marketPrice, quantity]
            add(createCoinRow(name, values[0], values[1], values[2]));
            add(Box.createRigidArea(new Dimension(0, 8)));
        }

        JButton addToken = new JButton("Add token");
        addToken.setAlignmentX(Component.CENTER_ALIGNMENT);
        add(Box.createRigidArea(new Dimension(0, 10)));
        add(addToken);
    }

    private JPanel createCoinRow(String name, String walletValue, String marketPrice, String quantity) {
        JPanel row = new JPanel(new BorderLayout());
        row.setLayout(new BoxLayout(row, BoxLayout.X_AXIS));
        row.setMaximumSize(new Dimension(350, 60));
//        row.setBackground(Color.WHITE);
        row.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));

        // Icon and name
        JPanel left = new JPanel();
        left.setLayout(new BoxLayout(left, BoxLayout.X_AXIS));
        left.setOpaque(false);

        JLabel icon;
        try {
            icon = new JLabel(IconResources.valueOf(name.toUpperCase().replace(" ", "_")).getIcon());
        } catch (Exception e) {
            icon = new JLabel("🪙");
        }
        icon.setPreferredSize(new Dimension(32, 32));
        icon.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 10));

        JPanel textGroup = new JPanel();
        textGroup.setLayout(new BoxLayout(textGroup, BoxLayout.Y_AXIS));
        textGroup.setOpaque(false);

        JLabel nameLabel = new JLabel(name);
        nameLabel.setFont(new Font("Arial", Font.PLAIN, 14));

        JLabel priceLabel = new JLabel(marketPrice);
        priceLabel.setFont(new Font("Arial", Font.ITALIC, 12));
        priceLabel.setForeground(Color.GRAY);

        textGroup.add(nameLabel);
        textGroup.add(priceLabel);

        left.add(icon);
        left.add(textGroup);

        // Right side: walletValue + quantity
        JPanel right = new JPanel();
        right.setLayout(new BoxLayout(right, BoxLayout.Y_AXIS));
        right.setOpaque(false);
        right.setAlignmentX(Component.RIGHT_ALIGNMENT);

        JLabel valLabel = new JLabel(walletValue);
        valLabel.setFont(new Font("Arial", Font.BOLD, 14));
        valLabel.setForeground(Color.DARK_GRAY);
        valLabel.setAlignmentX(Component.RIGHT_ALIGNMENT);
        valLabel.setAlignmentY(Component.TOP_ALIGNMENT);

        JLabel qtyLabel = new JLabel(quantity);
        qtyLabel.setFont(new Font("Arial", Font.PLAIN, 12));
        qtyLabel.setForeground(Color.GRAY);
        qtyLabel.setAlignmentX(Component.RIGHT_ALIGNMENT);

        right.add(valLabel);
        right.add(qtyLabel);

        row.add(left);
        row.add(Box.createHorizontalGlue());
        row.add(right);

        return row;
    }
}
