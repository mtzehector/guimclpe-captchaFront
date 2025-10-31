/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.gob.imss.dpes.captcha.test;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import mx.gob.imss.dpes.captchafront.util.CaptchaUtil;
import mx.gob.imss.dpes.interfaces.captcha.model.Captcha;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author juan.garfias
 */
public class NewEmptyJUnitTest {

    public NewEmptyJUnitTest() {
    }

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }

    // TODO add test methods here.
    // The methods must be annotated with annotation @Test. For example:
    //
    @Test
    public void hello() throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException {
            System.out.println("Test de Juan");


            Captcha c = CaptchaUtil.generateCaptchaImage("79302F7504AB73FF");

            System.out.println("Imagen String 1 CaptchaUtil.generateCaptchaImage(): " + c.getCaptchaValueEncrypted());
            System.out.println("Imagen String 1 CaptchaUtil.generateCaptchaImage(): " + c.getCaptchaImageInBase64());
            System.out.println("Valor decifrado: " +CaptchaUtil.decifrarValor(c.getCaptchaValueEncrypted(), "79302F7504AB73FF"));
            //System.out.println("Es valido captcha: " + isValidImage);
            System.out.println("");


    }
}
