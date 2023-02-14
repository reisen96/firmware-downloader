import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;


public class FirmwareInformation {

    private URL apiURL;

    private HttpClient infoClient;

    public FirmwareInformation() throws MalformedURLException {
        apiURL = new URL("https://api.ipsw.me/v4");
        infoClient = HttpClient.newBuilder().version(HttpClient.Version.HTTP_1_1).build();
    }

    public ArrayList<AppleDevice> getDeviceList() throws IOException, InterruptedException {
        ArrayList<AppleDevice> deviceList = new ArrayList<>();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("https://api.ipsw.me/v4/devices"))
                .header("Accept", "application/json")
                .GET()
                .build();
        HttpResponse<String> response = infoClient.send(request, HttpResponse.BodyHandlers.ofString());
        




        return deviceList;
    }
}
