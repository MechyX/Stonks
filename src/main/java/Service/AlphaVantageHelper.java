package Service;
import Database.RedisAdapter;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;

public class AlphaVantageHelper {
    String baseUri = "https://www.alphavantage.co/query?";
    private final HttpClient client = HttpClient.newBuilder()
            .version(HttpClient.Version.HTTP_1_1)
            .followRedirects(HttpClient.Redirect.NORMAL)
            .connectTimeout(Duration.ofSeconds(20))
            .build();
    private String APIKey;
    private RedisAdapter cache;

    public AlphaVantageHelper(String APIKey){
        cache = new RedisAdapter();
        cache.connect();
        setAPIKey(APIKey);
    }

    public void setAPIKey(String APIKey){
        this.APIKey = APIKey;
    }

    public String sendRequest(String endPoint, String searchKey){

        if (cache.get(searchKey) != null) {
            return cache.get(searchKey);
        }

        String reqURL = baseUri + endPoint;
        System.out.println("Making request to " + endPoint);

        HttpResponse<String> response;
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(reqURL))
                .build();

        try{
            response = client.send(request, HttpResponse.BodyHandlers.ofString());
        }catch(Exception e){
            System.err.println(e.getMessage());
            return " ";
        }

        if (response.statusCode() == 200) {
            String responseBody = response.body();
            cache.set(searchKey, responseBody);
            cache.expire(searchKey, 10);
            return responseBody;
        }

        return " ";
    }

    // latest 100 data points with interval as specified minutes
    public String latestTimeSeriesIntraday(String symbol, int interval){
        String endPoint = "function=TIME_SERIES_INTRADAY&symbol=" + symbol + "" +
                "&interval=" + interval + "min&" +
                "apikey=" + APIKey;
        return sendRequest(endPoint, symbol + "-" + interval);
    }

    // latest 100 data points with interval as day
    public String dailyTimeSeries(String symbol){
        String endPoint = "function=TIME_SERIES_DAILY&symbol=" + symbol + "&apikey=" + APIKey;
        return sendRequest(endPoint, symbol + "-d");
    }

    // last 20 years weekly data
    public String weeklyTimeSeries(String symbol){
        String endPoint = "function=TIME_SERIES_WEEKLY&symbol=" + symbol + "&apikey=" + APIKey;
        return sendRequest(endPoint,symbol + "-w");
    }

    // last 20 years monthly data
    public String monthlyTimeSeries(String symbol){
        String endPoint = "function=TIME_SERIES_MONTHLY&symbol=" + symbol + "&apikey=" + APIKey;
        return sendRequest(endPoint,symbol + "-m");
    }

}
