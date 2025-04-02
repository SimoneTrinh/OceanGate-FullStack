import { useState } from "react";
import dynamic from "next/dynamic";
import Script from "next/script";
import { useRouter } from "next/router";
import {
  ChartingLibraryWidgetOptions,
  ResolutionString,
} from "@/public/static/charting_library/charting_library";
import { ThemeName } from "@/charting_library/charting_library";

const TVChartContainer = dynamic(
  () =>
    import("@/components/TVChartContainer").then((mod) => mod.TVChartContainer),
  { ssr: false }
);

const defaultWidgetProps: Partial<ChartingLibraryWidgetOptions> = {
  library_path: "/static/charting_library/",
  locale: "en",
  fullscreen: false,
  autosize: true,
  theme: "dark"
};


export default function Home() {
  const [isScriptReady, setIsScriptReady] = useState(false);
  const router = useRouter();

  // Default
  const { symbol = "BTCUSDT", interval = "1D", theme = "light" } = router.query;
  const chartSymbol = typeof symbol === "string" ? symbol : "BTCUSDT";
  const chartInterval =
    typeof interval === "string" ? interval : ("1D" as ResolutionString);
  const chartTheme = typeof theme === "string" ? theme : "light";

  // ?symbol=ETHUSDT&interval=1h&theme=light

  return (
    <>
      <Script
        src="/static/datafeeds/udf/dist/bundle.js"
        strategy="lazyOnload"
        onReady={() => {
          setIsScriptReady(true);
        }}
      />
      {isScriptReady && router.isReady && (
        <TVChartContainer
          {...defaultWidgetProps}
          symbol={chartSymbol}
          interval={chartInterval as ResolutionString}
          theme={chartTheme as ThemeName}
        />
      )}
    </>
  );
}