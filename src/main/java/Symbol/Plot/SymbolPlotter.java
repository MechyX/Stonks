package Symbol.Plot;

import Symbol.SymbolData;
import org.knowm.xchart.*;
import org.knowm.xchart.internal.chartpart.Chart;
import org.knowm.xchart.style.markers.SeriesMarkers;
import org.knowm.xchart.style.theme.MatlabTheme;

import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class SymbolPlotter {
    public static XYChart buildLineChart(SymbolData data, PlotTitles titles){
        ArrayList<Double> x = getXLinspace(data.totalDataPointCount());

        XYChart chart = new XYChart(800, 600);
        chart.getStyler().setTheme(new MatlabTheme());
        XYSeries series = chart.addSeries("Stock Movement", x, data.getOpen());
        series.setMarker(SeriesMarkers.NONE);
        setChartTitles(chart, titles.getMainTitle(), titles.getXTitle(), titles.getYTitle());

        return chart;
    }

    public static BufferedImage buildOHLCChart(SymbolData data, PlotTitles titles){
        ArrayList<Double> x = getXLinspace(data.totalDataPointCount());

        OHLCChart chart = new OHLCChart(1200, 800);
        chart.getStyler().setTheme(new MatlabTheme());
        OHLCSeries series = chart.addSeries("Stock Movement", x, data.getOpen(), data.getHigh(),
                data.getLow(), data.getClose(), data.getVolume());
        series.setMarker(SeriesMarkers.NONE);

        setChartTitles(chart, titles.getMainTitle(), titles.getXTitle(), titles.getYTitle());

        return buildImage(chart);
    }

    private static void setChartTitles(Chart chart, String title, String xAxisTitle, String yAxisTitle){
        chart.setTitle(title);
        chart.setXAxisTitle(xAxisTitle);
        chart.setYAxisTitle(yAxisTitle);
    }

    private static ArrayList<Double> getXLinspace(int size){
        ArrayList<Double> time = new ArrayList<>();
        for(double i = 1; i <= size; i++)
            time.add(i);

        return time;
    }

    private static BufferedImage buildImage(Chart chart){
        BufferedImage img = null;
        try{
            img = BitmapEncoder.getBufferedImage(chart);
        }catch (Exception e){
            e.printStackTrace();
        }

        return img;
    }
}
