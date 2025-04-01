package ui.components.top;

import javax.swing.*;
import java.awt.*;

public class MainTopPanel extends JPanel {
    public MainTopPanel(LayoutManager layout){
        super(layout);
        InfoSymbolPanel infoSymbolPanel = new InfoSymbolPanel(new BorderLayout());
        TopNavPanel topNavPanel = new TopNavPanel(new BorderLayout());
        this.add(topNavPanel, BorderLayout.NORTH);
        this.add(infoSymbolPanel, BorderLayout.SOUTH);
    }
}
