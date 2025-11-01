/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.gob.imss.dpes.captchafront.service;

import java.util.logging.Level;
import java.util.logging.Logger;
import javax.inject.Inject;
import javax.ws.rs.ext.Provider;
import mx.gob.imss.dpes.captchafront.model.CaptchaGenerarRequest;
import mx.gob.imss.dpes.captchafront.model.CaptchaGenerarResponse;
import mx.gob.imss.dpes.captchafront.util.CaptchaUtil;
import mx.gob.imss.dpes.common.model.Message;
import mx.gob.imss.dpes.common.model.ServiceStatusEnum;
import mx.gob.imss.dpes.common.service.ServiceDefinition;
import mx.gob.imss.dpes.interfaces.captcha.model.Captcha;
import org.eclipse.microprofile.config.inject.ConfigProperty;

/**
 *
 * @author juan.garfias
 */
@Provider
public class CaptchaGenerarService extends ServiceDefinition<CaptchaGenerarRequest, CaptchaGenerarRequest> {

    
    @Inject 
    @ConfigProperty(name = "keyForDecrypt")
    private String keyForDecrypt;

    @Override
    public Message<CaptchaGenerarRequest> execute(Message<CaptchaGenerarRequest> request) {

        try {
            Captcha captcha = null;

            captcha = CaptchaUtil.generateCaptchaImage(keyForDecrypt);

            //log.log(Level.INFO, ">>>>CAPTCHA FRONT Genera Captcha.");

            CaptchaGenerarResponse response = new CaptchaGenerarResponse();
            response.setCaptcha(captcha);
            request.getPayload().setResponse(response);

            //log.log(Level.INFO, ">>>>CAPTCHA FRONT Previo al response del service Captcha.");

            return response(request.getPayload(), ServiceStatusEnum.EXITOSO, null, null);
        } catch (Exception ex) {
            Logger.getLogger(CaptchaGenerarService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;

    }

}
