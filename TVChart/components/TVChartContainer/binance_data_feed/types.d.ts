import {
  Bar,
  SubscribeBarsCallback,
} from "@/charting_library/charting_library";
import {
  readOnlyBinanceResolution,
  readOnlyTradingViewResolution,
} from "./configuration_data";

export type SymbolInfo = {
  short: string;
  full: string;
};

export type HistoricalPayload = {
  symbol: string;
  interval: string;
  startTime: number;
  endTime: number;
  timeZone: "7:00";
};

type HistoricalResponse = {
  time: number;
  open: number;
  close: number;
  low: number;
  high: number;
  volume: number;
}[];

export type TradingViewResolution =
  (typeof readOnlyTradingViewResolution)[number];

export type BinanceResolution = (typeof readOnlyBinanceResolution)[number];

type KlineData = {
  t: number; // Kline start time
  T: number; // Kline close time
  s: string; // Symbol
  i: string; // Interval
  o: string; // Open price
  c: string; // Close price
  h: string; // High price
  l: string; // Low price
  v: string; // Volume
  n: number; // Number of trades
  x: boolean; // Is this Kline closed?
  q: string; // Quote asset volume
};

type KlineMessage = {
  e: string; // Event type ("kline")
  E: number; // Event time
  s: string; // Symbol
  k: KlineData; // Kline data
};

type SubscriptionItem = {
  channelString: string;
  subscriberUID: number;
  resolution: string;
  lastDailyBar: Bar;
  listenerID: string;
  handler: SubscribeBarsCallback[];
};
