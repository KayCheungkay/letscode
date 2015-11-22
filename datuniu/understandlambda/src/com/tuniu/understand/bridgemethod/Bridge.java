package com.tuniu.understand.bridgemethod;

import java.util.Date;

/**
 * Created by nuc on 2015/11/20.
 */
public class Bridge {

    interface A<T> {
        void m(T t);
    }

    interface B extends A<String> {
        void m(String t);
    }

    static class BImpl implements B {
        @Override
        public void m(String t) {
            System.out.println("I'm BImpl");
        }
    }

    public static void main(String[] args) {
        B b = new BImpl();
        b.m("b");
        A a = (A)b;
        a.m(new Date());
    }
}
