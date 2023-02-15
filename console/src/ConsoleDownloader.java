import java.io.IOException;
import java.util.ArrayList;

public class ConsoleDownloader {
    public static void main(String[] args) throws IOException, InterruptedException {
        ArrayList<AppleFirmware> firmwareList;
        FirmwareInformation firmwareInformation = new FirmwareInformation();
        System.out.println("Firmware Downloader");
        System.out.println("Firm list:");
        firmwareList = firmwareInformation.getFirmwareListForDevice("iPhone15,2", true);
        for (AppleFirmware firmware : firmwareList) {
            System.out.println(firmware);
        }
    }
}
