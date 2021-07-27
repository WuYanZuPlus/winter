package com.jianghu.winter.singleton;

public class Singleton7 {
    private Singleton7() {
    }

    public static Singleton7 getInstance() {
        return SingletonEnum.INSTANCE.getInstance();
    }

    enum SingletonEnum {
        INSTANCE;
        private Singleton7 singleton;

        /**
         * JVM保证这个方法绝对只调用一次
         */
        SingletonEnum() {
            singleton = new Singleton7();
        }

        public Singleton7 getInstance() {
            return singleton;
        }
    }
}