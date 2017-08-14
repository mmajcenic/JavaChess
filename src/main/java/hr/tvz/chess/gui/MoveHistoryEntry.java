package hr.tvz.chess.gui;

public class MoveHistoryEntry {

    private String notation;
    private String algorithm;
    private long duration;

    public MoveHistoryEntry() {
    }

    public MoveHistoryEntry(String notation, String algorithm, long duration) {
        this.notation = notation;
        this.algorithm = algorithm;
        this.duration = duration;
    }

    public String getNotation() {
        return notation;
    }

    public void setNotation(String notation) {
        this.notation = notation;
    }

    public String getAlgorithm() {
        return algorithm;
    }

    public void setAlgorithm(String algorithm) {
        this.algorithm = algorithm;
    }

    public long getDuration() {
        return duration;
    }

    public void setDuration(long duration) {
        this.duration = duration;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        MoveHistoryEntry that = (MoveHistoryEntry) o;

        if (duration != that.duration) return false;
        if (notation != null ? !notation.equals(that.notation) : that.notation != null) return false;
        return algorithm != null ? algorithm.equals(that.algorithm) : that.algorithm == null;
    }

    @Override
    public int hashCode() {
        int result = notation != null ? notation.hashCode() : 0;
        result = 31 * result + (algorithm != null ? algorithm.hashCode() : 0);
        result = 31 * result + (int) (duration ^ (duration >>> 32));
        return result;
    }
}
