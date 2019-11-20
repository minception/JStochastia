package en.pocatko.michal.stochastia.utils;

/**
 * 2D coordinates key for the map
 */
public class PairKey {

    public int X;
    public int Y;

    public PairKey(int x, int y) {
        this.X = x;
        this.Y = y;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof PairKey)) return false;
        PairKey key = (PairKey) o;
        return X == key.X && Y == key.Y;
    }

    @Override
    public int hashCode() {
        return (X << 1) ^ Y;
    }

}