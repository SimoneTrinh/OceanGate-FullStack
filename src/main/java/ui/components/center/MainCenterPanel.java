package ui.components.center;

import javax.swing.*;
import java.awt.*;

public class MainCenterPanel extends JPanel {
    public MainCenterPanel(LayoutManager layout){
        super(layout);
        ChartAndOrderPanel chartAndOrderPanel = new ChartAndOrderPanel(new BorderLayout());
        TradeTablePanel tradeTablePanel = new TradeTablePanel(new BorderLayout());

        add(chartAndOrderPanel, BorderLayout.CENTER);
        add(tradeTablePanel, BorderLayout.SOUTH);
    }
}
