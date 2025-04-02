package chart;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;

public class StaticFileHandler implements HttpHandler {
    private final String baseDir;
    private final Map<String, String> mimeTypes;

    public StaticFileHandler(String baseDir) {
        this.baseDir = baseDir;
        this.mimeTypes = new HashMap<>();
        this.mimeTypes.put("html", "text/html");
        this.mimeTypes.put("css", "text/css");
        this.mimeTypes.put("js", "text/javascript");
        this.mimeTypes.put("json", "application/json");
        this.mimeTypes.put("png", "image/png");
        this.mimeTypes.put("jpg", "image/jpeg");
        this.mimeTypes.put("svg", "image/svg+xml");
    }
    @Override
    public void handle(HttpExchange exchange) throws IOException {
        String uri = exchange.getRequestURI().getPath();
        if ("/".equals(uri)) uri = "/index.html"; // Default file

        Path filePath = Path.of(baseDir + uri);
        if (!Files.exists(filePath)) {
            String response = "404 Not Found";
            exchange.sendResponseHeaders(404, response.length());
            exchange.getResponseBody().write(response.getBytes());
        } else {
            // Detect file extension
            String fileExtension = getFileExtension(filePath.getFileName().toString());
            String mimeType = mimeTypes.getOrDefault(fileExtension, "application/octet-stream");

            exchange.getResponseHeaders().set("Content-Type", mimeType);
            byte[] fileBytes = Files.readAllBytes(filePath);
            exchange.sendResponseHeaders(200, fileBytes.length);
            exchange.getResponseBody().write(fileBytes);
        }
        exchange.getResponseBody().close();
    }

    private String getFileExtension(String fileName) {
        int lastDot = fileName.lastIndexOf('.');
        return (lastDot == -1) ? "" : fileName.substring(lastDot + 1);
    }
}
