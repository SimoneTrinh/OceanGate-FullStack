package ui.components.left;

import ui.UIConfiguration;

import javax.swing.*;
import java.awt.*;

public class MainLeftPanel extends JPanel {
    public MainLeftPanel(LayoutManager layout){
        super(layout);
        setPreferredSize(new Dimension(UIConfiguration.MAIN_LEFT_WIDTH, UIConfiguration.MAIN_LEFT_HEIGHT));
        setBackground(Color.CYAN);
    }
}
