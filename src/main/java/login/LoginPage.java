package login;
import com.formdev.flatlaf.FlatClientProperties;
import controller.SessionManager;
import models.User;
import dataAccess.UserStore;
import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import java.awt.*;


public class LoginPage extends JPanel{
    private final JTextField txtUsername;
    private final JPasswordField txtPassword;
    private final JCheckBox chRememberme;
    private final JButton loginButton;
    private final JButton signupButton;

    public LoginPage(CardLayout layout, JPanel container, Runnable onLoginSuccess, JDialog dialog) {
        setLayout(new MigLayout("fill,insets 20", "[center]", "[center]"));

        txtUsername = new JTextField();
        txtUsername.putClientProperty(FlatClientProperties.PLACEHOLDER_TEXT, "Enter your username");

        txtPassword = new JPasswordField();
        txtPassword.putClientProperty(FlatClientProperties.PLACEHOLDER_TEXT, "Enter password");

        chRememberme = new JCheckBox("Remember me");

        loginButton = new JButton("Login");
        loginButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                String username = txtUsername.getText().trim();
                String password = new String(txtPassword.getPassword());

                if (username.isEmpty() || password.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Please enter your username and password");
                }
                else if(UserStore.validateLogin(username, password)){
                    User user = UserStore.findByUsername(username).get();
                    SessionManager.login(user);
                    onLoginSuccess.run();
                    dialog.dispose();
                }
                else {
                    JOptionPane.showMessageDialog(null, "Invalid username or password");
                }
            }
        });

        signupButton = new JButton("<html>Doesn't have an account?</html>");
        signupButton.setContentAreaFilled(false);
        signupButton.setBorderPainted(false);
        signupButton.setFocusPainted(false);
        signupButton.setOpaque(false);
        signupButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR)); // Đổi thành biểu tượng tay
        signupButton.setForeground(Color.LIGHT_GRAY); // Tuỳ chỉnh màu nếu cần
        signupButton.setFont(signupButton.getFont().deriveFont(Font.PLAIN, 12f)); // Nhẹ nhàng
        signupButton.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                signupButton.setText("<html><u>Doesn't have an account?</u></html>");
            }

            @Override
            public void mouseExited(java.awt.event.MouseEvent evt) {
                signupButton.setText("<html>Doesn't have an account?</html>");
            }

            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                layout.show(container, "signup");
            }
        });

        // Tạo panel khung đăng nhập
        JPanel panel = new JPanel(new MigLayout("wrap, fillx, insets 35 45 30 45", "fill, 250:280"));
        panel.putClientProperty(FlatClientProperties.STYLE,"" +
                "arc: 20;" +
                "[light]background:darken(@background, 3%);" +
                "[dark]background:lighten(@background, 3%);");

        // Title và description của Login Page
        JLabel lbTitle = new JLabel("COIN TRADING PLATFORM");
        lbTitle.putClientProperty(FlatClientProperties.STYLE,"" +
                "font: bold +10");

        JLabel description = new JLabel("Sign in to access your account");
        description.putClientProperty(FlatClientProperties.STYLE,"" +
                "[light]foreground:lighten(@foreground, 30%);" +
                "[dark]foreground:darken(@foreground, 20%);");

        // Thêm element vào panel
        panel.add(lbTitle);
        panel.add(description);
        panel.add(new JLabel("Username:"), "gapy 8");
        panel.add(txtUsername);
        panel.add(new JLabel("Password:"), "gapy 8");
        panel.add(txtPassword);
        panel.add(chRememberme, "grow 0");
        panel.add(loginButton, "gapy 10");
        panel.add(signupButton, "gapy 10");
        panel.setOpaque(false); // Cho phép background trong suốt
        panel.setBackground(new Color(255, 255, 255, 50));

        add(panel);
        setOpaque(false);
    }
}


