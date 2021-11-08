import Service.AlphaVantageHelper;

import java.awt.image.BufferedImage;
import java.io.File;

import Symbol.Plot.PlotTitles;
import Symbol.SymbolData;
import Symbol.Plot.SymbolPlotter;
import io.github.cdimascio.dotenv.Dotenv;

import javax.imageio.ImageIO;

public class app {

    public static void main(String[] args) {
        Dotenv dotenv = Dotenv.configure()
                .directory(".")
                .ignoreIfMalformed()
                .ignoreIfMissing()
                .load();
        String apiKey = dotenv.get("ALPHA_VANTAGE_API_KEY");
        AlphaVantageHelper helper = new AlphaVantageHelper(apiKey);
        String res = helper.dailyTimeSeries("IBM");
        System.out.println(res);


        SymbolData data = new SymbolData(res, "Daily");
        PlotTitles titles = new PlotTitles("Main Title", "X - Axis Title", "Y - Axis Title");
        BufferedImage img = SymbolPlotter.buildOHLCChart(data, titles);
        File outfile = new File("image.jpg");
        try{
            ImageIO.write(img, "jpg", outfile);
        } catch (Exception e){
           e.printStackTrace();
        }
    }
}
