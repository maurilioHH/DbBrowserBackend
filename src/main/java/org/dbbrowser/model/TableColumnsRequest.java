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
public class TableColumnsRequest {

    @NotNull(message = "databaseKey è obbligatoria")
        private String databaseKey;

    @NotNull(message = "owner è obbligatorio")
    private String owner;

    @NotNull(message = "tableName è obbligatorio")
    private String tableName;

}
