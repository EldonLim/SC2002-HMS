package model;

import using.Availability;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;

/**
 * Represents the weekly schedule for a doctor, containing time slots for each day 
 * and their availability status.
 * 
 * @author Chin Linn Sheng
 * @version 5.3
 * @since 2024-10-29
 */
public class Schedule {

    /** The starting hour of the schedule (9 AM). */
    public static final int START_TIME = 9;

    /** The ending hour of the schedule (5 PM). */
    public static final int END_TIME = 17;

    /** The total number of time slots in a day (calculated as END_TIME - START_TIME). */
    public static final int TOTAL_SLOTS = END_TIME - START_TIME;

    /** The total number of working days in the schedule. */
    public static final int TOTAL_DAYS = 5;

    private final Doctor doctor;
    private final HashMap<String, HashMap<Integer, Availability>> weeklySlots;

    /**
     * Constructs a Schedule object for the specified doctor, initializing 
     * the weekly time slots and setting all to AVAILABLE by default.
     *
     * @param doctor the doctor to whom this schedule belongs
     */
    public Schedule(Doctor doctor) {
        this.doctor = doctor;
        weeklySlots = new HashMap<>();
        this.initializeWeeklySchedule();
    }

    /**
     * Initializes the weekly schedule by setting up daily time slots 
     * for each of the specified working days, marking each slot as AVAILABLE.
     */
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

    /**
     * Gets the doctor associated with this schedule.
     *
     * @return the doctor associated with this schedule
     */
    public Doctor getDoctor() {
        return doctor;
    }

    /**
     * Gets the weekly schedule slots, mapping each date to its hourly availability.
     *
     * @return the weekly slots with availability statuses for each hour and date
     */
    public HashMap<String, HashMap<Integer, Availability>> getWeeklySlots() {
        return weeklySlots;
    }

    /**
     * Sets the availability status for a specific time slot on a particular date.
     *
     * @param date        the date for which the availability status is being set
     * @param time        the hour for which the availability is being set
     * @param availability the new availability status
     */
    public void setAvailabilityForParticularDate_Time(String date, int time, Availability availability) {
        weeklySlots.get(date).put(time, availability);
    }
}
