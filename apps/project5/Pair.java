public class Pair {

    private int v;
    private int w;

    public Pair(int v, int w) {
        this.v = v;
        this.w = w;
    }

    public int getV() {
        return v;
    }

    public int getW() {
        return w;
    }

    public void setV(int i) {
        this.v = i;
    }

    public void setW(int i) {
        this.w = i;
    }

    public void print() {
        StdOut.println("V: " + v);
        StdOut.println("W: " + w);
    }
}