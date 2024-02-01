package com.deque.axe.android;

import com.deque.axe.android.colorcontrast.AxeColor;
import com.deque.axe.android.colorcontrast.AxeImage;
import com.deque.axe.android.wrappers.AxeRect;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.util.Base64;
import javax.imageio.ImageIO;


public class MyAxeImage extends AxeImage {
    private  BufferedImage image;
    private AxeImage axeimage;
    
   
    public AxeImage createAxeImage(BufferedImage image, MyAxeImage myAxeImage) {

    	this.axeimage = myAxeImage;
    	this.image = image;
    	axeimage.runColorContrastCalculation(axeimage.frame());
		return axeimage;
                 
    }

    public MyAxeImage (BufferedImage image)
    {
    	this.image=image;
    }
    @Override
    public AxeRect frame() {
        return new AxeRect(0, image.getWidth()-1 , 0 , image.getHeight()-1);
    }

    @Override
    public AxeColor pixel(final int x, final int y) {
        int rgb = image.getRGB(x, y);
        int alpha = (rgb >> 24) & 0xFF;
        int red = (rgb >> 16) & 0xFF;
        int green = (rgb >> 8) & 0xFF;
        int blue = rgb & 0xFF;
        return new AxeColor(alpha, red, green, blue);
    }

    @Override
    public String toBase64Png() {
        try {
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            ImageIO.write(image, "png", outputStream);
            byte[] bytes = outputStream.toByteArray();
            return Base64.getEncoder().encodeToString(bytes);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}