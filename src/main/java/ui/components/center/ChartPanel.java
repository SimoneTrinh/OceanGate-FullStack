package ui.components.center;

import chart.ChartConfiguration;
import me.friwi.jcefmaven.CefAppBuilder;
import me.friwi.jcefmaven.CefInitializationException;
import me.friwi.jcefmaven.MavenCefAppHandlerAdapter;
import me.friwi.jcefmaven.UnsupportedPlatformException;
import org.cef.CefApp;
import org.cef.CefClient;
import org.cef.browser.CefBrowser;
import org.cef.browser.CefMessageRouter;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;

public class ChartPanel extends JPanel {
    private final CefApp cefApp;
    private final CefClient cefClient;
    private final CefBrowser cefBrowser;
    public String chartURL = ChartConfiguration.CHART_BASE_URL + ":" + ChartConfiguration.CHART_PORT + ChartConfiguration.CHART_INDEX_HTML;

    public ChartPanel(LayoutManager layout) {
        super(layout);
        CefAppBuilder builder = new CefAppBuilder();
        builder.getCefSettings().windowless_rendering_enabled = false;
        // Fixes compatibility issues with MacOSX
        builder.setAppHandler(new MavenCefAppHandlerAdapter() {
            @Override
            public void stateHasChanged(org.cef.CefApp.CefAppState state) {
                // Shutdown the app if the native CEF part is terminated
                if (state == CefApp.CefAppState.TERMINATED) System.exit(0);
            }
        });

        try {
            cefApp = builder.build();
        } catch (IOException | CefInitializationException | InterruptedException | UnsupportedPlatformException e) {
            throw new RuntimeException(e);
        }
        cefClient = cefApp.createClient();

        CefMessageRouter msgRouter = CefMessageRouter.create();
        cefClient.addMessageRouter(msgRouter);
        cefBrowser = cefClient.createBrowser(chartURL, false, false);
        add(cefBrowser.getUIComponent());
    }

    // todo: implement way to change url query of chart
}
