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
    private final int downloadListUpdateInterval;

    public ConsoleDownloader() throws MalformedURLException {
        firmwareInformation = new FirmwareInformation();
        firmwareDownloader = new FirmwareDownloader();
        downloadListUpdateInterval = 1000;
    }

    public void deviceList() {

    }

    public void firmwareList() {

    }

    public void deviceIdentifier() throws IOException, InterruptedException {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter device model: ");
        System.out.println("Device identifier: " + firmwareInformation.getIdentifier(scanner.nextLine()));
    }

    public void downloadFirmware() throws IOException, InterruptedException {
        ArrayList<AppleFirmware> deviceFirmwareList;
        int firmwareIndex = 1;
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter device identifier: ");
        deviceFirmwareList = firmwareInformation.getFirmwareListForDevice(scanner.nextLine(), true);
        for (AppleFirmware firmware : deviceFirmwareList) {
            System.out.println("Firmware number #" + firmwareIndex++);
            System.out.print(firmware);
        }
        System.out.print("Enter firmware number to download: ");
        firmwareIndex = scanner.nextInt() - 1;
        firmwareDownloader.downloadFirmware(deviceFirmwareList.get(firmwareIndex), null);
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
        }, 0, downloadListUpdateInterval);
        userInput = System.in.read();
        updateTimer.cancel();
    }

    public void mainMenu() throws IOException, InterruptedException {
        deviceIdentifier();
    }

    public static void main(String[] args) throws IOException, InterruptedException {
        ConsoleDownloader consoleDownloader = new ConsoleDownloader();
        consoleDownloader.mainMenu();
    }
}
