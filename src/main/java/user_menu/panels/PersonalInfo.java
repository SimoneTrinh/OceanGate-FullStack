package user_menu.panels;

import net.miginfocom.swing.MigLayout;
import user_menu.components.UIUtils;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;

public class PersonalInfo extends JPanel {
    public PersonalInfo() {
        setLayout(new MigLayout("wrap 2", "[right][grow, fill]", "10[]10[]10"));
        setBorder(UIUtils.createStyledTitle("Personal Information"));

        UIUtils.addEditableField(this, "First Name:", "Tuan");
        UIUtils.addEditableField(this, "Last Name:", "Nguyen");
    }
}
