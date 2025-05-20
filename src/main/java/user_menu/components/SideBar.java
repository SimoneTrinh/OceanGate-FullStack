package user_menu.components;

import com.formdev.flatlaf.FlatClientProperties;
import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import java.awt.*;
import java.util.function.Consumer;

public class SideBar extends JPanel {
    private JButton selectedButton = null;

    public SideBar(Consumer<String> onPageSelect) {
        setLayout(new MigLayout("wrap 1", "[grow, fill]", "[]15[]15[]"));
        setBorder(BorderFactory.createEmptyBorder(20, 0, 0, 0));
        setPreferredSize(new Dimension(220, 0));
        setOpaque(false);

        JButton personalInfoBtn = createModernButton("Personal Info", "/icons/user.png");
        JButton accountSettingBtn = createModernButton("Account Setting", "/icons/settings.png");
        JButton contactInfoBtn = createModernButton("Contact Info", "/icons/phone.png");

        personalInfoBtn.addActionListener(e -> {
            highlightButton(personalInfoBtn);
            onPageSelect.accept("Personal Info");
        });
        accountSettingBtn.addActionListener(e -> {
            highlightButton(accountSettingBtn);
            onPageSelect.accept("Account Setting");
        });
        contactInfoBtn.addActionListener(e -> {
            highlightButton(contactInfoBtn);
            onPageSelect.accept("Contact Info");
        });

        add(personalInfoBtn);
        add(accountSettingBtn);
        add(contactInfoBtn);

        // Chọn mặc định
        highlightButton(personalInfoBtn);
    }

    private JButton createModernButton(String text, String iconPath) {
        ImageIcon icon = null;
        java.net.URL iconURL = getClass().getResource(iconPath);
        if (iconURL != null)
            icon = new ImageIcon(iconURL);
        else
            System.err.println("⚠️ Icon not found: " + iconPath);

        JButton button = new JButton(text, icon);
        button.setHorizontalAlignment(SwingConstants.LEFT);
        button.setIconTextGap(10);
        button.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setContentAreaFilled(false);
        button.putClientProperty(FlatClientProperties.STYLE,
                "arc:10; hoverBackground:#E0E0E0; minimumHeight:36;");

        return button;
    }

    private void highlightButton(JButton button) {
//         Reset previous selected button
        if (selectedButton != null) {
            selectedButton.setContentAreaFilled(false);
            selectedButton.setForeground(Color.BLACK);
        }

        // Highlight current button
        button.setContentAreaFilled(true);
        button.setBackground(new Color(0x1A73E8)); // Google Blue
        button.setForeground(Color.WHITE);

        selectedButton = button;
    }
}
