import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.LinkedList;
import java.util.Queue;

public class FloodFill{
    int bgColor;
    boolean[][] matrix;
    Color newColor;

    public FloodFill() {
    }

    public void floodFill(BufferedImage image, Coordinate start, Color color) throws IOException {
        this.bgColor = image.getRGB(start.x, start.y);
        this.matrix = new boolean[image.getHeight()][image.getWidth()];
        this.newColor = color;

        Queue<Coordinate> queue = new LinkedList<>();

        queue.add(start);

        clearDir();
        int count = 0;
        int index = 0;
        while(!queue.isEmpty()) {
            Coordinate cord = queue.remove();

            if(verify(image, cord)) {
                queue.add(new Coordinate(cord.x,cord.y - 1));
                queue.add(new Coordinate(cord.x,cord.y + 1));
                queue.add(new Coordinate(cord.x - 1,cord.y));
                queue.add(new Coordinate(cord.x + 1,cord.y));

                image.setRGB(cord.x, cord.y, color.getRGB());
                this.matrix[cord.y][cord.x] = true;

                if(count == 500){
                    count = 0;
                    index++;
                    ImageIO.write(image, "png", new File("./images/image" + index +".png"));
                }
                count ++;
            }
        }
    }

    public boolean verify(BufferedImage image, Coordinate cord){
        //index out of bounds :((
        if(     cord.x < 0 || cord.x > image.getWidth() - 1 ||
                cord.y < 0 || cord.y > image.getHeight() - 1 ||
                this.matrix[cord.y][cord.x] ||
                image.getRGB(cord.x, cord.y) != this.bgColor){
            return false;
        }else{
            return true;
        }
    }

    public void clearDir(){
        File dir = new File("./images");
        for(File file: dir.listFiles())
            if (!file.isDirectory())
                file.delete();
    }
}
