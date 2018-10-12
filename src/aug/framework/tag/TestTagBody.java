package aug.framework.tag;

import java.io.IOException;
import java.io.StringWriter;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.SimpleTagSupport;

public class TestTagBody extends SimpleTagSupport {

	StringWriter sw = new StringWriter();
	
	@Override
	public void doTag() throws JspException, IOException {
		
		getJspBody().invoke(sw);
		getJspContext().getOut().println(sw.toString());
		sw.close();
	}

}
