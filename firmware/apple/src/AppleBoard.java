import java.text.MessageFormat;

public class AppleBoard {
    private String boardconfig;
    private String platform;
    private Long cpid;
    private Long bdid;

    public String getBoardConfig() {
        return boardconfig;
    }

    public void setBoardConfig(String boardconfig) {
        this.boardconfig = boardconfig;
    }

    public String getPlatform() {
        return platform;
    }

    public void setPlatform(String platform) {
        this.platform = platform;
    }

    public Long getCpid() {
        return cpid;
    }

    public void setCpid(Long cpid) {
        this.cpid = cpid;
    }

    public Long getBdid() {
        return bdid;
    }

    public void setBdid(Long bdid) {
        this.bdid = bdid;
    }

    @Override
    public String toString() {
        return MessageFormat.format("BoardConfig: {0}\nPlatform: {1}\nBDID: 0x{2}\nCPID: 0x{3}\n", boardconfig, platform, Long.toHexString(bdid), Long.toHexString(cpid));
    }
}
