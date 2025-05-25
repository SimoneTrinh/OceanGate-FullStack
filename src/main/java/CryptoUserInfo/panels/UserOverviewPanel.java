package CryptoUserInfo.panels;

import controller.SessionManager;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class UserOverviewPanel extends JPanel {
    public UserOverviewPanel() {
        setLayout(new BorderLayout());
        setBorder(new EmptyBorder(20, 20, 10, 20));

        // Lấy thông tin user
        String currentUserName = SessionManager.getCurrentUser().getUsername();

        // Tải avatar
        java.net.URL iconURL = getClass().getResource("/icons/user.png");
        ImageIcon avatar = iconURL != null ? new ImageIcon(iconURL) : null;

        // Tạo label cho avatar
        JLabel avatarLabel = new JLabel();
        if (avatar != null) {
            avatarLabel.setIcon(avatar);
        } else {
            avatarLabel.setText("👤"); // fallback emoji nếu không có icon
        }

        // Tạo label cho tên người dùng
        JLabel usernameLabel = new JLabel(currentUserName);
        usernameLabel.setFont(new Font("Arial", Font.BOLD, 13));
        usernameLabel.setBorder(new EmptyBorder(5, 10, 0, 0));

        add(avatarLabel, BorderLayout.WEST);
        add(usernameLabel, BorderLayout.CENTER);
    }
}
