package information;

public class TimeData {
    private String time;
    private String label;
    private boolean isVibrate;
    private boolean isEnable;
    private boolean isNotification;
    private boolean isRepeat;
    private String repeatDays;

    public TimeData(String time, String label, boolean isVibrate, boolean isNotification, boolean isEnable, boolean isRepeat, String repeatDays) {
        this.time = time;
        this.label = label;
        this.isVibrate = isVibrate;
        this.isNotification = isNotification;
        this.isEnable = isEnable;
        this.isRepeat = isRepeat;
        this.repeatDays = repeatDays;
    }

    public String getTime() {
        return time;
    }

    public String getLabel() {
        return label;
    }

    public boolean isVibrate() {
        return isVibrate;
    }

    public boolean isEnable() {

        return isEnable;
    }

    public boolean isRepeat() {
        return isRepeat;
    }

    public String getRepeatDays() {
        return repeatDays;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public void setVibrate(boolean vibrate) {
        isVibrate = vibrate;
    }

    public void setEnable(boolean enable) {
        isEnable = enable;
    }

    public void setRepeat(boolean repeat) {
        isRepeat = repeat;
        }

    public void setRepeatDays(String repeatDays) {
        this.repeatDays = repeatDays;
    }

    public boolean isEnabled() {
        return isEnable;
    }

    public boolean isNotification() {
        return isNotification;
    }
}
