package com.color;

import java.awt.Component;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;
import java.net.URL;

public class PrevailingColors extends Component {

    public static void main(String[] foo) {
        new PrevailingColors();
    }

    private PrevailingColors() {
        BufferedImage image = null;
        PrevailingColor pc = null;
        try {
            image = ImageIO.read(new URL("https://img-fotki.yandex.ru/get/370378/191119240.332/0_1891f0_27687416_orig"));
        } catch (IOException | IllegalArgumentException e) {
            System.err.println(e.getMessage());
        }

        if (image != null) {
            pc = new PrevailingColor(image);
        }

        System.out.println(pc.getPrevailingColor());
    }

}