package ui.components;

import javax.swing.*;
import java.awt.*;

public class TopNav extends JPanel {
    public TopNav(LayoutManager layout) {
        super(layout);
        JLabel logoLabel = new JLabel("LOGO", SwingConstants.CENTER);
        logoLabel.setPreferredSize(new Dimension(100, 50));
        this.add(logoLabel, BorderLayout.WEST);
    }
}
