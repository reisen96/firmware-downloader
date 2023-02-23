import java.util.ArrayList;
import java.util.Stack;
import java.util.TreeMap;

public class FirmwareDownloader {

    private final ArrayList<Download> downloadList;
    private final TreeMap<Long, DownloadTask> downloadTasks;
    private final String defaultDownloadDestination;

    public FirmwareDownloader() {
        downloadList = new ArrayList<>();
        downloadTasks = new TreeMap<>();
        defaultDownloadDestination = System.getProperty("user.home") + "/Downloads";
    }

    public void downloadFirmware(AppleFirmware firmwareToDownload, String downloadDestination) {
        String destination = downloadDestination == null ? defaultDownloadDestination : downloadDestination;
        Download newDownload = new Download(firmwareToDownload);
        Thread downloadThread = new Thread(new DownloadTask(newDownload, destination));
        downloadList.add(newDownload);
        downloadThread.start();
    }

    public ArrayList<Download> getDownloadList() {
        return downloadList;
    }

    public String getDefaultDownloadDestination() {
        return defaultDownloadDestination;
    }

    public void cancelDownload(Long downloadID) {
        downloadTasks.get(downloadID).stop();
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
