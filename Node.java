public class Node {
    private int xAxis;
    private int yAxis;
    private int distance;

    public int getXAxis() {
        return this.xAxis;
    }
    public int getYAxis() {
        return this.yAxis;
    }
    public int getDistance() {
        return this.distance;
    }

    public void setAxis(String axis) {
        String[] xandy = axis.split(",");
        this.xAxis = Integer.parseInt(xandy[0]);
        this.yAxis = Integer.parseInt(xandy[1]);
    }

    public void setDistance(int distance) {
        this.distance = distance;
    }

    public void addNewAxis(int x, int y) {
        this.xAxis = x;
        this.yAxis = y;
    }

    public String toString() {
        return this.xAxis + "," + this.yAxis;
    }
}
