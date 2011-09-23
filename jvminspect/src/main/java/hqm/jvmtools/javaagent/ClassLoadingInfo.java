package hqm.jvmtools.javaagent;

import java.net.URL;
import java.security.ProtectionDomain;


public class ClassLoadingInfo
{
    private String name; //full class name 
    private ClassLoader loader;
    private URL location;
    private int order;


    public ClassLoadingInfo()
    {
    }


    public ClassLoadingInfo(String name, ClassLoader loader, URL location, int order)
    {
        this.name = name;
        this.loader = loader;
        this.location = location;
        this.order = order;
    }
    public ClassLoadingInfo(String name, ClassLoader loader, ProtectionDomain protectionDomain, int order)
    {
        URL classLocation =
            protectionDomain.getCodeSource() == null ? null : protectionDomain.getCodeSource().getLocation();

        this.name = name;
        this.loader = loader;
        this.location = classLocation;
        this.order = order;
    }


    public ClassLoader getLoader()
    {
        return loader;
    }


    public void setLoader(ClassLoader loader)
    {
        this.loader = loader;
    }


    public URL getLocation()
    {
        return location;
    }


    public void setLocation(URL location)
    {
        this.location = location;
    }


    public String getName()
    {
        return name;
    }


    public void setName(String name)
    {
        this.name = name;
    }


    public int getOrder()
    {
        return order;
    }


    public void setOrder(int order)
    {
        this.order = order;
    }

    /*
    static class NameComparator implements Comparator<ClassLoadingInfo>
    {
        public int compare(ClassLoadingInfo o1, ClassLoadingInfo o2)
        {
            String className1 = o1.getName();
            String className2 = o2.getName();
            return className1.compareTo(className2);
        }
    }
    */
}
