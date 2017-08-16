import javax.imageio.ImageIO;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

public class Main {
	public static void main(String[] args) throws IOException {
		File input = new File(
				"C:\\Users\\yunus\\Desktop\\Sample Files\\tiger.jpg");
		BufferedImage image = ImageIO.read(input);
		ArrayList<Object[]> parts = new ArrayList<Object[]>();
		int row = image.getWidth() / 10;
		int column = image.getHeight() / 10;
		for (int i = 0; i < row; i++) {
			for (int j = 0; j < column; j++) {
				BufferedImage subimage = image.getSubimage(i * 10, j * 10, 10,
						10);
				int b = 0, g = 0, r = 0;
				for (int x = 0; x < 10; x++) {
					for (int y = 0; y < 10; y++) {
						int rgb = subimage.getRGB(x, y);
						b += rgb & 0xFF;
						g += (rgb >> 8) & 0xFF;
						r += (rgb >> 16) & 0xFF;
					}
				}
				double intens = 0.298 * r + 0.587 * g + 0.114 * b;
				Object temp[] = new Object[] { subimage, intens };
				parts.add(temp);
			}
		}
		sort(parts);
		BufferedImage result = new BufferedImage(image.getWidth(),
				image.getHeight(), BufferedImage.TYPE_INT_ARGB);
		Graphics merger = result.getGraphics();
		for (int i = 0; i < row; i++) {
			for (int j = 0; j < column; j++) {
				merger.drawImage((BufferedImage) parts.get(j * row + i)[0],
						i * 10, j * 10, null);
			}
		}
		ImageIO.write(result, "PNG", new File(input.getParent(),
				"combinedv3.png"));
		System.out.println("Done.");
	}

	public static void sort(ArrayList<Object[]> array) {
		Object[] tempVar;
		for (int i = 0; i < array.size(); i++) {
			for (int j = 0; j < array.size(); j++) {
				if ((double) array.get(i)[1] < (double) array.get(j)[1]) {
					tempVar = array.get(j);
					array.set(j, array.get(i));
					array.set(i, tempVar);
				}
			}
		}
	}
}
