package org.example;

import login.AuthDialog;
import javax.swing.*;
import CryptoUserInfo.CryptoUserInfo;
import java.util.concurrent.atomic.AtomicBoolean;


public class Main extends JFrame {
    public Main() {
        setTitle("Crypto Trading App");
        setSize(1200, 800);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        CryptoUserInfo ui = new CryptoUserInfo();
        add(ui);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new Main().setVisible(true); // ẩn frame cho đến khi login thành công
        });
    }
}
