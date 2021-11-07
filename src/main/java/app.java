import Service.AlphaVantageHelper;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.stream.Collectors;
import Service.SymbolPlotter;
import io.github.cdimascio.dotenv.Dotenv;
import org.json.JSONObject;

public class app {

    public static void main(String[] args) {
        Dotenv dotenv = Dotenv.configure()
                .directory(".")
                .ignoreIfMalformed()
                .ignoreIfMissing()
                .load();
        String apiKey = dotenv.get("ALPHA_VANTAGE_API_KEY");
        AlphaVantageHelper helper = new AlphaVantageHelper(apiKey);
        String res = helper.latestTimeSeriesIntraday("IBM", 5);
        System.out.println(res);
        ArrayList<String> timeSeriesString = null;
        ArrayList<Double> openValues = new ArrayList<>();

        try {
            JSONObject jsonObject = new JSONObject(res);
            JSONObject data = (JSONObject) jsonObject.get("Time Series (5min)");
            timeSeriesString = (ArrayList<String>) data.keySet().stream().sorted().
                    collect(Collectors.toList());

            for(int i = 0; i < timeSeriesString.size(); i++){
                String key = timeSeriesString.get(i);
                JSONObject profile = (JSONObject) data.get(key);
                double openValue = Double.parseDouble((String)profile.get("1. open"));
                openValues.add(openValue);
            }

            BufferedImage img = SymbolPlotter.plotLatestTimeSeriesIntraday(timeSeriesString, openValues);
        }  catch (Exception err){
            err.printStackTrace();
        }


    }
}
