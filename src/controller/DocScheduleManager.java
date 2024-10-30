package controller;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import model.Doctor;

import java.util.ArrayList;
import java.util.List;

// Association with doctorManager class
// DocScheduleManager manages schedule
public class DocScheduleManager {
    public enum Availability {
        AVAILABLE,
        NOT_AVAILABLE,
        CONFIRMED_APPOINTMENT,
        PENDING_APPOINTMENT
    }

    // schedule will show the doctor's schedule for the next 7 days
    // one slot is 30mins
    // by default all slots are initilized as NOT_AVAILABLE
    private Doctor initializeNextSevenDays(Doctor doctor) {
        // default working time of doctor
        LocalTime START_TIME = LocalTime.of(8, 0);
        LocalTime END_TIME = LocalTime.of(17, 0);
        int INTERVAL_MINUTES = 30;
        HashMap<LocalDateTime, DocScheduleManager.Availability> schedule = doctor.getSchedule();

        LocalDate currentDate = LocalDate.now();

        for (int day = 0; day < 7; day++) {
            LocalDate date = currentDate.plusDays(day);
            LocalTime time = START_TIME;

            while (!time.isAfter(END_TIME)) {
                LocalDateTime dateTime = LocalDateTime.of(date, time);
                schedule.put(dateTime, Availability.NOT_AVAILABLE);
                time = time.plusMinutes(INTERVAL_MINUTES);
            }
        }

        doctor.setSchedule(schedule);
        return doctor;
    }

    public static void setDoctorAvailability(Doctor doctor) {
        Scanner sc = new Scanner(System.in);
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm");
        LocalDateTime dateTime;

        while (true) {
            try {
                // let doctor choose available date
                System.out.print(
                        "Enter the date you wish to set/update availability for appointment (Please follow the format of 'YYYY-MM-DD'): ");
                String dateInput = sc.nextLine();
                LocalDate date = LocalDate.parse(dateInput, dateFormatter);

                // let doctor choose available time
                System.out.print(
                        "Enter the time you wish to set/update availability for appointment (Please follow the format of 'HH:mm'): ");
                String timeInput = sc.nextLine();
                LocalTime time = LocalTime.parse(timeInput, timeFormatter);

                dateTime = LocalDateTime.of(date, time);

                // check if the date & time is in the correct range
                if (!isValidDateTime(dateTime)) {
                    throw new Exception("Invalid date!");
                }
                break;
            } catch (Exception e) {
                if (e.getMessage().contains("Invalid")) {
                    // if date/time not in correct range
                    System.out.println("Invalid date or time. Please try again!");
                } else {
                    // if date/time not following desired format
                    System.out.println("Incorrect input format. Please try again.");
                }
                continue;
            }
        }

        while (true) {
            // let doctor set availability for the chosen timeslot
            System.out.print(dateTime + " (A?N)?");
            char choice = sc.next().charAt(0);
            try {
                if (choice == 'A' || choice == 'a' || choice == 'N' || choice == 'n') {
                    if (choice == 'A' || choice == 'a') {
                        doctor.getSchedule().put(dateTime, Availability.AVAILABLE);
                    } else {
                        doctor.getSchedule().put(dateTime, Availability.NOT_AVAILABLE);
                    }
                }
                break;
            } catch (Exception e) {
                System.out.println("Incorrect input. Please enter 'A' or 'N'.");
            }
        }
        sc.close();
    }

    // public Availability getAvailability(LocalDateTime dateTime) {
    // if (!schedule.containsKey(dateTime)) {
    // throw new IllegalArgumentException("DateTime not found in schedule");
    // }
    // return schedule.get(dateTime);
    // }

    private static boolean isValidDateTime(LocalDateTime dateTime) {
        LocalTime START_TIME = LocalTime.of(8, 0);
        LocalTime END_TIME = LocalTime.of(17, 0);
        int INTERVAL_MINUTES = 30;

        LocalTime time = dateTime.toLocalTime();
        LocalDate date = dateTime.toLocalDate();

        return !date.isBefore(LocalDate.now()) &&
                !date.isAfter(LocalDate.now().plusDays(6)) &&
                !time.isBefore(START_TIME) &&
                !time.isAfter(END_TIME) &&
                time.getMinute() % INTERVAL_MINUTES == 0;
    }

    public static List<LocalDateTime> getAvailableSlots() {
        List<LocalDateTime> availableSlots = new ArrayList<>();
        for (Map.Entry<LocalDateTime, Availability> entry : schedule.entrySet()) {
            if (entry.getValue() == Availability.AVAILABLE) {
                availableSlots.add(entry.getKey());
            }
        }
        return availableSlots;
    }
}
