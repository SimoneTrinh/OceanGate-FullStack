package ui.components.center;

import ui.UIConfiguration;

import javax.swing.*;
import java.awt.*;

public class OrderPanel extends JPanel {
    public OrderPanel(LayoutManager layout){
        super(layout);
        setPreferredSize(new Dimension(UIConfiguration.ORDER_MENU_MAX_WIDTH, UIConfiguration.ORDER_MENU_HEIGHT));
        setMaximumSize(new Dimension(UIConfiguration.ORDER_MENU_MAX_WIDTH, UIConfiguration.ORDER_MENU_HEIGHT));
        setMinimumSize(new Dimension(UIConfiguration.ORDER_MENU_MIN_WIDTH, UIConfiguration.ORDER_MENU_HEIGHT));
        setBackground(Color.ORANGE);
    }

//    @Override
//    protected void paintComponent(Graphics g) {
//        super.paintComponent(g);
//        System.out.println("Width: " + getWidth() + ", Height: " + getHeight());
//    }
}
