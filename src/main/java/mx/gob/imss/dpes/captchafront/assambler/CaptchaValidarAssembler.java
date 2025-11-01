/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.gob.imss.dpes.captchafront.assambler;

import javax.ws.rs.ext.Provider;

import mx.gob.imss.dpes.captchafront.model.CaptchaValidarRequest;
import mx.gob.imss.dpes.captchafront.model.CaptchaValidarResponse;
import mx.gob.imss.dpes.common.assembler.BaseAssembler;

/**
 *
 * @author juan.garfias
 */
@Provider
public class CaptchaValidarAssembler extends BaseAssembler<CaptchaValidarRequest,CaptchaValidarResponse> {
    
    @Override
    public CaptchaValidarResponse assemble(CaptchaValidarRequest source) {
    CaptchaValidarResponse out = new CaptchaValidarResponse();
    out.setIsValid(source.getResponse().getIsValid());
    return out;
    
    }
}
