import java.io.*;
import java.net.HttpURLConnection;

public class DownloadTask implements Runnable {

    private final Download download;
    private Thread downloaderThread;

    public DownloadTask(Download download, String destination) {
        download.setDestination(destination);
        this.download = download;
    }

    @Override
    public void run() {
        downloaderThread = Thread.currentThread();
        try (FileOutputStream downloadedFile = new FileOutputStream(download.getFullPath())) {
            HttpURLConnection httpConnection = (HttpURLConnection) (download.getUrl().openConnection());
            BufferedInputStream inputStream = new BufferedInputStream(httpConnection.getInputStream());
            BufferedOutputStream outputStream = new BufferedOutputStream(downloadedFile, 1024);
            byte[] downloadedBytes = new byte[1024];
            int completeFileSize = httpConnection.getContentLength(), currentFileSize = 0, currentlyRead;
            while ((currentlyRead = inputStream.read(downloadedBytes, 0, 1024)) >= 0) {
                currentFileSize += currentlyRead;
                download.setProgress((double) currentFileSize / (double) completeFileSize);
                outputStream.write(downloadedBytes, 0, currentlyRead);
            }
            download.setStatus(Download.DownloadStatus.COMPLETED);
            downloadedFile.close();
            inputStream.close();
            outputStream.close();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    public void stop() {
        File incompleteFile;
        downloaderThread.interrupt();
        incompleteFile = new File(download.getDestination() + download.getFileName());
        incompleteFile.delete();
        download.setStatus(Download.DownloadStatus.CANCELED);
    }
}
