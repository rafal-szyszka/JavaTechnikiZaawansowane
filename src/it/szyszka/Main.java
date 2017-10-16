package it.szyszka;

import it.szyszka.loader.MyClassLoader;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class Main {

    public static final String DEFAULT_DIR = "/home/rafal/Dropbox/Projekty/Pwr/Java-zaaw/byteClasses/";

    public static void main(String[] args) throws IOException {

        MyClassLoader myLoader = new MyClassLoader(Main.class.getClassLoader(), "");
        try {
            Class hello = myLoader.loadClass("it.szyszka.Hello");
            Method main = hello.getMethod("main", args.getClass());
            Object[] argsArray = { args };
            main.invoke(null, argsArray);
            Class addArgsType[] = {int.class, int.class};
            Method sayHello = hello.getMethod("sayHello", null);
            sayHello.invoke(null, new Object[0]);
            Method add = hello.getMethod("add", addArgsType);
            Object[] addArgs = {1,2};
            Integer result = (Integer) add.invoke(null, addArgs);
            System.out.println("Result from add: " + result);

        } catch (ClassNotFoundException | NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }

    }
}