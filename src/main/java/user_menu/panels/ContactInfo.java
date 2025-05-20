package user_menu.panels;

import net.miginfocom.swing.MigLayout;
import user_menu.components.UIUtils;

import javax.swing.*;

public class ContactInfo extends JPanel {
    public ContactInfo() {
        setLayout(new MigLayout("wrap 2", "[right][grow, fill]", "10[]10[]10"));
        setBorder(UIUtils.createStyledTitle("Contact Information"));

        UIUtils.addEditableField(this, "Email:", "tuan@example.com");
        UIUtils.addEditableField(this, "Phone:", "0123456789");
    }
}
