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
public class PersonaValidation implements Validator{

    @Override
    public boolean supports(Class<?> type) {
        return PersonaBean.class.isAssignableFrom(type);
    }

    @Override
    public void validate(Object o, Errors errors) {
        
        ValidationUtils.rejectIfEmptyOrWhitespace(
                errors, 
                "nombre", 
                "nombre.required", 
                "El nombre es obligatorio"
        );
        
        ValidationUtils.rejectIfEmptyOrWhitespace(
                errors, 
                "apellido", 
                "apellido.required", 
                "El apellido es obligatorio"
        );
        
        ValidationUtils.rejectIfEmptyOrWhitespace(
                errors, 
                "edad", 
                "edad.required", 
                "La edad es obligatoria"
        );
        
        ValidationUtils.rejectIfEmptyOrWhitespace(
                errors, 
                "correo", 
                "correo.required", 
                "El correo es obligatorio"
        );
        
//        ValidationUtils.rejectIfEmptyOrWhitespace(
//                errors, 
//                "foto", 
//                "foto.required",
//                "La foto es obligatoria");
    }
    
}
