package org.demo.springsecurity.model;

import lombok.Data;

@Data
public class GetAllUsersModel {

    private int page;
    private int pageSize;
    private String sortOrder;
    private String sortField;
    private String globalFilter;

}
