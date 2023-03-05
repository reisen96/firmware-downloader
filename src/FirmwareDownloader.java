import java.util.ArrayList;
import java.util.Stack;
import java.util.TreeMap;

public class FirmwareDownloader {

    private final ArrayList<Download> downloadList;
    private final TreeMap<Long, DownloadTask> downloadTasks;
    private final String defaultDownloadDestination;
    private Long downloadID;

    public FirmwareDownloader() {
        downloadList = new ArrayList<>();
        downloadTasks = new TreeMap<>();
        defaultDownloadDestination = System.getProperty("user.home") + "/Downloads";
        downloadID = 0L;
    }

    public void downloadFirmware(AppleFirmware firmwareToDownload, String downloadDestination) {
        String destination = downloadDestination == null ? defaultDownloadDestination : downloadDestination;
        Download newDownload = new Download(firmwareToDownload);
        DownloadTask newDownloadTask = new DownloadTask(newDownload, destination);
        Thread downloadThread = new Thread(newDownloadTask);
        newDownload.setId(++downloadID);
        downloadList.add(newDownload);
        downloadTasks.put(newDownload.getId(), newDownloadTask);
        downloadThread.start();
    }

    public ArrayList<Download> getDownloadList() {
        return downloadList;
    }

    public ArrayList<Download> getInProgressDownloadList() {
        ArrayList<Download> inProgressList = new ArrayList<>();
        for (Download download : downloadList) {
            if (download.isInProgress()) {
                inProgressList.add(download);
            }
        }
        return inProgressList;
    }

    public String getDefaultDownloadDestination() {
        return defaultDownloadDestination;
    }

    public void cancelDownload(Long downloadID) {
        if (downloadTasks.containsKey(downloadID)) {
            downloadTasks.get(downloadID).stop();
        } else {
            throw new RuntimeException("Invalid download ID");
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
            downloadTasks.remove(downloadsToRemove.pop().getId());
        }
    }
}
