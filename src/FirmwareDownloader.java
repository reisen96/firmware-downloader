import java.io.*;
import java.net.HttpURLConnection;
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
        Download newDownload = new Download(firmwareToDownload);
        downloadList.add(newDownload);
        Runnable download = () ->
        {
            try (FileOutputStream downloadedFile = new FileOutputStream(newDownload.getFileName())) {
                HttpURLConnection httpConnection = (HttpURLConnection) (newDownload.getUrl().openConnection());
                BufferedInputStream inputStream = new BufferedInputStream(httpConnection.getInputStream());
                BufferedOutputStream outputStream = new BufferedOutputStream(downloadedFile, 1024);
                byte[] downloadedBytes = new byte[1024];
                int completeFileSize = httpConnection.getContentLength(), currentFileSize = 0, currentlyRead;
                newDownload.setId(Thread.currentThread().threadId());
                newDownload.setDestination(destination);
                while ((currentlyRead = inputStream.read(downloadedBytes, 0, 1024)) >= 0) {
                    currentFileSize += currentlyRead;
                    newDownload.setProgress((double) currentFileSize / (double) completeFileSize);
                    outputStream.write(downloadedBytes, 0, currentlyRead);
                }
                downloadedFile.close();
                inputStream.close();
                outputStream.close();
            } catch (FileNotFoundException e) {
                // Hey!
            } catch (IOException e) {
                // Hey 2!
            }
        };
        Thread downloadThread = new Thread(download);
        downloadThread.start();
    }

    public ArrayList<Download> getDownloadList() {
        return downloadList;
    }

    public String getDefaultDownloadDestination() {
        return defaultDownloadDestination;
    }
}
