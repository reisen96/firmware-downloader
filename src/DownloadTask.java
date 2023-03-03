import java.io.*;
import java.net.HttpURLConnection;

public class DownloadTask implements Runnable {

    private final Download download;
    private final int bufferSize;
    private boolean active;

    public DownloadTask(Download download, String destination) {
        download.setDestination(destination);
        this.download = download;
        bufferSize = 1024;
        active = true;
    }

    @Override
    public void run() {
        try (FileOutputStream downloadedFile = new FileOutputStream(download.getFullPath())) {
            HttpURLConnection httpConnection = (HttpURLConnection) (download.getUrl().openConnection());
            BufferedInputStream inputStream = new BufferedInputStream(httpConnection.getInputStream());
            BufferedOutputStream outputStream = new BufferedOutputStream(downloadedFile, bufferSize);
            byte[] downloadedBytes = new byte[bufferSize];
            long completeFileSize = download.getFileSize(), currentFileSize = 0L, currentlyRead;
            while (active && (currentlyRead = inputStream.read(downloadedBytes, 0, bufferSize)) >= 0) {
                currentFileSize += currentlyRead;
                download.setProgress((double) currentFileSize / (double) completeFileSize);
                outputStream.write(downloadedBytes, 0, (int) currentlyRead);
            }
            inputStream.close();
            outputStream.close();
            if (active) {
                download.setStatus(Download.DownloadStatus.COMPLETED);
            } else {
                new File(download.getFullPath()).delete();
                download.setStatus(Download.DownloadStatus.CANCELED);
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    public void stop() {
        active = false;
    }
}
