package ui.components.top;

import ui.UIConfiguration;

import javax.swing.*;
import java.awt.*;

public class TopNavPanel extends JPanel {
    public TopNavPanel(LayoutManager layout) {
        super(layout);
        this.setPreferredSize(new Dimension(UIConfiguration.TOP_NAV_WIDTH, UIConfiguration.TOP_NAV_HEIGHT));

        // Main layout using BorderLayout
        setLayout(new BorderLayout());

        // Top panel for logo, symbol dropdown, resolution dropdown, and user menu
        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.setBorder(BorderFactory.createLineBorder(Color.RED)); // Red border for visibility

        // Logo (left side of top panel)
        JLabel logoLabel = new JLabel("LOGO", SwingConstants.CENTER);
        logoLabel.setPreferredSize(new Dimension(100, 50));
        topPanel.add(logoLabel, BorderLayout.WEST);

        // Center of top panel for dropdowns
        JPanel dropdownPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JComboBox<String> symbolDropdown = new JComboBox<>(new String[]{"BTC/USD", "ETH/USD", "XRP/USD"});
        JComboBox<String> resolutionDropdown = new JComboBox<>(new String[]{"1m", "5m", "15m", "1h"});
        dropdownPanel.add(new JLabel("Symbol:"));
        dropdownPanel.add(symbolDropdown);
        dropdownPanel.add(new JLabel("Resolution:"));
        dropdownPanel.add(resolutionDropdown);
        topPanel.add(dropdownPanel, BorderLayout.CENTER);

        // User menu (right side of top panel)
        JButton userMenuButton = new JButton("User Menu");
        topPanel.add(userMenuButton, BorderLayout.EAST);

        add(topPanel);
//        this.setBackground(Color.YELLOW);
//        JLabel logoLabel = new JLabel("LOGO", SwingConstants.CENTER);
//        logoLabel.setPreferredSize(new Dimension(100, 50));
//        this.add(logoLabel, BorderLayout.WEST);
    }
}
