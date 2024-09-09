package information;

public class FoodData {
    private String food;
    private String Date;
    private String time;
    private String lauch;

    public FoodData(String food, String Date, String time, String lauch) {
        this.food = food;
        this.Date = Date;
        this.time = time;
        this.lauch = lauch;
    }

    public String getFood() {
        return food;
    }

    public String getDate() {
        return Date;
    }

    public String getTime() {
        return time;
    }

    public String  getLauch() {
        return lauch;
    }

}
