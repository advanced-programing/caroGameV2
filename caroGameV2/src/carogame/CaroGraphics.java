package carogame;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;
import java.util.Vector;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.undo.UndoManager;
import javax.swing.JPanel;
import javax.swing.event.UndoableEditEvent;

public class CaroGraphics extends JPanel {

    public final static int sizeCell = 30;
    public final static int row = 18;
    public final static int col = 18;
    public final static int width = sizeCell * col + 1;
    public final static int height = sizeCell * row + 1;

    private int sizeImg = sizeCell - 2;
    private Icon iconActive;
    private MyImage myImage = new MyImage();
    private int winer = 0;
    private Process process;
    public boolean player = false, playerRoot = false;
    protected Vector<Point> pointVector;

    private UndoManager undoManager = new UndoManager();

    public int getWiner() {
        return winer;
    }

    public void setWiner(int winer) {
        this.winer = winer;
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        setBackground(new Color(238, 238, 238));
        for (int i = 0; i <= row; i++) {
            g.drawLine(i * sizeCell, 0, i * sizeCell, height - 1);
            g.drawLine(0, i * sizeCell, width - 1, i * sizeCell);
        }
        drawImg(g);

    }

    private void drawImg(Graphics g) {
        boolean player = playerRoot;
        for (int i = 0; i < pointVector.size(); i++) {
            Image image = player ? myImage.imgCross : myImage.imgNought;
            Point point = convertPointToCaro(convertPoint(pointVector.get(i)));
            g.drawImage(image, point.x, point.y, null);
            player = !player;
        }
    }

    public CaroGraphics() {
        makeIcon();
        setPreferredSize(new Dimension(width, height));
        init();
    }

    private void makeIcon() {
        iconActive = new ImageIcon(myImage.reSizeImage(
                myImage.getMyImageIcon("active.png"), 20, 20));
    }

    public void init() {
        winer = 0;
        process = new Process();
        player = playerRoot;
        pointVector = new Vector<Point>();
        repaint();
    }

    void actionClick(Point point) {
        Point pointTemp = convertPoint(point);
        if (process.updateMatrix(player,
                convertPointToMatrix(pointTemp))) {
            pointVector.addElement(point);
            undoManager.undoableEditHappened(new UndoableEditEvent(this,
                    new UndoablePointSquare(point, pointVector)));
            repaint();
            player = !player;
            setStatus();
            if (process.getWin() > 0) {
                winer = process.getWin();
            }
        }

    }

    private Point convertPoint(Point point) {
        int x, y;
        int deviation = 1;
        x = (point.x % sizeCell > deviation) ? (point.x / sizeCell * sizeCell + sizeCell / 2)
                : (point.x / sizeCell * sizeCell - sizeCell / 2);
        y = (point.y % sizeCell > deviation) ? (point.y / sizeCell * sizeCell + sizeCell / 2)
                : (point.y / sizeCell * sizeCell - sizeCell / 2);
        return new Point(x, y);
    }

    private Point convertPointToMatrix(Point point) {
        return new Point(point.y / sizeCell, point.x / sizeCell);
    }

    private Point convertPointToCaro(Point point) {
        return new Point(point.x - sizeImg / 2, point.y - sizeImg / 2);
    }

    void setStatus() {
        CaroFrame.lbStatusO.setIcon(iconActive);
        CaroFrame.lbStatusX.setIcon(iconActive);
        CaroFrame.lbStatusX.setEnabled(player);
        CaroFrame.lbStatusO.setEnabled(!player);
    }
    //undo 
    // canundo

}
