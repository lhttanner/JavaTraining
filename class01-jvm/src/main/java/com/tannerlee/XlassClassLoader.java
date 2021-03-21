package com.tannerlee;

import java.io.*;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class XlassClassLoader extends ClassLoader {


    public static void main(String[] args) {
        try {
            Object obj = new XlassClassLoader().findClass("Hello").newInstance();
            System.out.println(obj.getClass());
            Class<?> clazz = obj.getClass();
            Method method = clazz.getMethod("hello");
            method.invoke(clazz.newInstance());
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
    }

    protected Class<?> findClass(String name) throws ClassNotFoundException {
        //read xlass file
        byte[] xlassBytes = new byte[0];
        try {
            xlassBytes = readFile(this.getClass().getResource("/Hello.xlass").getPath());
        } catch (IOException e) {
            e.printStackTrace();
        }
        byte[] classBytes = decodeXlass(xlassBytes);
        return defineClass(name, classBytes, 0, classBytes.length);
    }

    private byte[] decodeXlass(byte[] xlassBytes) {
        byte[] classBytes = new byte[xlassBytes.length];
        byte temp;
        for (int i = 0; i < xlassBytes.length; i++) {
            temp = xlassBytes[i];
            classBytes[i] = (byte) ~temp;
        }
        return classBytes;
    }


    public static byte[] readFile(String filename) throws IOException {
        File f = new File(filename);
        if (!f.exists()) {
            throw new FileNotFoundException(filename);
        }
        ByteArrayOutputStream bos = new ByteArrayOutputStream((int) f.length());
        BufferedInputStream in = null;
        try {
            in = new BufferedInputStream(new FileInputStream(f));
            int buf_size = 1024;
            byte[] buffer = new byte[buf_size];
            int len = 0;
            while (-1 != (len = in.read(buffer, 0, buf_size))) {
                bos.write(buffer, 0, len);
            }
            return bos.toByteArray();
        } catch (IOException e) {
            e.printStackTrace();
            throw e;
        } finally {
            try {
                in.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            bos.close();
        }
    }


}
