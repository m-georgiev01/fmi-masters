package org.example.p02solarparkapi.system.db;

import org.example.p02solarparkapi.system.db.operations.DeleteQueryBuilder;
import org.example.p02solarparkapi.system.db.operations.InsertQueryBuilder;
import org.example.p02solarparkapi.system.db.operations.SelectQueryBuilder;
import org.example.p02solarparkapi.system.db.operations.UpdateQueryBuilder;
import org.springframework.stereotype.Service;

@Service
public class QueryBuilder<T> {
    private final QueryProcessor<T> queryProcessor;


    public QueryBuilder(QueryProcessor<T> queryProcessor) {
        this.queryProcessor = queryProcessor;
    }

    //SELECT
    public SelectQueryBuilder<T> select(String ...cols) {
        return new SelectQueryBuilder<T>(queryProcessor, cols);
    }

    public SelectQueryBuilder<T> selectAll() {
        return new SelectQueryBuilder<T>(queryProcessor, "*");
    }

    //INSERT
    public InsertQueryBuilder into(String tableName) {
        return new InsertQueryBuilder(this.queryProcessor, tableName);
    }

    //UPDATE
    public UpdateQueryBuilder updateTable(String tableName) {
        return new UpdateQueryBuilder(this.queryProcessor, tableName);
    }

    //DELETE
    public DeleteQueryBuilder deleteTable(String tableName) {
        return new DeleteQueryBuilder(this.queryProcessor, tableName);
    }
}
