package services;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.WebSocket;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletionStage;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

public class BinanceStreaming implements WebSocket.Listener {
    private static BinanceStreaming instance;
    private WebSocket webSocket;
    private final Gson gson = new Gson();
    private final Map<String, List<MessageListener>> listeners = new ConcurrentHashMap<>();
    private BinanceStreaming() {}

    public static synchronized BinanceStreaming getInstance() {
        return instance;
    }

    public void connect(String uri) {
        HttpClient client = HttpClient.newHttpClient();
        client.newWebSocketBuilder()
                .buildAsync(URI.create(uri), this)
                .thenAccept(ws -> {
                    this.webSocket = ws;
                    System.out.println("WebSocket connected");
                });
    }

    public void addListener(MessageListener listener) {
        listeners.computeIfAbsent(listener.getFlag(), f -> new CopyOnWriteArrayList<>()).add(listener);
    }

    public void removeListener(MessageListener listener) {
        List<MessageListener> list = listeners.get(listener.getFlag());
        if (list != null) list.remove(listener);
    }

    @Override
    public CompletionStage<?> onText(WebSocket webSocket, CharSequence data, boolean last) {
        String message = data.toString();
        try {
            JsonObject json = gson.fromJson(message, JsonObject.class);
            String flag = json.has("e") ? json.get("e").getAsString() : null;

            if (flag != null && listeners.containsKey(flag)) {
                for (MessageListener listener : listeners.get(flag)) {
                    listener.onMessageReceived(message);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return WebSocket.Listener.super.onText(webSocket, data, last);
    }

    @Override
    public void onError(WebSocket webSocket, Throwable error) {
        System.err.println("WebSocket error: " + error.getMessage());
    }

    public interface MessageListener {
        String getFlag(); // Identifier for which messages this listener receives
        void onMessageReceived(String message);
    }

// V1:
//    public interface TickerListener {
//        void onTickerUpdate(JsonObject data);
//    }
//
//    private WebSocket webSocket;
//    private TickerListener listener;
//    private final String WSS_BASE_URL = "wss://stream.binance.com:9443/ws/@+07:00";
//
//    public BinanceStreaming(WebSocket webSocket, TickerListener listener) {
//
//        this.listener = listener;
//        URI uri = URI.create(WSS_BASE_URL);
//        HttpClient client = HttpClient.newHttpClient();
//        client.newWebSocketBuilder().buildAsync(uri, this).thenAccept(ws -> {
//            this.webSocket = webSocket;
//            // send subscription if needed
//        });
//    }
//
//    public void sendPayload(JsonObject payload) {
//        webSocket.sendText(payload.toString(), true);
//    }
//
//    @Override
//    public void onOpen(WebSocket webSocket) {
//        System.out.println("WebSocket connection opened");
//        webSocket.request(1); // Request next message
//    }
//
//    @Override
//    public void onError(WebSocket webSocket, Throwable error) {
//        System.err.println("WebSocket error: " + error.getMessage());
//    }
//
//    @Override
//    public CompletionStage<?> onClose(WebSocket webSocket, int statusCode, String reason) {
//        System.out.println("WebSocket closed: " + reason);
//        return null;
//    }
//
//    @Override
//    public CompletionStage<?> onText(WebSocket webSocket, CharSequence data, boolean last) {
////        return WebSocket.Listener.super.onText(webSocket, data, last);
////        JsonObject json = new JsonObject(data.toString());
//        return null;
//    }
}
