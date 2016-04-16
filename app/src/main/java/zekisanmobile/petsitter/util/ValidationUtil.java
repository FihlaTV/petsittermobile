package zekisanmobile.petsitter.util;

import java.util.Iterator;
import java.util.List;

public class ValidationUtil {

    public static void validate(Validation... args) {
        for(Validation validation : args){
            validation.validate();
        }
    }

    public static void pruneInvalid(List<? extends Validation> validations) {
        Iterator<? extends Validation> iterator = validations.iterator();
        while (iterator.hasNext()) {
            try {
                Validation next = iterator.next();
                next.validate();
            } catch (ValidationFailedException ex) {
                iterator.remove();
            }
        }
    }
}
