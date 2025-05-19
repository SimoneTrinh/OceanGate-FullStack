package user_menu.components;

import com.formdev.flatlaf.FlatClientProperties;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.text.AbstractDocument;
import javax.swing.text.DocumentFilter;
import java.awt.*;

public class UIUtils {
    public static JLabel createStyledLabel(String text) {
        JLabel label = new JLabel(text);
        label.setFont(new Font("SansSerif", Font.PLAIN, 13));
        label.setForeground(new Color(0x5F6368)); // xám nhạt nhẹ nhàng
        return label;
    }

    public static JButton createStyledButton(String text) {
        JButton button = new JButton(text);
        button.putClientProperty(FlatClientProperties.STYLE, "arc:20");
        return button;
    }

    public static TitledBorder createStyledTitle(String title) {
        TitledBorder border = BorderFactory.createTitledBorder(title);

        // Font
        border.setTitleFont(new Font("SansSerif", Font.BOLD, 16));

        // Màu chữ
        border.setTitleColor(new Color(0x1A73E8)); // Màu xanh hiện đại (hoặc dùng Color.BLUE, Color.GRAY,...)

        // Vị trí chữ (tùy chọn)
        border.setTitleJustification(TitledBorder.LEFT);   // LEFT, CENTER, RIGHT
        border.setTitlePosition(TitledBorder.TOP);         // TOP, BELOW_TOP, ABOVE_TOP, BOTTOM,...

        return border;
    }

    public static JPasswordField createPasswordField(int maxLength) {
        JPasswordField passwordField = new JPasswordField();
        passwordField.putClientProperty(FlatClientProperties.STYLE,
                "showRevealButton:true; arc:10;");
        limitInputLength(passwordField, maxLength);
        return passwordField;
    }

    public static void addEditableField(JPanel panel, String label, String value) {
        addEditableField(panel, label, value, true);
    }

    public static void addEditableField(JPanel panel, String label, String value, boolean editable) {
        panel.add(createStyledLabel(label));
        JPanel fieldPanel = new JPanel(new BorderLayout());
        JLabel valueLabel = new JLabel(value);
        valueLabel.setFont(new Font("SansSerif", Font.PLAIN, 14));
        JButton changeButton = createStyledButton("Change");

        changeButton.addActionListener(e -> {
            JTextField inputField = new JTextField(value);
            inputField.putClientProperty(FlatClientProperties.PLACEHOLDER_TEXT, "Enter " + label.toLowerCase());
            limitInputLength(inputField, 30);

            int result = JOptionPane.showConfirmDialog(panel, inputField, "Enter new " + label.toLowerCase(), JOptionPane.OK_CANCEL_OPTION);
            if (result == JOptionPane.OK_OPTION) {
                String newValue = inputField.getText();
                if (newValue != null && !newValue.trim().isEmpty()) {
                    valueLabel.setText(newValue.trim());
                }
            }
        });

        fieldPanel.add(valueLabel, BorderLayout.CENTER);
        if (editable) fieldPanel.add(changeButton, BorderLayout.EAST);
        panel.add(fieldPanel);
    }

    public static JButton createPrimaryButton(String text) {
        JButton button = new JButton(text);
        button.putClientProperty(FlatClientProperties.STYLE,
                "arc:15; background:#1A73E8; foreground:#FFFFFF;" +
                        "hoverBackground:#1664C0;");
        button.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        return button;
    }

    public static JButton createSecondaryButton(String text) {
        JButton button = new JButton(text);
        button.putClientProperty(FlatClientProperties.STYLE,
                "arc:15; background:#F1F3F4; foreground:#202124;" +
                        "hoverBackground:#E0E0E0;");
        button.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        return button;
    }


    public static void limitInputLength(JTextField field, int maxLength) {
        AbstractDocument doc = (AbstractDocument) field.getDocument();
        doc.setDocumentFilter(new DocumentFilter() {
            @Override
            public void insertString(FilterBypass fb, int offset, String string, javax.swing.text.AttributeSet attr)
                    throws javax.swing.text.BadLocationException {
                if ((fb.getDocument().getLength() + string.length()) <= maxLength) {
                    super.insertString(fb, offset, string, attr);
                }
            }

            @Override
            public void replace(FilterBypass fb, int offset, int length, String text, javax.swing.text.AttributeSet attrs)
                    throws javax.swing.text.BadLocationException {
                if ((fb.getDocument().getLength() - length + (text != null ? text.length() : 0)) <= maxLength) {
                    super.replace(fb, offset, length, text, attrs);
                }
            }
        });
    }
}
