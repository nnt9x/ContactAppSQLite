package com.bkacad.nnt.contactappsqlite.database;

import java.util.List;

public interface DAO <T>{
    public List<T> all();
    public T get(long id);
    public long create(T item);
    public int edit(T item);
    public int delete(T item);
}
