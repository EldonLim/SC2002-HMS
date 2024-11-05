package using;

public enum Gender {
    MALE("Male"),
    FEMALE("Female");

    private final String label;

    Gender(String label) {
        this.label = label;
    }

    public static Gender fromString(String gender) {
        for (Gender gender_ : values())
            if (gender_.label.equals(gender))
                return gender_;
        return null;
    }

    public String getLabel() {
        return label;
    }
}
