package symbol.plot;

import symbol.SymbolData;
import org.knowm.xchart.*;
import org.knowm.xchart.internal.chartpart.Chart;
import org.knowm.xchart.style.markers.SeriesMarkers;
import org.knowm.xchart.style.theme.MatlabTheme;

import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class SymbolPlotter {

    public static BufferedImage buildLineChart(SymbolData data, PlotTitles titles, int imgWidth, int imgHeight){
        ArrayList<Double> x = getXLinspace(data.totalDataPointCount());

        XYChart chart = new XYChart(imgWidth, imgHeight);
        chart.getStyler().setTheme(new MatlabTheme());
        XYSeries series = chart.addSeries("Stock Movement", x, data.getOpen());
        series.setMarker(SeriesMarkers.NONE);
        setChartTitles(chart, titles);

        return buildImage(chart);
    }

    public static BufferedImage buildOHLCChart(SymbolData data, PlotTitles titles, int imgWidth, int imgHeight){
        ArrayList<Double> x = getXLinspace(data.totalDataPointCount());

        OHLCChart chart = new OHLCChart(imgWidth, imgHeight);
        chart.getStyler().setTheme(new MatlabTheme());
        OHLCSeries series = chart.addSeries("Stock Movement", x, data.getOpen(), data.getHigh(),
                data.getLow(), data.getClose(), data.getVolume());
        series.setMarker(SeriesMarkers.NONE);

        setChartTitles(chart, titles);

        return buildImage(chart);
    }

    private static void setChartTitles(Chart chart, PlotTitles titles){
        chart.setTitle(titles.getMainTitle());
        chart.setXAxisTitle(titles.getXTitle());
        chart.setYAxisTitle(titles.getYTitle());
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
