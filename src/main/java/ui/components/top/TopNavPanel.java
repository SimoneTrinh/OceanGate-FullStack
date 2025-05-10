package ui.components.top;

import chart.ChartConfiguration;
import services.BinanceStreaming;
import ui.UIConfiguration;
import ui.components.center.ChartAndOrderPanel;
import ui.components.center.ChartPanel;
import utils.ResolutionInterval;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class TopNavPanel extends JPanel {

    private final BinanceStreaming socketState = BinanceStreaming.getInstance();
    public static String currentPair = "BTC/USDT"; // get from default and wwhen change, set it again
    private JComboBox<String> symbolDropdown;
    private JComboBox<String> resolutionDropdown;


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

        renderSymbolDropDown();
        renderIntervalDropDown();

        dropdownPanel.add(new JLabel("Symbol:"));
        dropdownPanel.add(symbolDropdown);
        dropdownPanel.add(new JLabel("Resolution:"));
        dropdownPanel.add(resolutionDropdown);
        topPanel.add(dropdownPanel, BorderLayout.CENTER);

        // User menu (right side of top panel)
        JButton userMenuButton = new JButton("User Menu");
        topPanel.add(userMenuButton, BorderLayout.EAST);

        add(topPanel);
    }

    private void renderSymbolDropDown() {
        symbolDropdown = new JComboBox<>(new String[]{"BTC/USDT", "ETH/USDT", "XRP/USDT"});
        symbolDropdown.addActionListener(e -> {
            // Need handle ticker24, chart, order book

            // Handle ticker24
            String currentTicker24Stream = socketState.getTicker24Stream();
            String symbolDropDownText = (String) symbolDropdown.getSelectedItem();
            currentPair = symbolDropDownText; // for symbol info panel
            String parseDropDown = symbolDropDownText.replace("/", "").toLowerCase();
            String newStream = parseDropDown + "@ticker";
            // check and send unsubscribe / subscribe payload

            if (!currentTicker24Stream.equals(newStream)) {
                String unSubPayload = socketState.createUnSubscribePayload(currentTicker24Stream);
                socketState.getWebSocket().sendText(unSubPayload, true);
                socketState.setTicker24Stream(newStream);
                String newSubPayload = socketState.createSubscribePayload(parseDropDown + "@ticker");
                socketState.getWebSocket().sendText(newSubPayload, true);
            }

            // Handle chart
            String resolution = ResolutionInterval.getValueByLabel(supportedResolution, (String) resolutionDropdown.getSelectedItem());
            String query = ChartConfiguration.generateQuery(parseDropDown, resolution, ChartConfiguration.CURRENT_THEME);
            ChartPanel.cefBrowser.loadURL(ChartConfiguration.CHART_BASE_URL + query);

            // Handle order book
            System.out.println(symbolDropdown.getSelectedItem());
        });
    }

    private final ResolutionInterval[] supportedResolution = {
            new ResolutionInterval("1", "1 minute"),
            new ResolutionInterval("15", "15 minutes"),
            new ResolutionInterval("60", "1 hour"),
            new ResolutionInterval("240", "4 hours"),
            new ResolutionInterval("1D", "1 day")
    };

    private void renderIntervalDropDown() {
        resolutionDropdown = new JComboBox<>(ResolutionInterval.getAllLabels(supportedResolution));
        resolutionDropdown.addActionListener(e -> {
            // Handle chart
            String symbolDropDownText = (String) symbolDropdown.getSelectedItem();
            String parseDropDown = symbolDropDownText.replace("/", "").toLowerCase();
            String resolution = ResolutionInterval.getValueByLabel(supportedResolution, (String) resolutionDropdown.getSelectedItem());

            String query = ChartConfiguration.generateQuery(parseDropDown, resolution, "dark");
            ChartPanel.cefBrowser.loadURL(ChartConfiguration.CHART_BASE_URL + query);
        });
    }
}
