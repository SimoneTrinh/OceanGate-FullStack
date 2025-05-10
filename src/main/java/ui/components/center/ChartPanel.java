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
    public static CefBrowser cefBrowser;


//    public CefBrowser getCefBrowser(){
//        return this.cefBrowser;
//    }
//
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
        String query = ChartConfiguration.generateQuery(ChartConfiguration.CURRENT_SYMBOL, ChartConfiguration.CURRENT_INTERVAL, ChartConfiguration.CURRENT_THEME);

        cefBrowser = cefClient.createBrowser(ChartConfiguration.CHART_BASE_URL + query, false, false);
        add(cefBrowser.getUIComponent());
    }

//    public void cefBrowserLoadUrl(String url){
//        this.cefBrowser.loadURL(url);
//    }
}
