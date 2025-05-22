import models.BaseOrder;
import models.TradeHistory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import services.OrderMatching;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;

public class OrderMatchingTest {


    private List<TradeHistory> listCurrentSell;
    private List<TradeHistory> listCurrentBuy;
    private OrderMatching orderMatching;

    @BeforeEach
    public void setUp() {
        listCurrentSell = new ArrayList<>();
        listCurrentBuy = new ArrayList<>();
    }

    @Test
    public void testFullMatch() {
        TradeHistory sellOrder = new TradeHistory(1, "SELL", "BTC", "USD", 100.0f, 10.0f, 0.0f, "OPEN", "2025-05-22");
        listCurrentSell.add(sellOrder);
        BaseOrder bestMatchingSell = new BaseOrder("100.0", "10.0");
        BaseOrder bestMatchingBuy = new BaseOrder("100.0", "10.0");
        orderMatching = new OrderMatching(listCurrentSell, listCurrentBuy, bestMatchingSell, bestMatchingBuy);

        orderMatching.matchingSell();

        assertEquals(0, listCurrentSell.size(), "Sell order should be removed (fully filled)");
        assertEquals(10.0f, sellOrder.getFilled(), 0.0001f, "Sell order should be fully filled");
        assertEquals("CLOSED", sellOrder.getStatus(), "Sell order status should be CLOSED");
    }

    @Test
    public void testMultipleMatches() {
        TradeHistory sellOrder1 = new TradeHistory(1, "SELL", "BTC", "USD", 100.0f, 5.0f, 0.0f, "OPEN", "2025-05-22");
        TradeHistory sellOrder2 = new TradeHistory(2, "SELL", "BTC", "USD", 100.0f, 10.0f, 0.0f, "OPEN", "2025-05-22");
        listCurrentSell.add(sellOrder1);
        listCurrentSell.add(sellOrder2);
        BaseOrder bestMatchingSell = new BaseOrder("100.0", "8.0");
        BaseOrder bestMatchingBuy = new BaseOrder("100.0", "8.0");
        orderMatching = new OrderMatching(listCurrentSell, listCurrentBuy, bestMatchingSell, bestMatchingBuy);

        orderMatching.matchingSell();

        assertEquals(1, listCurrentSell.size(), "One sell order should remain");
        assertEquals(5.0f, sellOrder1.getFilled(), 0.0001f, "First sell order should be fully filled");
        assertEquals("CLOSED", sellOrder1.getStatus(), "First sell order status should be CLOSED");
        assertEquals(3.0f, sellOrder2.getFilled(), 0.0001f, "Second sell order should be partially filled");
        assertEquals("OPEN", sellOrder2.getStatus(), "Second sell order status should be OPEN");
        assertEquals(2, listCurrentSell.get(0).getOrderID(), "Remaining order should be sellOrder2");
    }

    @Test
    public void testPartialMatch() {
        TradeHistory sellOrder = new TradeHistory(1, "SELL", "BTC", "USD", 100.0f, 20.0f, 0.0f, "OPEN", "2025-05-22");
        listCurrentSell.add(sellOrder);
        BaseOrder bestMatchingSell = new BaseOrder("100.0", "10.0");
        BaseOrder bestMatchingBuy = new BaseOrder("100.0", "10.0");
        orderMatching = new OrderMatching(listCurrentSell, listCurrentBuy, bestMatchingSell, bestMatchingBuy);

        orderMatching.matchingSell();

        assertEquals(1, listCurrentSell.size(), "Sell order should remain (partially filled)");
        assertEquals(10.0f, sellOrder.getFilled(), 0.0001f, "Sell order should be partially filled");
        assertEquals("OPEN", sellOrder.getStatus(), "Sell order status should remain OPEN");
    }

    @Test
    public void testZeroAmount() {
        TradeHistory sellOrder = new TradeHistory(1, "SELL", "BTC", "USD", 100.0f, 10.0f, 0.0f, "OPEN", "2025-05-22");
        listCurrentSell.add(sellOrder);
        BaseOrder bestMatchingSell = new BaseOrder("100.0", "0.0");
        BaseOrder bestMatchingBuy = new BaseOrder("100.0", "10.0");
        orderMatching = new OrderMatching(listCurrentSell, listCurrentBuy, bestMatchingSell, bestMatchingBuy);

        orderMatching.matchingSell();

        assertEquals(1, listCurrentSell.size(), "Sell order should remain (zero amount to match)");
        assertEquals(0.0f, sellOrder.getFilled(), 0.0001f, "Sell order should not be filled");
        assertEquals("OPEN", sellOrder.getStatus(), "Sell order status should remain OPEN");
    }

    @Test
    public void testNoMatches() {
        TradeHistory sellOrder = new TradeHistory(1, "SELL", "BTC", "USD", 101.0f, 10.0f, 0.0f, "OPEN", "2025-05-22");
        listCurrentSell.add(sellOrder);
        BaseOrder bestMatchingSell = new BaseOrder("100.0", "10.0");
        BaseOrder bestMatchingBuy = new BaseOrder("100.0", "10.0");
        orderMatching = new OrderMatching(listCurrentSell, listCurrentBuy, bestMatchingSell, bestMatchingBuy);

        orderMatching.matchingSell();

        assertEquals(1, listCurrentSell.size(), "Sell order should remain (no match)");
        assertEquals(0.0f, sellOrder.getFilled(), 0.0001f, "Sell order should not be filled");
        assertEquals("OPEN", sellOrder.getStatus(), "Sell order status should remain OPEN");
    }

    @Test
    public void testEmptySellList() {
        BaseOrder bestMatchingSell = new BaseOrder("100.0", "10.0");
        BaseOrder bestMatchingBuy = new BaseOrder("100.0", "10.0");
        orderMatching = new OrderMatching(listCurrentSell, listCurrentBuy, bestMatchingSell, bestMatchingBuy);

        orderMatching.matchingSell();

        assertEquals(0, listCurrentSell.size(), "Sell list should remain empty");
    }
}