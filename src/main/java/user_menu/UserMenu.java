package user_menu;

import com.formdev.flatlaf.FlatClientProperties;
import com.formdev.flatlaf.FlatLaf;
import com.formdev.flatlaf.fonts.roboto.FlatRobotoFont;
import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.text.AbstractDocument;
import javax.swing.text.DocumentFilter;
import java.awt.*;

public class UserMenu extends JPanel {
    private CardLayout cardLayout;
    private JPanel contentPanel;

    public UserMenu() {
        FlatRobotoFont.install(); // optional
        UIManager.put("defaultFont", new Font("Roboto", Font.PLAIN, 14)); // apply globally

        setLayout(new BorderLayout());

        // Sidebar
        JPanel sidebar = new JPanel(new MigLayout("wrap 1", "[grow, fill]", "[]15[]15[]"));
        sidebar.setPreferredSize(new Dimension(160, getHeight()));

        JButton personalInfoBtn = createStyledButton("Personal Info");
        JButton accountSettingBtn = createStyledButton("Account Setting");
        JButton contactInfoBtn = createStyledButton("Contact Info");

        sidebar.add(personalInfoBtn);
        sidebar.add(accountSettingBtn);
        sidebar.add(contactInfoBtn);

        add(sidebar, BorderLayout.WEST);

        // Content panel with CardLayout
        // Wrapper để giữ center cố định kích thước

        cardLayout = new CardLayout();
        contentPanel = new JPanel(cardLayout);
        contentPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        contentPanel.add(createPersonalInfoPanel(), "Personal Info");
        contentPanel.add(createAccountSettingPanel(), "Account Setting");
        contentPanel.add(createContactInfoPanel(), "Contact Info");
        JPanel wrapperPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 0));
        wrapperPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        contentPanel.setPreferredSize(new Dimension(800, 600)); // 👈 Giới hạn size
        contentPanel.setMaximumSize(new Dimension(1200, 700));   // Optional, đảm bảo không kéo giãn

        wrapperPanel.add(contentPanel);
        add(wrapperPanel, BorderLayout.CENTER);

        // Button actions
        personalInfoBtn.addActionListener(e -> cardLayout.show(contentPanel, "Personal Info"));
        accountSettingBtn.addActionListener(e -> cardLayout.show(contentPanel, "Account Setting"));
        contactInfoBtn.addActionListener(e -> cardLayout.show(contentPanel, "Contact Info"));
    }

    private JPanel createPersonalInfoPanel() {
        JPanel panel = new JPanel(new MigLayout("wrap 2", "[right][grow, fill]", "10[]10[]10"));
        panel.setBorder(createStyledTitle("Personal Information"));

        addEditableField(panel, "First Name:", "Tuan");
        addEditableField(panel, "Last Name:", "Nguyen");

        return panel;
    }

    private JPanel createAccountSettingPanel() {
        JPanel panel = new JPanel(new MigLayout("wrap 2", "[right][grow, fill]", "10[]10[]10"));
        panel.setBorder(createStyledTitle("Account Settings"));

        addEditableField(panel, "Username:", "tuan123", false);

        panel.add(createStyledLabel("Change Password:"), "span 2");

        panel.add(createStyledLabel("Current Password:"));
        panel.add(createPasswordField(20));

        panel.add(createStyledLabel("New Password:"));
        panel.add(createPasswordField(20));

        panel.add(createStyledLabel("Confirm Password:"));
        panel.add(createPasswordField(20));

        JButton saveButton = createStyledButton("Save Changes");
        JButton cancelButton = createStyledButton("Cancel");
        panel.add(saveButton, "span 1, right");
        panel.add(cancelButton, "span 1, left");

        return panel;
    }

    private JPanel createContactInfoPanel() {
        JPanel panel = new JPanel(new MigLayout("wrap 2", "[right][grow, fill]", "10[]10[]10"));
        panel.setBorder(createStyledTitle("Contact Information"));

        addEditableField(panel, "Email:", "tuan@example.com");
        addEditableField(panel, "Phone:", "0123456789");

        return panel;
    }

    private void addEditableField(JPanel panel, String label, String value) {
        addEditableField(panel, label, value, true);
    }

    private void addEditableField(JPanel panel, String label, String value, boolean editable) {
        panel.add(createStyledLabel(label));
        JPanel fieldPanel = new JPanel(new BorderLayout());
        JLabel valueLabel = new JLabel(value);
        valueLabel.setFont(new Font("SansSerif", Font.PLAIN, 14));
        JButton changeButton = createStyledButton("Change");

        changeButton.addActionListener(e -> {
            JTextField inputField = new JTextField(value);
            inputField.putClientProperty(FlatClientProperties.PLACEHOLDER_TEXT, "Enter " + label.toLowerCase());
            limitInputLength(inputField, 30);

            int result = JOptionPane.showConfirmDialog(this, inputField, "Enter new " + label.toLowerCase(), JOptionPane.OK_CANCEL_OPTION);
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

    private JPasswordField createPasswordField(int maxLength) {
        JPasswordField passwordField = new JPasswordField();
        passwordField.putClientProperty(FlatClientProperties.STYLE, "showRevealButton:true");
        limitInputLength(passwordField, maxLength);
        return passwordField;
    }

    private JLabel createStyledLabel(String text) {
        JLabel label = new JLabel(text);
        label.setFont(new Font("SansSerif", Font.BOLD, 14));
        return label;
    }

    private JButton createStyledButton(String text) {
        JButton button = new JButton(text);
        button.putClientProperty(FlatClientProperties.STYLE, "arc:20");
        return button;
    }

    private TitledBorder createStyledTitle(String title) {
        TitledBorder border = BorderFactory.createTitledBorder(title);
        border.setTitleFont(new Font("SansSerif", Font.BOLD, 16));
        return border;
    }

    private void limitInputLength(JTextField field, int maxLength) {
        AbstractDocument doc = (AbstractDocument) field.getDocument();
        doc.setDocumentFilter(new DocumentFilter() {
            @Override
            public void insertString(FilterBypass fb, int offset, String string, javax.swing.text.AttributeSet attr) throws javax.swing.text.BadLocationException {
                if ((fb.getDocument().getLength() + string.length()) <= maxLength) {
                    super.insertString(fb, offset, string, attr);
                }
            }

            @Override
            public void replace(FilterBypass fb, int offset, int length, String text, javax.swing.text.AttributeSet attrs) throws javax.swing.text.BadLocationException {
                if ((fb.getDocument().getLength() - length + (text != null ? text.length() : 0)) <= maxLength) {
                    super.replace(fb, offset, length, text, attrs);
                }
            }
        });
    }
}
