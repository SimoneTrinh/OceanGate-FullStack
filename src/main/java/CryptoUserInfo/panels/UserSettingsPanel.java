package CryptoUserInfo.panels;

import user_menu.UserMenu;

import javax.swing.*;
import java.awt.*;

public class UserSettingsPanel extends JPanel {
    public UserSettingsPanel() {
        setLayout(new BorderLayout());
        UserMenu userMenu = new UserMenu();
        add(userMenu);
    }
}

