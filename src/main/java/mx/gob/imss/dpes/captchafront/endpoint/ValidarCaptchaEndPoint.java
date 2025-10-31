/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.gob.imss.dpes.captchafront.endpoint;

import java.util.logging.Level;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import mx.gob.imss.dpes.captchafront.assambler.CaptchaValidarAssembler;
import mx.gob.imss.dpes.captchafront.exception.CaptchaGenerarException;
import mx.gob.imss.dpes.captchafront.model.CaptchaValidarRequest;
import mx.gob.imss.dpes.captchafront.service.CaptchaValidarService;
import mx.gob.imss.dpes.common.endpoint.BaseGUIEndPoint;
import mx.gob.imss.dpes.common.exception.BusinessException;
import mx.gob.imss.dpes.common.model.BaseModel;
import mx.gob.imss.dpes.common.model.Message;
import mx.gob.imss.dpes.common.service.ServiceDefinition;
import org.eclipse.microprofile.openapi.annotations.Operation;

/**
 *
 * @author juan.garfias
 */
@Path("/validarCaptcha")
@RequestScoped
public class ValidarCaptchaEndPoint extends BaseGUIEndPoint<BaseModel, BaseModel, BaseModel> {

    @Inject
    private CaptchaValidarService captchaValidarService;

    @Inject
    private CaptchaValidarAssembler assembler;

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Operation(summary = "Validación de captcha", description = "Valida Captcha")
    public Response validarCaptcha(CaptchaValidarRequest request) throws BusinessException {
        
        //log.log(Level.INFO, ">>>>CAPTCHA FRONT comienza inicio de validar captcha.");
        
        ServiceDefinition[] steps = {captchaValidarService};

        Message<CaptchaValidarRequest> response
                = captchaValidarService.executeSteps(steps, new Message<>( request ));
        //log.log(Level.INFO, ">>>>CAPTCHA FRONT Despues de ejecutar steps.");

        if (!Message.isException(response)) {
        //    log.log(Level.INFO, ">>>>CAPTCHA FRONT No hay errores.");

            return toResponse(new Message<>(assembler.assemble(response.getPayload())));
        }
        //log.log(Level.INFO, ">>>>CAPTCHA FRONT hay errores.");
        throw new CaptchaGenerarException();

    }

}
