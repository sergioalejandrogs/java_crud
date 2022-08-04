/*
 * @version 1.0.0 Validation method in order to verify if the form is 
 * being filled correctly. It checks it a field is blank and throws an error.
*/
package models;

import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

/**
 *
 * @author Sergio González
 * @since 23/03/2022
 * @version 1.0.0 Validation method in order to verify if the form is 
 * being filled correctly. It checks it a field is blank and throws an error.
 */
public class VentaValidation implements Validator{
    
    @Override
    public boolean supports(Class<?> type){
        return VentaBean.class.isAssignableFrom(type);
    }

    @Override
    public void validate(Object o, Errors errors) {        
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "fecha_venta", "fecha_venta.required", "La fecha de venta es oligatoria.");
    }
    
    
}
