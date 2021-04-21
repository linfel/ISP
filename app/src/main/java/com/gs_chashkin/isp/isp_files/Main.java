package com.gs_chashkin.isp.isp_files;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class Main {

    public static void main(String[] args) {
        MyClass myClass = new MyClass();
        int number = myClass.getNumber();
        String name = null; //no getter =(
        System.out.println(number + name);//output 0null

        try {
            Field field = myClass.getClass().getDeclaredField("name");
            field.setAccessible(true);

            name = (String) field.get(myClass);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace();
        }
        System.out.println(number + name);//output 0default


        try {
            Field field = myClass.getClass().getDeclaredField("name");
            field.setAccessible(true);

            // for setters
            field.set(myClass, (String) "new value");
            name = (String) field.get(myClass);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace();//output 0new value
        }

        printData(myClass); // outout 0default
        try {
            Field field = myClass.getClass().getDeclaredField("name");
            field.setAccessible(true);
            field.set(myClass, (String) "new value");
            name = (String) field.get(myClass);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace();
        }
        printData(myClass);// output 0new value

        try {
            Class classs = MyClass.class;
            MyClass newinstance = (MyClass) classs.newInstance();
            Class[] params = {int.class, String.class};
            newinstance =(MyClass) classs.getConstructor(params).newInstance(1488, "Pupa");
        }
        catch (InstantiationException | IllegalAccessException | NoSuchMethodException | InvocationTargetException e ) {
            //;
        }

    }

    public static void printData(Object myClass){
        try {
            Method method = myClass.getClass().getDeclaredMethod("printData");
            method.setAccessible(true);
            method.invoke(myClass);
        } catch (NoSuchMethodException | InvocationTargetException | IllegalAccessException e) {
            e.printStackTrace();
        }
    }



}
