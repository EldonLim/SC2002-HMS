import database.DataBase;
import view.*;
import model.*;

import java.util.HashMap;

public class HMSApp {
    public static void main(String[] args) {

        new DataBase();
        HMSAppView hmsAppView = new HMSAppView();
    }


}