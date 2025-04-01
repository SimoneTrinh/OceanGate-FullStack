package ui.components.top;

import ui.UIConfiguration;

import javax.swing.*;
import java.awt.*;

public class TopNavPanel extends JPanel {
    public TopNavPanel(LayoutManager layout) {
        super(layout);
        this.setPreferredSize(new Dimension(UIConfiguration.TOP_NAV_WIDTH, UIConfiguration.TOP_NAV_HEIGHT));
        this.setBackground(Color.YELLOW);
//        JLabel logoLabel = new JLabel("LOGO", SwingConstants.CENTER);
//        logoLabel.setPreferredSize(new Dimension(100, 50));
//        this.add(logoLabel, BorderLayout.WEST);
    }
}
