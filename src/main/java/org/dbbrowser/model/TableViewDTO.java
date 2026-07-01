package org.dbbrowser.model;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TableViewDTO {
    private String dbKey;
    private String url;
    private String user;
    private String owner;
    private String tableName;
}