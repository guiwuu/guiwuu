package hqm.jvmtools.javaagent;

import hqm.jvmtools.Logger;

import java.io.IOException;
import java.lang.instrument.ClassFileTransformer;
import java.lang.instrument.IllegalClassFormatException;
import java.net.URL;
import java.security.ProtectionDomain;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;


public class ClassLoadingInspector implements ClassFileTransformer
{
    private Logger logger;
    private long flushInterval = 3 * 60 * 1000L;
    private int classCount = 0;
    private Object lock = new Object();
    private Flusher flusher = new HtmlFlusher();

    private Map<ClassLoader, List<ClassLoadingInfo>> classLoaders = new ConcurrentHashMap<ClassLoader, List<ClassLoadingInfo>>();
    private Map<URL, List<ClassLoadingInfo>> locations = new ConcurrentHashMap<URL, List<ClassLoadingInfo>>();
    private List<ClassLoadingInfo> allClasses = new ArrayList<ClassLoadingInfo>(10240);
    private Class[] preLoadClasses;
    

    public ClassLoadingInspector(Logger logger, Class[] loadedClasses)
    {
        this.logger = logger;
        this.preLoadClasses = loadedClasses;
        startFlushThread();
    }


    public byte[] transform(ClassLoader loader, String className, Class<?> classBeingRedefined,
            ProtectionDomain protectionDomain, byte[] classfileBuffer) throws IllegalClassFormatException
    {
        String classNameDot = className.replaceAll("/", ".");
        ClassLoadingInfo info = new ClassLoadingInfo(classNameDot, loader, protectionDomain, classCount);

        synchronized (lock) {
            allClasses.add(info);
        }

        List<ClassLoadingInfo> loaderView = classLoaders.get(loader);
        if (loaderView == null) {
            loaderView = new ArrayList<ClassLoadingInfo>();
            classLoaders.put(loader, loaderView);
        }
        synchronized (lock) {
            loaderView.add(info);
        }

        List<ClassLoadingInfo> locationView = locations.get(info.getLocation());
        if (locationView == null) {
            locationView = new ArrayList<ClassLoadingInfo>();
            locations.put(info.getLocation(), locationView);
        }
        synchronized (lock) {
            locationView.add(info);
        }

        classCount++;
        //logger.newLine();
        logger.info(new StringBuilder(loader.getClass().getName()).append("@").append(
            Integer.toHexString(loader.hashCode())).append(" loaded class: ").append(classNameDot).append(" from: ")
            .append(info.getLocation()).toString());

        return null;
    }


    private void startFlushThread()
    {
        new Thread(new Runnable() {
            public void run()
            {
                while (true) {
                    try {
                        Thread.sleep(flushInterval);
                    }
                    catch (InterruptedException e1) {
                    }

                    try {
                        synchronized (lock) {
                            //flushInfo();
                            System.out.println("[ClassLoadingInspector.flushInfo] called. total classloaders:"
                                    + classLoaders.size() + ", total locations:" + locations.size() + ", total class:"
                                    + classCount);

                            flusher.flush("classloaders.html", classLoaders, locations, allClasses, preLoadClasses);
                        }
                    }
                    catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        },"ClassLoadingInspector-FlushThread").start();
    }


    public void setFlushInterval(long flushInterval)
    {
        this.flushInterval = flushInterval;
    }

    /*static class ClassComparator implements Comparator<Object[]>
    {
        public int compare(Object[] o1, Object[] o2)
        {
            String className1 = (String) o1[0];
            String className2 = (String) o2[0];
            return className1.compareTo(className2);
        }
    }*/
}
