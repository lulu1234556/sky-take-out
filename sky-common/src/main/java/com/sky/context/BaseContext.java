package com.sky.context;

public class BaseContext {
    public static ThreadLocal<Long> threadLocal=new ThreadLocal<>();

    public static void setThreadLocal(Long id){
        threadLocal.set(id);
    }
    public static Long getCurrentId(){
        return threadLocal.get();
    }
    public static void removeCurrentId(){
        threadLocal.remove();
    }
}
