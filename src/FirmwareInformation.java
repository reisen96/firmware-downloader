import com.google.gson.Gson;
import org.json.JSONArray;
import org.json.JSONObject;

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

    private Gson gson;

    public FirmwareInformation() throws MalformedURLException {
        apiURL = new URL("https://api.ipsw.me/v4");
        infoClient = HttpClient.newBuilder().version(HttpClient.Version.HTTP_1_1).build();
        gson = new Gson();
    }

    public ArrayList<AppleDevice> getDeviceList() throws IOException, InterruptedException {
        AppleDevice appleDevice;
        JSONArray deviceListJson;
        ArrayList<AppleDevice> deviceList = new ArrayList<>();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("https://api.ipsw.me/v4/devices"))
                .header("Accept", "application/json")
                .GET()
                .build();
        HttpResponse<String> response = infoClient.send(request, HttpResponse.BodyHandlers.ofString());
        deviceListJson = new JSONArray(response.body());
        for (Object deviceJson : deviceListJson) {
            appleDevice = gson.fromJson(deviceJson.toString(), AppleDevice.class);
            deviceList.add(appleDevice);
        }
        return deviceList;
    }
}
