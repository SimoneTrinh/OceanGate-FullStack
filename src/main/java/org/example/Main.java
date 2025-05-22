package org.example;

import javax.swing.*;


public class Main extends JFrame {
    public Main() {
        setTitle("Crypto Trading App");
        setSize(1200, 800);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new Main().setVisible(true); // ẩn frame cho đến khi login thành công
        });
    }
}
