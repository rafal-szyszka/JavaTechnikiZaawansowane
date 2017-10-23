package it.szyszka.loader;

import java.io.*;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.stream.Stream;

/**
 * Created by rafal on 16.10.17.
 */
public class MyClassLoader extends ClassLoader {

    private String baseDir;
    private String packageName;
    private ArrayList<String> classNames;
    private ArrayList<Class<?>> classes;

    public MyClassLoader(ClassLoader parent, String baseDir, String packageName) {
        super(parent);
        this.baseDir = baseDir;
        this.packageName = packageName;
        classNames = new ArrayList<>();
        classes = new ArrayList<>();
        init();
    }

    private void init() {
        try(Stream<Path> paths = Files.walk(Paths.get(baseDir))) {
            paths
                    .filter(Files::isRegularFile)
                    .forEach(file -> classNames.add(
                            file.toAbsolutePath().toString()
                                    .replace(baseDir, "")
                                    .replace(".class", "")
                                    .replace(File.separatorChar, '.')
                    ));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public ArrayList<Class<?>> loadAll() {
        classNames.forEach(
                className -> {
                    try {
                        classes.add(
                                loadClass(className)
                        );
                    } catch (ClassNotFoundException e) {
                        e.printStackTrace();
                    }
                }
        );
        return classes;
    }

    private Class getClass(String className) {
        String fileName = baseDir + className.replace('.', File.separatorChar) + ".class";
        byte[] b;
        try {
            b = loadClassFileData(fileName);
            Class clazz = defineClass(className, b, 0, b.length);
            resolveClass(clazz);
            return clazz;
        } catch (IOException | URISyntaxException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public Class<?> loadClass(String name) throws ClassNotFoundException {
        if(name.startsWith(packageName)) {
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
