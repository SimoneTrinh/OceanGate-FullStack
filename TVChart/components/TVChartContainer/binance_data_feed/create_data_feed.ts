import {
  IBasicDataFeed,
  IDatafeedQuotesApi,
  LibrarySymbolInfo,
} from "@/charting_library/charting_library";
import { configurationData, convertResolution } from "./configuration_data";
import { getAllSymbols, getHistoricalData } from "./binance_api";
import { subscribeOnStream, unsubscribeFromStream } from "./binance_streaming";
import { getCurrentUnixTime } from "./utils";

const lastBarsCache = new Map();

export const createDataFeed = ():
  | IBasicDataFeed
  | (IBasicDataFeed & IDatafeedQuotesApi) => {
  const unix = getCurrentUnixTime();
  return {
    onReady(callback) {
      console.log("[onReady]: Method call");
      setTimeout(() => callback(configurationData));
    },

    searchSymbols: async (userInput, exchange, symbolType, onResult) => {
      console.log("[searchSymbols]: Method call");
      const symbols = await getAllSymbols();
      const newSymbols = symbols.filter((symbol) => {
        const isExchangeValid = exchange === "" || symbol.exchange === exchange;
        const isFullSymbolContainsInput =
          symbol.symbol.toLowerCase().indexOf(userInput.toLowerCase()) !== -1;
        return isExchangeValid && isFullSymbolContainsInput;
      });
      onResult(newSymbols);
    },

    resolveSymbol: async (symbolName, onResolve, onError) => {
      console.log("[resolveSymbol]: Method call", symbolName);
      const symbols = await getAllSymbols();
      const symbolItem = symbols.find(({ symbol }) => symbol === symbolName);
      if (!symbolItem) {
        console.log("[resolveSymbol]: Cannot resolve symbol", symbolName);
        onError("cannot resolve symbol");
        return;
      }

      const symbolInfo: LibrarySymbolInfo = {
        ticker: symbolItem.symbol,
        name: symbolItem.symbol,
        description: symbolItem.description,
        type: symbolItem.type,
        session: "24x7",
        timezone: "Asia/Ho_Chi_Minh",
        exchange: symbolItem.exchange,
        minmov: 1,
        pricescale: 100,
        has_intraday: true,
        has_weekly_and_monthly: false,
        supported_resolutions: configurationData.supported_resolutions,
        volume_precision: 2,
        data_status: "streaming",
        listed_exchange: "Binance",
        format: "price",
      };

      console.log("[resolveSymbol]: Symbol resolved", symbolName);
      onResolve(symbolInfo);
    },

    getBars: async (
      symbolInfo,
      resolution,
      periodParams,
      onResult,
      onError
    ) => {
      const { from, to, firstDataRequest } = periodParams;
      console.log("[getBars]: Method call", symbolInfo, resolution, from, to);
      if (resolution) {
        try {
          const data = await getHistoricalData({
            symbol: symbolInfo.name,
            startTime: from * 1000,
            endTime: to * 1000,
            interval: convertResolution(resolution),
            timeZone: "7:00",
          });
          if (firstDataRequest) {
            lastBarsCache.set(symbolInfo.name, {
              ...data[data.length - 1],
            });
          }
          console.log(`[getBars]: returned ${data.length} bar(s)`);
          onResult(data, {
            noData: false,
          });
        } catch (error) {
          console.log("[getBars]: Get error", error);
          onError(error as string);
        }
      }
    },

    subscribeBars(
      symbolInfo,
      resolution,
      onTick,
      listenerGuid,
      onResetCacheNeededCallback
    ) {
      console.log(
        "[subscribeBars]: Method call with subscriberUID:",
        listenerGuid
      );
      subscribeOnStream(
        symbolInfo,
        resolution,
        onTick,
        listenerGuid,
        onResetCacheNeededCallback,
        lastBarsCache.get(symbolInfo.name)
      );
    },

    unsubscribeBars(listenerGuid) {
      // BTCUSDT_#_60
      console.log(
        "[unsubscribeBars]: Method call with subscriberUID:",
        listenerGuid,
        unix
      );
      unsubscribeFromStream(listenerGuid);
    },
  };
};
