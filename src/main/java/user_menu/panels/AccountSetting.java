package user_menu.panels;

import controller.SessionManager;
import dataAccess.UserStore;
import net.miginfocom.swing.MigLayout;
import user_menu.components.UIUtils;

import javax.swing.*;
import java.awt.*;

public class AccountSetting extends JPanel {
    private final JPasswordField txtCurrentPassword = new JPasswordField(20);
    private final JPasswordField txtNewPassword = new JPasswordField(20);
    private final JPasswordField txtConfirmPassword = new JPasswordField(20);
    private final JLabel lblMessage = new JLabel("");

    public AccountSetting() {
        setLayout(new MigLayout("wrap 2", "[right][grow, fill]", "15[]10[]10[]10[]15[]10[]"));
        setBorder(UIUtils.createStyledTitle("Change Password"));

        lblMessage.setForeground(Color.RED);
        lblMessage.setFont(new Font("Arial", Font.PLAIN, 12));

        add(UIUtils.createStyledLabel("Current Password:"));
        add(txtCurrentPassword);

        add(UIUtils.createStyledLabel("New Password:"));
        add(txtNewPassword);

        add(UIUtils.createStyledLabel("Confirm Password:"));
        add(txtConfirmPassword);

        add(lblMessage, "span 2");

        JButton saveButton = UIUtils.createPrimaryButton("Save Changes");
        JButton cancelButton = UIUtils.createSecondaryButton("Clear All");

        add(saveButton, "span, split 2, sizegroup btn, gapright 10, align center");
        add(cancelButton, "sizegroup btn");

        // 🔒 Save button logic
        saveButton.addActionListener(e -> {
            String currentPass = new String(txtCurrentPassword.getPassword());
            String newPass = new String(txtNewPassword.getPassword());
            String confirmPass = new String(txtConfirmPassword.getPassword());

            if (currentPass.isEmpty() || newPass.isEmpty() || confirmPass.isEmpty()) {
                lblMessage.setText("All fields are required.");
                return;
            }

            String actualCurrentPass = SessionManager.getCurrentUser().getPassword();

            if (!currentPass.equals(actualCurrentPass)) {
                lblMessage.setText("Current password is incorrect.");
                return;
            }

            if (!newPass.equals(confirmPass)) {
                lblMessage.setText("New passwords do not match.");
                return;
            }

            // ✅ Save new password
            SessionManager.getCurrentUser().setPassword(newPass);
            lblMessage.setForeground(new Color(0, 153, 0));
            lblMessage.setText("Password changed successfully.");
            UserStore.updateUser(SessionManager.getCurrentUser());
            clearFields();
        });

        // 🔁 Clear button logic
        cancelButton.addActionListener(e -> {
            clearFields();
            lblMessage.setText("");
        });
    }

    private void clearFields() {
        txtCurrentPassword.setText("");
        txtNewPassword.setText("");
        txtConfirmPassword.setText("");
    }
}
