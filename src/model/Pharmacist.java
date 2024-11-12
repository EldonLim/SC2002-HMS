package model;

import using.Gender;
import using.Role;

/**
 * Represents a pharmacist in the medical system, extending the Staff class 
 * with attributes inherited from a staff member.
 * 
 * @author Goh Jun Keat
 * @version 1.1
 * @since 2024-10-27
 */
public class Pharmacist extends Staff {

    /**
     * Constructs a Pharmacist object with the specified details.
     *
     * @param name     the pharmacist's name
     * @param id       the pharmacist's unique identifier
     * @param password the pharmacist's password
     * @param role     the role of the user (should be PHARMACIST)
     * @param gender   the pharmacist's gender
     * @param age      the pharmacist's age
     */
    public Pharmacist(String name, String id, String password, Role role, Gender gender, int age) {
        super(name, id, password, gender, role, age);
    }
}
