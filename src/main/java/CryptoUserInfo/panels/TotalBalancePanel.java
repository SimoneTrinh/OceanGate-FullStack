package CryptoUserInfo.panels;

import javax.swing.*;
import java.awt.*;

public class TotalBalancePanel extends JPanel {
    public TotalBalancePanel() {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setBorder(BorderFactory.createEmptyBorder(20, 20, 10, 20));

        JLabel title = new JLabel("Total balance");
        title.setFont(new Font("Arial", Font.PLAIN, 14));
        title.setForeground(Color.GRAY);

        JLabel balance = new JLabel("9 849 $");
        balance.setFont(new Font("Arial", Font.BOLD, 32));
        balance.setForeground(new Color(92, 100, 245)); // xanh tím

        JLabel sub = new JLabel("↑ +738,67 $ (7.5%)");
        sub.setFont(new Font("Arial", Font.PLAIN, 13));
        sub.setForeground(new Color(0, 153, 51)); // xanh lục

        add(title);
        add(Box.createRigidArea(new Dimension(0, 5)));
        add(balance);
        add(Box.createRigidArea(new Dimension(0, 5)));
        add(sub);
    }
}
