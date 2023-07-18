package com.thundergod.tutil.core;

public class TUtil {
    public static class ReworkOptional<T,K>{
        K val;
        T key;
        public ReworkOptional(T key,K val){
            this.key = key;
            this.val = val;
        }

        public T get() {
            return key;
        }
        public K getVal() {
            return val;
        }

        @Override
        public String toString() {
            return "ReworkOptional{" +
                    "key=" + key +
                    ", val=" + val +
                    '}';
        }
    }
}
