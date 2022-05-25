import javax.swing.*;
import javax.imageio.*;

import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;
import java.io.*;
import java.util.ArrayList;

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
        ArrayList<BufferedImage> animation = new ArrayList<>();
        File dir = new File("./images");
        for(File file: dir.listFiles()){
            BufferedImage image2 = ImageIO.read(file);
            animation.add(image2);
        }

        numImages = animation.size();
        main_images = new BufferedImage[numImages];
        System.out.println(numImages);
        for(int i=0;i<numImages;i++){
            main_images[i]= animation.get(i);
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
