package personal.aug.easy.validation;

import java.text.SimpleDateFormat;
import java.util.Date;

import personal.aug.easy.validation.annotations.ValidateNotNull;
import personal.aug.easy.validation.annotations.ValidateString;

public class Test extends EasyValidation {

	@ValidateNotNull
	private Object o;
	
	@ValidateNotNull
	@ValidateString(match = "\\d+")
	private String s;
	
	public Object getO() {
		return o;
	}

	public void setO(Object o) {
		this.o = o;
	}

	public String getS() {
		return s;
	}

	public void setS(String s) {
		this.s = s;
	}

	public static void main(String[] args) throws Exception {
		Test t = new Test();
		t.setO(5);
		t.setS("44444 fsdfdsf");
		
		//Map<String, Object> result = t.validate();
		//System.out.println(result);
		SimpleDateFormat sdf = null;
		try {
			sdf = new SimpleDateFormat("dd/MM/yyyy");
			sdf.format(new Date());
			
		} catch (IllegalArgumentException e) {
			// TODO: handle exception
		}
		
		Date d = sdf.parse("50/13f/19752r");
		System.out.println(sdf);
		System.out.println(d);
	}
}
