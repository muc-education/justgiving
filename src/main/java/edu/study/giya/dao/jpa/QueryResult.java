package edu.study.giya.dao.jpa;

import java.util.List;

public class QueryResult<T> {

    private List<T> result;

    private long size;

    public List<T> getResult() {
        return result;
    }

    public void setResult(List<T> result) {
        this.result = result;
    }

    public long getSize() {
        return size;
    }

    public void setSize(long size) {
        this.size = size;
    }
}