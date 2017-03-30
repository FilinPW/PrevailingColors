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
            image = ImageIO.read(new URL("https://img-fotki.yandex.ru/get/44819/99828525.0/0_f51d2_9b7aed00_orig"));
        } catch (IOException | IllegalArgumentException e) {
            System.err.println(e.getMessage());
        }

        if (image != null) {
            pc = new PrevailingColor(image);
        }

        System.out.println(pc.getPrevailingColor());
    }

}