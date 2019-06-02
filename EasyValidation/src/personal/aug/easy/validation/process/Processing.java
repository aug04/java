package personal.aug.easy.validation.process;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

import personal.aug.easy.validation.annotations.ValidateNotNull;
import personal.aug.easy.validation.annotations.ValidateString;
import personal.aug.easy.validation.supporttypes.Status;

public class Processing {

	public Map<String, Object> processing(Class<?> clazz, Object instance) throws Exception {
		Map<String, Object> result = null;
		
		if (clazz != null 
				&& instance != null 
				&& (instance.getClass() == clazz)) {
			result = new HashMap<>();
			
			Field[] fields = clazz.getDeclaredFields();
			if (fields.length > 0) {
				for (Field field : fields) {
					field.setAccessible(true);
					Object value = field.get(instance);
					
					Annotation[] annotations = field.getAnnotations();
					Map<String, Object> resultField = new HashMap<>();
					for (Annotation ann : annotations) {
						if (ann instanceof ValidateNotNull) {
							String val = !String.valueOf(value).equals("null") ? value.toString() : "";
							resultField.put(ValidateNotNull.class.getSimpleName(), 
									handleValidateNotNull((ValidateNotNull) ann, val));
						}
						
						if (ann instanceof ValidateString) {
							String val = !String.valueOf(value).equals("null") ? value.toString() : "";
							resultField.put(ValidateString.class.getSimpleName(), 
									handleValidateString((ValidateString) ann, val));
						}
					}
					
					result.put(field.getName(), resultField);
				}
			}
			boolean allIsValid = true;
			for (Object obj : result.values()) {
				if (obj instanceof Map<?, ?>) {
					for (Object obj2 : ((Map<?, ?>) obj).values()) {
						if (obj2 instanceof ProcessResult) {
							if (!((ProcessResult) obj2).isValid()) {
								allIsValid = false;
								break;
							}
						}
					}
					
					if (!allIsValid)
						break;
				}
			}
			
			result.put("allIsValid", allIsValid);
		}
		
		return result;
	}
	
	private ProcessResult handleValidateNotNull(ValidateNotNull ann, String value) {
		ProcessResult result = new ProcessResult();
		
		if (value == null) {
			result.getStatusList().add(Status.IS_NULL_OR_EMPTY);
		} else {
			result.getStatusList().add(Status.IS_VALID);
		}
		
		return result;
	}
	
	private ProcessResult handleValidateString(ValidateString ann, String value) {
		ProcessResult result = new ProcessResult();
		
		if (ann.isNotNullOrEmpty()) {
			if (value == null || value.isEmpty()) {
				result.getStatusList().add(Status.IS_NULL_OR_EMPTY);
			} else {
				result.getStatusList().add(Status.IS_VALID);
			}
		}
		
		if (ann.minLength() > 0) {
			if (value.length() < ann.minLength()) {
				result.getStatusList().add(Status.INVALID_LENGTH);
			} else {
				result.getStatusList().add(Status.IS_VALID);
			}
		}
		
		if (ann.maxLength() > 0) {
			if (value.length() > ann.maxLength()) {
				result.getStatusList().add(Status.INVALID_LENGTH);
			} else {
				result.getStatusList().add(Status.IS_VALID);
			}
		}
		
		if (ann.match() != null && !ann.match().isEmpty()) {
			if (!value.matches(ann.match())) {
				result.getStatusList().add(Status.NOT_MATCH);
			} else {
				result.getStatusList().add(Status.IS_VALID);
			}
		}
		
		return result;
	}

}
