package login;

import com.formdev.flatlaf.FlatClientProperties;
import models.User;
import controller.UserStore;
import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import java.awt.*;
import java.util.Map;

public class SignupPage extends JPanel {

    private JTextField txtFirstName;
    private JTextField txtLastName;
    private JTextField txtUsername;
    private JPasswordField txtPassword;
    private JPasswordField txtPasswordConfirm;
    private JTextField txtEmail;
    private JTextField txtPhone;
    private JButton btnSignup;
    private JButton btnToSignIn;
    private JLabel lblMessage; // Label thông báo

    public SignupPage(CardLayout cardLayout, JPanel container) {
        setOpaque(false);
        setLayout(new MigLayout("fill,insets 20", "[center]", "[center]"));

        // Khởi tạo component
        txtFirstName = new JTextField();
        txtLastName = new JTextField();
        txtUsername = new JTextField();
        txtPassword = new JPasswordField();
        txtPasswordConfirm = new JPasswordField();
        txtEmail = new JTextField();
        txtPhone = new JTextField();
        btnSignup = new JButton("Sign up");
        btnToSignIn = new JButton("<html>Back to Sign In</html>");
        lblMessage = new JLabel(""); // Label hiển thị thông báo
        lblMessage.setForeground(Color.RED);
        lblMessage.setFont(lblMessage.getFont().deriveFont(Font.PLAIN, 12f));

        // Panel chính
        JPanel panel = new JPanel(new MigLayout("wrap, fillx, insets 35 45 30 45", "fill, 250:280"));
        panel.putClientProperty(FlatClientProperties.STYLE, ""
                + "arc: 20;"
                + "[light]background:darken(@background, 3%);"
                + "[dark]background:lighten(@background, 3%);");
        panel.setOpaque(false);
        panel.setBackground(new Color(255, 255, 255, 50));

        // Tiêu đề
        JLabel title = new JLabel("SIGN UP NEW ACCOUNT");
        JLabel description = new JLabel("Please enter your account details");
        title.putClientProperty(FlatClientProperties.STYLE, "font: bold +10");
        description.putClientProperty(FlatClientProperties.STYLE, ""
                + "[light]foreground:lighten(@foreground, 30%);"
                + "[dark]foreground:darken(@foreground, 20%);");

        // Placeholder
        txtFirstName.putClientProperty(FlatClientProperties.PLACEHOLDER_TEXT, "Enter your first name");
        txtLastName.putClientProperty(FlatClientProperties.PLACEHOLDER_TEXT, "Enter your last name");
        txtUsername.putClientProperty(FlatClientProperties.PLACEHOLDER_TEXT, "Enter your new username");
        txtPassword.putClientProperty(FlatClientProperties.PLACEHOLDER_TEXT, "Enter your new password");
        txtPasswordConfirm.putClientProperty(FlatClientProperties.PLACEHOLDER_TEXT, "Re-type your new password");
        txtEmail.putClientProperty(FlatClientProperties.PLACEHOLDER_TEXT, "Enter your email address");
        txtPhone.putClientProperty(FlatClientProperties.PLACEHOLDER_TEXT, "Enter your phone number");

        // Custom button "Back to Sign In"
        btnToSignIn.setContentAreaFilled(false);
        btnToSignIn.setBorderPainted(false);
        btnToSignIn.setFocusPainted(false);
        btnToSignIn.setOpaque(false);
        btnToSignIn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        btnToSignIn.setFont(btnToSignIn.getFont().deriveFont(Font.PLAIN, 12f));
        btnToSignIn.setForeground(Color.LIGHT_GRAY);

        btnToSignIn.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnToSignIn.setText("<html><u>Back to Sign In</u></html>");
            }

            @Override
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnToSignIn.setText("<html>Back to Sign In</html>");
            }

            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblMessage.setText(""); // reset thông báo khi quay lại login
                cardLayout.show(container, "login");
            }
        });

        // 👉 Xử lý nút Sign up
        btnSignup.addActionListener(e -> {
            String firstName = txtFirstName.getText().trim();
            String lastName = txtLastName.getText().trim();
            String userName = txtUsername.getText().trim();
            String password = new String(txtPassword.getPassword());
            String confirmPassword = new String(txtPasswordConfirm.getPassword());
            String email = txtEmail.getText().trim();
            String phone = txtPhone.getText().trim();

            Map<String, String> requiredFields = Map.of(
                    "First name", firstName,
                    "Last name", lastName,
                    "Username", userName,
                    "Email", email,
                    "Phone number", phone,
                    "Password", password,
                    "Confirm password", confirmPassword
            );

            // Kiểm tra field rỗng
            for (Map.Entry<String, String> entry : requiredFields.entrySet()) {
                if (entry.getValue().isEmpty()) {
                    lblMessage.setText(entry.getKey() + " is required.");
                    return;
                }
            }
            // Kiểm tra xác nhận mật khẩu
            if (!password.equals(confirmPassword)) {
                lblMessage.setText("Passwords do not match.");
            }
            else {
                try {
                    User newUser = new User(userName, password, email, firstName, lastName, phone);

                    // Gọi setEmail/setPhone là thừa vì đã có trong constructor
                    UserStore.addUser(newUser);  // có thể throw nếu user trùng

                    lblMessage.setForeground(new Color(0, 153, 0)); // xanh lá
                    lblMessage.setText("Account created successfully!");
                } catch (IllegalArgumentException ex) {
                    lblMessage.setForeground(Color.RED);
                    lblMessage.setText("Invalid input: " + ex.getMessage());
                } catch (Exception ex) {
                    lblMessage.setForeground(Color.RED);
                    lblMessage.setText("User already exists.");
                }
            }
        });

        // Thêm các thành phần vào panel
        panel.add(title);
        panel.add(description);
        panel.add(lblMessage, "gapy 5"); // Thông báo
        panel.add(new JLabel("First Name:"), "gapy 8");
        panel.add(txtFirstName);
        panel.add(new JLabel("Last Name:"), "gapy 8");
        panel.add(txtLastName);
        panel.add(new JLabel("Username:"), "gapy 8");
        panel.add(txtUsername);
        panel.add(new JLabel("Email:"), "gapy 8");
        panel.add(txtEmail);
        panel.add(new JLabel("Phone:"), "gapy 8");
        panel.add(txtPhone);
        panel.add(new JLabel("Password:"), "gapy 8");
        panel.add(txtPassword);
        panel.add(new JLabel("Password Confirm:"), "gapy 8");
        panel.add(txtPasswordConfirm);
        panel.add(btnSignup, "gapy 10");
        panel.add(btnToSignIn, "gapy 10");

        add(panel);
    }
}
