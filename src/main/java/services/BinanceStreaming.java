package services;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.WebSocket;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletionStage;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

public class BinanceStreaming implements WebSocket.Listener {
    private static final BinanceStreaming instance = new BinanceStreaming();
    private WebSocket webSocket;
    private final Map<String, List<MessageListener>> listeners = new ConcurrentHashMap<>();
    private final String endPoint = "wss://stream.binance.com:9443/ws/@+07:00";
    private String ticker24Stream = "btcusdt@ticker";
    private String orderBookStream = "btcusdt@depth";
    private boolean isConnected = false;


    public static synchronized BinanceStreaming getInstance() {
        return instance;
    }

    public String getTicker24Stream() {
        return ticker24Stream;
    }

    public void setTicker24Stream(String ticker24Stream) {
        this.ticker24Stream = ticker24Stream;
    }

    public String getOrderBookStream() {
        return orderBookStream;
    }

    public void setOrderBookStream(String orderBookStream) {
        this.orderBookStream = orderBookStream;
    }

    public WebSocket getWebSocket() {
        return this.webSocket;
    }

    public void connect() {
        HttpClient client = HttpClient.newHttpClient();
        client.newWebSocketBuilder()
                .buildAsync(URI.create(endPoint), this)
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
    public void onOpen(WebSocket webSocket) {
        isConnected = true;
        String subscribePayload = createSubscribePayload(new String[]{ticker24Stream, orderBookStream});
        webSocket.sendText(subscribePayload, true);
        webSocket.request(1);
    }

    private final StringBuilder messageBuffer = new StringBuilder(); // for handle large payloads split

    @Override
    public CompletionStage<?> onText(WebSocket webSocket, CharSequence data, boolean last) {
        messageBuffer.append(data);
        if (last) {
            try {
                String completeMessage = messageBuffer.toString();
                messageBuffer.setLength(0); // clear memory for next message

                JsonObject json = JsonParser.parseString(completeMessage).getAsJsonObject();
                if (json.has("result")) {
                    System.out.println(json.toString());
                } else {
                    String flag = json.get("e").getAsString();
                    if (flag != null && listeners.containsKey(flag)) {
                        for (MessageListener listener : listeners.get(flag)) {
                            listener.onMessageReceived(json);
                        }
                    }
                }

            } catch (Exception e) {
                System.out.println("Data: ---- " + data.toString());
                e.printStackTrace();
            }
        }
        webSocket.request(1);
        return null;
    }

    @Override
    public void onError(WebSocket webSocket, Throwable error) {
        System.err.println("WebSocket error: " + error.getMessage());
    }

    public interface MessageListener {
        String getFlag(); // ID for listener received

        void onMessageReceived(JsonObject message);
    }

    public String createSubscribePayload(String[] stream) {
        JsonObject payload = new JsonObject();
        payload.addProperty("method", "SUBSCRIBE");
        payload.add("params", new Gson().toJsonTree(stream));
        payload.addProperty("id", 1);
        return new Gson().toJson(payload);
    }

    public String createUnSubscribePayload(String[] stream) {
        JsonObject payload = new JsonObject();
        payload.addProperty("method", "UNSUBSCRIBE");
        payload.add("params", new Gson().toJsonTree(stream));
        payload.addProperty("id", 1);
        return new Gson().toJson(payload);
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
//
}
