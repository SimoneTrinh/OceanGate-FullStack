package CryptoUserInfo.panels;

import javax.swing.*;
import java.awt.*;

public class UserSettingsPanel extends JPanel {
    public UserSettingsPanel() {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setBorder(BorderFactory.createEmptyBorder(30, 50, 30, 50));

        add(new JLabel("Settings"));
        add(Box.createRigidArea(new Dimension(0, 20)));
        add(new JLabel("Username: tuan.crypto"));
        add(new JLabel("Email: tuan@example.com"));
        add(new JLabel("2FA: Enabled"));
        add(new JLabel("Language: English"));

        // You can add buttons for Update, Save, etc.
    }
}
