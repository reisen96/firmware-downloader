import java.io.*;
import java.net.HttpURLConnection;

public class DownloadTask implements Runnable {

    private final Download download;
    private final int bufferSize;
    private Thread downloaderThread;

    public DownloadTask(Download download, String destination) {
        download.setDestination(destination);
        this.download = download;
        bufferSize = 1024;
    }

    @Override
    public void run() {
        downloaderThread = Thread.currentThread();
        try (FileOutputStream downloadedFile = new FileOutputStream(download.getFullPath())) {
            HttpURLConnection httpConnection = (HttpURLConnection) (download.getUrl().openConnection());
            BufferedInputStream inputStream = new BufferedInputStream(httpConnection.getInputStream());
            BufferedOutputStream outputStream = new BufferedOutputStream(downloadedFile, bufferSize);
            byte[] downloadedBytes = new byte[bufferSize];
            int completeFileSize = httpConnection.getContentLength(), currentFileSize = 0, currentlyRead;
            while ((currentlyRead = inputStream.read(downloadedBytes, 0, bufferSize)) >= 0) {
                currentFileSize += currentlyRead;
                download.setProgress((double) currentFileSize / (double) completeFileSize);
                outputStream.write(downloadedBytes, 0, currentlyRead);
            }
            inputStream.close();
            outputStream.close();
            download.setStatus(Download.DownloadStatus.COMPLETED);
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    public void stop() {
        downloaderThread.interrupt();
        new File(download.getDestination() + download.getFileName()).delete();
        download.setStatus(Download.DownloadStatus.CANCELED);
    }
}
