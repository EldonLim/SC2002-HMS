package controller;

import database.DataBase;

public class DoctorManager {

    public DoctorManager() {}

    public static void printAllAvailableSlots() {
        DataBase.Schedules.keySet().forEach(ScheduleManager::printSchedule);
    }
}
