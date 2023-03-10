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
import java.text.MessageFormat;
import java.util.ArrayList;


public class FirmwareInformation {

    private final HttpClient infoClient;
    private final URL apiURL;
    private final Gson gson;

    public FirmwareInformation() throws MalformedURLException {
        apiURL = new URL("https://api.ipsw.me/v4");
        infoClient = HttpClient.newBuilder().version(HttpClient.Version.HTTP_1_1).build();
        gson = new Gson();
    }

    public ArrayList<AppleDevice> getDeviceList() throws IOException, InterruptedException {
        JSONArray deviceListJson;
        ArrayList<AppleDevice> deviceList = new ArrayList<>();
        HttpRequest request = HttpRequest.newBuilder().uri(URI.create(apiURL + "/devices")).header("Accept", "application/json").GET().build();
        HttpResponse<String> response = infoClient.send(request, HttpResponse.BodyHandlers.ofString());
        deviceListJson = new JSONArray(response.body());
        for (Object deviceJson : deviceListJson) {
            deviceList.add(gson.fromJson(deviceJson.toString(), AppleDevice.class));
        }
        return deviceList;
    }

    public ArrayList<AppleFirmware> getFirmwareListForDevice(String identifier, boolean signedOnly) throws IOException, InterruptedException {
        JSONArray firmwareListJson;
        ArrayList<AppleFirmware> firmwareList = new ArrayList<>();
        HttpRequest request = HttpRequest.newBuilder().uri(URI.create(apiURL + MessageFormat.format("/device/{0}?type=ipsw", identifier))).header("Accept", "application/json").GET().build();
        HttpResponse<String> response = infoClient.send(request, HttpResponse.BodyHandlers.ofString());
        if (response.statusCode() == 200) {
            firmwareListJson = new JSONArray(new JSONObject(response.body()).getJSONArray("firmwares"));
            for (Object firmwareJson : firmwareListJson) {
                if (!signedOnly || (boolean) ((JSONObject) firmwareJson).get("signed"))
                    firmwareList.add(gson.fromJson(firmwareJson.toString(), AppleFirmware.class));
            }
        }
        return firmwareList;
    }

    public String getIdentifier(String model) throws IOException, InterruptedException {
        HttpRequest request = HttpRequest.newBuilder().uri(URI.create(apiURL + MessageFormat.format("/model/{0}", model))).header("Accept", "application/json").GET().build();
        HttpResponse<String> response = infoClient.send(request, HttpResponse.BodyHandlers.ofString());
        if (response.statusCode() == 200) {
            return new JSONObject(response.body()).getString("identifier");
        } else {
            return "Invalid model";
        }
    }


}
