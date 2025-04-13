package org.example;

import com.formdev.flatlaf.FlatDarculaLaf;
import com.formdev.flatlaf.FlatLaf;
import com.formdev.flatlaf.fonts.roboto.FlatRobotoFont;
import com.formdev.flatlaf.themes.FlatMacDarkLaf;
import login.AuthPanel;
import login.LoginPage;
import javax.swing.*;
import java.awt.*;


public class Main {
    public static void main(String[] args) {
        System.out.println("Hello, World!");
        FlatRobotoFont.install();
        FlatLaf.registerCustomDefaultsSource("raven.themes");
//        UIManager.put("defaultFont", new Font(FlatRobotoFont.FAMILY, Font.PLAIN, 13));
        FlatMacDarkLaf.setup();
        SwingUtilities.invokeLater(() -> {
//            CryptoTradingPlatformUI ui = new CryptoTradingPlatformUI();
            JFrame ui = new JFrame();
            AuthPanel authPanel = new AuthPanel();

            ui.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            ui.setTitle("Test Login Page");
            ui.setLocationRelativeTo(null);
            ui.setSize(800, 600);
            ui.setContentPane(authPanel);
            ui.setVisible(true);
        });
    }

}