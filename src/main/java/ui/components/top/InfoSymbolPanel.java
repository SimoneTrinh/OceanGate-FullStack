package ui.components.top;

import com.google.gson.JsonObject;
import services.BinanceStreaming;
import ui.UIConfiguration;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.util.HashMap;
import java.util.Map;

public class InfoSymbolPanel extends JPanel implements BinanceStreaming.MessageListener {
    private final String CHANGE_PANEL_24 = "24changePanel";
    private final String HIGH_PANEL_24 = "24highPanel";
    private final String LOW_PANEL_24 = "24lowPanel";
    private final String VOLUME_PANEL_24_BTC = "24volumePanelBTC";
    private final String VOLUME_PANEL_24_USDT = "24volumePanelUSDT";

    private GridBagConstraints gbc;
    private Map<String, JLabel> controls = new HashMap<>();

    public InfoSymbolPanel(LayoutManager layout) {
        super(layout);
        this.setPreferredSize(new Dimension(UIConfiguration.INFO_SYMBOL_WIDTH, UIConfiguration.INFO_SYMBOL_HEIGHT));
        setBorder(BorderFactory.createEmptyBorder(0, 30, 0, 30));
        JPanel mainContent = new JPanel(new GridLayout());
        mainContent.setOpaque(false);
        BinanceStreaming.getInstance().addListener(this);


        renderStarIcon(mainContent);
        renderSymbolText(mainContent);
        renderCommonStat(mainContent, "24h Change", "1,534.84 +1.95%", CHANGE_PANEL_24);
        renderCommonStat(mainContent, "24h Change", "1,534.84 +1.95%", CHANGE_PANEL_24);
        renderCommonStat(mainContent, "24h High", "81,243.58", HIGH_PANEL_24);
        renderCommonStat(mainContent, "24h Low", "74,508.00", LOW_PANEL_24);
        renderCommonStat(mainContent, "24h Volume(BTC)", "76,581.28", VOLUME_PANEL_24_BTC);
        renderCommonStat(mainContent, "24h Volume(USDT)", "5,948,215,778.00", VOLUME_PANEL_24_USDT);

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

    private void renderCommonStat(JPanel panel, String label, String value, String panelID) {
        JLabel statLabel = new JLabel(label);
//        valueColor = Color.BLACK; // debugging
        statLabel.setForeground(Color.GRAY);
        statLabel.setFont(statLabel.getFont().deriveFont(12f));

        JLabel statValue = new JLabel(value);
        controls.put(panelID, statValue);
//        statValue.setForeground(valueColor);
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

    @Override
    public String getFlag() {
        return "24hrTicker";
    }

    @Override
    public void onMessageReceived(JsonObject jsonMessage) {
        // object mapping to panels
        System.out.println("123");

        System.out.println(jsonMessage.toString());
        String change = jsonMessage.get("p").getAsString();
        String percentChange = jsonMessage.get("P").getAsString();

        controls.get(CHANGE_PANEL_24).setText(change + " " + percentChange + "%");
//
//
        String high = jsonMessage.get("h").getAsString();
        controls.get(HIGH_PANEL_24).setText(high);
        String low = jsonMessage.get("l").getAsString();
        controls.get(LOW_PANEL_24).setText(low);
//
        System.out.println("Change: " + change + " Percent: " + percentChange + " High: " + high + " Low: " + low);
// TODO: add more panels
    }
}
