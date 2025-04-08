package services;

import com.google.gson.JsonObject;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.WebSocket;
import java.util.concurrent.CompletionStage;

public class BinanceStreaming implements WebSocket.Listener {
    public interface TickerListener {
        void onTickerUpdate(JsonObject data);
    }

    private WebSocket webSocket;
    private TickerListener listener;
    private final String WSS_BASE_URL = "wss://stream.binance.com:9443/ws";

    public BinanceStreaming(WebSocket webSocket, TickerListener listener) {

        this.listener = listener;
        URI uri = URI.create(WSS_BASE_URL);
        HttpClient client = HttpClient.newHttpClient();
        client.newWebSocketBuilder().buildAsync(uri, this).thenAccept(ws -> {
            this.webSocket = webSocket;
            // send subscription if needed
        });
    }

    public void sendPayload(JsonObject payload) {
        webSocket.sendText(payload.toString(), true);
    }

    @Override
    public void onOpen(WebSocket webSocket) {
        System.out.println("WebSocket connection opened");
        webSocket.request(1); // Request next message
    }

    @Override
    public void onError(WebSocket webSocket, Throwable error) {
        System.err.println("WebSocket error: " + error.getMessage());
    }

    @Override
    public CompletionStage<?> onClose(WebSocket webSocket, int statusCode, String reason) {
        System.out.println("WebSocket closed: " + reason);
        return null;
    }

    @Override
    public CompletionStage<?> onText(WebSocket webSocket, CharSequence data, boolean last) {
//        return WebSocket.Listener.super.onText(webSocket, data, last);
//        JsonObject json = new JsonObject(data.toString());
        return null;
    }
}
