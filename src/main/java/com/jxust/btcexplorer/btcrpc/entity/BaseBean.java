package com.jxust.btcexplorer.btcrpc.entity;

public class BaseBean<T> {
    private long id;
    private Object error;
    private T result;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Object getError() {
        return error;
    }

    public void setError(Object error) {
        this.error = error;
    }

    public T getResult() {
        return result;
    }

    public void setResult(T result) {
        this.result = result;
    }
}
