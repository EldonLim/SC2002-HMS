import database.DataBase;
import view.HMSAppView;

public class HMSApp {
    public static void main(String[] args) {
        new DataBase();
        HMSAppView hmsAppView = new HMSAppView();
    }
}