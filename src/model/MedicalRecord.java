package model;

import java.util.List;

public class MedicalRecord {

    private Patient patient; // Composition with patient
    private String recordID;
    private List<String> diagnosis;
    private List<String> treatments;

}
