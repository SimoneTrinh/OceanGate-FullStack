package ui.components.center;

import ui.UIConfiguration;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

public class ChartAndOrderPanel extends JPanel {
    private JSplitPane jSplitPane;
    private OrderPanel orderPanel;
    private ChartPanel chartPanel;
    public ChartAndOrderPanel(LayoutManager layout) {
        super(layout);
        chartPanel = new ChartPanel(new BorderLayout());
        orderPanel = new OrderPanel(new BorderLayout());
        jSplitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, chartPanel, orderPanel);
        add(jSplitPane);

        // Responsive for order and chart
        addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                calculateDividerLocation();
            }
        });
    }

    private void calculateDividerLocation(){
        int panelWidth = getWidth();
        int maxOrderWidth = orderPanel.getMaximumSize().width;
        int maxChartWidth = panelWidth - maxOrderWidth;
        chartPanel.setMinimumSize(new Dimension(maxChartWidth, UIConfiguration.CHART_HEIGHT));
        chartPanel.setMaximumSize(new Dimension(maxChartWidth, Integer.MAX_VALUE));
        int dividerLocation = panelWidth - orderPanel.getPreferredSize().width;
        jSplitPane.setDividerLocation(dividerLocation);
    }

}
