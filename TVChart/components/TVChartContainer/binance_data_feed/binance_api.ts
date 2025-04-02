import {
  Bar,
  SearchSymbolResultItem,
} from "@/charting_library/charting_library";
import axios, { AxiosRequestConfig } from "axios";
import { configurationData } from "./configuration_data";
import { HistoricalPayload } from "./types";

export const createRestApiRequest = async (
  path: string,
  config: AxiosRequestConfig = {}
) => {
  try {
    const response = await axios.get(
      `https://data-api.binance.vision/api/v3/${path}`,
      config
    );
    return response.data;
  } catch (error) {
    console.error("Error fetching exchange info:", error);
  }
};

type Market = {
  baseAsset: string;
  quoteAsset: string;
  status: string;
  symbol: string;
};

export const getAllSymbols = async (): Promise<SearchSymbolResultItem[]> => {
  const data = await createRestApiRequest("exchangeInfo");
  let allSymbols: SearchSymbolResultItem[] = [];

  for (const exchange of configurationData.exchanges!) {
    allSymbols = [
      ...allSymbols,
      ...data.symbols.map((market: Market): SearchSymbolResultItem => {
        return {
          symbol: `${market.baseAsset}${market.quoteAsset}`,
          description: market.status,
          ticker: market.symbol,
          exchange: exchange.value,
          type: "crypto",
        };
      }),
    ];

    //     {
    //     symbol: 'BTC|ETH',
    //     ticker: 'BTC|ETH',
    //     description: 'TRADING',
    //     exchange: 'Binance',
    //     type: 'crypto',
    // }
  }
  return allSymbols;
};

// type KLine = {
//   time: number;
//   open: number;
//   high: number;
//   low: number;
//   close: number;
//   volumne: number;
// };

export const getHistoricalData = async (
  payload: HistoricalPayload
): Promise<Bar[]> => {
  const response = await createRestApiRequest("klines", {
    params: {
      symbol: payload.symbol,
      interval: payload.interval,
      timeZone: payload.timeZone,
      startTime: payload.startTime,
      endTime: payload.endTime,
    },
  });

  return response.map((bar: Bar[]) => ({
    time: bar[0], // Open time
    open: bar[1],
    high: bar[2],
    low: bar[3],
    close: bar[4],
    volume: bar[5],
  }));
};

// export const getHistoricalData2 = async () => {
//   const response = await createRestApiRequest("klines", {
//     params: {
//       symbol: "BTCUSDT",
//       interval: "1h",
//       timeZone: "7:00",
//     },
//   });

//   return response.map((bar: Bar[]) => ({
//     time: bar[0], // Open time
//     open: bar[1],
//     high: bar[2],
//     low: bar[3],
//     close: bar[4],
//     volume: bar[5],
//   }));
// };
