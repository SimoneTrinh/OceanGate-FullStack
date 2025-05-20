package org.example;

import com.formdev.flatlaf.FlatLightLaf;
import login.AuthDialog;
import javax.swing.*;
import java.util.concurrent.atomic.AtomicBoolean;
import user_menu.UserMenu;


public class Main extends JFrame {
    public Main() {
        setTitle("Test User Menu");
        setSize(1200, 800);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        FlatLightLaf.setup();
        UserMenu userMenu = new UserMenu();
        add(userMenu);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new Main().setVisible(true); // ẩn frame cho đến khi login thành công
        });
    }
}
