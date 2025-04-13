package login;

import com.formdev.flatlaf.FlatClientProperties;
import net.miginfocom.swing.MigLayout;
import javax.swing.*;
import java.awt.*;

public class SignupPage extends JPanel {
    private JTextField txtFirstName;
    private JTextField txtLastName;
    private JTextField txtUsername;
    private JPasswordField txtPassword;
    private JPasswordField txtPasswordConfirm;
    private JButton btnSignup;
    private JButton btnToSignIn;

    public SignupPage(CardLayout cardLayout, JPanel container) {
        setLayout(new MigLayout("fill,insets 20", "[center]", "[center]"));
        JTextField txtFirstName = new JTextField();
        JTextField txtLastName = new JTextField();
        JTextField txtUsername = new JTextField();
        JPasswordField txtPassword = new JPasswordField();
        JPasswordField txtPasswordConfirm = new JPasswordField();
        JButton btnSignup = new JButton("Sign up");
        JButton btnToSignIn = new JButton("To Sign In");

        JPanel panel = new JPanel(new MigLayout("wrap, fillx, insets 35 45 30 45", "fill, 250:280"));
        panel.putClientProperty(FlatClientProperties.STYLE,"" +
                "arc: 20;" +
                "[light]background:darken(@background, 3%);" +
                "[dark]background:lighten(@background, 3%);");
        JLabel title = new JLabel("SIGN UP NEW ACCOUNT");
        JLabel description = new JLabel("Please enter your account details");
        title.putClientProperty(FlatClientProperties.STYLE, "" +
                "font: bold +10");
        description.putClientProperty(FlatClientProperties.STYLE, ""+
                "[light]foreground:darken(@foreground, 30%);" +
                "[dark]foreground:lighten(@foreground, 3%);");

        txtUsername.putClientProperty(FlatClientProperties.PLACEHOLDER_TEXT, "Enter your username");
        txtFirstName.putClientProperty(FlatClientProperties.PLACEHOLDER_TEXT, "Enter your first name");
        txtLastName.putClientProperty(FlatClientProperties.PLACEHOLDER_TEXT, "Enter your last name");

        panel.add(title);
        panel.add(description);
        panel.add(new JLabel("First Name:"), "gapy 8");
        panel.add(txtFirstName);
        panel.add(new JLabel("Last Name:"), "gapy 8");
        panel.add(txtLastName);
        panel.add(new JLabel("Username:"), "gapy 8");
        panel.add(txtUsername);
        panel.add(new JLabel("Password:"), "gapy 8");
        panel.add(txtPassword);
        panel.add(new JLabel("Password Confirm:"), "gapy 8");
        panel.add(txtPasswordConfirm);
        panel.add(btnSignup);
        panel.add(btnToSignIn);
        panel.setOpaque(false); // Cho phép background trong suốt
        panel.setBackground(new Color(255, 255, 255, 30)); // Trắng, alpha = 150/255 (mờ)
        add(panel);
        setOpaque(false);
    }

}
