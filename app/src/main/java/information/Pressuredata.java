package information;

public class Pressuredata {
    private String Systolic;
    private String Diastolic;
    private  String time;

    public Pressuredata(String Systolic, String Diastolic, String time) {
        this.Systolic = Systolic;
        this.Diastolic = Diastolic;
        this.time = time;
    }

    public String getSystolic() {
        return Systolic;
    }

    public String getDiastolic() {
        return Diastolic;
    }

    public String getTime() {
        return time;
    }
}
