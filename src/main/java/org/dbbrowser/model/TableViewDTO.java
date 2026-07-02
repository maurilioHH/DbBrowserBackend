package org.dbbrowser.model;


import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
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
    private String name;
    private String type;
}