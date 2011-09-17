package utils;
/** 
 * @author dingyi
 */

import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;
import java.util.Properties;

import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;
import org.apache.velocity.exception.MethodInvocationException;
import org.apache.velocity.exception.ParseErrorException;
import org.apache.velocity.exception.ResourceNotFoundException;

public class VelocityUtil {

	private static final String LOADER = "file.resource.loader.class";
	private static final String CLASSPATH = "org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader";

	public static Template getTemplate(String path) throws Exception {
		Properties p = new Properties();
		p.setProperty(Velocity.INPUT_ENCODING, "UTF-8");
		p.setProperty(Velocity.OUTPUT_ENCODING, "UTF-8");
		p.setProperty(LOADER, CLASSPATH);
		Velocity.init(p);
		Template template = Velocity.getTemplate(path);
		return template;
	}

	public static VelocityContext getVelocityContext() {
		return new VelocityContext();
	}

	public static String getTemplateInfo(Template template, VelocityContext ctx)
			throws ResourceNotFoundException, ParseErrorException,
			MethodInvocationException, IOException {
		Writer writer = new StringWriter();
		template.merge(ctx, writer);
		String info=writer.toString();
		writer.close();
		return info;
	}

}
