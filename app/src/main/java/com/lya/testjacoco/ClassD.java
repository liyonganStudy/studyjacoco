package com.lya.testjacoco;

/**
 * Created by hzliyongan on 2018/5/28.
 */

public class ClassD {
    int id;

    public String getName() {
        if (id == 0) {
            return "default";
        } else {
            return this.getClass().getName();
        }
    }

    public void add() {
        id++;
    }

}
