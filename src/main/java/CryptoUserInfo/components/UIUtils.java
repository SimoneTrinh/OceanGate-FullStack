package CryptoUserInfo.components;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class UIUtils {

    public static Font defaultFont = new Font("Segoe UI", Font.PLAIN, 14);
    public static Font titleFont = new Font("Segoe UI", Font.BOLD, 18);
    public static Color primaryColor = new Color(0, 123, 255);

    public static JButton createStyledButton(String text) {
        JButton btn = new JButton(text);
        btn.setFont(defaultFont);
        btn.setBackground(primaryColor);
        btn.setForeground(Color.WHITE);
        btn.setFocusPainted(false);
        btn.setBorder(BorderFactory.createEmptyBorder(8, 20, 8, 20));
        return btn;
    }

//    public static JLabel createStyledLabel(String text, boolean bold) {
//        JLabel label = new JLabel(text);
//        label.setFont(bold ? titleFont : defaultFont);
//        return label;
//    }
//
//    public static JPanel createPaddedPanel(LayoutManager layout, int padding) {
//        JPanel panel = new JPanel(layout);
//        panel.setBorder(new EmptyBorder(padding, padding, padding, padding));
//        return panel;
//    }
}
