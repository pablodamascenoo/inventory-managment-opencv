package com.untree.inventory_managment_opencv.service;

import org.opencv.core.*;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;
import org.springframework.stereotype.Service;
import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.MatOfRect;
import org.opencv.core.Rect;
import org.opencv.core.Scalar;
import org.opencv.core.Size;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;
import org.opencv.objdetect.CascadeClassifier;

@Service
public class ImageProcessingService {

    static {
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
    }

    /**
     * Detects edges in the given image file.
     * 
     * @param imagePath Path to the image file.
     * @return Processed image with edges detected.
     */
    public Mat detectEdges(String imagePath) {
        Mat source = Imgcodecs.imread(imagePath);
        if (source.empty()) {
            throw new IllegalArgumentException("Image file not found: " + imagePath);
        }

        Mat edges = new Mat();
        Imgproc.Canny(source, edges, 100, 200);
        return edges;
    }

    /**
     * Saves a processed image to the specified path.
     * 
     * @param image      Processed image.
     * @param outputPath Path to save the image.
     */
    public void saveImage(Mat image, String outputPath) {
        Imgcodecs.imwrite(outputPath, image);
    }
}
