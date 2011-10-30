package tag;

/** 
 * @author dingyi
 * @version 创建时间:2009-4-2 下午05:21:39 
 */

import javax.servlet.jsp.JspException;

import org.apache.velocity.VelocityContext;


public class AdPreviewScriptTag extends BaseTag {
	
	

	private static final long serialVersionUID = 1L;
	
	private String param = "";
	private String scriptContent;
	private final static String PATH = "adPreviewScriptTag.vm";

	
	public int doEndTag() throws JspException {
		scriptContent = bodyContent!=null?bodyContent.getString():"";
		try {
			init(PATH);
		} catch (Exception e) {
			throw new JspException(e);
		}
		return super.doEndTag();
	}

	public void release() {
		super.release();
	}

	public String getParam() {
		return param;
	}

	public void setParam(String param) {
		this.param = param;
	}

	public String getScriptContent() {
		return scriptContent;
	}

	public void setScriptContent(String scriptContent) {
		this.scriptContent = scriptContent;
	}

	@Override
	public void puts(VelocityContext ctx) {
		// TODO Auto-generated method stub
		ctx.put("param", param);
		ctx.put("scriptContent", scriptContent);
	}

}
