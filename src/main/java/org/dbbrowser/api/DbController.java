package org.dbbrowser.api;


import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.apache.coyote.BadRequestException;
import org.dbbrowser.config.DatabaseProperties;
import org.dbbrowser.model.*;
import org.dbbrowser.service.DbService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/api/database/")
public class DbController {

    private final DbService dbService;

    public DbController(DbService dbService) {
        this.dbService = dbService;
    }

    @GetMapping(value = "/getAll", produces = "application/json; charset=utf-8")
    public ResponseEntity<Map<String, DatabaseProperties.DbConfig>> getAll() {
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_TYPE, "application/json; charset=utf-8")
                .body(dbService.getDatabaseConfigs());
    }

    @PostMapping(value = "/searchOwners", produces = "application/json; charset=utf-8")
    public ResponseEntity<List<String>> searchOwners(
            @Valid RequestEntity<String> request
    ) {
        List<String> res = dbService.searchOwners(request.getBody());

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_TYPE, "application/json; charset=utf-8")
                .body(res);
    }

    @PostMapping(value = "/searchTablesAndViews", produces = "application/json; charset=utf-8")
    public ResponseEntity<List<TableViewDTO>> searchTables(
            @Valid RequestEntity<SearchTableRequest> request
    ) throws BadRequestException {
        List<TableViewDTO> res = dbService.searchTablesAndViews(request.getBody());

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_TYPE, "application/json; charset=utf-8")
                .body(res);
    }


    @PostMapping(value = "/searchTableColumns", produces = "application/json; charset=utf-8")
    public ResponseEntity<List<ColumnDTO>> searchTableColumns(
            @Valid RequestEntity<TableColumnsRequest> request
    ) {
        List<ColumnDTO> res = dbService.searchTableColumns(request.getBody());

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_TYPE, "application/json; charset=utf-8")
                .body(res);
    }


    @PostMapping(value = "/getQueryView", produces = "application/json; charset=utf-8")
    public ResponseEntity<String> getQueryView(
            @Valid @RequestBody QueryViewRequest request
    ) {
        String res = dbService.getQueryView(request);

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_TYPE, "text/html; charset=utf-8")
                .body(res);
    }

}
