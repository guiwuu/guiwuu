package hqm.test.jdk;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * A test case for SimpleDateFormat, proved it's not thread safe.
 * It may get a such format result under muti-thread concurrency: 
 * 2007-12-00 00:00:00
 * -------------->[Thread-7]:20070100120000
 * -------------->[Thread-1]:20070100000000
 * @author qweiqia
 *
 */
public class DateFormatTest
{
    public static void main(String[] args) throws ParseException
    {
        SimpleDateFormat DF = new SimpleDateFormat("yyyyMMddHHmmss");
        java.util.Calendar c = java.util.Calendar.getInstance();
        c.setTime(new Date());
        c.set(Calendar.DATE, 0);
        c.set(Calendar.HOUR_OF_DAY, 0);
        c.set(Calendar.MINUTE, 0);
        c.set(Calendar.SECOND, 0);
        c.set(Calendar.MILLISECOND, 0);
        System.out.println(DF.format(c.getTime()));
        
        Date d = DF.parse("20071200000000");
        System.out.println(DF.format(d));
        System.out.println("==================");
        
        for(int i=0; i<15; i++)
        {
            final int j=i;
            new Thread(new Runnable(){
                public void run()
                {
                    try {
                        format(j);
                    }
                    catch (ParseException e) {
                        e.printStackTrace();
                        System.out.println("Thread "+j+" quit.");
                    }
                }},"MyThread-"+i).start();
        }
        try {
            System.out.print("Press any key to continue:");
            System.in.read();
        }
        catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
    
    private static final SimpleDateFormat DF = new SimpleDateFormat("yyyyMMddHHmmss");
    private static void format(int id) throws ParseException
    {
        Date d = new SimpleDateFormat("yyyyMMddHHmmss").parse("20071204120000");
        long t1 = d.getTime();
        d = DF.parse("20071204170000");
        long t2 = d.getTime();
        String str;
        for (long t = t1; t <= t2; t++) {
            str = DF.format(new Date(t));
            if (str.substring(6, 8).equals("00"))
                System.out.println("-------------->[Thread-"+id+"]:"+str);
            if(t%300000==0)
                System.out.println("[Thread-"+id+"]:"+str);
        }
        System.out.println("Thread "+id+" end.");
    }

}
