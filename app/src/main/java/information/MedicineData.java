package information;

public class MedicineData {

    private String medicine;

    private String Type;

    public MedicineData(String medicine, String Type) {
        this.medicine = medicine;
        this.Type = Type;
    }

    public String getMedicine() {
        return medicine;
    }

    public String getType() {
        return Type;
    }

    public void setMedicine(String medicine) {
        this.medicine = medicine;
    }

    public void setType(String Type) {
        this.Type = Type;
    }
}
