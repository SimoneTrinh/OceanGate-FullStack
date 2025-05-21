package test;

import me.friwi.jcefmaven.CefAppBuilder;
import me.friwi.jcefmaven.CefInitializationException;
import me.friwi.jcefmaven.UnsupportedPlatformException;
import org.cef.CefApp;
import org.cef.CefClient;
import org.cef.browser.CefBrowser;
import org.cef.handler.CefAppHandlerAdapter;
import org.cef.handler.CefFocusHandler;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.IOException;

public class WebViewWithUrlBar {
    public static void main(String[] args) throws UnsupportedPlatformException, CefInitializationException, IOException, InterruptedException {
        // CEF Initialization
        CefAppBuilder builder = new CefAppBuilder();
        builder.getCefSettings().windowless_rendering_enabled = false;

        CefApp cefApp = builder.build();
        CefClient client = cefApp.createClient();

        // Initial URL
        String startURL = "https://www.youtube.com";
        CefBrowser browser = client.createBrowser(startURL, false, false);
        Component browserUI = browser.getUIComponent();
        browserUI.setFocusable(false);
        browserUI.setEnabled(false);


        // Swing GUI
        JFrame frame = new JFrame("CEF WebView with URL Bar");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1024, 768);
        frame.setLocationRelativeTo(null);

        JPanel panel = new JPanel(new BorderLayout());

        // URL Bar
        JTextField urlField = new JTextField(startURL);
        JButton goButton = new JButton("Go");

        JPanel topPanel = new JPanel(new BorderLayout());
        JButton setUrlButton = new JButton("Set URL");
        topPanel.add(setUrlButton, BorderLayout.WEST);
        topPanel.add(urlField, BorderLayout.CENTER);
        topPanel.add(goButton, BorderLayout.EAST);

        // Go button action
        ActionListener loadUrl = e -> {
            String url = urlField.getText();
            if (!url.startsWith("http")) {
                url = "https://" + url;
            }
            browser.loadURL(url);
        };

        goButton.addActionListener(loadUrl);
        urlField.addActionListener(loadUrl); // allow pressing Enter

        // Add components to panel
        panel.add(topPanel, BorderLayout.NORTH);
        panel.add(browserUI, BorderLayout.CENTER);

        // Finalize frame
        frame.getContentPane().add(panel);
        frame.setVisible(true);

//        urlField.addFocusListener(new FocusAdapter() {
//            @Override
//            public void focusGained(FocusEvent e){
//                showInputDialog(frame);
//                dialog.dispose();
//            }
//        });

        urlField.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                showInputDialog(frame);
                dialog.dispose();
            }
        });


        setUrlButton.addActionListener(e -> {
            showInputDialog(frame);
            dialog.dispose();
//            System.out.println("123");
//            JOptionPane jo = new JOptionPane();
//            jo.showInputDialog("123");
//            jo.
//            jo.

//            JDialog jd = new JDialog();
//            jd.dispose();
//            String newUrl = JOptionPane.showInputDialog(frame, "Enter URL:", urlField.getText());
//            if (newUrl != null && !newUrl.trim().isEmpty()) {
//                if (!newUrl.startsWith("http")) {
//                    newUrl = "https://" + newUrl;
//                }
//                urlField.setText(newUrl);
//                browser.loadURL(newUrl);
//            }
        });



//        SwingUtilities.invokeLater(() -> urlField.requestFocusInWindow());

//        LockSetForegroundWindow
    }
    private static JDialog dialog;

    public static void showInputDialog(JFrame parent) {
        JOptionPane optionPane = new JOptionPane(
                "Enter your name:",
                JOptionPane.QUESTION_MESSAGE,
                JOptionPane.OK_CANCEL_OPTION,
                null,
                null,
                null
        );

        optionPane.setWantsInput(true);

        dialog = optionPane.createDialog(parent, "Custom Input Dialog");
        dialog.setModal(false); // non-blocking so other buttons can be clicked
        dialog.setVisible(true);

        dialog = new JDialog(parent, "Dialog", false); // false = non-modal
        dialog.add(new JLabel("This is non-modal dialog"));
        dialog.setSize(200, 100);
        dialog.setLocationRelativeTo(parent);
        dialog.setVisible(true);

    }
}
