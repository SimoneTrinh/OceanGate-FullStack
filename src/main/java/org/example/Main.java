package org.example;

import javax.swing.*;


public class Main {
    public static void main(String[] args) {
        System.out.println("Hello, World!");
        SwingUtilities.invokeLater(() -> {
            CryptoTradingPlatformUI ui = new CryptoTradingPlatformUI();
            ui.setVisible(true);
        });
    }

}