package org.example.p02solarparkapi.system.db.operations;

import org.example.p02solarparkapi.system.db.QueryProcessor;

public class UpdateQueryBuilder extends WhereQueryBuilder<UpdateQueryBuilder> {
    private final QueryProcessor queryProcessor;

    public UpdateQueryBuilder(QueryProcessor queryProcessor, String tableName) {
        super(queryProcessor);
        this.queryProcessor = queryProcessor;

        this.queryProcessor.initNewQueryOperation();
        this.queryProcessor.getQueryBuilder().append("UPDATE ").append(tableName).append(" SET ");
    }

    public UpdateQueryBuilder set(String columnName, Object value) {
        if (!this.queryProcessor.getColumnCollection().isEmpty()){
            this.queryProcessor.getQueryBuilder().append(",");
        }

        this.queryProcessor.buildColumnValuePair(columnName);
        this.queryProcessor.setQueryColumnValuePair(columnName, value);

        return this;
    }

    public int update() {
        return this.queryProcessor.processQuery();
    }
}
