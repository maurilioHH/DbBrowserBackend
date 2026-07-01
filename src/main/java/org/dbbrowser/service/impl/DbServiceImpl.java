package org.dbbrowser.service.impl;


import org.apache.coyote.BadRequestException;
import org.dbbrowser.config.DatabaseProperties;
import org.dbbrowser.model.ColumnDTO;
import org.dbbrowser.model.SearchTableRequest;
import org.dbbrowser.model.TableColumnsRequest;
import org.dbbrowser.model.TableViewDTO;
import org.dbbrowser.repository.OracleRepository;
import org.dbbrowser.service.DbService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class DbServiceImpl implements DbService {

    private final DatabaseProperties dbProperties;
    private final OracleRepository repository;

    public DbServiceImpl(DatabaseProperties dbProperties, OracleRepository repository) {
        this.dbProperties = dbProperties;
        this.repository = repository;
    }

    public Map<String, DatabaseProperties.DbConfig> getDatabaseConfigs() {
        return dbProperties.getDatasource();
    }

    @Override
    public List<String> searchOwners(String dbKey) {
        DbContextHolder.setCurrentDb(dbKey);
        List<String> list = repository.findOwners();
        return list;
    }

    @Override
    public List<TableViewDTO> searchTablesAndViews(SearchTableRequest request) throws BadRequestException {

        if(request.getDatabaseName() == null && request.getTableName() == null){
            throw new BadRequestException("Almeno uno dei parametri databaseName o tableName deve essere valorizzato.");
        }

        List<TableViewDTO> returnList = new java.util.ArrayList<>();

        if(request.getTipologie() != null ) {
            if (request.getTipologie().contains("T")) {
                returnList.addAll(searchTables(request));
            }
            if (request.getTipologie().contains("V")) {
                returnList.addAll(searchViews(request));
            }
        }
        return returnList;
    }


    @Override
    public List<ColumnDTO> searchTableColumns(TableColumnsRequest request) {
        try {
            DbContextHolder.setCurrentDb(request.getDatabaseKey());
            List<ColumnDTO> list = repository.searchTableColumns(request);
            return list;
        }
        finally {
            // FONDAMENTALE: Svuota sempre il contesto per evitare memory leak nei thread del server
            DbContextHolder.clear();
        }
    }


    private List<TableViewDTO>  searchTables(SearchTableRequest request){
        List<TableViewDTO> returnList = new java.util.ArrayList<>();
        try {
            if (request.getDatabaseName() != null) {
                DatabaseProperties.DbConfig dbConfig = dbProperties.getDatasource().get(request.getDatabaseName());
                DbContextHolder.setCurrentDb(request.getDatabaseName());
                String url = dbConfig.getUrl();
                String user = dbConfig.getUsername();

                List<TableViewDTO> list = repository.findTablesByNameLike(request);
                list.forEach(table -> {
                    table.setDbKey(request.getDatabaseName());
                    table.setUrl(url);
                    table.setUser(user);
                });
                returnList.addAll(list);
            } else {
                for (Map.Entry<String, DatabaseProperties.DbConfig> entry : dbProperties.getDatasource().entrySet()) {
                    String url = entry.getValue().getUrl();
                    String user = entry.getValue().getUsername();
                    String dbKey = entry.getKey();
                    DbContextHolder.setCurrentDb(dbKey);

                    List<TableViewDTO> list = repository.findTablesByNameLike(request);
                    list.forEach(table -> {
                        table.setDbKey(dbKey);
                        table.setUrl(url);
                        table.setUser(user);
                    });
                    returnList.addAll(list);
                }
            }
        } finally {
            // FONDAMENTALE: Svuota sempre il contesto per evitare memory leak nei thread del server
            DbContextHolder.clear();
        }
        return returnList;
    }

    private List<TableViewDTO> searchViews(SearchTableRequest request) {
        List<TableViewDTO> returnList = new java.util.ArrayList<>();
        try {
            if (request.getDatabaseName() != null) {
                DatabaseProperties.DbConfig dbConfig = dbProperties.getDatasource().get(request.getDatabaseName());
                DbContextHolder.setCurrentDb(request.getDatabaseName());
                String url = dbConfig.getUrl();
                String user = dbConfig.getUsername();

                List<TableViewDTO> list = repository.findViewsByNameLike(request);
                list.forEach(table -> {
                    table.setDbKey(request.getDatabaseName());
                    table.setUrl(url);
                    table.setUser(user);
                });
                returnList.addAll(list);
            } else {
                for (Map.Entry<String, DatabaseProperties.DbConfig> entry : dbProperties.getDatasource().entrySet()) {
                    String url = entry.getValue().getUrl();
                    String user = entry.getValue().getUsername();
                    String dbKey = entry.getKey();
                    DbContextHolder.setCurrentDb(dbKey);

                    List<TableViewDTO> list = repository.findViewsByNameLike(request);
                    list.forEach(table -> {
                        table.setDbKey(dbKey);
                        table.setUrl(url);
                        table.setUser(user);
                    });
                    returnList.addAll(list);
                }
            }
        } finally {
            // FONDAMENTALE: Svuota sempre il contesto per evitare memory leak nei thread del server
            DbContextHolder.clear();
        }
        return returnList;
    }


}