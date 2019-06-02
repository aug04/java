package personal.aug.easy.validation.supporttypes;

public enum Status {

	ALL_IS_VALID("all_is_valid"),
	IS_VALID("is_valid"),
	IS_NULL_OR_EMPTY("is_null_or_empty"),
	NOT_MATCH("not_match"),
	INVALID_LENGTH("invalid_length");
	
	private String code;
	
	private Status(String code) {
		this.code = code;
	}

	public String getCode() {
		return code;
	}

	public static Status lookup(String code) {
		for (Status s : Status.values()) {
			if (s.getCode().equals(code)) {
				return s;
			}
		}
		
		return null;
	}
}
