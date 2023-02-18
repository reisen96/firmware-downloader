import java.io.IOException;
import java.net.MalformedURLException;

public class ConsoleDownloader {
    private enum MenuOption {
        DEVICELIST,
        FIRMWARELIST,
        IDENTIFIER,
        DOWNLOAD,
        DOWNLOADLIST
    }

    private final FirmwareInformation firmwareInformation;
    private final FirmwareDownloader firmwareDownloader;

    public ConsoleDownloader() throws MalformedURLException {
        firmwareInformation = new FirmwareInformation();
        firmwareDownloader = new FirmwareDownloader();
    }

    public void mainMenu() {


    }

    public static void main(String[] args) throws IOException {
        ConsoleDownloader consoleDownloader = new ConsoleDownloader();
        consoleDownloader.mainMenu();
    }
}
