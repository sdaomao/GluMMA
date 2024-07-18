package information;

public class FoodData {
    private String food;
    private String Date;
    private String time;

    public FoodData(String food, String Date, String time) {
        this.food = food;
        this.Date = Date;
        this.time = time;
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

}
