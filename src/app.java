import APIHelper.APIHelper;

public class app {
    public static void main(String[] args) {
        APIHelper helper = new APIHelper("");
        String res = helper.dailyTimeSeries("IBM");
        System.out.println();
    }
}