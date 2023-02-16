import java.net.http.HttpClient;

public class FirmwareDownloader {

    private final HttpClient downloadClient;

    public FirmwareDownloader() {
        downloadClient = HttpClient.newBuilder().version(HttpClient.Version.HTTP_1_1).build();
    }

}
