package login;
import com.formdev.flatlaf.FlatClientProperties;
import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import java.awt.*;


public class LoginPage extends JPanel{
    private JTextField txtUsername;
    private JPasswordField txtPassword;
    private JCheckBox chRememberme;
    private JButton loginButton;
    private Image loginBg;

    public LoginPage(CardLayout cardLayout, JPanel container) {
        setLayout(new MigLayout("fill,insets 20", "[center]", "[center]"));
        txtUsername = new JTextField();
        txtPassword = new JPasswordField();
        chRememberme = new JCheckBox("Remember me");
        loginButton = new JButton("Login");
//        loginBg = new ImageIcon(getClass().getResource("/img/login-bg.png")).getImage();

        JPanel panel = new JPanel(new MigLayout("wrap, fillx, insets 35 45 30 45", "fill, 250:280"));
        panel.putClientProperty(FlatClientProperties.STYLE,"" +
                "arc: 20;" +
                "[light]background:darken(@background, 3%);" +
                "[dark]background:lighten(@background, 3%);");

        JLabel lbTitle = new JLabel("COIN TRADING PLATFORM");
        JLabel description = new JLabel("Sign in to access your account");
        lbTitle.putClientProperty(FlatClientProperties.STYLE,"" +
                "font: bold +10");
        description.putClientProperty(FlatClientProperties.STYLE,"" +
                "[light]foreground:lighten(@foreground, 30%);" +
                "[dark]foreground:darken(@foreground, 20%);");

        txtUsername.putClientProperty(FlatClientProperties.PLACEHOLDER_TEXT, "Enter your username");
        txtPassword.putClientProperty(FlatClientProperties.PLACEHOLDER_TEXT, "Enter password");

        panel.add(lbTitle);
        panel.add(description);
        panel.add(new JLabel("Username:"), "gapy 8");
        panel.add(txtUsername);
        panel.add(new JLabel("Password:"), "gapy 8");
        panel.add(txtPassword);
        panel.add(chRememberme, "grow 0");
        panel.add(loginButton, "gapy 10");
        panel.setOpaque(false); // Cho phép background trong suốt
        panel.setBackground(new Color(255, 255, 255, 30)); // Trắng, alpha = 150/255 (mờ)

        add(panel);
        setOpaque(false);
    }

    //    @Override
    //    protected void paintComponent(Graphics g) {
    //        super.paintComponent(g);
    //
    //        Graphics2D g2d = (Graphics2D) g.create();
    //
    //        // Vẽ ảnh nền
    //        g2d.drawImage(loginBg, 0, 0, getWidth(), getHeight(), this);
    //
    //        // Vẽ lớp phủ màu trắng mờ (alpha = 100/255 ≈ 40%)
    //        Color overlay = new Color(255, 255, 255, 30); // trắng mờ
    //        g2d.setColor(overlay);
    //        g2d.fillRect(0, 0, getWidth(), getHeight());
    //
    //        g2d.dispose();
    //    }
    }


//    public LoginPage(CardLayout cardLayout, JPanel container){
//        setLayout(new GridBagLayout());
//        GridBagConstraints gbc = new GridBagConstraints();
//        gbc.fill = GridBagConstraints.HORIZONTAL;
//        gbc.insets = new Insets(5, 5, 5, 5);
//
//        JLabel titleLabel = new JLabel("login", SwingConstants.CENTER);
//        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
//
//        gbc.gridx = 0;
//        gbc.gridy = 0;
//        gbc.gridwidth = 2;
//        add(titleLabel, gbc);
//
//        // Username
//        gbc.gridx = 0;
//        gbc.gridy = 1;
//        add(new JLabel("Username:"), gbc);
//
//        gbc.gridx = 1;
//        txtUsername = new JTextField(15);
//        add(txtUsername, gbc);
//
//        //Password
//        gbc.gridx = 0;
//        gbc.gridy = 2;
//        add(new JLabel("Password:"), gbc);
//
//        gbc.gridx = 1;
//        txtPassword = new JPasswordField(15);
//        add(txtPassword, gbc);
//
//        // Login Button
//        gbc.gridx = 0;
//        gbc.gridy = 3;
//        gbc.gridwidth = 2;
//        gbc.anchor = GridBagConstraints.CENTER;
//        loginButton = new JButton("login");
//        add(loginButton, gbc);
//
//        // Action
//        loginButton.addActionListener(new ActionListener(){
//            @Override
//            public void actionPerformed(ActionEvent e){
//                String username = txtUsername.getText();
//                String pass = new String(txtPassword.getPassword());
//
//                if(username.equals("admin") && pass.equals("123")){
//                    JOptionPane.showMessageDialog(null, "Login Successful");
//                    cardLayout.show(container, "main");
//                }
//                else{
//                    JOptionPane.showMessageDialog(null, "Invalid Username or Password");
//                }
//            }
//        });
//    }
