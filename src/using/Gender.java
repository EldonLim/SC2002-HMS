package using;

public enum Gender {
    MALE("Male"),
    FEMALE("Female");

    private final String label;

    private Gender(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }
}
