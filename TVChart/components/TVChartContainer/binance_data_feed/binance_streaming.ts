import {
  Bar,
  LibrarySymbolInfo,
  SubscribeBarsCallback,
} from "@/charting_library/charting_library";
import { convertResolution } from "./configuration_data";
import { KlineMessage, SubscriptionItem } from "./types";
import { getNextBarTime } from "./utils";

const wsBaseUrl = "wss://stream.binance.com:9443/ws/@+07:00";
const socket = new WebSocket(wsBaseUrl);

socket.addEventListener("open", () => {
  console.log("[socket] Connected");
});

socket.addEventListener("close", (reason) => {
  console.log("[socket] Disconnected:", reason);
});

socket.addEventListener("error", (error) => {
  console.log("[socket] Error:", error);
});

socket.addEventListener("message", (event) => {
  const data = JSON.parse(event.data) as KlineMessage;
  console.log("[socket] Message:", data);
  if (data.e != "kline") {
    console.log("[socket] Successful subscrible to stream");
    // skip success response
    return;
  }
  const channelString = `${data.k.s.toLowerCase()}@kline_${data.k.i}`; //btcusdt@kline_15m

  const subscriptionItem: SubscriptionItem | undefined =
    channelToSubscription.find((item) => {
      return item.channelString == channelString;
    });

  const lastDailyBar = subscriptionItem!.lastDailyBar;
  const nextDailyBarTime = getNextBarTime(
    lastDailyBar.time,
    subscriptionItem!.resolution
  );

  console.log(
    "Channel String: ",
    channelString,
    subscriptionItem,
    nextDailyBarTime
  );

  const tradeTime = data.E;
  const tradePrice = parseFloat(data.k.c);
  let bar: Bar;

  if (tradeTime >= nextDailyBarTime) {
    bar = {
      time: nextDailyBarTime,
      open: tradePrice,
      high: tradePrice,
      low: tradePrice,
      close: tradePrice,
    };
    console.log("[socket] Generate new bar", bar);
  } else {
    bar = {
      ...lastDailyBar,
      high: Math.max(lastDailyBar.high, tradePrice),
      low: Math.min(lastDailyBar.low, tradePrice),
      close: tradePrice,
    };
    console.log("[socket] Update the latest bar by price", bar);
  }

  subscriptionItem!.lastDailyBar = bar;

  subscriptionItem!.handler.forEach((handler) => {
    handler({ ...bar });
  });
});

const channelToSubscription: SubscriptionItem[] = [];

export const subscribeOnStream = (
  symbolInfo: LibrarySymbolInfo,
  resolution: string,
  onTick: SubscribeBarsCallback,
  listenerGuid: string,
  onResetCacheNeededCallback: () => void,
  lastDailyBar: Bar
) => {
  const symbol = symbolInfo.name.toLowerCase();
  const reso = convertResolution(resolution);
  const subscriptionName = `${symbol}@kline_${reso}`;
  let subscriptionItem: SubscriptionItem | undefined =
    channelToSubscription.find((item) => {
      return item.channelString == subscriptionName;
    });

  if (subscriptionItem) {
    subscriptionItem.handler.push(onTick);
    return;
  }

  subscriptionItem = {
    subscriberUID: 1,
    channelString: subscriptionName,
    resolution: reso,
    lastDailyBar: lastDailyBar,
    listenerID: listenerGuid,
    handler: [onTick],
  };

  channelToSubscription.push(subscriptionItem);
  console.log(
    "[subscribeBars]: Subscribe to streaming. Channel:",
    subscriptionItem
  );
  const subPayload = {
    method: "SUBSCRIBE",
    params: [subscriptionName],
    id: 1,
  };
  socket.send(JSON.stringify(subPayload));
};

export const unsubscribeFromStream = (listenerGuid: string) => {
  // BTCUSDT_#_60
  const subscriptionItem: SubscriptionItem | undefined =
    channelToSubscription.find((item) => {
      return item.listenerID == listenerGuid;
    });
  if (subscriptionItem) {
    const unSubPayload = {
      method: "UNSUBSCRIBE",
      params: [subscriptionItem.channelString],
      id: 1,
    };
    socket.send(JSON.stringify(unSubPayload));
    console.log(
      "[unsubscribeBars]: Unsubscribe from streaming. Payload:",
      unSubPayload
    );
    channelToSubscription.filter(
      (channel) => channel.listenerID !== subscriptionItem?.listenerID
    );
  } else {
    console.log(
      "[unsubscribeBars]: Can not find subscription item",
      subscriptionItem
    );
  }
};
