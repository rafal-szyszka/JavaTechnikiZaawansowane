package it.szyszka.loader;

import java.io.*;
import java.net.URI;
import java.net.URISyntaxException;

/**
 * Created by rafal on 16.10.17.
 */
public class MyClassLoader extends ClassLoader {

    private String baseDir;

    public MyClassLoader(ClassLoader parent, String baseDir) {
        super(parent);
        this.baseDir = baseDir;
    }

    private Class getClass(String name) {
        String fileName = baseDir + name.replace('.', File.separatorChar) + ".class";
        byte[] b = null;
        try {
            b = loadClassFileData(fileName);
            Class clazz = defineClass(name, b, 0, b.length);
            resolveClass(clazz);
            return clazz;
        } catch (IOException | URISyntaxException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public Class<?> loadClass(String name) throws ClassNotFoundException {
        if(name.startsWith("it.szyszka")) {
            System.out.format("Loading class: \'%s\' using \'MyClassLoader\'\n", name);
            return getClass(name);
        }
        System.out.format("Loading class: \'%s\' using default class loader\n", name);
        return super.loadClass(name);
    }

    private byte[] loadClassFileData(String fileName) throws IOException, URISyntaxException {
        InputStream in = new FileInputStream(new File(fileName));
        int size = in.available();
        byte buff[] = new byte[size];
        DataInputStream dataIn = new DataInputStream(in);
        dataIn.readFully(buff);
        dataIn.close();
        return buff;
    }
}
