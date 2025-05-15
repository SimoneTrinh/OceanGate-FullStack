package org.example;

import com.formdev.flatlaf.FlatLaf;
import com.formdev.flatlaf.fonts.roboto.FlatRobotoFont;
import com.formdev.flatlaf.themes.FlatMacDarkLaf;
import login.AuthDialog;
import javax.swing.*;
import java.awt.*;
import java.util.concurrent.atomic.AtomicBoolean;


public class Main extends JFrame {
    public Main() {
        setTitle("Crypto Trading App");
        setSize(1200, 800);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Bắt đầu với giao diện ứng dụng chính
        CryptoTradingPlatformUI appPanel = new CryptoTradingPlatformUI();
        add(appPanel);

        AtomicBoolean loginSuccess = new AtomicBoolean(false);

        // Chạy Auth Dialog
        SwingUtilities.invokeLater(() -> {
            AuthDialog authDialog = new AuthDialog(this, () -> {
                loginSuccess.set(true);
                this.setVisible(true); // hiện main app sau login
            });

            authDialog.setVisible(true);

            // Sau khi dialog đóng, kiểm tra xem login có thành công không
            if (!loginSuccess.get()) {
                System.exit(0); // Thoát chương trình nếu login thất bại
            }
        });
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new Main().setVisible(false); // ẩn frame cho đến khi login thành công
        });
    }
}
