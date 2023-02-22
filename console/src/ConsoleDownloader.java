import java.io.IOException;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.Timer;
import java.util.TimerTask;

public class ConsoleDownloader {
    private enum MenuOption {
        EXIT,
        DEVICELIST,
        IDENTIFIER,
        DOWNLOAD,
        DOWNLOADLIST,
        INVALID,
    }

    private int userInput;
    private final FirmwareInformation firmwareInformation;
    private final FirmwareDownloader firmwareDownloader;
    private final int downloadListUpdateInterval;

    public ConsoleDownloader() throws MalformedURLException {
        firmwareInformation = new FirmwareInformation();
        firmwareDownloader = new FirmwareDownloader();
        downloadListUpdateInterval = 1000;
        userInput = -1;
    }

    public void deviceList() throws IOException, InterruptedException {
        ArrayList<AppleDevice> deviceList = firmwareInformation.getDeviceList();
        for (AppleDevice device : deviceList) {
            System.out.println(device);
        }
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
        if (deviceFirmwareList.isEmpty()) {
            System.out.println("No firmwares for device");
            return;
        }
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
        MenuOption userSelected;
        Scanner scanner = new Scanner(System.in);
        while (true) {
            clearConsole();
            System.out.println("Apple firmware downloader");
            System.out.println("--------Main menu--------");
            printMenu();
            userInput = scanner.nextInt();
            userSelected = userInput > -1 && userInput < MenuOption.values().length ? MenuOption.values()[userInput] : MenuOption.INVALID;
            switch (userSelected) {
                case DEVICELIST -> deviceList();
                case IDENTIFIER -> deviceIdentifier();
                case DOWNLOAD -> downloadFirmware();
                case DOWNLOADLIST -> downloadList();
                case EXIT -> {
                    return;
                }
                default -> System.out.println("Invalid option");
            }
        }
    }

    private void printMenu() {
        System.out.println(MenuOption.DEVICELIST.ordinal() + ". Device list");
        System.out.println(MenuOption.IDENTIFIER.ordinal() + ". Get device identifier from model");
        System.out.println(MenuOption.DOWNLOAD.ordinal() + ". Download a firmware");
        System.out.println(MenuOption.DOWNLOADLIST.ordinal() + ". Download list");
        System.out.println(MenuOption.EXIT.ordinal() + ". Exit program");
    }

    private void clearConsole() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }

    public static void main(String[] args) throws IOException, InterruptedException {
        ConsoleDownloader consoleDownloader = new ConsoleDownloader();
        consoleDownloader.mainMenu();
    }
}
