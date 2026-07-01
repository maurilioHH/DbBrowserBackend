package org.dbbrowser.repository;

import org.dbbrowser.model.ColumnDTO;
import org.dbbrowser.model.SearchTableRequest;
import org.dbbrowser.model.TableColumnsRequest;
import org.dbbrowser.model.TableViewDTO;

import java.util.List;

public interface OracleRepository {

    List<String> findOwners();

    List<TableViewDTO> findTablesByNameLike(SearchTableRequest request);

    List<TableViewDTO> findViewsByNameLike(SearchTableRequest request);

    List<ColumnDTO> searchTableColumns(TableColumnsRequest request);
}
