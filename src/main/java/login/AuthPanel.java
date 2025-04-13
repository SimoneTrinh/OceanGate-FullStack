package login;

import com.formdev.flatlaf.FlatClientProperties;

import javax.swing.*;
import java.awt.*;

public class AuthPanel extends JPanel {
    private final Image backgroundImage;

    public AuthPanel() {
        // Load background
        backgroundImage = new ImageIcon(getClass().getResource("/img/login-bg.png")).getImage();

        setLayout(new BorderLayout());

        // Card panel để chứa Login và Sign Up
        CardLayout cardLayout = new CardLayout();
        JPanel cardPanel = new JPanel(cardLayout);
        cardPanel.setOpaque(false);

        // Add các page
        cardPanel.add(new LoginPage(cardLayout, cardPanel), "login");
        cardPanel.add(new SignupPage(cardLayout, cardPanel), "signup");

        // Dùng GridBagLayout để căn giữa phần form
        JPanel centerPanel = new JPanel(new GridBagLayout());
        centerPanel.setOpaque(false);
        centerPanel.add(cardPanel);

        add(centerPanel, BorderLayout.CENTER);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
    }
}