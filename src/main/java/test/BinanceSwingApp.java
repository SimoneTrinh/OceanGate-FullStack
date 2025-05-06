package test;

import javax.swing.*;
import java.awt.*;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.WebSocket;
import java.util.concurrent.CompletionStage;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.Gson;
import java.util.HashMap;
import java.util.Map;

public class BinanceSwingApp {
    private final JFrame frame;
    private final JTextArea textArea;
    private final JLabel statusLabel;
    private final JLabel priceLabel;
    private final JButton switchSymbolButton;
    private WebSocket webSocket;
    private boolean isConnected = false;
    private String currentStream = "btcusdt@trade"; // Start with BTC/USDT trade stream
    private static final String BTC_STREAM = "btcusdt@trade";
    private static final String ETH_STREAM = "ethusdt@trade";
    private static final String WS_URL = "wss://stream.binance.com:9443/ws";
    private int requestId = 1; // Increment for each subscription request
    private final Map<String, Double> lastPrices = new HashMap<>(); // Track last price per symbol

    public BinanceSwingApp() {
        // Initialize the main frame
        frame = new JFrame("Binance WebSocket Client");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600, 400);
        frame.setLayout(new BorderLayout());

        // Create top panel for button and price label
        JPanel topPanel = new JPanel();
        topPanel.setLayout(new BoxLayout(topPanel, BoxLayout.Y_AXIS));

        // Button panel
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        switchSymbolButton = new JButton("Switch to ETH/USDT");
        switchSymbolButton.addActionListener(e -> switchStream());
        buttonPanel.add(switchSymbolButton);
        topPanel.add(buttonPanel);

        // Price label panel
        JPanel pricePanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        priceLabel = new JLabel("Price: N/A", SwingConstants.CENTER);
        priceLabel.setFont(new Font("Monospaced", Font.BOLD, 16));
        priceLabel.setForeground(Color.BLACK);
        pricePanel.add(priceLabel);
        topPanel.add(pricePanel);

        // Add top panel to NORTH
        frame.add(topPanel, BorderLayout.NORTH);

        // Create text area for WebSocket messages
        textArea = new JTextArea();
        textArea.setEditable(false);
        textArea.setLineWrap(true);
        textArea.setWrapStyleWord(true);
        textArea.setFont(new Font("Monospaced", Font.PLAIN, 12));
        JScrollPane scrollPane = new JScrollPane(textArea);
        frame.add(scrollPane, BorderLayout.CENTER);

        // Create status label
        statusLabel = new JLabel("Status: Disconnected", SwingConstants.CENTER);
        statusLabel.setForeground(Color.RED);
        frame.add(statusLabel, BorderLayout.SOUTH);

        // Center the frame
        frame.setLocationRelativeTo(null);
    }

    public void start() {
        // Show the frame
        frame.setVisible(true);

        // Connect to WebSocket
        connectToWebSocket();
    }

    private void switchStream() {
        if (!isConnected || webSocket == null) {
            appendMessage("Not connected. Please wait for connection.");
            return;
        }

        // Unsubscribe from current stream
        String unsubscribePayload = createUnsubscribePayload(currentStream);
        webSocket.sendText(unsubscribePayload, true);
        appendMessage("Sent: " + unsubscribePayload);

        // Switch to new stream
        if (currentStream.equals(BTC_STREAM)) {
            currentStream = ETH_STREAM;
            switchSymbolButton.setText("Switch to BTC/USDT");
        } else {
            currentStream = BTC_STREAM;
            switchSymbolButton.setText("Switch to ETH/USDT");
        }

        // Subscribe to new stream
        String subscribePayload = createSubscribePayload(currentStream);
        webSocket.sendText(subscribePayload, true);
        appendMessage("Sent: " + subscribePayload);

        // Update status label
        statusLabel.setText("Status: Connected (" + currentStream.toUpperCase().split("@")[0] + ")");
    }

    private String createSubscribePayload(String stream) {
        JsonObject payload = new JsonObject();
        payload.addProperty("method", "SUBSCRIBE");
        payload.add("params", new Gson().toJsonTree(new String[]{stream}));
        payload.addProperty("id", requestId++);
        return new Gson().toJson(payload);
    }

    private String createUnsubscribePayload(String stream) {
        JsonObject payload = new JsonObject();
        payload.addProperty("method", "UNSUBSCRIBE");
        payload.add("params", new Gson().toJsonTree(new String[]{stream}));
        payload.addProperty("id", requestId++);
        return new Gson().toJson(payload);
    }

    private void connectToWebSocket() {
        try {
            appendMessage("Connecting to Binance WebSocket...");
            HttpClient client = HttpClient.newHttpClient();
            webSocket = client.newWebSocketBuilder()
                    .buildAsync(URI.create(WS_URL), new WebSocketListener())
                    .join();
        } catch (Exception e) {
            appendMessage("Error connecting to WebSocket: " + e.getMessage());
            statusLabel.setText("Status: Error");
            statusLabel.setForeground(Color.RED);
        }
    }

    private void appendMessage(String message) {
        // Ensure GUI updates happen on the Event Dispatch Thread
        SwingUtilities.invokeLater(() -> {
            textArea.append(message + "\n");
            // Scroll to the bottom
            textArea.setCaretPosition(textArea.getDocument().getLength());
        });
    }

    private void updatePriceLabel(String symbol, double currentPrice) {
        // Ensure GUI updates happen on the Event Dispatch Thread
        SwingUtilities.invokeLater(() -> {
            // Format price to 2 decimal places
            String priceText = String.format("Price: %.2f", currentPrice);
            priceLabel.setText(priceText);

            // Determine color based on price change
            String symbolKey = symbol.toLowerCase();
            if (lastPrices.containsKey(symbolKey)) {
                double lastPrice = lastPrices.get(symbolKey);
                if (currentPrice > lastPrice) {
                    priceLabel.setForeground(Color.GREEN);
                } else if (currentPrice < lastPrice) {
                    priceLabel.setForeground(Color.RED);
                }
            } else {
                priceLabel.setForeground(Color.GREEN); // First price, neutral
            }

            // Update last price
            lastPrices.put(symbolKey, currentPrice);
        });
    }

    private class WebSocketListener implements WebSocket.Listener {
        @Override
        public void onOpen(WebSocket webSocket) {
            isConnected = true;
            appendMessage("Connected to Binance WebSocket");
            statusLabel.setText("Status: Connected (" + currentStream.toUpperCase().split("@")[0] + ")");
            statusLabel.setForeground(Color.GREEN);

            // Subscribe to initial stream
            String subscribePayload = createSubscribePayload(currentStream);
            webSocket.sendText(subscribePayload, true);
            appendMessage("Sent: " + subscribePayload);
            webSocket.request(1);
        }

        @Override
        public CompletionStage<?> onText(WebSocket webSocket, CharSequence data, boolean last) {
            try {
                JsonObject json = JsonParser.parseString(data.toString()).getAsJsonObject();
                if (json.has("result")) {
                    // Subscription response
                    appendMessage("Subscription response: " + data);
                } else if (json.has("e") && json.get("e").getAsString().equals("trade")) {
                    // Trade data
                    String symbol = json.get("s").getAsString();
                    String priceStr = json.get("p").getAsString();
                    String quantity = json.get("q").getAsString();
                    long tradeTime = json.get("T").getAsLong();
                    double price = Double.parseDouble(priceStr);

                    // Update price label with color coding
                    updatePriceLabel(symbol, price);

                    // Format trade message
                    String message = String.format("%s - Price: %s, Quantity: %s, Trade Time: %d",
                            symbol, priceStr, quantity, tradeTime);
                    appendMessage(message);
                } else {
                    appendMessage("Received: " + data);
                }
            } catch (Exception e) {
                appendMessage("Error parsing message: " + e.getMessage());
            }
            webSocket.request(1);
            return null;
        }

        @Override
        public CompletionStage<?> onClose(WebSocket webSocket, int statusCode, String reason) {
            isConnected = false;
            appendMessage("Disconnected: " + reason + " (Code: " + statusCode + ")");
            statusLabel.setText("Status: Disconnected");
            statusLabel.setForeground(Color.RED);
            priceLabel.setText("Price: N/A");
            priceLabel.setForeground(Color.BLACK);
            return null;
        }

        @Override
        public void onError(WebSocket webSocket, Throwable error) {
            isConnected = false;
            appendMessage("Error: " + error.getMessage());
            statusLabel.setText("Status: Error");
            statusLabel.setForeground(Color.RED);
            priceLabel.setText("Price: N/A");
            priceLabel.setForeground(Color.BLACK);
        }
    }

    public static void main(String[] args) {
        // Ensure GUI creation happens on the Event Dispatch Thread
        SwingUtilities.invokeLater(() -> {
            BinanceSwingApp app = new BinanceSwingApp();
            app.start();
        });
    }
}