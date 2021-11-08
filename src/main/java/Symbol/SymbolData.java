package Symbol;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.stream.Collectors;

// Immutable class
public class SymbolData {
    private final ArrayList<String> timeSeriesString;
    private final ArrayList<Double> open;
    private final ArrayList<Double> close;
    private final ArrayList<Double> high;
    private final ArrayList<Double> low;
    private final ArrayList<Double> volume;
    private final String timePeriod;
    private final String responseString;

    public String getTimePeriod() {
        return timePeriod;
    }

    public int totalDataPointCount(){
        return open.size();
    }

    public ArrayList<Double> getClose() {
        return new ArrayList<Double>(close);
    }

    public ArrayList<Double> getHigh() {
        return new ArrayList<Double>(high);
    }

    public ArrayList<Double> getOpen(){
        return new ArrayList<Double>(open);
    }

    public ArrayList<Double> getLow(){
        return new ArrayList<Double>(low);
    }

    public ArrayList<Double> getVolume(){
        return new ArrayList<Double>(volume);
    }

    public ArrayList<String> getTimeSeriesString() {
        return new ArrayList<String>(timeSeriesString);
    }

    public String getResponseString() {
        return responseString;
    }

    public SymbolData(String responseString, String timePeriod){
        this.timePeriod = timePeriod;
        this.responseString = responseString;
        open = new ArrayList<>();
        close = new ArrayList<>();
        high = new ArrayList<>();
        low = new ArrayList<>();
        volume = new ArrayList<>();

        JSONObject jsonObject = new JSONObject(responseString);
        JSONObject data = (JSONObject) jsonObject.get("Time Series " + "(" + timePeriod + ")");
        timeSeriesString = (ArrayList<String>) data.keySet().stream().sorted().
                collect(Collectors.toList());
        populateFields(data);
    }

    private void populateFields(JSONObject data){
        try {
            for(int i = 0; i < timeSeriesString.size(); i++){
                String key = timeSeriesString.get(i);
                JSONObject profile = (JSONObject) data.get(key);
                double openValue = Double.parseDouble((String)profile.get("1. open"));
                double highValue = Double.parseDouble((String)profile.get("2. high"));
                double lowValue = Double.parseDouble((String)profile.get("3. low"));
                double closeValue = Double.parseDouble((String)profile.get("4. close"));
                double volumeValue = Double.parseDouble((String)profile.get("5. volume"));
                open.add(openValue);
                high.add(highValue);
                low.add(lowValue);
                close.add(closeValue);
                volume.add(volumeValue);
            }

        }  catch (Exception err){
            err.printStackTrace();
        }
    }
}
