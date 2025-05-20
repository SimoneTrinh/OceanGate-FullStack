package ui.components.top;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class MainTopPanel extends JPanel {
    public MainTopPanel(LayoutManager layout, ActionListener userMenuListener) {
        super(layout);
        InfoSymbolPanel infoSymbolPanel = new InfoSymbolPanel(new BorderLayout());
        TopNavPanel topNavPanel = new TopNavPanel(new BorderLayout(), userMenuListener);
        this.add(topNavPanel, BorderLayout.NORTH);
        this.add(infoSymbolPanel, BorderLayout.SOUTH);
    }
}
