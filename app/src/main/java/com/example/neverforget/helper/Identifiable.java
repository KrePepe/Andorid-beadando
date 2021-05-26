package com.example.neverforget.helper;

abstract public class Identifiable {

    protected Integer id;

    public Identifiable() {
        this.id = getNextId();
    }

    public Integer getId() {
        return id;
    }

    protected static Integer index = 0;

    protected static Integer getNextId() {
        return ++index;
    }
}
