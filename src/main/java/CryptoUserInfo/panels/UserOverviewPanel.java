package CryptoUserInfo.panels;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class UserOverviewPanel extends JPanel {
    public UserOverviewPanel() {
        setLayout(new BorderLayout());
        setBorder(new EmptyBorder(20, 20, 10, 20));

        JLabel avatar = new JLabel(new ImageIcon("resources/icons/user.png"));
        JLabel username = new JLabel("User: tuan.crypto");
        username.setFont(new Font("Arial", Font.BOLD, 20));

        add(avatar, BorderLayout.WEST);
        add(username, BorderLayout.CENTER);
    }
}
