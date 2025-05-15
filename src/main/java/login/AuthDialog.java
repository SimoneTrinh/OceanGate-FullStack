package login;

import com.formdev.flatlaf.FlatLaf;
import com.formdev.flatlaf.fonts.roboto.FlatRobotoFont;
import com.formdev.flatlaf.themes.FlatMacDarkLaf;

import javax.swing.*;
import java.awt.*;

public class AuthDialog extends JDialog {
    private final Image backgroundImage;

    public AuthDialog(JFrame parent, Runnable onLoginSuccess) {
        super(parent, "Sign In", true); // modal

        setSize(1200, 800);
        setLocationRelativeTo(parent);
        setLayout(new BorderLayout());

        // Setup FlatLaf
        FlatRobotoFont.install();
        FlatLaf.registerCustomDefaultsSource("raven.themes");
        FlatMacDarkLaf.setup();

        // Load ảnh nền
        backgroundImage = new ImageIcon(getClass().getResource("/img/login-bg.png")).getImage();

        // Panel có background
        JPanel backgroundPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
            }
        };
        backgroundPanel.setLayout(new GridBagLayout()); // căn giữa nội dung
        backgroundPanel.setOpaque(false);

        // CardLayout cho Login / Signup
        CardLayout layout = new CardLayout();
        JPanel container = new JPanel(layout);
        container.setOpaque(false); // trong suốt để nhìn thấy background

        // Các page
        LoginPage loginPage = new LoginPage(layout, container, onLoginSuccess, this);
        SignupPage signupPage = new SignupPage(layout, container);

        container.add(loginPage, "login");
        container.add(signupPage, "signup");

        layout.show(container, "login");

        backgroundPanel.add(container); // add form vào nền
        add(backgroundPanel, BorderLayout.CENTER);
    }
}

