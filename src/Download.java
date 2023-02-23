import java.net.URL;
import java.text.MessageFormat;

public class Download {

    public enum DownloadStatus {
        INPROGRESS,
        CANCELED,
        COMPLETED
    }

    private Long id;
    private Double progress;
    private DownloadStatus status;
    private final String fileName;
    private String destination;
    private final AppleFirmware firmware;

    public Download(AppleFirmware firmwareToDownload) {
        firmware = firmwareToDownload;
        fileName = firmware.getFileName();
        progress = 0.0;
        status = DownloadStatus.INPROGRESS;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public DownloadStatus getStatus() {
        return status;
    }

    public void setStatus(DownloadStatus status) {
        this.status = status;
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

    public boolean isCompleted() {
        return status == DownloadStatus.COMPLETED;
    }

    public boolean isCanceled() {
        return status == DownloadStatus.CANCELED;
    }

    @Override
    public String toString() {
        return MessageFormat.format("File Name: {0}\nDestination: {1}\nDownload ID: {2}\nProgress: {3}%\n", fileName, destination, id, progress * 100.0);
    }
}
