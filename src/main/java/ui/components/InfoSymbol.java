package ui.components;

import javax.swing.*;
import java.awt.*;

public class InfoSymbol extends JPanel {
    public InfoSymbol(LayoutManager layout) {
        super(layout);
        JLabel logoLabel = new JLabel("LOGO", SwingConstants.CENTER);
        logoLabel.setPreferredSize(new Dimension(100, 50));
        this.add(logoLabel, BorderLayout.WEST);
    }
}
