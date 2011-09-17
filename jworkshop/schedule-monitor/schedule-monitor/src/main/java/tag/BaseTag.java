package tag;

import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.BodyTagSupport;

import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;

import utils.VelocityUtil;

/** 
 * @author dingyi
 * @version 创建时间:2009-4-2 下午08:40:39 
 */
@SuppressWarnings("serial")
public abstract class BaseTag extends BodyTagSupport {
	
	public void init(String vmPath) throws Exception {
		JspWriter out = pageContext.getOut();
		Template template = VelocityUtil.getTemplate("tags/"+vmPath);
		VelocityContext ctx = VelocityUtil.getVelocityContext();
		puts(ctx);
		String info = VelocityUtil.getTemplateInfo(template, ctx);
		out.println(info);
	}
	
	public abstract void puts(VelocityContext ctx);

}
