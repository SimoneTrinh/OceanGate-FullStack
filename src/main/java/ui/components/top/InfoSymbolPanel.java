package ui.components.top;

import com.google.gson.JsonObject;
import services.BinanceStreaming;
import ui.UIConfiguration;
import ui.components.left.MainLeftPanel;
import utils.Constants;
import utils.NumberConversion;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;
import javax.swing.border.MatteBorder;
import java.awt.*;
import java.util.HashMap;
import java.util.Map;

public class InfoSymbolPanel extends JPanel implements BinanceStreaming.MessageListener {
    private final String CHANGE_PANEL_24 = "24changePanel";
    private final String HIGH_PANEL_24 = "24highPanel";
    private final String LOW_PANEL_24 = "24lowPanel";
    private final String VOLUME_PANEL_24_BASE_ASSET_VALUE = "24volumeBaseAssetValue";
    private final String VOLUME_PANEL_24_QUOTE_ASSET_VALUE = "24volumeQuoteAssetValue";
    private final String CURRENT_PRICE_LABEL = "currentPriceLabel";
    private final String CURRENT_PRICE_USD_LABEL = "currentPriceUSDLabel";
    private final String SYMBOL_DESC_LABEL = "symbolDescriptionLabel";
    public final static String LABEL_CURRENT_SYMBOL = "labelCurrentSymbol"; // render again in top nav
    public final static String VOLUME_24_BASE_ASSET_LABEL = "24BaseAssetLabel"; // render again in top nav
    public final static String VOLUME_24_QUOTE_ASSET_LABEL = "24QuoteAssetLabel"; // render again in top nav
    private float previousPrice = 0;

    public static Map<String, JLabel> controls = new HashMap<>();

    public InfoSymbolPanel(LayoutManager layout) {
        super(layout);
        this.setPreferredSize(new Dimension(UIConfiguration.INFO_SYMBOL_WIDTH, UIConfiguration.INFO_SYMBOL_HEIGHT));
        setBorder(new LineBorder(Color.BLACK, 2, true));
//        setBorder(new MatteBorder(0, 3, 3, 3, Color.BLACK));

        JPanel mainContent = new JPanel(new GridLayout());
        mainContent.setOpaque(false);
        mainContent.setBorder(BorderFactory.createEmptyBorder(0, 30, 0, 30));

        BinanceStreaming.getInstance().addListener(this);


        renderStarIcon(mainContent);
        renderSymbolText(mainContent);
        renderPriceStat(mainContent);
        renderCommonStat(mainContent, "24h Change", CHANGE_PANEL_24);
        renderCommonStat(mainContent, "24h High", HIGH_PANEL_24);
        renderCommonStat(mainContent, "24h Low", LOW_PANEL_24);
        renderCommonStat(mainContent, "24h Volume (BTC)", VOLUME_PANEL_24_BASE_ASSET_VALUE);
        renderCommonStat(mainContent, "24h Volume (USDT)", VOLUME_PANEL_24_QUOTE_ASSET_VALUE);

        add(mainContent);
    }

    private void renderStarIcon(JPanel panel) {
        JLabel starIcon = new JLabel("★"); // Replace with icon if you want
        starIcon.setForeground(new Color(255, 204, 0));
        panel.add(starIcon);
    }

    private JPanel renderInformation(JLabel firstLine, JLabel secondLine) {
        JPanel stat = new JPanel();
        stat.setLayout(new BoxLayout(stat, BoxLayout.Y_AXIS));
        stat.setOpaque(false);
        stat.add(Box.createVerticalStrut(10));
        stat.add(firstLine);
        stat.add(Box.createVerticalGlue());
        stat.add(secondLine);
        stat.add(Box.createVerticalStrut(10));
        return stat;
    }

    private void renderSymbolText(JPanel panel) {
        JLabel pairLabel = new JLabel(TopNavPanel.currentPair); // need set again when changing pair - ex: BTC/USDT
        pairLabel.setForeground(Color.WHITE);
        pairLabel.setFont(pairLabel.getFont().deriveFont(Font.BOLD, 20f));

        JLabel descLabel = new JLabel("Price ↗");
        descLabel.setForeground(Constants.COLOR_GRAY);
        descLabel.setFont(descLabel.getFont().deriveFont(12f));

        controls.put(LABEL_CURRENT_SYMBOL, pairLabel);
        controls.put(SYMBOL_DESC_LABEL, descLabel);

        panel.add(renderInformation(pairLabel, descLabel));
    }

    private void renderPriceStat(JPanel panel) {
        JLabel firstLinePrice = new JLabel(); // need set again when changing pair
        firstLinePrice.setForeground(Constants.COLOR_GREEN); // logic green when up, red when down
        firstLinePrice.setFont(firstLinePrice.getFont().deriveFont(Font.BOLD, 20f));
        controls.put(CURRENT_PRICE_LABEL, firstLinePrice);

        JLabel secondLinePrice = new JLabel();
        controls.put(CURRENT_PRICE_USD_LABEL, secondLinePrice);
        secondLinePrice.setForeground(Color.WHITE);
        secondLinePrice.setFont(secondLinePrice.getFont().deriveFont(12f));


        panel.add(renderInformation(firstLinePrice, secondLinePrice));
    }


    private void renderCommonStat(JPanel panel, String label, String panelID) {
        JLabel statLabel = new JLabel(label);
        statLabel.setForeground(Constants.COLOR_GRAY);
        statLabel.setFont(statLabel.getFont().deriveFont(12f));
        if(panelID.equals(VOLUME_PANEL_24_BASE_ASSET_VALUE)){
            controls.put(VOLUME_24_BASE_ASSET_LABEL, statLabel);
        }else if (panelID.equals(VOLUME_PANEL_24_QUOTE_ASSET_VALUE)){
            controls.put(VOLUME_24_QUOTE_ASSET_LABEL, statLabel);
        }

        JLabel statValue = new JLabel();
        controls.put(panelID, statValue);
        statValue.setFont(statValue.getFont().deriveFont(Font.BOLD, 13f));
        statValue.setForeground(Color.WHITE);

        panel.add(renderInformation(statLabel, statValue));
    }

    @Override
    public String getFlag() {
        return "24hrTicker";
    }

    @Override
    public void onMessageReceived(JsonObject jsonMessage) {
        // 24h change
        String change = jsonMessage.get("p").getAsString();
        String percentChange = jsonMessage.get("P").getAsString();
        String changeText = NumberConversion.convertStringToFloat(change) + " " + NumberConversion.convertStringToFloat(percentChange) + "%";
        if (Float.parseFloat(change) < 0) {
            controls.get(CHANGE_PANEL_24).setForeground(Constants.COLOR_RED);
        } else if (Float.parseFloat(change) >= 0) {
            changeText = "+" + NumberConversion.convertStringToFloat(change) + " +" + NumberConversion.convertStringToFloat(percentChange) + "%";
            controls.get(CHANGE_PANEL_24).setForeground(Constants.COLOR_RED);
        }
        controls.get(CHANGE_PANEL_24).setText(changeText);

        // Price & Order book price
        String open = jsonMessage.get("c").getAsString();
        String formattedFloatPrice = NumberConversion.convertStringToFloat(open);
        MainLeftPanel.labelComponents.get(MainLeftPanel.CURRENT_PRICE_LABEL).setText(formattedFloatPrice); // order book price

        controls.get(CURRENT_PRICE_LABEL).setText(formattedFloatPrice);
        if (Float.parseFloat(open) < previousPrice) {
            controls.get(CURRENT_PRICE_LABEL).setForeground(Constants.COLOR_RED);
            MainLeftPanel.labelComponents.get(MainLeftPanel.CURRENT_PRICE_LABEL).setForeground(Constants.COLOR_RED);
            controls.get(SYMBOL_DESC_LABEL).setText("Price ↘");
        } else if (Float.parseFloat(open) > previousPrice) {
            controls.get(CURRENT_PRICE_LABEL).setForeground(Constants.COLOR_GREEN);
            MainLeftPanel.labelComponents.get(MainLeftPanel.CURRENT_PRICE_LABEL).setForeground(Constants.COLOR_GREEN);
            controls.get(SYMBOL_DESC_LABEL).setText("Price ↗");
        }
        previousPrice = Float.parseFloat(open); // set as current price
        controls.get(CURRENT_PRICE_USD_LABEL).setText("$" + formattedFloatPrice);
        MainLeftPanel.labelComponents.get(MainLeftPanel.CURRENT_PRICE_USD).setText("$" + formattedFloatPrice);

        // High
        String high = jsonMessage.get("h").getAsString();
        controls.get(HIGH_PANEL_24).setText(NumberConversion.convertStringToFloat(high));

        // Low
        String low = jsonMessage.get("l").getAsString();
        controls.get(LOW_PANEL_24).setText(NumberConversion.convertStringToFloat(low));

        // Vol
        String volume = jsonMessage.get("v").getAsString(); // base asset
        controls.get(VOLUME_PANEL_24_BASE_ASSET_VALUE).setText(NumberConversion.convertStringToFloat(volume));

        // Quote Vol
        String quoteVolume = jsonMessage.get("q").getAsString(); // quote asset
        controls.get(VOLUME_PANEL_24_QUOTE_ASSET_VALUE).setText(NumberConversion.convertStringToFloat(quoteVolume));

        System.out.println("Change: " + change + " Percent: " + percentChange + " High: " + high + " Low: " + low);
    }
}
