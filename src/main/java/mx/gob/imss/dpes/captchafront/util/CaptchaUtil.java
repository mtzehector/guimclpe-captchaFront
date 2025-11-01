/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.gob.imss.dpes.captchafront.util;

/**
 *
 * @author juan.garfias
 */
import com.octo.captcha.service.image.DefaultManageableImageCaptchaService;
import com.octo.captcha.service.image.ImageCaptchaService;
import mx.gob.imss.dpes.interfaces.captcha.model.Captcha;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.imageio.IIOImage;
import javax.imageio.ImageIO;
import javax.imageio.ImageWriteParam;
import javax.imageio.ImageWriter;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.Iterator;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;

public class CaptchaUtil {


    
    private static final int WIDTH = 220;
    private static final int HEIGHT = 40;
    private static final int CIRCLES_TO_DRAW = 6;
    private static final int CHARS_TO_PRINT = 7;
    private static final ImageCaptchaService imageCaptchaService = new DefaultManageableImageCaptchaService();
    private static final Logger logger = LoggerFactory.getLogger(CaptchaUtil.class);

    public static boolean isValidCaptcha(String value, String strTyped, String keyForDecrypt) throws NoSuchAlgorithmException, InvalidKeyException, BadPaddingException, NoSuchPaddingException, IllegalBlockSizeException {

        if (decifrarValor(value, keyForDecrypt ).equals(strTyped)) {
            return true;
        }

        return false;
    }

    public static Captcha generateCaptchaImage(String keyForDecrypt) throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException {

        StringBuilder finalString = new StringBuilder();
        String imgBase64Str = null;
        try {

            Color textColor = Color.white;
            Color circleColor = Color.gray;

            float horizMargin = 40.0f;
            float imageQuality = 0.65f;
            double rotationRange = 0.9;
            BufferedImage bufferedImage = new BufferedImage(WIDTH, HEIGHT,
                    BufferedImage.TYPE_INT_RGB);
            Graphics2D g = (Graphics2D) bufferedImage.getGraphics();

            g.setColor(Color.orange);
            g.fillRect(0, 0, WIDTH, HEIGHT);
            Font textFont = new Font("Agency FB", Font.BOLD, 24); // getFont();
            g.setColor(circleColor);
            g.setStroke(new BasicStroke(3));
            for (int i = 0; i < CIRCLES_TO_DRAW; i++) {
                int circleRadius = (int) (Math.random() * HEIGHT / 2.0);
                int circleX = (int) (Math.random() * WIDTH - circleRadius);
                int circleY = (int) (Math.random() * HEIGHT - circleRadius);
                g.drawOval(circleX, circleY, circleRadius * 2, circleRadius * 2);
            }
            g.setColor(textColor);
            g.setFont(textFont);
            FontMetrics fontMetrics = g.getFontMetrics();
            int maxAdvance = fontMetrics.getMaxAdvance();
            int fontHeight = fontMetrics.getHeight();
            String elegibleChars = "ABCDEFGHJKLMPQRSTUVWXYabcdefhjkmnpqrstuvwxy23456789";
            char[] chars = elegibleChars.toCharArray();
            float spaceForLetters = -horizMargin * 2 + WIDTH;
            float spacePerChar = spaceForLetters / (CHARS_TO_PRINT - 1.0f);

            for (int i = 0; i < CHARS_TO_PRINT; i++) {
                double randomValue = Math.random();
                int randomIndex = (int) Math.round(randomValue
                        * (chars.length - 1));
                char characterToShow = chars[randomIndex];
                finalString.append(characterToShow);
                int charWidth = fontMetrics.charWidth(characterToShow);
                int charDim = Math.max(maxAdvance, fontHeight);
                int halfCharDim = (int) (charDim / 2);
                BufferedImage charImage = new BufferedImage(charDim, charDim,
                        BufferedImage.TYPE_INT_ARGB);
                Graphics2D charGraphics = charImage.createGraphics();
                charGraphics.translate(halfCharDim, halfCharDim);
                double angle = (Math.random() - 0.2) * rotationRange;
                charGraphics
                        .transform(AffineTransform.getRotateInstance(angle));
                charGraphics.translate(-halfCharDim, -halfCharDim);
                charGraphics.setColor(textColor);
                charGraphics.setFont(textFont);
                int charX = (int) (0.5 * charDim - 0.5 * charWidth);
                charGraphics
                        .drawString(
                                "" + characterToShow,
                                charX,
                                (int) ((charDim - fontMetrics.getAscent()) / 2 + fontMetrics
                                .getAscent()));
                float x = horizMargin + spacePerChar * (i) - charDim / 2.0f;
                int y = (int) ((HEIGHT - charDim) / 2);

                g.drawImage(charImage, (int) x, y, charDim, charDim, null, null);
                charGraphics.dispose();
            }

            Iterator iter = ImageIO.getImageWritersByFormatName("JPG");
            ImageIO.setUseCache(false);

            if (iter.hasNext()) {
                ImageWriter writer = (ImageWriter) iter.next();
                ImageWriteParam iwp = writer.getDefaultWriteParam();
                iwp.setCompressionMode(ImageWriteParam.MODE_EXPLICIT);
                iwp.setCompressionQuality(imageQuality);
                ByteArrayOutputStream out = new ByteArrayOutputStream();

                writer.setOutput(ImageIO.createImageOutputStream(
                        out
                ));

                IIOImage imageIO = new IIOImage(bufferedImage, null, null);
                writer.write(null, imageIO, iwp);

                byte[] qualityImageBytes = out.toByteArray();

                byte[] encoded2 = Base64.getEncoder().encode(qualityImageBytes);
                imgBase64Str = new String(encoded2);

                //System.out.println("Valor del Captcha: " + finalString);
                return new Captcha(cifrarValor(finalString.toString(), keyForDecrypt), imgBase64Str);
            }
            g.dispose();
        } catch (IOException ioe) {
            throw new RuntimeException("No pude construir la imagen:"
                    + ioe.getMessage(), ioe);
        }
        return null;
    }

    public static String cifrarValor(String value, String key) throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException {

        // Create key and cipher
        Key aesKey = new SecretKeySpec(key.getBytes(), "AES");
        Cipher cipher = Cipher.getInstance("AES");
        // encrypt the text
        cipher.init(Cipher.ENCRYPT_MODE, aesKey);
        byte[] encrypted = cipher.doFinal(value.getBytes());
        //System.err.println(new String(encrypted));
        byte[] encoded2 = Base64.getEncoder().encode(encrypted);
        return new String(encoded2);
    }

    public static String decifrarValor(String valueEncodedBase64, String key) throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException {
        
                
        byte[] decoded = Base64.getDecoder().decode(valueEncodedBase64);

        
        // Create key and cipher
        Key aesKey = new SecretKeySpec(key.getBytes(), "AES");
        Cipher cipher = Cipher.getInstance("AES");
        cipher.init(Cipher.DECRYPT_MODE, aesKey);
        String decrypted = new String(cipher.doFinal(decoded));
        //System.err.println(decrypted);
        return decrypted;
    }

}
