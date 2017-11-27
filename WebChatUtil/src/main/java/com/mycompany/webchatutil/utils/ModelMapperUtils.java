/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.webchatutil.utils;

import org.modelmapper.ModelMapper;

/**
 *
 * @author TuanTV
 */
public class ModelMapperUtils {
    
    private static final ModelMapper MODEL_MAPPER = new ModelMapper();

    private ModelMapperUtils() {
    }

    public static <T> T toObject(Object obj, Class<T> type) {
        T t = null;
        if (obj != null) {
            try {
                t = MODEL_MAPPER.map(obj, type);
            } catch (Exception ex) {
                ex.printStackTrace();
            }

        }
        return t;
    }
    
//    public static void main(String[] args) {
//        A a = new A("a");
//        B b = new B("b", "b1");
//        D d = new D(a);
//        
//        C c = toObject(d, C.class);
//        System.out.println(c);
//    }
//    
//    public static class D {
//        private A a;
//        private B b;
//
//        public D(A a) {
//            this.a = a;
//        }
//
//        public D(A a, B b) {
//            this.a = a;
//            this.b = b;
//        }
//
//        public A getA() {
//            return a;
//        }
//
//        public void setA(A a) {
//            this.a = a;
//        }
//
//        public B getB() {
//            return b;
//        }
//
//        public void setB(B b) {
//            this.b = b;
//        }
//        
//    }
//    
//    public static class A {
//        private String a;
//
//        public A(String a) {
//            this.a = a;
//        }
//
//        public String getA() {
//            return a;
//        }
//
//        public void setA(String a) {
//            this.a = a;
//        }
//        
//    }
//    
//    public static  class B {
//        private String b;
//        private String b1;
//
//        public B(String b, String b1) {
//            this.b = b;
//            this.b1 = b1;
//        }
//
//        public String getB() {
//            return b;
//        }
//
//        public void setB(String b) {
//            this.b = b;
//        }
//
//        public String getB1() {
//            return b1;
//        }
//
//        public void setB1(String b1) {
//            this.b1 = b1;
//        }
//        
//    }
//    
//    public static class C {
//        private String a;
//        private String b;
//
//        public String getA() {
//            return a;
//        }
//
//        public void setA(String a) {
//            this.a = a;
//        }
//
//        public String getB() {
//            return b;
//        }
//
//        public void setB(String b) {
//            this.b = b;
//        }
//
//        @Override
//        public String toString() {
//            return "C{" + "a=" + a + ", b=" + b + '}';
//        }
//    }
}
