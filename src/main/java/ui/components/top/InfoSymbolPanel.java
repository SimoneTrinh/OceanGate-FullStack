package ui.components.top;

import ui.UIConfiguration;

import javax.swing.*;
import java.awt.*;

public class InfoSymbolPanel extends JPanel {
    public InfoSymbolPanel(LayoutManager layout) {
        super(layout);
        this.setPreferredSize(new Dimension(UIConfiguration.INFO_SYMBOL_WIDTH, UIConfiguration.INFO_SYMBOL_HEIGHT));
        this.setBackground(Color.BLUE);
//        JLabel logoLabel = new JLabel("LOGO", SwingConstants.CENTER);
//        logoLabel.setPreferredSize(new Dimension(100, 50));
//        this.add(logoLabel, BorderLayout.WEST);
    }
}
