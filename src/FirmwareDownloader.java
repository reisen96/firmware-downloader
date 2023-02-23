import java.io.*;
import java.net.HttpURLConnection;
import java.util.ArrayList;
import java.util.Objects;
import java.util.Stack;

public class FirmwareDownloader {

    private final ArrayList<Download> downloadList;
    private final String defaultDownloadDestination;

    public FirmwareDownloader() {
        downloadList = new ArrayList<>();
        defaultDownloadDestination = System.getProperty("user.home") + "/Downloads";
    }

    public void downloadFirmware(AppleFirmware firmwareToDownload, String downloadDestination) {
        String destination = downloadDestination == null ? defaultDownloadDestination : downloadDestination;
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
                newDownload.setStatus(Download.DownloadStatus.COMPLETED);
                downloadedFile.close();
                inputStream.close();
                outputStream.close();
            } catch (IOException e) {
                System.out.println(e.getMessage());
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

    public void cancelDownload(Long downloadID) {
        for (Download download : downloadList) {
            if (download.getId().equals(downloadID)) {
                // Download threads!!!
                download.setStatus(Download.DownloadStatus.CANCELED);
                return;
            }
        }
    }

    public void clearDownloadList() {
        Stack<Download> downloadsToRemove = new Stack<>();
        for (Download download : downloadList) {
            if (download.isCompleted() || download.isCanceled()) {
                downloadsToRemove.push(download);
            }
        }
        while (!downloadsToRemove.isEmpty()) {
            downloadList.remove(downloadsToRemove.peek());
            downloadsToRemove.pop();
        }
    }
}
