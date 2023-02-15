import java.text.MessageFormat;
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

    @Override
    public String toString() {
        long currentBoard = 1L;
        StringBuilder appleDeviceString = new StringBuilder();
        appleDeviceString.append(MessageFormat.format("Device name: {0}\nIdentifier: {1}", name, identifier));
        for (AppleBoard board : boards) {
            appleDeviceString.append(MessageFormat.format("Board {0}\n", currentBoard++));
            appleDeviceString.append(board.toString());
        }
        return appleDeviceString.toString();
    }
}
