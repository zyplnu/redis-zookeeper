package com.redis.pojo;

public class Test {

    static {
        System.out.println("static second block inited...");
    }

    static {
        System.out.println("static block inited...");
    }



    public Test(){
        System.out.println("constructor inited...");
    }

    {
        System.out.println("non-static second block inited...");
    }

    {
        System.out.println("non-static block inited...");
    }


    public static void main(String[] args) {
        Test test = new Test();
        Test test1 = new Test();
    }

}
