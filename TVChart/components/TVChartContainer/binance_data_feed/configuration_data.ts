import { ResolutionString } from "@/charting_library/charting_library";
import { DatafeedConfiguration } from "@/charting_library/datafeed-api";
import { TradingViewResolution } from "./types";

const tradingViewResolutions = ["1", "15", "60", "240", "1D"];

const binanceResolutions = ["1m", "15m", "1h", "4h", "1d"];

export const readOnlyTradingViewResolution = [
  ...tradingViewResolutions,
] as const;
export const readOnlyBinanceResolution = [...binanceResolutions] as const;

export const configurationData: DatafeedConfiguration = {
  supported_resolutions: tradingViewResolutions as ResolutionString[],
  exchanges: [
    {
      value: "Binance",
      name: "Binance",
      desc: "Binance crypto trading platform",
    },
  ],
  symbols_types: [
    {
      name: "crypto",
      value: "crypto",
    },
  ],
};

export const conversionObject: Record<TradingViewResolution, string> = {
  "1": "1m",
  "15": "15m",
  "60": "1h",
  "240": "4h",
  "1D": "1d",
  "1W": "1w",
  "1M": "1M",
};

export const convertResolution = (resolution: TradingViewResolution) => {
  return conversionObject[resolution];
};
