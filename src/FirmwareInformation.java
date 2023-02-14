import java.net.MalformedURLException;
import java.net.URL;
import java.net.http.HttpClient;
import java.util.ArrayList;

public class FirmwareInformation {

    private URL apiURL;

    private HttpClient infoClient;

    public FirmwareInformation() throws MalformedURLException {
        apiURL = new URL("https://api.ipsw.me/v4");
        infoClient = HttpClient.newBuilder().version(HttpClient.Version.HTTP_1_1).build();
    }

    public ArrayList<AppleDevice> getDeviceList() {
        ArrayList<AppleDevice> deviceList = new ArrayList<>();


        return deviceList;
    }
}
