package user_menu.panels;

import dataAccess.UserStore;
import net.miginfocom.swing.MigLayout;
import user_menu.components.UIUtils;

import javax.swing.*;
import controller.SessionManager;

import java.awt.*;

public class PersonalInfo extends JPanel {
    private final JTextField txtFirstName = new JTextField(20);
    private final JTextField txtLastName = new JTextField(20);
    private final JLabel lblMessage = new JLabel("");

    public PersonalInfo() {
        setLayout(new MigLayout("wrap 2", "[right][grow, fill]", "10[]10[]10[]10"));
        setBorder(UIUtils.createStyledTitle("Personal Information"));

        String firstname = SessionManager.getCurrentUser().getFirstName();
        String lastname = SessionManager.getCurrentUser().getLastName();

        txtFirstName.setText(firstname);
        txtLastName.setText(lastname);

        add(UIUtils.createStyledLabel("First Name:"));
        add(txtFirstName);

        add(UIUtils.createStyledLabel("Last Name:"));
        add(txtLastName);

        lblMessage.setForeground(Color.RED);
        lblMessage.setFont(new Font("Arial", Font.PLAIN, 12));
        add(lblMessage, "span 2");

        JButton saveButton = UIUtils.createPrimaryButton("Save Changes");
        JButton cancelButton = UIUtils.createSecondaryButton("Cancel");

        add(saveButton, "span, split 2, sizegroup btn, gapright 10, align center");
        add(cancelButton, "sizegroup btn");

        // ✅ Save logic
        saveButton.addActionListener(e -> {
            String newFirst = txtFirstName.getText().trim();
            String newLast = txtLastName.getText().trim();

            if (newFirst.isEmpty() || newLast.isEmpty()) {
                lblMessage.setForeground(Color.RED);
                lblMessage.setText("First and Last name cannot be empty.");
                return;
            }

            SessionManager.getCurrentUser().setFirstName(newFirst);
            SessionManager.getCurrentUser().setLastName(newLast);

            lblMessage.setForeground(new Color(0, 153, 0));
            lblMessage.setText("Information updated successfully.");
            UserStore.updateUser(SessionManager.getCurrentUser());
        });

        // 🔄 Cancel logic
        cancelButton.addActionListener(e -> {
            txtFirstName.setText(SessionManager.getCurrentUser().getFirstName());
            txtLastName.setText(SessionManager.getCurrentUser().getLastName());
            lblMessage.setText("");
        });
    }
}

