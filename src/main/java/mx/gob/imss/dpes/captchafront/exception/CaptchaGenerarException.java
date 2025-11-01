/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.gob.imss.dpes.captchafront.exception;

import mx.gob.imss.dpes.common.exception.BusinessException;

/**
 *
 * @author juan.garfias
 */
public class CaptchaGenerarException extends BusinessException{
  private static final String KEY = "msg014";

  public CaptchaGenerarException() {super(KEY);}
}
