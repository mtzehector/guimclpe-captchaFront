/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.gob.imss.dpes.captchafront.model;

import lombok.Data;
import mx.gob.imss.dpes.common.model.BaseModel;

/**
 *
 * @author juan.garfias
 */
@Data
public class CaptchaGenerarRequest extends BaseModel{
    private CaptchaGenerarResponse response = new CaptchaGenerarResponse();
}
