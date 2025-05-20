package user_menu.panels;

import net.miginfocom.swing.MigLayout;
import user_menu.components.UIUtils;

import javax.swing.*;

public class AccountSetting extends JPanel {
    public AccountSetting() {
        setLayout(new MigLayout("wrap 2", "[right][grow, fill]", "15[]10[]10[]10[]15[]"));
        setBorder(UIUtils.createStyledTitle("Change Password"));


        add(UIUtils.createStyledLabel("Current Password:"));
        add(UIUtils.createPasswordField(20));

        add(UIUtils.createStyledLabel("New Password:"));
        add(UIUtils.createPasswordField(20));

        add(UIUtils.createStyledLabel("Confirm Password:"));
        add(UIUtils.createPasswordField(20));

        JButton saveButton = UIUtils.createPrimaryButton("Save Changes");
        JButton cancelButton = UIUtils.createSecondaryButton("Cancel");

        add(saveButton, "span, split 2, sizegroup btn, gapright 10, align center");
        add(cancelButton, "sizegroup btn");
    }
}
