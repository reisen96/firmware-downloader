import java.io.IOException;
import java.util.ArrayList;

public class ConsoleDownloader {
    public static void main(String[] args) throws IOException, InterruptedException {
        FirmwareInformation firmwareInformation = new FirmwareInformation();
        String model = "A1387";
        System.out.println(firmwareInformation.getIdentifier(model));
    }
}
