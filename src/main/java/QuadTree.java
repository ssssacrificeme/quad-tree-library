import org.w3c.dom.Node;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.function.Consumer;

public class QuadTree<T> {
    private final QuadNode<T> root;
    private int size = 0;

    public QuadTree(int minX, int minY, int maxX, int maxY) throws Exception {
        if (minX >= 0 && minY >= 0 && maxX > 0 && maxY > 0 && maxY > minY && maxX > minX) {
            root = new QuadNode<>(minX, minY, maxX - minX, maxY - minY, null);
        }else {
            throw new Exception("Неверные координаты");
        }
    }
    public T add(double x, double y, T val) {
        if (x < root.getX() || y < root.getY() || x > root.getX() + root.getWidth() || y > root.getY() + root.getHeight()) {
            return null;
        }
        Point<T> addingPoint = new Point<>(x, y, val);
        boolean added = canAdded(root, addingPoint);
        if (added) {
            size++;
            return val;
        }
        return null;
    }
    public void printQuadTree(){
        QuadNode<T> r = root;
        StringBuilder stringBuilder = new StringBuilder("");
        stringBuilder.append("root" + root + "\n");
        System.out.println(stringBuilder);
    }
    private boolean canAdded(QuadNode<T> node, Point<T> point) {
        boolean added = false;
        if (node.getPoint() == null && node.getNorthWest() == null) {
            setPointInNode(node, point);
            added = true;
        } else if (node.getNorthWest() == null) {
            if (node.getPoint().getX() == point.getX() && node.getPoint().getY() == point.getY()) {
                setPointInNode(node, point);
            } else {
                split(node);
                added = canAdded(node, point);
            }
        } else if (node.getNorthWest() != null)
            added = canAdded(getNodeForPoint(node, point.getX(), point.getY()), point);
        return added;
    }
    private QuadNode<T> getNodeForPoint(QuadNode<T> parent, double x, double y) {
        double borderX = parent.getX() + parent.getWidth() / 2;
        double borderY = parent.getY() + parent.getHeight() / 2;
        if (x < borderX) {
            return y < borderY ? parent.getNorthWest() : parent.getSouthWest();
        } else {
            return y < borderY ? parent.getNorthEast() : parent.getSouthEast();
        }
    }
    private void setPointInNode(QuadNode<T> quadNode, Point<T> point) {
        if (quadNode.getNorthWest() != null) {
            return;
        }
        quadNode.setPoint(point);
    }
    private void split(QuadNode<T> quadNode) {
        Point<T> point = quadNode.getPoint();
        quadNode.setPoint(null);
        double x = quadNode.getX();
        double y = quadNode.getY();
        double halfWidth = quadNode.getWidth() / 2;
        double halfHeight = quadNode.getHeight() / 2;
        quadNode.setNorthWest(new QuadNode<>(x, y, halfWidth, halfHeight, quadNode));
        quadNode.setNorthEast(new QuadNode<>(x + halfWidth, y, halfWidth, halfHeight, quadNode));
        quadNode.setSouthWest(new QuadNode<>(x, y + halfHeight, halfWidth, halfHeight, quadNode));
        quadNode.setSouthEast(new QuadNode<>(x + halfWidth, y + halfHeight, halfWidth, halfHeight, quadNode));
        canAdded(quadNode, point);
    }
    public T get(int x, int y) {
        QuadNode<T> node = searchNode(root, x, y);
        return node != null ? node.getPoint().getValue() : null;
    }
    public T remove(int x, int y) {
        QuadNode<T> node = searchNode(root, x, y);
        if (node != null) {
            T value = node.getPoint().getValue();
            node.setPoint(null);
            size--;
            return value;
        } else {
            return null;
        }
    }
    private QuadNode<T> searchNode(QuadNode<T> node, int x, int y) {
        QuadNode<T> result = null;
        if (node.getPoint() != null && node.getNorthWest() == null) {
            result = node.getPoint().getX() == x && node.getPoint().getY() == y ? node : null;
        } else if (node.getNorthWest() != null) {
            result = searchNode(getNodeForPoint(node, x, y), x, y);
        }
        return result;
    }
    public boolean contains(int x, int y) {
        return get(x, y) != null;
    }
    public boolean isEmpty() {
        return root.getNorthWest() == null && root.getPoint() == null;
    }
    private void visit(QuadNode<T> quadNode, Consumer<QuadNode<T>> consumer) {
        if (quadNode.getPoint() != null) {
            consumer.accept(quadNode);
        } else if (quadNode.getNorthWest() != null) {
            visit(quadNode.getNorthWest(), consumer);
            visit(quadNode.getNorthEast(), consumer);
            visit(quadNode.getSouthEast(), consumer);
            visit(quadNode.getSouthWest(), consumer);
        }
    }
    public Iterator<T> iterator() {
        List<T> list = new LinkedList<>();
        visit(root, quadNode -> list.add(quadNode.getPoint().getValue()));
        return list.iterator();
    }
}
