package org.dbbrowser.model;


import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SearchTableRequest {

    private String databaseName;
    private String owner;

    @NotNull(message = "tableName è obbligatorio")
    private String tableName;

    private String tipologia;

}
