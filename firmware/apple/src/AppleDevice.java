import java.util.ArrayList;

public class AppleDevice {

    private String name;

    private String identifier;

    private ArrayList<AppleBoard> boards;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIdentifier() {
        return identifier;
    }

    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }

    public ArrayList<AppleBoard> getBoards() {
        return boards;
    }
}
