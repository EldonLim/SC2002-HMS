package controller;

import database.DataBase;
import model.Doctor;
import model.Schedule;
import using.Availability;

import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;

// Depndency with DoctorManager
public class ScheduleManager {

    private static java.util.Comparator<Object> Comparator;

    public ScheduleManager() {}

    public static void printDoctorSchedule(String doctorID) {
        System.out.println("Doctor " + DataBase.getUsers().get(doctorID).getName());
        Schedule schedule = ((Doctor) DataBase.getUsers().get(doctorID)).getSchedule();
        schedule.getSlots().entrySet().stream()
                .filter(entry -> entry.getValue() == Availability.AVAILABLE)
                .sorted(java.util.Comparator.comparingInt(Map.Entry::getKey))
                .forEach(entry -> System.out.printf("%2d:00 - %2d:00\n", entry.getKey(), entry.getKey() + 1));
        System.out.println();
    }

    public static void printPersonalSchedule(String doctorID) {
        Schedule schedule = ((Doctor) DataBase.getUsers().get(doctorID)).getSchedule();
        schedule.getSlots().entrySet().stream()
                .sorted(java.util.Comparator.comparingInt(Map.Entry::getKey))
                .forEach(entry -> System.out.printf("%2d:00 - %2d:00 %s\n", entry.getKey(), entry.getKey() + 1, entry.getValue().getLabel()));
        System.out.println();
    }
}