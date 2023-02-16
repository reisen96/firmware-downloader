import java.io.IOException;
import java.net.MalformedURLException;

public class ConsoleDownloader {

    private final FirmwareInformation firmwareInformation;

    public ConsoleDownloader() throws MalformedURLException {
        firmwareInformation = new FirmwareInformation();
    }

    public void mainMenu() {

    }

    public static void main(String[] args) throws IOException, InterruptedException {
        ConsoleDownloader consoleDownloader = new ConsoleDownloader();
        consoleDownloader.mainMenu();
    }
}
