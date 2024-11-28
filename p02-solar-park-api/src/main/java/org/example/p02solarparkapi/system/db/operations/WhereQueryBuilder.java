package org.example.p02solarparkapi.system.db.operations;

import org.example.p02solarparkapi.system.db.QueryProcessor;

public class WhereQueryBuilder<T> {
    private QueryProcessor queryProcessor;

    public WhereQueryBuilder(QueryProcessor queryProcessor) {
        this.queryProcessor = queryProcessor;
    }

    public T where(String columnName, String operator, Object value) {
        this.queryProcessor.getQueryBuilder().append(" WHERE ");
        this.queryProcessor.buildColumnValuePair(columnName, operator);

        this.queryProcessor.setQueryColumnValuePair(columnName, value);
        return (T) this;
    }

    public T where(String columnName, Object value) {
        return this.where(columnName, "=", value);
    }

    public T andWhere (String columnName, String operator, Object value) {
        this.queryProcessor.getQueryBuilder().append(" AND ( ");
        this.queryProcessor.buildColumnValuePair(columnName, operator);
        this.queryProcessor.getQueryBuilder().append(" ) ");
        this.queryProcessor.setQueryColumnValuePair(columnName, value);
        return (T) this;
    }

    public T andWhere (String columnName, Object value) {
        return this.andWhere(columnName, " = ", value);
    }

    public T orWhere (String columnName, String operator, Object value) {
        this.queryProcessor.getQueryBuilder().append(" OR ( ");
        this.queryProcessor.buildColumnValuePair(columnName, operator);
        this.queryProcessor.getQueryBuilder().append(" ) ");
        this.queryProcessor.setQueryColumnValuePair(columnName, value);
        return (T) this;
    }

    public T orWhere (String columnName, Object value) {
        return this.orWhere(columnName, " = ", value);
    }
}
