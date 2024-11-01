package model;

import using.Availability;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;

public class Schedule {

    private Doctor doctor;
    private String doctorID;
    private HashMap<String, HashMap<Integer, Availability>> weeklySlots;

    public static final int START_TIME = 9;
    public static final int END_TIME = 17;
    public static final int TOTAL_SLOTS = END_TIME - START_TIME; // Corrected the calculation

    public static final int TOTAL_DAYS = 5; // Number of days in the schedule

    public Schedule(Doctor doctor) {
        this.doctor = doctor;
        weeklySlots = new HashMap<>();
        this.initializeWeeklySchedule();
    }

    public void initializeWeeklySchedule() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yy");
        Calendar calendar = Calendar.getInstance();

        for (int day = 0; day < TOTAL_DAYS; day++) {
            String date = dateFormat.format(calendar.getTime()); // Format date as dd/MM/yy
            HashMap<Integer, Availability> dailySlots = new HashMap<>();

            for (int hour = START_TIME; hour < END_TIME; hour++) {
                dailySlots.put(hour, Availability.AVAILABLE);
            }
            weeklySlots.put(date, dailySlots);

            calendar.add(Calendar.DAY_OF_YEAR, 1); // Move to the next day
        }
    }

    public Doctor getDoctor() { return doctor; }
    public HashMap<String, HashMap<Integer, Availability>> getWeeklySlots() { return weeklySlots; }

    public void setAvailabilityForParticularDate_Time (String date, int time, Availability availability) { weeklySlots.get(date).put(time, availability); }
    public void setDoctor(Doctor user) {
    }
}
