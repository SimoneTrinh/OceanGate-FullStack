package ui.components.center;

import ui.UIConfiguration;

import javax.swing.*;
import java.awt.*;

public class TradeTablePanel extends JPanel {
    public TradeTablePanel(LayoutManager layout){
        super(layout);
        setPreferredSize(new Dimension(UIConfiguration.TRADE_TABLE_WIDTH, UIConfiguration.TRADE_TABLE_HEIGHT));
        setBackground(Color.green);
    }
}
