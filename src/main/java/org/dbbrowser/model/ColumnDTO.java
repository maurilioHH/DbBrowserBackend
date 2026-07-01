package org.dbbrowser.model;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ColumnDTO {
    private Integer id;
    private String name;
    private String dataType;
    private Integer dataLength;
    private Integer dataPrecision;
    private Integer dataScale;
    private boolean nullable;
    private String dataDefault;
    private Long numDistinct;
    private String comments;
}