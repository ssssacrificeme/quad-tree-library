final class QuadNode<T> {
    private double x;
    private double y;
    private double width;
    private double height;
    private QuadNode<T> parent;
    private Point<T> point;
    private QuadNode<T> northWest;
    private QuadNode<T> northEast;
    private QuadNode<T> southWest;
    private QuadNode<T> southEast;

    public QuadNode(double x, double y, double width, double height, QuadNode<T> parent) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.parent = parent;
    }

    @Override
    public String toString() {
        return "QuadNode{" +
                "x1=" + x +
                ", y1=" + y +
                ", x2=" + (x + width) +
                ", y2=" + (y + height) +
                ", point=" + point + "\n" +
                 "\t" +
                ", northWest=" + northWest + "\n" +
                "\t" +
                ", northEast=" + northEast + "\n" +
                "\t" +
                ", southWest=" + southWest + "\n" +
                "\t" +
                ", southEast=" + southEast + "\n" +
                "\t"  +
                '}';
    }

    public double getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public double getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public double getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public QuadNode<T> getParent() {
        return parent;
    }

    public Point<T> getPoint() {
        return point;
    }

    public void setPoint(Point<T> point) {
        this.point = point;
    }

    public QuadNode<T> getNorthWest() {
        return northWest;
    }

    public void setNorthWest(QuadNode<T> northWest) {
        this.northWest = northWest;
    }

    public QuadNode<T> getNorthEast() {
        return northEast;
    }

    public void setNorthEast(QuadNode<T> northEast) {
        this.northEast = northEast;
    }

    public QuadNode<T> getSouthWest() {
        return southWest;
    }

    public void setSouthWest(QuadNode<T> southWest) {
        this.southWest = southWest;
    }

    public QuadNode<T> getSouthEast() {
        return southEast;
    }

    public void setSouthEast(QuadNode<T> southEast) {
        this.southEast = southEast;
    }
}