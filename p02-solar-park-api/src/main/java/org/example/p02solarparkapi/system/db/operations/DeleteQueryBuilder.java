package org.example.p02solarparkapi.system.db.operations;

import org.example.p02solarparkapi.system.db.QueryProcessor;

public class DeleteQueryBuilder extends WhereQueryBuilder<DeleteQueryBuilder> {
    private QueryProcessor queryProcessor;

    public DeleteQueryBuilder(QueryProcessor queryProcessor, String tableName) {
        super(queryProcessor);
        this.queryProcessor = queryProcessor;

        this.queryProcessor.initNewQueryOperation();
        this.queryProcessor.getQueryBuilder().append("DELETE FROM ").append(tableName);
    }

    public int delete() {
        return this.queryProcessor.processQuery();
    }
}
