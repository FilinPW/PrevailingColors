package com.color;

import java.awt.image.BufferedImage;
import java.awt.Color;
import java.util.Random;
import java.util.Arrays;

public class PrevailingColor {

    private int amoColorCenter = 5;
    private BufferedImage image = null;

    PrevailingColor() {}

    PrevailingColor(BufferedImage inputImage) {
        image = inputImage;
    }

    PrevailingColor(int inputAmoColorCenter) {
        amoColorCenter = inputAmoColorCenter;
    }

    PrevailingColor(BufferedImage inputImage, int inputAmoColorCenter) {
        image = inputImage;
        amoColorCenter = inputAmoColorCenter;
    }

    public void setImage(BufferedImage inputImage) {
        image = inputImage;
    }

    public void setAmoColorCenter(int inputAmoColorCenter) {
        amoColorCenter = inputAmoColorCenter;
    }

    public Color getPrevailingColor() {
        int imageWidth = image.getWidth();
        int imageHeight = image.getHeight();
        Random rnd = new Random(imageWidth + imageHeight);
        int rndIW = rnd.nextInt(imageWidth);
        int rndIH = rnd.nextInt(imageHeight);
        int[][] arrayColorCenter = new int[amoColorCenter][4];
        for (int i = 0; i < amoColorCenter; i++) {
            arrayColorCenter[i][1] = (image.getRGB(rndIW, rndIH) >> 16) & 0xff;
            arrayColorCenter[i][2] = (image.getRGB(rndIW, rndIH) >> 8) & 0xff;
            arrayColorCenter[i][3] = image.getRGB(rndIW, rndIH) & 0xff;
        }

        int[][] newArrayColorCenters = getNewColorCenters(arrayColorCenter);

        while (!Arrays.deepEquals(newArrayColorCenters, arrayColorCenter)) {
            arrayColorCenter = CopyArrayDimension2(newArrayColorCenters);
            newArrayColorCenters = getNewColorCenters(arrayColorCenter);
        }

        newArrayColorCenters = SortArrayColorCenter(newArrayColorCenters);

        for (int i = 0; i < amoColorCenter; i++) {
            System.out.print(newArrayColorCenters[i][0] + " ");
            System.out.print(newArrayColorCenters[i][1] + " ");
            System.out.print(newArrayColorCenters[i][2] + " ");
            System.out.println(newArrayColorCenters[i][3] + " ");
        }

        return new Color(
                newArrayColorCenters[0][1],
                newArrayColorCenters[0][2],
                newArrayColorCenters[0][3]);
    }

    private int[][] getNewColorCenters(int[][] arrayColorCenters){
        int arrayColorCentersAmoLine = arrayColorCenters.length;
        double minDistance, distance;
        int imageWidth = image.getWidth();
        int imageHeight = image.getHeight();
        int redPixel, greenPixel, bluePixel;
        int tempForDistanceRed, tempForDistanceGreen, tempForDistanceBlue;
        int nearestColorCenter = arrayColorCentersAmoLine;
        long[][] sumColor = new long[arrayColorCentersAmoLine][4];
        int[][] prevailingColor = new int[arrayColorCentersAmoLine][4];

        for (int i = 0; i < imageWidth; i++) {
            for (int j = 0; j < imageHeight; j++) {
                redPixel = (image.getRGB(i, j) >> 16) & 0xff;
                greenPixel = (image.getRGB(i, j) >> 8) & 0xff;
                bluePixel = image.getRGB(i, j) & 0xff;
                minDistance = 450;

                for (int k = 0; k < arrayColorCentersAmoLine; k++) {
                    tempForDistanceRed = Math.abs(redPixel - arrayColorCenters[k][1]);
                    tempForDistanceGreen = Math.abs(greenPixel - arrayColorCenters[k][2]);
                    tempForDistanceBlue = Math.abs(bluePixel - arrayColorCenters[k][3]);
                    distance = Math.sqrt(tempForDistanceRed * tempForDistanceRed
                            + tempForDistanceGreen * tempForDistanceGreen
                            + tempForDistanceBlue * tempForDistanceBlue);
                    if (minDistance > distance) {
                        minDistance = distance;
                        nearestColorCenter = k;
                    }
                }

                sumColor[nearestColorCenter][0]++;
                sumColor[nearestColorCenter][1] += redPixel;
                sumColor[nearestColorCenter][2] += greenPixel;
                sumColor[nearestColorCenter][3] += bluePixel;
            }
        }

        for (int k = 0; k < arrayColorCentersAmoLine; k++) {
            prevailingColor[k][0] = (int) sumColor[k][0];
            try {
                prevailingColor[k][1] = (int) (sumColor[k][1] / sumColor[k][0]);
                prevailingColor[k][2] = (int) (sumColor[k][2] / sumColor[k][0]);
                prevailingColor[k][3] = (int) (sumColor[k][3] / sumColor[k][0]);
            } catch (ArithmeticException  e) {
                prevailingColor[k][1] = arrayColorCenters[k][1];
                prevailingColor[k][2] = arrayColorCenters[k][2];
                prevailingColor[k][3] = arrayColorCenters[k][3];
            }
        }

        return prevailingColor;
    }

    private int[][] SortArrayColorCenter(int[][] inputACC) {
        int inputACCAmoLine = inputACC.length;
        int[][] newACC = CopyArrayDimension2(inputACC);
        int temp;
        int position;
        int maxAmoPixel;

        for (int i = 0; i < inputACCAmoLine - 1; i++) {
            maxAmoPixel = newACC[i][0];
            position = i;

            for (int j = i + 1; j < inputACCAmoLine; j++) {
                if (maxAmoPixel < newACC[j][0]) {
                    maxAmoPixel = newACC[j][0];
                    position = j;
                }
            }

            temp = newACC[i][0];
            newACC[i][0] = newACC[position][0];
            newACC[position][0] = temp;

            temp = newACC[i][1];
            newACC[i][1] = newACC[position][1];
            newACC[position][1] = temp;

            temp = newACC[i][2];
            newACC[i][2] = newACC[position][2];
            newACC[position][2] = temp;

            temp = newACC[i][3];
            newACC[i][3] = newACC[position][3];
            newACC[position][3] = temp;
        }

        return newACC;
    }

    private int[][] CopyArrayDimension2(int[][] inputACC) {
        int inputACCAmoLine = inputACC.length;
        int inputACCAmoColumn = inputACC[0].length;
        int[][] newArrayColorCenter = new int[inputACCAmoLine][inputACCAmoColumn];

        for (int i = 0; i < inputACCAmoLine; i++) {
            for (int j = 0; j < inputACCAmoColumn; j++) {
                newArrayColorCenter[i][j] = inputACC[i][j];
            }
        }

        return newArrayColorCenter;
    }

}
