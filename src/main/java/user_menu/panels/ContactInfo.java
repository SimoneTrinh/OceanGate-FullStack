package user_menu.panels;

import model.SessionManager;
import model.User;
import model.UserStore;
import net.miginfocom.swing.MigLayout;
import user_menu.components.UIUtils;

import javax.swing.*;
import java.awt.*;

public class ContactInfo extends JPanel {
    private final JTextField txtEmail = new JTextField(20);
    private final JTextField txtPhone = new JTextField(20);
    private final JLabel lblMessage = new JLabel("");

    public ContactInfo() {
        User user = SessionManager.getCurrentUser();

        setLayout(new MigLayout("wrap 2", "[right][grow, fill]", "10[]10[]10[]10"));
        setBorder(UIUtils.createStyledTitle("Contact Information"));

        txtEmail.setText(user.getEmail());
        txtPhone.setText(user.getPhone());

        add(UIUtils.createStyledLabel("Email:"));
        add(txtEmail);

        add(UIUtils.createStyledLabel("Phone:"));
        add(txtPhone);

        lblMessage.setForeground(Color.RED);
        lblMessage.setFont(new Font("Arial", Font.PLAIN, 12));
        add(lblMessage, "span 2");

        JButton saveButton = UIUtils.createPrimaryButton("Save Changes");
        JButton clearButton = UIUtils.createSecondaryButton("Clear");

        add(saveButton, "span, split 2, sizegroup btn, gapright 10, align center");
        add(clearButton, "sizegroup btn");

        // ✅ Save button logic
        saveButton.addActionListener(e -> {
            String newEmail = txtEmail.getText().trim();
            String newPhone = txtPhone.getText().trim();

            try {
                user.setEmail(newEmail);  // validation inside setter
                user.setPhone(newPhone);  // validation inside setter

                lblMessage.setForeground(new Color(0, 153, 0));
                UserStore.updateUser(SessionManager.getCurrentUser());
                lblMessage.setText("Contact info updated successfully.");
            } catch (IllegalArgumentException ex) {
                lblMessage.setForeground(Color.RED);
                lblMessage.setText(ex.getMessage());
            }
        });

        // 🔁 Clear/reset logic
        clearButton.addActionListener(e -> {
            txtEmail.setText(user.getEmail());
            txtPhone.setText(user.getPhone());
            lblMessage.setText("");
        });
    }
}
