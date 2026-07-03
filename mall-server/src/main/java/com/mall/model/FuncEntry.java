package com.mall.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class FuncEntry {
    private Integer id;
    private String iconPath;
    private String textDesc;
    private Integer sortOrder;
    private Boolean isActive;
}