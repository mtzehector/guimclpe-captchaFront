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
import mx.gob.imss.dpes.captchafront.model.CaptchaValidarRequest;
import mx.gob.imss.dpes.captchafront.model.CaptchaValidarResponse;
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
public class CaptchaValidarService extends ServiceDefinition<CaptchaValidarRequest, CaptchaValidarRequest> {

    @Inject 
    @ConfigProperty(name = "keyForDecrypt")
    private String keyForDecrypt;

    
    @Override
    public Message<CaptchaValidarRequest> execute(Message<CaptchaValidarRequest> request) {

        try {
            Captcha captcha = null;

            Boolean isValid = CaptchaUtil.isValidCaptcha(
                    request.getPayload().getCaptchaValueEncrypted(), 
                    request.getPayload().getCaptchaValueTyped(),
                    keyForDecrypt
            );

            //log.log(Level.INFO, ">>>>CAPTCHA FRONT Valida Captcha: ");

            CaptchaValidarResponse response = new CaptchaValidarResponse();
            response.setIsValid(isValid);
            request.getPayload().setResponse(response);

            //log.log(Level.INFO, ">>>>CAPTCHA FRONT Previo al response del service valida Captcha.");

            return response(request.getPayload(), ServiceStatusEnum.EXITOSO, null, null);
        } catch (Exception ex) {
            Logger.getLogger(CaptchaValidarService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;

    }

}
