package org.dbbrowser.repository;

import org.dbbrowser.model.*;

import java.util.List;

public interface OracleRepository {

    List<String> findOwners();

    List<TableViewDTO> findTablesByNameLike(SearchTableRequest request);

    List<TableViewDTO> findViewsByNameLike(SearchTableRequest request);

    List<ColumnDTO> searchTableColumns(TableColumnsRequest request);

    String getQueryView(QueryViewRequest request);
}
