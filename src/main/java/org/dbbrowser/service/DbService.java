package org.dbbrowser.service;


import org.apache.coyote.BadRequestException;
import org.dbbrowser.config.DatabaseProperties;
import org.dbbrowser.model.ColumnDTO;
import org.dbbrowser.model.SearchTableRequest;
import org.dbbrowser.model.TableColumnsRequest;
import org.dbbrowser.model.TableViewDTO;

import java.util.List;
import java.util.Map;

public interface DbService {

    Map<String, DatabaseProperties.DbConfig> getDatabaseConfigs();

    List<String> searchOwners(String dbKey);

    List<TableViewDTO> searchTablesAndViews(SearchTableRequest request)  throws BadRequestException;

    List<ColumnDTO> searchTableColumns(TableColumnsRequest request);

}