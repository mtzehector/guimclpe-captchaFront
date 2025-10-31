/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.gob.imss.dpes.captchafront.assambler;

import javax.ws.rs.ext.Provider;
import mx.gob.imss.dpes.captchafront.model.CaptchaGenerarRequest;
import mx.gob.imss.dpes.captchafront.model.CaptchaGenerarResponse;
import mx.gob.imss.dpes.common.assembler.BaseAssembler;

/**
 *
 * @author juan.garfias
 */
@Provider
public class CaptchaGenerarAssembler extends BaseAssembler<CaptchaGenerarRequest,CaptchaGenerarResponse> {
    
    @Override
    public CaptchaGenerarResponse assemble(CaptchaGenerarRequest source) {
    CaptchaGenerarResponse out = new CaptchaGenerarResponse();
    out.setCaptcha(source.getResponse().getCaptcha());
    return out;
    
    }
}
