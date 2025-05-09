package chart;

import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.net.InetSocketAddress;

public class ChartHosting {

    public ChartHosting() {
    }

    public void init() throws IOException {
        int port = ChartConfiguration.CHART_PORT;
        HttpServer server = HttpServer.create(new InetSocketAddress(port), 0);
        server.createContext("/", new StaticFileHandler("TVChart/dist"));
        server.setExecutor(null);
        server.start();
        System.out.println("Server running at " + ChartConfiguration.CHART_ENDPOINT + ":" + port + ChartConfiguration.CHART_INDEX_HTML);
    }
}
