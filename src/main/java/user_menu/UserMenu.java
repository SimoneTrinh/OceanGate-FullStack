package user_menu;

import com.formdev.flatlaf.fonts.roboto.FlatRobotoFont;
import net.miginfocom.swing.MigLayout;
import user_menu.panels.PersonalInfo;
import user_menu.panels.ContactInfo;
import user_menu.panels.AccountSetting;
import user_menu.components.SideBar;
import javax.swing.*;
import java.awt.*;

public class UserMenu extends JPanel {
    private CardLayout cardLayout;
    private JPanel contentPanel;

    public UserMenu() {
        FlatRobotoFont.install();
        UIManager.put("defaultFont", new Font("Roboto", Font.PLAIN, 14));

        setLayout(new BorderLayout());
        setBackground(new Color(247, 228, 236)); // trắng hồng nhẹ

        setLayout(new MigLayout("wrap 1", "[grow, fill]", "15[]20[]20[]"));

        // Gộp 3 phần vào một panel
        add(new PersonalInfo());
        add(new ContactInfo());
        add(new AccountSetting());
    }
    private void showPage(String name) {
        cardLayout.show(contentPanel, name);
    }

}
