package Service;

import org.knowm.xchart.BitmapEncoder;
import org.knowm.xchart.XYChart;
import org.knowm.xchart.XYSeries;
import org.knowm.xchart.style.markers.SeriesMarkers;
import org.knowm.xchart.style.theme.MatlabTheme;

import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class SymbolPlotter {
    public static XYChart buildLineChart(ArrayList<Double> x, ArrayList<Double> y){
        XYChart chart = new XYChart(800, 600);
        chart.getStyler().setTheme(new MatlabTheme());
        XYSeries series = chart.addSeries("Stock Movement", x, y);
        series.setMarker(SeriesMarkers.NONE);
        return chart;
    }

    public static void setChartTitles(XYChart chart, String title, String xAxisTitle, String yAxisTitle){
        chart.setTitle(title);
        chart.setXAxisTitle(xAxisTitle);
        chart.setYAxisTitle(yAxisTitle);
    }

    public static BufferedImage buildImage(XYChart chart){
        BufferedImage img = null;
        try{
            img = BitmapEncoder.getBufferedImage(chart);
        }catch (Exception e){
            e.printStackTrace();
        }

        return img;
    }

    public static BufferedImage plotLatestTimeSeriesIntraday(ArrayList<String> timeSeriesString, ArrayList<Double> price){
        ArrayList<Double> time = new ArrayList<>();
        for(double i = 1; i <= timeSeriesString.size(); i++)
            time.add(i);

        XYChart chart = buildLineChart(time, price);
        String title = "Starting from " + timeSeriesString.get(0) + "Till " +
                timeSeriesString.get(timeSeriesString.size() - 1);
        setChartTitles(chart, title, "Time (minutes)", "Price");

        return buildImage(chart);
    }
}
