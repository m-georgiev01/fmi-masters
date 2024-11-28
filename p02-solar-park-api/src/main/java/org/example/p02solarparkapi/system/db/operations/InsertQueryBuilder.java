package org.example.p02solarparkapi.system.db.operations;

import org.example.p02solarparkapi.system.db.QueryProcessor;

public class InsertQueryBuilder {
    private QueryProcessor queryProcessor;

    public InsertQueryBuilder(QueryProcessor queryProcessor, String tableName) {
        this.queryProcessor = queryProcessor;

        this.queryProcessor.initNewQueryOperation();
        this.queryProcessor.getQueryBuilder().append("INSERT INTO ").append(tableName);
    }

    public InsertQueryBuilder withValue(String columnName, Object value) {
        this.queryProcessor.setQueryColumnValuePair(columnName, value);
        return this;
    }

    public boolean insert() {
        String columnDefinition = String.join(",", this.queryProcessor.getColumnCollection());
        String valueDefinition = String.join(",", this.queryProcessor.getPlaceholderCollection());

        this.queryProcessor.getQueryBuilder().append(" (").append(columnDefinition).append(") ")
                .append("VALUES")
                .append(" (").append(valueDefinition).append(")");


        int resultCount = this.queryProcessor.processQuery();
        return resultCount == 1;
    }
}
