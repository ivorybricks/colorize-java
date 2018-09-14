import java.util.*;

/*
This project implements Image Processing.

Main.java - A program which takes in user input and then
            applies the chosen filter to the image.

DisplayImage.java - Displays the image

BEFORE RUNNING THIS PROGRAM --
Change the first two String variables to the path for catp1.jpg and catp1copy.jpg.

 */

import java.awt.*;
import java.util.*;
import java.io.*;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;

public class Main {

    public static void main(String[] args) {

        //Before running the program, make sure these two variables map to the correct place:

        //pathname1 should be the path to "catp1.jpg," the original image.
        String pathname1 = "catp1.jpg";

        //pathname2 should be the path to "catp1copy.jpg," a copy of the original.
        String pathname2 = "catp1copy.jpg";


        /*
        Note: You can also make pathname1 and pathname2 map to different images, such as
        pineapple.jpg.
        
        But, keep in mind that the image you choose for pathname2 will be overwritten and replaced
        with a new filtered image when you run the program. 
        This is why, unless your goal is to overwrite the original image, you want to make pathname2 
        lead to a different image file, such as a copy, or an file you don't care about.
         */

        // For storing image in RAM
        BufferedImage img = null;
        File f = null;
        Scanner console = new Scanner(System.in);

        //Attempts to read our image
        try {
            //Path to the original image (pathname1)
            f = new File(pathname1);
            img = ImageIO.read(f);
        } catch (IOException e) {
            System.out.println(e);
        }


        //Client interaction to ask for filter
        System.out.println("Which filter would you like to apply?\n" +
                "(Available filters are: \n" +
                "'greyscale', 'negative', 'red', 'green', 'blue', 'sepia', 'glitch', 'grainy', 'artsy', and 'intense')");

        String s = console.next();
        System.out.println("Your filter will be applied...please wait" +
                "\nTo view the filtered image, "
                + "either wait for it to appear, " +
                "\nor take a look at the image file of the copy.");

        //The filter is applied
        filterImage(img, s);

        //The image copy is overwritten with the filter.
        try {
            //path to the copy of the original image (pathname2)
            f = new File(pathname2);
            ImageIO.write(img, "jpg", f);
            ImageIO.read(f);
        } catch (IOException e) {
            System.out.println(e);
        }

        System.out.println("Done filtering.");

        //Display Filtered image
        DisplayImage ex = new DisplayImage(pathname2, false);
        ex.setVisible(true);

    }

    //This method cycles through each pixel in the image and applies filters
    public static void filterImage(BufferedImage img, String filter) {

        if (img == null) {
            throw new IllegalArgumentException("The image path is incorrect...");
        }
        if (!(filter.toLowerCase().equals("greyscale") || filter.toLowerCase().equals("negative") || filter.equals("red")
                || filter.toLowerCase().equals("green") || filter.toLowerCase().equals("blue") || filter.toLowerCase().equals("sepia")
                || filter.toLowerCase().equals("grainy") || filter.toLowerCase().equals("glitch") || filter.toLowerCase().equals("artsy")
                || filter.toLowerCase().equals("intense"))) {

            throw new IllegalArgumentException("Please enter a valid filter!");
        }

        int width = img.getWidth();
        int height = img.getHeight();

        // i represents our "x" position in the image
        for (int i = 0; i < width; i++) {
            //k represents our "y" position in the image
            for (int k = 0; k < height; k++) {
                int p = img.getRGB(i, k); //This gets an individual pixel from the image at position (i, k).

                int[] rgba = getRGBA(p);

                int r = rgba[0];
                int g = rgba[1];
                int b = rgba[2];
                int a = rgba[3];



                //Apply filter based on user input:

                if (filter.toLowerCase().equals("greyscale")) {
                    //Apply Greyscale filter
                    p = greyscale(a, r, g, b, p);
                } else if (filter.toLowerCase().equals("negative")) {

                    //Apply negative filter
                    p = negative(a, r, g, b, p);
                } else if (filter.toLowerCase().equals("red")) {

                    //Apply red filter
                    p = red(a, r, g, b, p);
                } else if (filter.toLowerCase().equals("green")) {

                    //Apply green filter
                    p = green(a, r, g, b, p);
                } else if (filter.toLowerCase().equals("blue")) {

                    //Apply blue filter
                    p = blue(a, r, g, b, p);
                } else if (filter.toLowerCase().equals("sepia")) {

                    //Apply sepia filter
                    p = sepia(a, r, g, b, p);
                } else if (filter.toLowerCase().equals("grainy")) {

                    //Apply grainy filter
                    p = grainy(a, r, g, b, p);

                }else if(filter.toLowerCase().equals("glitch")){
                    //Apply glitch filter
                    p = glitch(a, r, g, b, p);
                }else if(filter.toLowerCase().equals("artsy")){
                    //Apply artsy filter
                    p = artsy(a, r, g, b, p);
                }else if(filter.toLowerCase().equals("intense")){
                    //Apply intense filter
                    p = intense(a, r, g, b, p);
                }

                //Set the pixel at (i, k) to the int "p"
                img.setRGB(i, k, p);
            }
        }
    }

    public static int[] getRGBA(int p) {
        /* The following code finds the rgba values of each pixel in decimal form.
           Each value's maximum is 255.

           To find each value, the pixel, "p" has to shifted to the
           right the appropriate amount (because each pixel is 32 bits)
           and then 0xFF must be added, which will give us the decimal number
           we are looking for.

           alpha: shift to the right by 24; red: shift right by 16; green: shift right by 9; blue: no need to shift.
           (Note: The ">>" syntax tells the code to shift to the right) */


        //Get the Red decimal value
        int r = (p >> 16) & 0xff;
        //Get the Green decimal value
        int g = (p >> 8) & 0xff;
        //Get the Blue decimal value
        int b = p & 0xff;
        // Get the Alpha decimal value (A.K.A. the transparency of an image)
        int a = (p >> 24) & 0xff;


        int[] rgba = {r, g, b, a};

        return rgba;
    }

    //Filters, organized from Basic --> Custom created filters

    //Basic filter to turn the image into a greyscale image
    public static int greyscale(int a, int r, int g, int b, int p) {
        int avg = (r + b + g) / 3;
        r = avg;
        b = avg;
        g = avg;


        //Assign new r, g, b values to the pixel and return it
        p = (a << 24) | (r << 16) | (g << 8) | b;
        return p;
    }

    //Basic filter to make the image a negative
    public static int negative(int a, int r, int g, int b, int p) {
        //Do 255 - RGB to change each value to its "negative"
        r = 255 - r;
        g = 255 - g;
        b = 255 - b;

        //Assign new r, g, b values to the pixel and return it
        p = (a << 24) | (r << 16) | (g << 8) | b;
        return p;
    }

    //Basic filter to make the image red-colored
    public static int red(int a, int r, int g, int b, int p) {
        //Only the green and blue values are changed
        g = 0;
        b = 0;

        //Assign new r, g, b values to the pixel and return it
        p = (a << 24) | (r << 16) | (g << 8) | b;
        return p;
    }

    //Basic filter to make the image green-colored
    public static int green(int a, int r, int g, int b, int p) {
        //Green remains unchanged
        r = 0;
        b = 0;

        //Assign new r, g, b values to the pixel and return it
        p = (a << 24) | (r << 16) | (g << 8) | b;
        return p;
    }

    //Basic filter to make the image blue-colored
    public static int blue(int a, int r, int g, int b, int p) {
        //Blue remains unchanged
        r = 0;
        g = 0;

        //Assign new r, g, b values to the pixel and return it
        p = (a << 24) | (r << 16) | (g << 8) | b;
        return p;
    }

    //Basic filter which uses a formula to make image sepia-colored
    public static int sepia(int a, int r, int g, int b, int p) {
        //calculate newRed, newGreen, newBlue
        int newRed = (int) (0.393 * r + 0.769 * g + 0.189 * b);
        int newGreen = (int) (0.349 * r + 0.686 * g + 0.168 * b);
        int newBlue = (int) (0.272 * r + 0.534 * g + 0.131 * b);

        //If the calculated rgb values are greater than 255, set them equal to 255.
        //Check red
        if (newRed > 255) {
            r = 255;
        } else {
            r = newRed;
        }

        //Check green
        if (newGreen > 255) {
            g = 255;
        } else {
            g = newGreen;
        }

        //Check blue
        if (newBlue > 255) {
            b = 255;
        } else {
            b = newBlue;
        }

        //Assign new r, g, b values to the pixel and return it
        p = (a << 24) | (r << 16) | (g << 8) | b;
        return p;
    }


    //Custom filters:

    public static int grainy(int a, int r, int g, int b, int p) {
        //Blue remains unchanged

        r*=b;
        b*=g;

        //Assign new r, g, b values to the pixel and return it
        p = (a << 24) | (r << 16) | (g << 8) | b;
        return p;
    }

    public static int artsy(int a, int r, int g, int b, int p) {
        b *= 2;

        //Assign new r, g, b values to the pixel and return it
        p = (a << 24) | (r << 16) | (g << 8) | b;
        return p;
    }

    public static int glitch(int a, int r, int g, int b, int p) {

        r = r-b;

        //Assign new r, g, b values to the pixel and return it
        p = (a << 24) | (r << 16) | (g << 8) | b;
        return p;
    }

    public static int intense(int a, int r, int g, int b, int p) {


        r= r+30;
        g= 30;
        b= 30;

        //Assign new r, g, b values to the pixel and return it
        p = (a << 24) | (r << 16) | (g << 8) | b;
        return p;
    }



}//Main class ends

