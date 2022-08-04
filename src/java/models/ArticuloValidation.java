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
public class ArticuloValidation implements Validator{

    @Override
    public boolean supports(Class<?> type) {
        return ArticuloBean.class.isAssignableFrom(type);
    }

    @Override
    public void validate(Object o, Errors errors) {
        ArticuloBean articulo = (ArticuloBean)o;
        
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "titulo", "titulo.required", "El título del artículo es obligatorio");
        
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "descripcion", "descripcion.required", "La descripción del artídulo es obligarotia");
        
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "precio", "precio.required", "El precio del artículo es obligatorio");
        
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "foto", "foto.required", "La foto del artículo es obligatoria");
    }
    
}
