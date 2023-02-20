import java.io.IOException;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.Timer;
import java.util.TimerTask;

public class ConsoleDownloader {
    private enum MenuOption {
        DEVICELIST,
        FIRMWARELIST,
        IDENTIFIER,
        DOWNLOAD,
        DOWNLOADLIST
    }

    private int userInput;

    private final FirmwareInformation firmwareInformation;
    private final FirmwareDownloader firmwareDownloader;

    public ConsoleDownloader() throws MalformedURLException {
        firmwareInformation = new FirmwareInformation();
        firmwareDownloader = new FirmwareDownloader();
    }

    public void deviceList() {

    }

    public void firmwareList() {

    }

    public void deviceIdentifier() {

    }

    public void downloadFirmware() throws IOException, InterruptedException {
        String deviceIdentifier;
        ArrayList<AppleFirmware> deviceFirmwareList;
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter device identifier: ");
        deviceIdentifier = scanner.nextLine();
        deviceFirmwareList = firmwareInformation.getFirmwareListForDevice(deviceIdentifier, true);



    }

    public void downloadList() throws IOException {
        Timer updateTimer = new Timer();
        ArrayList<Download> downloadList = firmwareDownloader.getDownloadList();
        System.out.println("Press enter to return");
        updateTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                for (Download download : downloadList) {
                    System.out.println(download);
                }
            }
        }, 0, 1000);
        userInput = System.in.read();
        updateTimer.cancel();
    }

    public void mainMenu() {


    }

    public static void main(String[] args) throws IOException {
        ConsoleDownloader consoleDownloader = new ConsoleDownloader();
        consoleDownloader.mainMenu();
    }
}
