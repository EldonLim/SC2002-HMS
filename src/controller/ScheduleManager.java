package controller;

import database.DataBase;
import model.Doctor;
import model.Schedule;
import using.Availability;

import java.util.Map;

public class ScheduleManager {

    public ScheduleManager() {}

    public static void printDoctorSchedule(String doctorID) {
        System.out.println("Doctor " + DataBase.getUsers().get(doctorID).getName());
        Schedule schedule = ((Doctor) DataBase.getUsers().get(doctorID)).getSchedule();

        // Print header row with dates in natural order (already sorted)
        System.out.print("Time Slot     ");
        schedule.getWeeklySlots().keySet().stream()
                .sorted()
                .forEach(date -> System.out.printf("| %-15s ", date));
        System.out.println();

        // Print each time slot row with availability across dates
        for (int hour = Schedule.START_TIME; hour < Schedule.END_TIME; hour++) {
            System.out.printf("%2d:00 - %2d:00 ", hour, hour + 1);

            int finalHour = hour;
            schedule.getWeeklySlots().entrySet().stream()
                    .sorted(Map.Entry.comparingByKey()) // Sort by date key
                    .forEach(dayEntry -> {
                        System.out.printf("| %-15s ", dayEntry.getValue().get(finalHour));
                    });
            System.out.println(); // Move to the next line after each time slot
        }
        System.out.println();
    }

    public static void printPersonalSchedule(String doctorID) {
        Schedule schedule = ((Doctor) DataBase.getUsers().get(doctorID)).getSchedule();
        System.out.println("Personal Schedule:");

        // Print header row with dates in natural order (already sorted)
        System.out.print("Time Slot     ");
        schedule.getWeeklySlots().keySet().stream()
                .sorted()
                .forEach(date -> System.out.printf("| %-10s ", date));
        System.out.println();

        // Print each time slot row with availability across dates
        for (int hour = Schedule.START_TIME; hour < Schedule.END_TIME; hour++) {
            System.out.printf("%2d:00 - %2d:00 ", hour, hour + 1);

            int finalHour = hour;
            schedule.getWeeklySlots().entrySet().stream()
                    .sorted(Map.Entry.comparingByKey()) // Sort by date key
                    .forEach(dayEntry -> {
                        System.out.printf("| %-10s ", dayEntry.getValue().get(finalHour).getLabel());
                    });
            System.out.println(); // Move to the next line after each time slot
        }
        System.out.println();
    }
}
