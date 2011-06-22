package com.guiwuu.design.aop.intro;

import java.lang.reflect.Method;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author guiwuu
 */
public class RunAop {
    
    private static final Logger logger = Logger.getLogger(RunAop.class.getName());
    
    public static int run(Class clazz) {
        int success = 0;
        Method[] methods = clazz.getMethods();
        try{
            Object obj = clazz.newInstance();
            for(Method method : methods){
                if(method.isAnnotationPresent(Run.class)){
                    try{
                        method.invoke(obj);
                        success++;
                    }catch(Exception e){
                        logger.log(Level.SEVERE, "method error", e);
                    }
                }
            }
        } catch (Exception e){
            logger.log(Level.SEVERE, "unknown error", e);
        } finally{
            return success;
        }
    }
    
}
