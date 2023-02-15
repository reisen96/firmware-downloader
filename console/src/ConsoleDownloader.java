import java.io.IOException;
import java.net.MalformedURLException;
import java.util.ArrayList;

public class ConsoleDownloader {
    public static void main(String[] args) throws IOException, InterruptedException {
        ArrayList<AppleDevice> deviceList;
        FirmwareInformation firmwareInformation = new FirmwareInformation();
        System.out.println("Firmware Downloader");
        System.out.println("Device list:");
        deviceList = firmwareInformation.getDeviceList();
        for (AppleDevice device : deviceList) {
            System.out.println(device);
        }
    }
}
