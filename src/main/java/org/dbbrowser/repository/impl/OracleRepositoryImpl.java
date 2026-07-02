package org.dbbrowser.repository.impl;

import org.dbbrowser.model.*;
import org.dbbrowser.repository.OracleRepository;
import org.dbbrowser.utils.Constants;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class OracleRepositoryImpl implements OracleRepository {

    private final JdbcClient jdbcClient;

    public OracleRepositoryImpl(JdbcClient jdbcClient) {
        this.jdbcClient = jdbcClient;
    }

    @Override
    public List<String> findOwners() {
        String sql = """
                    SELECT DISTINCT owner FROM all_tables
                    WHERE owner NOT IN (:systemOwners)
                    ORDER BY 1
                """;
        return jdbcClient.sql(sql)
                .param("systemOwners", Constants.systemOwners)
                .query((rs, rowNum) -> rs.getString("owner"))
                .list();
    }


    @Override
    public List<TableViewDTO> findTablesByNameLike(SearchTableRequest request) {
        StringBuilder sqlBuilder = new StringBuilder();
        sqlBuilder.append("""
                    SELECT owner, table_name 
                    FROM all_tables 
                    WHERE 1=1
                """);
        sqlBuilder.append(" AND owner NOT IN (:systemOwners) ");

        if (request.getOwner() != null && !request.getOwner().isEmpty()) {
            sqlBuilder.append(" AND owner = :owner ");
        }
        if (request.getTableName() != null && !request.getTableName().isEmpty()) {
            sqlBuilder.append(" AND lower(table_name) LIKE lower(:tableName) ");
        }
        sqlBuilder.append(" order by owner, table_name ");

        // PARAMS
        var statement = jdbcClient.sql(sqlBuilder.toString())
           .param("systemOwners", Constants.systemOwners);

        if (request.getOwner() != null && !request.getOwner().isEmpty()) {
            statement = statement.param("owner", request.getOwner());
        }
        if (request.getTableName() != null && !request.getTableName().isEmpty()) {
            statement = statement.param("tableName", "%" + request.getTableName() + "%");
        }

        // Eseguiamo la query e mappiamo i risultati
        return statement.query((rs, rowNum) ->
                        new TableViewDTO(
                                null,
                                null,
                                null,
                                rs.getString("owner"),
                                rs.getString("table_name"),
                                "T"
                        ))
                .list();
    }

    @Override
    public List<TableViewDTO> findViewsByNameLike(SearchTableRequest request) {
        StringBuilder sqlBuilder = new StringBuilder();
        sqlBuilder.append("""
                    SELECT owner, view_name 
                    FROM all_views 
                    WHERE 1=1
                """);
        sqlBuilder.append(" AND owner NOT IN (:systemOwners) ");

        if (request.getOwner() != null && !request.getOwner().isEmpty()) {
            sqlBuilder.append(" AND owner = :owner ");
        }
        if (request.getTableName() != null && !request.getTableName().isEmpty()) {
            sqlBuilder.append(" AND lower(view_name) LIKE lower(:view_name) ");
        }
        sqlBuilder.append(" order by owner, view_name ");

        // PARAMS
        var statement = jdbcClient.sql(sqlBuilder.toString())
                .param("systemOwners", Constants.systemOwners);

        if (request.getOwner() != null && !request.getOwner().isEmpty()) {
            statement = statement.param("owner", request.getOwner());
        }
        if (request.getTableName() != null && !request.getTableName().isEmpty()) {
            statement = statement.param("view_name", "%" + request.getTableName() + "%");
        }

        // Eseguiamo la query e mappiamo i risultati
        return statement.query((rs, rowNum) ->
                        new TableViewDTO(
                                null,
                                null,
                                null,
                                rs.getString("owner"),
                                rs.getString("view_name"),
                                "V"
                        ))
                .list();
    }


    @Override
    public List<ColumnDTO> searchTableColumns(TableColumnsRequest request) {
        String sql = """
                SELECT C.COLUMN_ID, C.COLUMN_NAME, C.DATA_TYPE, C.DATA_LENGTH, C.DATA_PRECISION, C.DATA_SCALE, C.NULLABLE, 
                    C.DATA_DEFAULT, C.NUM_DISTINCT, COM.COMMENTS
                FROM all_tab_columns c
                JOIN all_col_comments com 
                  ON c.owner = com.owner 
                 AND c.table_name = com.table_name 
                 AND c.column_name = com.column_name
                WHERE c.table_name = :tableName
                AND c.owner = :owner
                ORDER BY column_id
                """;

        return jdbcClient.sql(sql)
                .param("tableName", request.getTableName())
                .param("owner", request.getOwner())
                .query((rs, rowNum) ->
                        new ColumnDTO(
                                rs.getInt("COLUMN_ID"),
                                rs.getString("COLUMN_NAME"),
                                rs.getString("DATA_TYPE"),
                                rs.getInt("DATA_LENGTH"),
                                rs.getInt("DATA_PRECISION"),
                                rs.getInt("DATA_SCALE"),
                                rs.getString("NULLABLE").equals("Y"),
                                rs.getString("DATA_DEFAULT"),
                                rs.getLong("NUM_DISTINCT"),
                                rs.getString("COMMENTS")
                        ))
                .list();
    }

    @Override
    public String getQueryView(QueryViewRequest request) {
        String sql = """
                SELECT text 
                FROM all_views 
                WHERE owner = :owner
                AND view_name = :view_name
                """;
        return jdbcClient.sql(sql)
                .param("owner", request.getOwner())
                .param("view_name", request.getViewName())
                .query((rs, rowNum) -> rs.getString("text"))
                .single();

    }

}