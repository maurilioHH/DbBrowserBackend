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
public class QueryViewRequest {

    @NotNull(message = "databaseKey è obbligatoria")
    private String databaseKey;

    @NotNull(message = "owner è obbligatorio")
    private String owner;

    @NotNull(message = "viewName è obbligatorio")
    private String viewName;

}
