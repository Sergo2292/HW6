package HW7;

import javax.sound.midi.ControllerEventListener;
import java.io.IOException;
import java.util.Scanner;

public class UserInterfaceView {
    private Controller controller = new Controller();

    public void runInterface() throws IOException {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println();
            System.out.println("Введите город: ");
            String city = scanner.nextLine();

            if (!controller.rightCity(city)) {
                System.out.println("Ошибка, введите город...");
                continue;
            } else {
                System.out.println("Введите \"1\" для прогноза погода на 1 день, \"5\" для прогноза погоды 5 дней, \"0\" выход: ");

                String command = scanner.nextLine();

                switch (command) {
                    case "0":
                        return;
                    case "1":
                        try {
                            controller.getWeather("1", city);
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                        break;
                    case "5":
                        try {
                            controller.getWeather("5", city);
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                        break;
                    default:
                        System.out.println("Ошибка, введите город...");
                        break;
                }
            }
        }
    }
}
