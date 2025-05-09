package ui.components.top;

import chart.ChartConfiguration;
import services.BinanceStreaming;
import ui.UIConfiguration;
import ui.components.center.ChartAndOrderPanel;
import ui.components.center.ChartPanel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class TopNavPanel extends JPanel {

    private final BinanceStreaming socketState = BinanceStreaming.getInstance();

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
        JComboBox<String> symbolDropdown = new JComboBox<>(new String[]{"BTC/USDT", "ETH/USDT", "XRP/USDT"});
        JComboBox<String> resolutionDropdown = new JComboBox<>(new String[]{"1", "15", "60", "240", "1D"});

        symbolDropdown.addActionListener(e -> {
            // Need handle ticker24, chart, order book

            // Handle ticker24
            String currentTicker24Stream = socketState.getTicker24Stream();
            String dropDownText = (String) symbolDropdown.getSelectedItem();
            String parseDropDown = dropDownText.replace("/", "").toLowerCase();
            String newStream = parseDropDown + "@ticker";
            // check and send unsubscrible / subscrible payload

            if (!currentTicker24Stream.equals(newStream)) {
                String unSubPayload = socketState.createUnSubscribePayload(currentTicker24Stream);
                socketState.getWebSocket().sendText(unSubPayload, true);
                socketState.setTicker24Stream(newStream);
                String newSubPayload = socketState.createSubscribePayload(parseDropDown + "@ticker");
                socketState.getWebSocket().sendText(newSubPayload, true);
            }

            // Handle chart
            String query = ChartConfiguration.generateQuery(parseDropDown, (String) resolutionDropdown.getSelectedItem(), "dark");
            ChartPanel.cefBrowser.loadURL(ChartConfiguration.CHART_BASE_URL + query);

            // Handle order book
            System.out.println(symbolDropdown.getSelectedItem());
        });

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

    private void renderPairDropDown(JPanel jPanel) {

    }
}
