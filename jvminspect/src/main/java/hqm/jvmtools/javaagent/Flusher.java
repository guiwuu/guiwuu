package hqm.jvmtools.javaagent;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.URL;
import java.util.List;
import java.util.Map;


public interface Flusher
{
    void flush(String fileName, Map<ClassLoader, List<ClassLoadingInfo>> classLoaders,
            Map<URL, List<ClassLoadingInfo>> locations, List<ClassLoadingInfo> allClasses, Class[] preLoadClasses) throws IOException;
    public void flush(PrintWriter pw, Map<ClassLoader, List<ClassLoadingInfo>> classLoaders,
            Map<URL, List<ClassLoadingInfo>> locations, List<ClassLoadingInfo> allClasses, Class[] preLoadClasses) throws IOException;
}
