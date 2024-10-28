import database.DataBase;
import view.*;
import model.*;

import java.util.HashMap;

public class HMSApp {
    public static void main(String[] args) {

        new DataBase();
        for (User user : DataBase.Users.values())
            System.out.println(user.getName());
        HMSAppView hmsAppView = new HMSAppView();
    }


}