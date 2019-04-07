package carogame;

import java.awt.Point;
import java.util.Vector;
import javax.swing.undo.AbstractUndoableEdit;

public class UndoablePointSquare extends AbstractUndoableEdit {
    protected Vector points; 
    protected Point point; 
    
    public UndoablePointSquare(Point p, Vector<Point> v) {
        points = v; 
        point = p; 
    }

    public void undo() {
        super.undo(); 
        points.remove(point); 
    }
    public void redo() {
        super.redo(); 
        points.add(point); 
    }
}
