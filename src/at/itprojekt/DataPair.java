package at.itprojekt;

public class DataPair {
    public final String key;
    public final String value;
    public final int level;

    public DataPair(int level, String key, String value) {
        this.key = key;
        this.value = value;
        this.level = level;
    }

    @Override
    public String toString() {
        return "DataPair{" +
                "key='" + key + '\'' +
                ", value='" + value + '\'' +
                ", level=" + level +
                '}';
    }
}
