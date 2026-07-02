package org.dbbrowser.service;


import org.apache.coyote.BadRequestException;
import org.dbbrowser.config.DatabaseProperties;
import org.dbbrowser.model.*;

import java.util.List;
import java.util.Map;

public interface DbService {

    Map<String, DatabaseProperties.DbConfig> getDatabaseConfigs();

    List<String> searchOwners(String dbKey);

    List<TableViewDTO> searchTablesAndViews(SearchTableRequest request)  throws BadRequestException;

    List<ColumnDTO> searchTableColumns(TableColumnsRequest request);

    String getQueryView(QueryViewRequest request);

}