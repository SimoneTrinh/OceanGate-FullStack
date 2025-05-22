package services;

import models.BaseOrder;
import models.TradeHistory;
import ui.components.center.OrderPanel;
import ui.components.center.TradeTablePanel;
import utils.Constants;
import utils.LocalStorage;

import java.util.ArrayList;
import java.util.List;

public class OrderMatching {
    private List<TradeHistory> listCurrentSell;
    private List<TradeHistory> listCurrentBuy;
    private BaseOrder bestMatchingSell;
    private BaseOrder bestMatchingBuy;
    private static final float EPSILON = 0.0001f;

    public OrderMatching(List<TradeHistory> listCurrentSell, List<TradeHistory> listCurrentBuy, BaseOrder bestMatchingSell, BaseOrder getBestMatchingBuy) {
        this.listCurrentSell = listCurrentSell;
        this.listCurrentBuy = listCurrentBuy;
        this.bestMatchingSell = bestMatchingSell;
        this.bestMatchingBuy = getBestMatchingBuy;
    }

    public float getRemainingAmountToMatch(TradeHistory trade) {
        return trade.getAmount() - trade.getFilled();
    }

//    // v1 - original
//    public void matchingSell0() {
//        List<TradeHistory> acceptToMatch = new ArrayList<>();
//        for (int i = 0; i < listCurrentSell.size(); i++) {
//            if (listCurrentSell.get(i).getPrice() == Float.parseFloat(bestMatchingBuy.getPrice())) {
//                acceptToMatch.add(listCurrentSell.get(i));
//            }
//        }
//        if (!acceptToMatch.isEmpty()) {
//            float remainingAmountToMatch = Float.parseFloat(bestMatchingSell.getAmount());
//            for (int i = 0; i < acceptToMatch.size(); i++) {
//                remainingAmountToMatch = remainingAmountToMatch - getRemainingAmountToMatch(acceptToMatch.get(i)); // remain book - accept filled
//
//                // 1st time matching - fully
//                if (remainingAmountToMatch >= 0) {
//                    acceptToMatch.get(i).setFilledAsOrder();
//                    // set order closed, create trade add balance for user
//                    // check next order
//
//                    // 2nd+ times matching - partially
//                } else if (remainingAmountToMatch < 0) {
//                    acceptToMatch.get(i).setFilled(remainingAmountToMatch);
//                    // set order partially, create trade add balance for user
//                    break; // close matching as nothing to match
//                }
//            }
//        }
//    }

    //    // v2 - good
    public void matchingSell() {
        List<TradeHistory> acceptToMatch = new ArrayList<>();
        float buyPrice = Float.parseFloat(bestMatchingBuy.getPrice()); // Assume getPrice() returns String
        for (TradeHistory sellOrder : listCurrentSell) {
            if (Math.abs(sellOrder.getPrice() - buyPrice) < EPSILON) {
                acceptToMatch.add(sellOrder);
            }
        }

        if (!acceptToMatch.isEmpty()) {
            float remainingAmountToMatch = Float.parseFloat(bestMatchingSell.getAmount()); // Assume getAmount() returns String
            List<TradeHistory> toRemove = new ArrayList<>();

            for (TradeHistory sellOrder : acceptToMatch) {
                float sellRemaining = getRemainingAmountToMatch(sellOrder);
                if (sellRemaining <= 0) {
                    continue; // Skip already filled orders
                }

                if (remainingAmountToMatch >= sellRemaining) {
                    sellOrder.setFilledAsOrder(); // Fully filled
                    toRemove.add(sellOrder);
                    remainingAmountToMatch -= sellRemaining;
                } else if (remainingAmountToMatch > 0) {
                    sellOrder.setFilled(sellOrder.getFilled() + remainingAmountToMatch); // Partial fill
                    remainingAmountToMatch = 0;
                    break;
                } else {
                    break; // No more to match
                }
            }

            listCurrentSell.removeAll(toRemove);
        }
    }

    // v2 - with sql
    public void matchingSellSQL() {
        System.out.println("Running matching sell:");
        List<TradeHistory> acceptToMatch = new ArrayList<>();
        float buyPrice = Float.parseFloat(bestMatchingBuy.getPrice()); // Assume getPrice() returns String
        for (TradeHistory sellOrder : listCurrentSell) {
            if (Math.abs(sellOrder.getPrice() - buyPrice) < EPSILON) {
                acceptToMatch.add(sellOrder);
            }
        }
        if (!listCurrentSell.isEmpty()) {
            acceptToMatch.add(listCurrentSell.get(0)); // force matching
        } else {
            System.out.println("Not found potential matching sell");
        }
        if (!acceptToMatch.isEmpty()) {
            System.out.println("Found potential matching sell");
            float remainingAmountToMatch = Float.parseFloat(bestMatchingSell.getAmount()); // Assume getAmount() returns String
            List<TradeHistory> toRemove = new ArrayList<>();

            for (TradeHistory sellOrder : acceptToMatch) {
                float sellRemaining = getRemainingAmountToMatch(sellOrder); // amount - filled
                if (sellRemaining <= 0) {
                    continue; // Skip already filled orders
                }

                if (remainingAmountToMatch >= sellRemaining) {
                    sellOrder.setFilledAsOrder(); // Fully filled
//                    OrderPanel.placeOrderController.createTrade(0, sellOrder.getOrderID(), sellOrder.getPrice(), sellOrder.getAmount());
                    OrderPanel.placeOrderController.updateOrder(sellOrder.getOrderID(), sellOrder.getFilled(), Constants.ORDER_STATUS.get(sellOrder.getStatus()));
                    OrderPanel.placeOrderController.updateMatchingWalletBalances(sellOrder.getOrderID(), sellOrder.getAmount(), sellOrder.getPrice(), Constants.SYMBOL_MAP.get(sellOrder.getBaseCurrency()), Constants.SYMBOL_MAP.get(sellOrder.getQuoteCurrency()));
                    // create trade, update order, update user wallet

                    toRemove.add(sellOrder);
                    remainingAmountToMatch -= sellRemaining;
                } else if (remainingAmountToMatch > 0) {
                    // create trade, update order == part fill, update user wallet
                    sellOrder.setFilled(sellOrder.getFilled() + remainingAmountToMatch); // Partial fill
                    sellOrder.setStatus("PARTIALLY_FILLED");
//                    OrderPanel.placeOrderController.createTrade(3, sellOrder.getOrderID(), sellOrder.getPrice(), sellOrder.getAmount());
                    OrderPanel.placeOrderController.updateOrder(sellOrder.getOrderID(), sellOrder.getFilled(), Constants.ORDER_STATUS.get(sellOrder.getStatus()));
                    OrderPanel.placeOrderController.updateMatchingWalletBalances(sellOrder.getOrderID(), sellOrder.getAmount(), sellOrder.getPrice(), Constants.SYMBOL_MAP.get(sellOrder.getBaseCurrency()), Constants.SYMBOL_MAP.get(sellOrder.getQuoteCurrency()));

                    remainingAmountToMatch = 0;
                    break;
                } else {
                    break; // No more to match
                }
            }

            listCurrentSell.removeAll(toRemove);
        }
    }

    public void matchingBuySQL() {
        System.out.println("Running matching buy:");
        List<TradeHistory> acceptToMatch = new ArrayList<>();
        float buyPrice = Float.parseFloat(bestMatchingSell.getPrice()); // Assume getPrice() returns String
        for (TradeHistory sellOrder : listCurrentBuy) {
            if (Math.abs(sellOrder.getPrice() - buyPrice) < EPSILON) {
                acceptToMatch.add(sellOrder);
            }
        }
        if (!listCurrentBuy.isEmpty()) {
            acceptToMatch.add(listCurrentBuy.get(0)); // force matching
        } else {
            System.out.println("Not found potential matching buy");
        }
        if (!acceptToMatch.isEmpty()) {
            System.out.println("Found potential matching buy");
            float remainingAmountToMatch = Float.parseFloat(bestMatchingBuy.getAmount()); // Assume getAmount() returns String
            List<TradeHistory> toRemove = new ArrayList<>();

            for (TradeHistory buyOrder : acceptToMatch) {
                float buyRemaining = getRemainingAmountToMatch(buyOrder); // amount - filled
                if (buyRemaining <= 0) {
                    continue; // Skip already filled orders
                }

                if (remainingAmountToMatch >= buyRemaining) {
                    buyOrder.setFilledAsOrder(); // Fully filled
//                    OrderPanel.placeOrderController.createTrade(0, sellOrder.getOrderID(), sellOrder.getPrice(), sellOrder.getAmount());
                    OrderPanel.placeOrderController.updateOrder(buyOrder.getOrderID(), buyOrder.getFilled(), Constants.ORDER_STATUS.get(buyOrder.getStatus()));
                    OrderPanel.placeOrderController.updateMatchingWalletBalances(buyOrder.getOrderID(), buyOrder.getAmount(), buyOrder.getPrice(), Constants.SYMBOL_MAP.get(buyOrder.getBaseCurrency()), Constants.SYMBOL_MAP.get(buyOrder.getQuoteCurrency()));
                    // create trade, update order, update user wallet

                    toRemove.add(buyOrder);
                    remainingAmountToMatch -= buyRemaining;
                } else if (remainingAmountToMatch > 0) {
                    // create trade, update order == part fill, update user wallet
                    buyOrder.setFilled(buyOrder.getFilled() + remainingAmountToMatch); // Partial fill
                    buyOrder.setStatus("PARTIALLY_FILLED");
//                    OrderPanel.placeOrderController.createTrade(3, sellOrder.getOrderID(), sellOrder.getPrice(), sellOrder.getAmount());
                    OrderPanel.placeOrderController.updateOrder(buyOrder.getOrderID(), buyOrder.getFilled(), Constants.ORDER_STATUS.get(buyOrder.getStatus()));
                    OrderPanel.placeOrderController.updateMatchingWalletBalances(buyOrder.getOrderID(), buyOrder.getAmount(), buyOrder.getPrice(), Constants.SYMBOL_MAP.get(buyOrder.getBaseCurrency()), Constants.SYMBOL_MAP.get(buyOrder.getQuoteCurrency()));

                    remainingAmountToMatch = 0;
                    break;
                } else {
                    break; // No more to match
                }
            }

            listCurrentSell.removeAll(toRemove);
        }
    }
}
