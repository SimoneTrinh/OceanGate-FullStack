import styles from "./index.module.css";
import { useEffect, useRef } from "react";
import { ChartingLibraryWidgetOptions, LanguageCode, ResolutionString, widget } from "@/public/static/charting_library";
import { createDataFeed } from "./binance_data_feed/create_data_feed";

export const TVChartContainer = (props: Partial<ChartingLibraryWidgetOptions>) => {
	const chartContainerRef = useRef<HTMLDivElement>(null) as React.MutableRefObject<HTMLInputElement>;

	useEffect(() => {
		const widgetOptions: ChartingLibraryWidgetOptions = {
			symbol: props.symbol,
			// BEWARE: no trailing slash is expected in feed URL
			datafeed: createDataFeed(),
			interval: props.interval as ResolutionString,
			container: chartContainerRef.current,
			library_path: props.library_path,
			locale: props.locale as LanguageCode,
			disabled_features: ["use_localstorage_for_settings", "header_compare", "header_quick_search", "header_screenshot", "header_saveload", "context_menus", "adaptive_logo", "go_to_date", "timeframes_toolbar"],
			enabled_features: [],
			charts_storage_url: props.charts_storage_url,
			charts_storage_api_version: props.charts_storage_api_version,
			client_id: props.client_id,
			user_id: props.user_id,
			fullscreen: props.fullscreen,
			autosize: props.autosize,
			study_count_limit: 3,
			timezone: "Asia/Ho_Chi_Minh",
			theme: props.theme,
		};

		const tvWidget = new widget(widgetOptions);

		tvWidget.onChartReady(() => {
			tvWidget.headerReady().then(() => {
				const button = tvWidget.createButton();
				button.setAttribute("title", "Click to show a notification popup");
				button.classList.add("apply-common-tooltip");
				button.addEventListener("click", () =>
					tvWidget.showNoticeDialog({
						title: "About Us",
						body: "Chart integration by Trinh The Long - UIT \n IE303.E21.CN2.CNTT",
						callback: () => {
							console.log("Noticed!");
						},
					})
				);

				button.innerHTML = "About Us";
			});
		});

		return () => {
			tvWidget.remove();
		};
	}, [props]);

	return (
		<>
			<div ref={chartContainerRef} className={styles.TVChartContainer} />
		</>
	);
};