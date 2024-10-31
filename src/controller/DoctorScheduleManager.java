// package controller;

// import java.time.LocalDate;
// import java.time.LocalTime;
// import java.time.LocalDateTime;
// import java.time.format.DateTimeFormatter;
// import java.util.HashMap;
// import java.util.Map;
// import java.util.Scanner;

// import helper.Helper;
// import model.Doctor;
// import model.Schedule;

// import java.util.ArrayList;
// import java.util.List;

// // Association with doctorManager class
// // DocScheduleManager manages schedule
// public class DoctorScheduleManager {
//     public enum Availability {
//         AVAILABLE,
//         NOT_AVAILABLE,
//         CONFIRMED_APPOINTMENT,
//         PENDING_APPOINTMENT,
//         CANCELLED_APPOINTMENT
//     }

//     public DoctorScheduleManager() {}

//     // schedule will show the doctor's schedule for the next 7 days
//     // one slot is 30mins
//     // by default all slots are initilized as NOT_AVAILABLE
//     public static Doctor initializeNextSevenDays(Doctor doctor) {
//         // default working time of doctor
//         LocalTime START_TIME = LocalTime.of(8, 0);
//         LocalTime END_TIME = LocalTime.of(17, 0);
//         int INTERVAL_MINUTES = 30;
//         HashMap<LocalDateTime, DoctorScheduleManager.Availability> schedule = doctor.getSchedule();

//         LocalDate currentDate = LocalDate.now();

//         for (int day = 0; day < 7; day++) {
//             LocalDate date = currentDate.plusDays(day);
//             LocalTime time = START_TIME;

//             while (!time.isAfter(END_TIME)) {
//                 LocalDateTime dateTime = LocalDateTime.of(date, time);
//                 schedule.put(dateTime, Availability.PENDING_APPOINTMENT);
//                 time = time.plusMinutes(INTERVAL_MINUTES);
//             }
//         }

//         doctor.setSchedule(schedule);
//         return doctor;
//     }

//     public static void setDoctorAvailability(Doctor doctor) {
//         // Scanner sc1 = new Scanner(System.in);
//         DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
//         DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm");
//         LocalDateTime dateTime;

//         while (true) {
//             try {
//                 // let doctor choose available date
//                 System.out.print(
//                         "Enter the date you wish to set/update availability for appointment (Please follow the format of 'YYYY-MM-DD'): ");
//                 String dateInput = Helper.readString();
//                 LocalDate date = LocalDate.parse(dateInput, dateFormatter);

//                 // let doctor choose available time
//                 System.out.print(
//                         "Enter the time you wish to set/update availability for appointment (Please follow the format of 'HH:mm'): ");
//                 String timeInput = Helper.readString();
//                 LocalTime time = LocalTime.parse(timeInput, timeFormatter);

//                 dateTime = LocalDateTime.of(date, time);

//                 // check if the date & time is in the correct range
//                 if (!isValidDateTime(dateTime)) {
//                     throw new Exception("Invalid date!");
//                 }
//                 break;
//             } catch (Exception e) {
//                 if (e.getMessage().contains("Invalid")) {
//                     // if date/time not in correct range
//                     System.out.println("Invalid date or time. Please try again!");
//                 } else {
//                     // if date/time not following desired format
//                     System.out.println("Incorrect input format. Please try again.");
//                 }
//                 continue;
//             }
//         }

//         while (true) {
//             // let doctor set availability for the chosen timeslot
//             System.out.print(dateTime + " (A for Available, N for Not Available, Y for Confirm Appointment, C for Cancel Appointment)?");
//             char choice = Helper.readChar();
//             choice = Character.toLowerCase(choice);
//             try {
//                 if (choice == 'a' || choice == 'n' || choice == 'y' || choice == 'c') {
//                     if (choice == 'a') {
//                         doctor.getSchedule().put(dateTime, Availability.AVAILABLE);
//                     } else if (choice == 'n') {
//                         doctor.getSchedule().put(dateTime, Availability.NOT_AVAILABLE);
//                     } else if (choice == 'y') {
//                         doctor.getSchedule().put(dateTime, Availability.CONFIRMED_APPOINTMENT);
//                     } else if (choice == 'c') {
//                         doctor.getSchedule().put(dateTime, Availability.CANCELLED_APPOINTMENT);
//                     }
//                 }
//                 break;
//             } catch (Exception e) {
//                 System.out.println("Incorrect input. Please enter 'A' or 'N' or 'Y' or 'C' ");
//             }
//         }
//     }

//     // public Availability getAvailability(LocalDateTime dateTime) {
//     // if (!schedule.containsKey(dateTime)) {
//     // throw new IllegalArgumentException("DateTime not found in schedule");
//     // }
//     // return schedule.get(dateTime);
//     // }

//     private static boolean isValidDateTime(LocalDateTime dateTime) {
//         LocalTime START_TIME = LocalTime.of(8, 0);
//         LocalTime END_TIME = LocalTime.of(17, 0);
//         int INTERVAL_MINUTES = 30;

//         LocalTime time = dateTime.toLocalTime();
//         LocalDate date = dateTime.toLocalDate();

//         return !date.isBefore(LocalDate.now()) &&
//                 !date.isAfter(LocalDate.now().plusDays(6)) &&
//                 !time.isBefore(START_TIME) &&
//                 !time.isAfter(END_TIME) &&
//                 time.getMinute() % INTERVAL_MINUTES == 0;
//     }

//     public static List<LocalDateTime> getAvailableSlots(Doctor doctor) {
//         List<LocalDateTime> availableSlots = new ArrayList<>();
//         HashMap<LocalDateTime, DoctorScheduleManager.Availability> schedule = doctor.getSchedule();
//         for (Map.Entry<LocalDateTime, Availability> entry : schedule.entrySet()) {
//             if (entry.getValue() == Availability.AVAILABLE) {
//                 availableSlots.add(entry.getKey());
//             }
//         }
//         return availableSlots;
//     }

//     // method overloading
//     public static String serealizeSchedule(HashMap<LocalDateTime, DoctorScheduleManager.Availability> schedule) {
//         StringBuilder sb = new StringBuilder();
//         LocalDate currentDate = null;

//         for (Map.Entry<LocalDateTime, Availability> entry : schedule.entrySet()
//                 .stream()
//                 .sorted(Map.Entry.comparingByKey())
//                 .toList()) {

//             LocalDateTime dateTime = entry.getKey();

//             if (currentDate == null || !currentDate.equals(dateTime.toLocalDate())) {
//                 currentDate = dateTime.toLocalDate();
//                 sb.append("\n=== ").append(currentDate).append(" ===\n");
//             }

//             sb.append(dateTime.toLocalTime())
//                     .append(" - ")
//                     .append(entry.getValue())
//                     .append("\n");
//         }

//         return sb.toString();
//     }

//     public static String serealizeSchedule(List<LocalDateTime> schedule) {
//         StringBuilder sb = new StringBuilder();
//         LocalDate currentDate = null;

//         // Sort the input List<LocalDateTime>
//         List<LocalDateTime> sortedDateTimes = schedule.stream()
//                 .sorted()
//                 .toList();

//         for (LocalDateTime dateTime : sortedDateTimes) {
//             if (currentDate == null || !currentDate.equals(dateTime.toLocalDate())) {
//                 currentDate = dateTime.toLocalDate();
//                 sb.append("\n=== ").append(currentDate).append(" ===\n");
//             }

//             sb.append(dateTime.toLocalTime())
//                     .append(" - ")
//                     .append("PENDING_APPOINTMENT")
//                     .append("\n");

//         }

//         return sb.toString();
//     }
// }

// // TEST CASE 11
// // DOCTOR can view it's schedule by calling serealizeSchedule()

// // TEST CASE 12
// // DOCTOR can set it's availability by calling setDoctorAvailability()

// // TEST CASE 13
// // DOCTOR can accept or decline appointment requests by calling AppointmentManager.getAppointmentSlots() and setDoctorAvailability()