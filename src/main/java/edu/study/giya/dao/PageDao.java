package edu.study.giya.dao;

import edu.study.giya.dao.jpa.QueryResult;

public interface PageDao<T> {
    public <T> QueryResult<T> getScrollData(Class<T> entityClass, int firstindex, int maxresult);
}
