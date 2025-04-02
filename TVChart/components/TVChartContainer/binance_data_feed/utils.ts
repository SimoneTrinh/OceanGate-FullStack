import dayjs from "dayjs";
import { BinanceResolution, SymbolInfo } from "./types";

export const generateSymbol = (
  exchange: string,
  fromSymbol: string,
  toSymbol: string
): SymbolInfo => {
  const short = `${fromSymbol}|${toSymbol}`;
  return {
    short: short,
    full: `${exchange}:${short}`,
  };
};

export const getCurrentUnixTime = () => {
  return dayjs().unix();
};

export const getNextDailyBarTime = (barTime: number) => {
  const date = dayjs(barTime).add(1, "day").startOf("day");
  return date.valueOf();
};

export const getNextBarTime = (
  barTime: number,
  resolution: BinanceResolution
): number => {
  switch (resolution) {
    case "1m":
      return dayjs(barTime).add(1, "minute").startOf("minute").valueOf();
    case "15m":
      return dayjs(barTime).add(15, "minute").startOf("minute").valueOf();
    case "1h":
      return dayjs(barTime).add(1, "hour").startOf("hour").valueOf();
    case "4h":
      return dayjs(barTime).add(4, "hours").startOf("hours").valueOf();
    case "1d":
      return dayjs(barTime).add(1, "day").startOf("day").valueOf();
    case "1w":
      return dayjs(barTime).add(1, "week").startOf("week").valueOf();
    case "1M":
      return dayjs(barTime).add(1, "month").startOf("month").valueOf();
    default:
      console.log("Invalid resolution: ", resolution);
      return 0;
  }
};
