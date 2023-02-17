import java.net.http.HttpClient;
import java.util.ArrayList;

public class FirmwareDownloader {

    private final HttpClient downloadClient;
    private final ArrayList<Download> downloadList;

    private final String defaultDownloadDestination;

    public FirmwareDownloader() {
        downloadClient = HttpClient.newBuilder().version(HttpClient.Version.HTTP_1_1).build();
        downloadList = new ArrayList<>();
        defaultDownloadDestination = System.getProperty("user.home" + "Downloads");
    }

    public void downloadFirmware(AppleFirmware firmwareToDownload, String downloadDestination) {
        String destination = downloadDestination.isBlank() ? defaultDownloadDestination : downloadDestination;



    }

    public ArrayList<Download> getDownloadList() {
        return downloadList;
    }

    public String getDefaultDownloadDestination() {
        return defaultDownloadDestination;
    }

    private void download(){

    }
}
