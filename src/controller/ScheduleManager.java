package controller;

import database.DataBase;
import using.Availability;

import java.util.Comparator;
import java.util.HashMap;

// Depndency with DoctorManager
public class ScheduleManager {

    private static java.util.Comparator<Object> Comparator;

    public ScheduleManager() {}

    public static void printSchedule(String doctorID) {
        System.out.println("Doctor " + DataBase.getUsers().get(doctorID).getName());
        DataBase.Schedules.get(doctorID).getSchedule().entrySet().stream()
                .filter(entry -> entry.getValue() == Availability.AVAILABLE)
                .sorted(java.util.Comparator.comparingInt(HashMap.Entry::getKey))
                .forEach(entry -> System.out.printf("%d:00 - %d:00\n", entry.getKey(), entry.getKey() + 1));        System.out.println();
    }
}