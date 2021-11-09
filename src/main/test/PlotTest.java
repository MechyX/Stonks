import service.AlphaVantageHelper;

import java.awt.image.BufferedImage;
import java.io.File;

import symbol.plot.PlotTitles;
import symbol.SymbolData;
import symbol.plot.SymbolPlotter;
import io.github.cdimascio.dotenv.Dotenv;

import javax.imageio.ImageIO;

public class PlotTest {
    public static void main(String[] args) {
        Dotenv dotenv = Dotenv.configure()
                .directory(".")
                .ignoreIfMalformed()
                .ignoreIfMissing()
                .load();
        String apiKey = dotenv.get("ALPHA_VANTAGE_API_KEY");
        AlphaVantageHelper helper = new AlphaVantageHelper(apiKey);
        String res = helper.monthlyTimeSeries("IBM");


        SymbolData data = new SymbolData(res);
        PlotTitles titles = new PlotTitles("Main Title", "X - Axis Title", "Y - Axis Title");
        SymbolPlotter plotter = new SymbolPlotter();
        BufferedImage img = SymbolPlotter.buildLineChart(data, titles, 1600, 800);
        File outfile = new File("image.jpg");
        try{
            ImageIO.write(img, "jpg", outfile);
        } catch (Exception e){
            e.printStackTrace();
        }
    }
}
