import javax.swing.*;
import javax.imageio.*;

import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;
import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;

public class Main extends JPanel implements ActionListener {
    BufferedImage [] main_images=null;
    Timer t=null;
    int currentImage=0;
    int delay=1000/24;
    int numImages=0;

    Main() throws IOException{
        File image = new File("./image.png");
        BufferedImage bufferedImage = ImageIO.read(image);

        FloodFill ff = new FloodFill();
        Coordinate start = new Coordinate(499, 0);
        ff.floodFill(bufferedImage, start, Color.MAGENTA);
        File dir = new File("./images");

        File[] files = dir.listFiles();
        File[] filesArray = new File[files.length];

        for (int i = 0; i < files.length; i++) {
            for (int j = 0; j < files.length; j++) {
                String s = files[j].getName();
                s = s.substring(s.indexOf("e") + 1);
                s = s.substring(0, s.indexOf("."));
                if(s.equals(String.valueOf(i))){
                    filesArray[i] = files[j];
                    System.out.println(filesArray[i]);
                }
            }
        }

        numImages = files.length;
        System.out.println(numImages);
        main_images = new BufferedImage[numImages];

        for (int i = 1; i < numImages; i++) {
            main_images[i] = ImageIO.read(filesArray[i]);
        }

        startAnimation();
    }

    public void stopAnimation(){
        t.stop();
    }

    public void startAnimation(){
        if(t==null){
            currentImage=0;
            t=new Timer(delay,this);
            t.start();
        }else if(!t.isRunning()){
            t.restart();
        }
    }

    public void paint(Graphics g){
        if(getWidth()!=0 && getHeight()!=0){
            BufferedImage img=new BufferedImage(getWidth(),getHeight(),BufferedImage.TYPE_INT_RGB);
            Graphics2D g2d=img.createGraphics();
            if(main_images[currentImage]!=null)
                g2d.drawImage(main_images[currentImage], 0, 0, img.getWidth(), img.getHeight(), null);
            g.drawImage(img, 0, 0, null);
        }

    }

    public static void main(String[] args) throws IOException{
        // TODO Auto-generated method stub
        JFrame frame=new JFrame();
        frame.setContentPane(new Main());
        frame.setSize(600, 600);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    @Override
    public void actionPerformed(ActionEvent arg0) {
        // TODO Auto-generated method stub
        repaint();
        currentImage=(currentImage + 1) % numImages ;
    }

}
