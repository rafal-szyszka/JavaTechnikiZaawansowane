package it.szyszka;

import it.szyszka.loader.MyClassLoader;

import java.io.IOException;
import java.lang.reflect.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class Main {

    public static final int ARGS_DEFAULT_DIR = 0;
    public static final int ARGS_DEFAULT_PACKAGE = 1;
    public static final int ARGS_METHOD_SIGNATURES = 2;

    private static ArrayList<Method> toInvoke = new ArrayList<>();
    private static ArrayList<Object> myObjects = new ArrayList<>();
    private static ArrayList<Class<?>> myClasses;

    public static void main(String[] args) throws IOException {

        String defaultDir = args[ARGS_DEFAULT_DIR];
        String defaultPackage = args[ARGS_DEFAULT_PACKAGE];
        String[] signatures = args[ARGS_METHOD_SIGNATURES].split(",");


        MyClassLoader myLoader = new MyClassLoader(Main.class.getClassLoader(), defaultDir, defaultPackage);
        myClasses = myLoader.loadAll();
        instantiateClasses(myClasses);
        showMethods(myClasses);
        invokeMethodsBySignatures(myClasses, signatures, null);
        invokeByName("getArray");
        unload();
        myLoader = null;
    }

    private static void unload() {
        toInvoke = null;
        myObjects = null;
        myClasses = null;
    }

    private static void instantiateClasses(ArrayList<Class<?>> myClasses) {
        myClasses.forEach(aClass -> {
            try {
                Constructor cons = aClass.getConstructor(null);
                myObjects.add(cons.newInstance(null));
            } catch (NoSuchMethodException | IllegalAccessException | InstantiationException | InvocationTargetException e) {
                e.printStackTrace();
            }
        });
    }

    private static void invokeMethodsBySignatures(ArrayList<Class<?>> myClasses, String[] signatures, Object[] args) {
        myClasses.forEach(
                aClass -> {
                    for (String signature : signatures) {
                        Arrays.asList(aClass.getDeclaredMethods()).stream()
                                .filter(method -> getSignature(method).equals(signature))
                                .forEach(method -> toInvoke.add(method));
                    }
                }
        );
        System.out.println();
        toInvoke.forEach(method -> System.out.println("To invoke: " + method));
        invoke(args);
    }

    private static void invoke(Object[] args) {
        toInvoke.stream()
                .filter(method -> method.toString().contains("static"))
                .forEach(method -> {
                    callMethod(args, method);
                });
    }

    private static void invokeByName(String name) {
        myObjects.forEach(
                object -> {
                    try {
                        System.out.println("Invoking by name: ");
                        int[] result = (int[]) object.getClass().getMethod(name, new Class<?>[]{int.class, int.class}).invoke(object, new Object[]{10, 32});
                        for (int r : result) {
                            System.out.print(r + " ");
                        }
                        System.out.println();
                    } catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
                        System.err.println("nope");
                    }
                }
        );
    }

    private static void callMethod(Object[] args, Method method) {
        try {
            System.out.println("\nInvoking by signature: " + method);
            method.invoke(null, args);
            System.out.println();
        } catch (IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }
    }

    private static void showMethods(ArrayList<Class<?>> myClasses) {
        myClasses.forEach(
                aClass -> {
                    System.out.println(aClass.getName());
                    Method[] methods = aClass.getDeclaredMethods();
                    for (Method method : methods) {
                        System.out.println("\t" + method + "\t" + getSignature(method));
                    }
                }
        );
    }

    public static String getSignature(Method method) {
        String signature;
        try {
            Field field = Method.class.getDeclaredField("signature");
            field.setAccessible(true);
            signature = (String) field.get(method);
            if(signature != null) return signature;
        } catch (NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace();
        }

        StringBuilder sb = new StringBuilder("(");
        for(Class<?> c : method.getParameterTypes())
            sb.append((signature= Array.newInstance(c, 0).toString())
                    .substring(1, signature.indexOf('@')));
        return sb.append(')')
                .append(
                        method.getReturnType()==void.class?"V":
                                (signature=Array.newInstance(method.getReturnType(), 0).toString()).substring(1, signature.indexOf('@'))
                )
                .toString();
    }
}