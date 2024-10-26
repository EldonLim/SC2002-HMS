package using;

public enum BloodType {
    A_NEGATIVE("A-"),
    A_POSITIVE("A+"),
    B_NEGATIVE("B-"),
    B_POSITIVE("B+"),
    O_NEGATIVE("O-"),
    O_POSITIVE("O+"),
    AB_NEGATIVE("AB-"),
    AB_POSITIVE("AB+S");

    private final String label;

    private BloodType(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }
}
