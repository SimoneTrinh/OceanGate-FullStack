package ui.components.top;

import ui.UIConfiguration;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;

public class InfoSymbolPanel extends JPanel {
    private GridBagConstraints gbc;

    public InfoSymbolPanel(LayoutManager layout) {
        super(layout);
        this.setPreferredSize(new Dimension(UIConfiguration.INFO_SYMBOL_WIDTH, UIConfiguration.INFO_SYMBOL_HEIGHT));
        setBorder(BorderFactory.createEmptyBorder(0, 30, 0, 30));


        JPanel mainContent = new JPanel(new GridLayout());
//        mainContent.setLayout(new GridBagLayout());

//        gbc = new GridBagConstraints();
//        gbc.gridx = 0;
//        gbc.gridy = 0;
//        gbc.anchor = GridBagConstraints.CENTER;

        mainContent.setOpaque(false); // transparent to parent bg


        renderStarIcon(mainContent);
        renderSymbolText(mainContent);
        renderCommonStat(mainContent, "24h Change", "1,534.84 +1.95%", new Color(0, 200, 120));
        renderCommonStat(mainContent, "24h Change", "1,534.84 +1.95%", new Color(0, 200, 120));
        renderCommonStat(mainContent, "24h High", "81,243.58", Color.WHITE);
        renderCommonStat(mainContent, "24h Low", "74,508.00", Color.WHITE);
        renderCommonStat(mainContent, "24h Volume(BTC)", "76,581.28", Color.WHITE);
        renderCommonStat(mainContent, "24h Volume(USDT)", "5,948,215,778.00", Color.WHITE);

        add(mainContent);
    }

    private void renderStarIcon(JPanel panel) {
        JLabel starIcon = new JLabel("★"); // Replace with icon if you want
        starIcon.setForeground(new Color(255, 204, 0));
        panel.add(starIcon);
    }

    private void renderSymbolText(JPanel panel) {
        JLabel pairLabel = new JLabel("BTC/USDT");
        pairLabel.setForeground(Color.GREEN);
        pairLabel.setFont(pairLabel.getFont().deriveFont(Font.BOLD, 16f));

        JLabel descLabel = new JLabel("Bitcoin Price ↗");
        descLabel.setForeground(Color.GRAY);
        descLabel.setFont(descLabel.getFont().deriveFont(12f));
        JPanel symbolText = new JPanel();
        symbolText.setLayout(new FlowLayout(FlowLayout.CENTER));
        symbolText.setOpaque(false);
        symbolText.add(pairLabel);
        symbolText.add(descLabel);
        panel.add(symbolText);
    }

    private void renderCommonStat(JPanel panel, String label, String value, Color valueColor) {
        JLabel statLabel = new JLabel(label);
        valueColor = Color.BLACK; // debugging
        statLabel.setForeground(Color.GRAY);
        statLabel.setFont(statLabel.getFont().deriveFont(12f));

        JLabel statValue = new JLabel(value);
        statValue.setForeground(valueColor);
        statValue.setFont(statValue.getFont().deriveFont(Font.BOLD, 13f));

        JPanel stat = new JPanel();
        stat.setLayout(new BoxLayout(stat, BoxLayout.Y_AXIS));
        stat.setOpaque(false);
        stat.add(Box.createVerticalStrut(10));
        stat.add(statLabel);
        stat.add(Box.createVerticalGlue());
        stat.add(statValue);
        stat.add(Box.createVerticalStrut(10));
        panel.add(stat);
    }

}
