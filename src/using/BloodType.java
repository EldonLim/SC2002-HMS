package using;

public enum BloodType {
    A_NEGATIVE("A-"),
    A_POSITIVE("A+"),
    B_NEGATIVE("B-"),
    B_POSITIVE("B+"),
    O_NEGATIVE("O-"),
    O_POSITIVE("O+"),
    AB_NEGATIVE("AB-"),
    AB_POSITIVE("AB+");

    private final String label;

    BloodType(String label) {
        this.label = label;
    }

    public static BloodType fromString(String bloodType) {
        for (BloodType bloodType_ : values())
            if (bloodType_.label.equals(bloodType))
                return bloodType_;
        return null;
    }

    public String getLabel() {
        return label;
    }

}
