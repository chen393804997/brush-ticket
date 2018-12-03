package com.czw.brushticket.utils;

/**
 * @author: czw
 * @Date: 2018-12-03 17:39
 **/
public class Result<T> {
    public boolean success;
    public T value;

    public Result() {}

    public Result(boolean success) {
        this.success = success;
    }

    public Result(boolean success, T value) {
        this.success = success;
        this.value = value;
    }
}
