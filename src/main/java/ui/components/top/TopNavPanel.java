package ui.components.top;

import chart.ChartConfiguration;
import services.BinanceStreaming;
import ui.UIConfiguration;
import ui.components.center.ChartAndOrderPanel;
import ui.components.center.ChartPanel;
import utils.ResolutionInterval;
import utils.TradingPair;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class TopNavPanel extends JPanel {

    private final BinanceStreaming socketState = BinanceStreaming.getInstance();
    public static String currentPair = "BTC/USDT"; // get from default and wwhen change, set it again
    public static JComboBox<String> symbolDropdown = new JComboBox<>(new String[]{"BTC/USDT", "ETH/USDT", "XRP/USDT"});;
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
        symbolDropdown.addActionListener(e -> {
            // Need handle ticker24, chart, order book

            // Handle ticker24 and order book stream
            String currentTicker24Stream = socketState.getTicker24Stream();
            String currentOrderBookStream = socketState.getOrderBookStream();
            String symbolDropDownText = (String) symbolDropdown.getSelectedItem();

            // re-render when change for symbol info panel
            currentPair = symbolDropDownText;
            TradingPair pair = TradingPair.splitSymbol(symbolDropDownText);
            InfoSymbolPanel.controls.get(InfoSymbolPanel.LABEL_CURRENT_SYMBOL).setText(currentPair);
            InfoSymbolPanel.controls.get(InfoSymbolPanel.VOLUME_24_BASE_ASSET_LABEL).setText("24h Volume (" + pair.getBaseAsset() + ")");
            InfoSymbolPanel.controls.get(InfoSymbolPanel.VOLUME_24_QUOTE_ASSET_LABEL).setText("24h Volume (" + pair.getQuoteAsset() + ")");

            String parseDropDown = symbolDropDownText.replace("/", "").toLowerCase();
            String newStreamTicker = parseDropDown + "@ticker";
            String newStreamOrderBook = parseDropDown + "@depth";
            // check and send unsubscribe / subscribe payload

            if (!currentTicker24Stream.equals(newStreamTicker)) {
                String unSubPayload = socketState.createUnSubscribePayload(new String[]{currentTicker24Stream, currentOrderBookStream});
                socketState.getWebSocket().sendText(unSubPayload, true);
                socketState.setTicker24Stream(newStreamTicker);
                socketState.setOrderBookStream(newStreamOrderBook);
                String newSubPayload = socketState.createSubscribePayload(new String[]{newStreamTicker, newStreamOrderBook});
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
        resolutionDropdown.setSelectedItem(supportedResolution[2].getLabel());
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
