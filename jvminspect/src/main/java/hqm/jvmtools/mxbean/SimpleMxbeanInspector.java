package hqm.jvmtools.mxbean;

import java.io.IOException;
import java.lang.management.ManagementFactory;
import java.lang.management.RuntimeMXBean;
import java.lang.management.ClassLoadingMXBean;
import java.util.Hashtable;

import javax.management.MBeanServerConnection;
import javax.management.remote.JMXConnector;
import javax.management.remote.JMXConnectorFactory;
import javax.management.remote.JMXServiceURL;
import javax.naming.Context;

public class SimpleMxbeanInspector 
{
	private static MBeanServerConnection getMBeanServerConnector(String mbeanServerHost, String mbeanServerPort) throws IOException 
	{
		int port = Integer.parseInt(mbeanServerPort);
		String protocol = "rmi";
		String jndiroot = new String("/jndi/iiop://" + mbeanServerHost + ":" + port
				+ "/");
		String mserver = "weblogic/management/mbeanservers/runtime";

		JMXServiceURL serviceURL = new JMXServiceURL(protocol, mbeanServerHost, port,
				jndiroot + mserver);

		Hashtable<String,Object> h = new Hashtable<String,Object>();
		h.put(Context.SECURITY_PRINCIPAL, "weblogic");
		h.put(Context.SECURITY_CREDENTIALS, "weblogic");

		JMXConnector c = JMXConnectorFactory.connect(serviceURL, h);
		return c.getMBeanServerConnection();
	}
	
	private static void inspect(MBeanServerConnection mbs) throws IOException
	{
		   RuntimeMXBean proxyRuntimeMXBean = ManagementFactory.newPlatformMXBeanProxy(
				   mbs, ManagementFactory.RUNTIME_MXBEAN_NAME, RuntimeMXBean.class);

		   ClassLoadingMXBean proxyClassLoadingMXBean = ManagementFactory.newPlatformMXBeanProxy(
				   mbs, ManagementFactory.RUNTIME_MXBEAN_NAME, ClassLoadingMXBean.class);
		   
		   System.out.println("LoadedClassCount="+proxyClassLoadingMXBean.getLoadedClassCount());
		   System.out.println("TotalLoadedClassCount="+proxyClassLoadingMXBean.getTotalLoadedClassCount());
		   System.out.println("UnloadedClassCount="+proxyClassLoadingMXBean.getUnloadedClassCount());
		   System.out.println("ClassLoadingMXBean.isVerbose="+proxyClassLoadingMXBean.isVerbose());
		   proxyClassLoadingMXBean.setVerbose(true);
	}

	public static void main(String[] args) throws IOException {
		String mbeanServerHost = args.length>0? args[0] :"150.236.80.181";
		String mbeanServerPort = args.length>1? args[1] :"9001";
		MBeanServerConnection mbs = getMBeanServerConnector(mbeanServerHost, mbeanServerPort);
		System.out.println(mbs);
		//inspect(mbs);
	}
}
