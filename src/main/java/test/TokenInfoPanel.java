package test;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class TokenInfoPanel extends JPanel {

    public TokenInfoPanel() {
        setLayout(new BorderLayout());
        setBackground(new Color(24, 26, 32)); // Dark theme background
        setBorder(new EmptyBorder(10, 20, 10, 20));

        JPanel mainContent = new JPanel();
        mainContent.setLayout(new BoxLayout(mainContent, BoxLayout.X_AXIS));
        mainContent.setOpaque(false); // transparent to parent bg

        // === Left: Symbol Panel ===
        JPanel symbolPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        symbolPanel.setOpaque(false);

        JLabel starIcon = new JLabel("★"); // Replace with icon if you want
        starIcon.setForeground(new Color(255, 204, 0));

        JLabel pairLabel = new JLabel("BTC/USDT");
        pairLabel.setForeground(Color.WHITE);
        pairLabel.setFont(pairLabel.getFont().deriveFont(Font.BOLD, 16f));

        JLabel descLabel = new JLabel("Bitcoin Price ↗");
        descLabel.setForeground(Color.GRAY);
        descLabel.setFont(descLabel.getFont().deriveFont(12f));

        JPanel symbolText = new JPanel();
        symbolText.setLayout(new BoxLayout(symbolText, BoxLayout.Y_AXIS));
        symbolText.setOpaque(false);
        symbolText.add(pairLabel);
        symbolText.add(descLabel);

        symbolPanel.add(starIcon);
        symbolPanel.add(symbolText);

        // === Price Panel ===
        JLabel priceLabel = new JLabel("80,372.12");
        priceLabel.setForeground(Color.RED);
        priceLabel.setFont(priceLabel.getFont().deriveFont(Font.BOLD, 18f));
        priceLabel.setBorder(new EmptyBorder(0, 20, 0, 20));

        // === Stats Panel ===
        JPanel statsPanel = new JPanel();
        statsPanel.setLayout(new GridLayout(2, 4, 20, 4)); // rows x cols
        statsPanel.setOpaque(false);
        statsPanel.setBorder(new EmptyBorder(0, 20, 0, 20));

        addStat(statsPanel, "24h Change", "1,534.84 +1.95%", new Color(0, 200, 120));
        addStat(statsPanel, "24h High", "81,243.58", Color.WHITE);
        addStat(statsPanel, "24h Low", "74,508.00", Color.WHITE);
        addStat(statsPanel, "24h Volume(BTC)", "76,581.28", Color.WHITE);
        addStat(statsPanel, "24h Volume(USDT)", "5,948,215,778.00", Color.WHITE);

        // === Tags Panel ===
        JPanel tagPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 0));
        tagPanel.setOpaque(false);

        addTag(tagPanel, "POW");
        addTag(tagPanel, "Payments");
        addTag(tagPanel, "Vol");
        addTag(tagPanel, "Hot");
        addTag(tagPanel, "Price Protection");

        // === Assemble ===
        mainContent.add(symbolPanel);
        mainContent.add(priceLabel);
        mainContent.add(statsPanel);

        this.add(mainContent, BorderLayout.CENTER);
        this.add(tagPanel, BorderLayout.SOUTH);
    }

    private void addStat(JPanel panel, String label, String value, Color valueColor) {
        JLabel statLabel = new JLabel(label);
        statLabel.setForeground(Color.GRAY);
        statLabel.setFont(statLabel.getFont().deriveFont(12f));

        JLabel statValue = new JLabel(value);
        statValue.setForeground(valueColor);
        statValue.setFont(statValue.getFont().deriveFont(Font.BOLD, 13f));

        JPanel stat = new JPanel();
        stat.setLayout(new BoxLayout(stat, BoxLayout.Y_AXIS));
        stat.setOpaque(false);
        stat.add(statLabel);
        stat.add(statValue);

        panel.add(stat);
    }

    private void addTag(JPanel panel, String text) {
        JLabel tag = new JLabel(text);
        tag.setForeground(new Color(255, 204, 0));
        tag.setFont(tag.getFont().deriveFont(Font.BOLD, 12f));
        panel.add(tag);
    }

    // For testing
    public static void main(String[] args) {
        JFrame frame = new JFrame("Token Info");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().setBackground(new Color(18, 20, 24));
        frame.add(new TokenInfoPanel());
        frame.setSize(1100, 150);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}
