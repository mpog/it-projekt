package at.itprojekt;
// A datapair contains out of a level, a key and a value. The key is the "Heading", the value is the "text". There is no need, that the heading is unique, it is more like a data storage
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
