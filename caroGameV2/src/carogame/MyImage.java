package carogame;

import java.awt.Image;
import java.io.File;
import java.net.URL;
import javax.swing.ImageIcon;

public class MyImage {
    private String urlImage = ".." + File.separator + "images" + File.separator; 
    public Image imgCross; 
    public Image imgNought; 
    
    public MyImage() {
        int size = CaroGraphics.sizeCell - 2; 
        imgCross = reSizeImage(getMyImageIcon("cross.png"), size, size); 
        imgNought = reSizeImage(getMyImageIcon("nought.png"), size, size);            
    }
    
    public Image getMyImageIcon(String nameImageIcon) {
        URL url = getClass().getResource(urlImage + nameImageIcon); 
        Image ii = new ImageIcon(url).getImage();
        return ii; 
    }

    public Image reSizeImage(Image myImageIcon, int width, int height) {
        myImageIcon = new ImageIcon(myImageIcon.getScaledInstance(width, height, Image.SCALE_SMOOTH)).getImage(); 
        return myImageIcon; 
    }
}
