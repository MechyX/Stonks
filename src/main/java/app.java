import Service.AlphaVantageHelper;
import io.github.cdimascio.dotenv.Dotenv;

public class app {
    public static void main(String[] args) {
        Dotenv dotenv = Dotenv.configure()
                .directory(".")
                .ignoreIfMalformed()
                .ignoreIfMissing()
                .load();
        String apiKey = dotenv.get("ALPHA_VANTAGE_API_KEY");
        System.out.println(apiKey);
        AlphaVantageHelper helper = new AlphaVantageHelper(apiKey);
        String res = helper.dailyTimeSeries("IBM");
        System.out.println(res);
    }
}