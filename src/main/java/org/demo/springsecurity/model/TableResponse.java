package org.demo.springsecurity.model;

import lombok.Data;

@Data
public class TableResponse {

    private long totalRows;
    private Object data;

    public TableResponse(Object data, long totalRows) {
        this.data = data;
        this.totalRows = totalRows;
    }
}
