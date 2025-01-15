public class Point {
    //The point class will be used for calculation Horse Cordinate
    private int x;
    private int y;
    public Point(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }
    public int getY() {
        return y;
    }
    public void setX(int x) {
        this.x = x;
    }
    public void setY(int y) {
        this.y = y;
    }
    public void printPoint(){
        System.out.println(this.x + "," + this.y);
    }
    public Point Addition(Point a){
        int sumX = this.getX()+a.getX();
        int sumY = this.getY()+a.getY();
        return new Point(sumX,sumY);
    }
}
