import java.net.URL;
import java.text.MessageFormat;

public class AppleFirmware {

    private String identifier;

    private String version;

    private String buildid;

    private String sha1sum;

    private String md5sum;

    private String sha256sum;

    private Long filesize;

    private URL url;

    private String releasedate;

    private String uploaddate;

    private boolean signed;

    public String getIdentifier() {
        return identifier;
    }

    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getBuildid() {
        return buildid;
    }

    public void setBuildid(String buildid) {
        this.buildid = buildid;
    }

    public String getSha1sum() {
        return sha1sum;
    }

    public void setSha1sum(String sha1sum) {
        this.sha1sum = sha1sum;
    }

    public String getMd5sum() {
        return md5sum;
    }

    public void setMd5sum(String md5sum) {
        this.md5sum = md5sum;
    }

    public String getSha256sum() {
        return sha256sum;
    }

    public void setSha256sum(String sha256sum) {
        this.sha256sum = sha256sum;
    }

    public Long getFilesize() {
        return filesize;
    }

    public void setFilesize(Long filesize) {
        this.filesize = filesize;
    }

    public URL getUrl() {
        return url;
    }

    public void setUrl(URL url) {
        this.url = url;
    }

    public String getReleasedate() {
        return releasedate;
    }

    public void setReleasedate(String releasedate) {
        this.releasedate = releasedate;
    }

    public String getUploaddate() {
        return uploaddate;
    }

    public void setUploaddate(String uploaddate) {
        this.uploaddate = uploaddate;
    }

    public boolean isSigned() {
        return signed;
    }

    public void setSigned(boolean signed) {
        this.signed = signed;
    }

    public String getFileName() {
        return url.getFile();
    }

    @Override
    public String toString() {
        return MessageFormat.format("Signed: {0}\nRelease Date: {1}\nUpload Date: {2}\nURL: {3}\nFilesize: {4}\nSHA1sum: {5}\nIdentifier: {6}\n", signed, releasedate, uploaddate, url, filesize, sha1sum, identifier);
    }
}
