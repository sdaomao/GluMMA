package information;

public class Information {
    private String time;
    private String filename;
    private String period;
    private String glucose;
    private String systolic;
    private String diastolic;
    private String bloodPressure;
    private String timer;
    private String weight;
    private String food;
    private String exercise;
    private String notes;

    // Constructor, getters, and setters`
    public Information(String time, String period, String glucose, String bloodPressure, String timer) {
        this.time = time;
        this.period = period;
        this.glucose = glucose;
        this.bloodPressure = bloodPressure;
        this.timer = timer;

    }
    public String getTimer() {
        return timer;
    }

    public String getBloodPressure() {
        return bloodPressure;
    }

    public String getTime() {
        return time;
    }

    public String getFilename() {
        return filename;
    }

    public String getPeriod() {
        return period;
    }

    public String getGlucose() {
        return glucose;
    }

    public String getSystolic() {
        return systolic;
    }

    public String getDiastolic() {
        return diastolic;
    }

    public String getWeight() {
        return weight;
    }

    public String getFood() {
        return food;
    }

    public String getExercise() {
        return exercise;
    }

    public String getNotes() {
        return notes;
    }
}

