package HW7;

import java.io.IOException;
import java.sql.SQLException;

public class Main {
    public static void main(String[] args) throws IOException, SQLException, ClassCastException {
        UserInterfaceView userInterfaceView = new UserInterfaceView();

        userInterfaceView.runInterface();
    }
}

