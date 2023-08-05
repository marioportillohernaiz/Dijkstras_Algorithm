public class Grid {
    private int xAxis;
    private int yAxis;

    public int getXAxis() {
        return this.xAxis;
    }
    public int getYAxis() {
        return this.yAxis;
    }

    public void setAxis(String axis) {
        String[] xandy = axis.split("x");        
        this.xAxis = Integer.parseInt(xandy[0]);
        this.yAxis = Integer.parseInt(xandy[1]);
    }
}
