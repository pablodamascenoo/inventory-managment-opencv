package com.untree.inventory_managment_opencv.service;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import org.springframework.stereotype.Service;

@Service
public class ImageProcessingService {

    /**
     * Detects edges in the given image file using a simple edge detection algorithm.
     *
     * @param imagePath Path to the image file.
     * @return Processed image with edges detected.
     * @throws IOException if the image file cannot be read.
     */
    public BufferedImage detectEdges(String imagePath) throws IOException {
        BufferedImage source = ImageIO.read(new File(imagePath));
        if (source == null) {
            throw new IllegalArgumentException("Image file not found: " + imagePath);
        }

        BufferedImage edges = new BufferedImage(source.getWidth(), source.getHeight(), BufferedImage.TYPE_INT_ARGB);

        for (int y = 1; y < source.getHeight() - 1; y++) {
            for (int x = 1; x < source.getWidth() - 1; x++) {
                Color color = new Color(source.getRGB(x, y));
                int gray = (color.getRed() + color.getGreen() + color.getBlue()) / 3;

                Color colorRight = new Color(source.getRGB(x + 1, y));
                int grayRight = (colorRight.getRed() + colorRight.getGreen() + colorRight.getBlue()) / 3;

                Color colorBottom = new Color(source.getRGB(x, y + 1));
                int grayBottom = (colorBottom.getRed() + colorBottom.getGreen() + colorBottom.getBlue()) / 3;

                int edgeColor = Math.abs(gray - grayRight) + Math.abs(gray - grayBottom);
                edgeColor = edgeColor > 255 ? 255 : edgeColor;

                edges.setRGB(x, y, new Color(edgeColor, edgeColor, edgeColor).getRGB());
            }
        }

        return edges;
    }
}