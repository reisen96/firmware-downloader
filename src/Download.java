import java.net.URL;

public class Download {

    private Long id;

    private Double progress;

    private final String fileName;

    private String destination;

    private final AppleFirmware firmware;

    public Download(AppleFirmware firmwareToDownload) {
        firmware = firmwareToDownload;
        fileName = firmware.getFileName();
        progress = 0.0;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getProgress() {
        return progress;
    }

    public void setProgress(Double progress) {
        this.progress = progress;
    }

    public String getFileName() {
        return fileName;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public AppleFirmware getFirmware() {
        return firmware;
    }

    public URL getUrl() {
        return firmware.getUrl();
    }
}
