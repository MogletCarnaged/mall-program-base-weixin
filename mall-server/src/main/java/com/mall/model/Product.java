package com.mall.model;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
@Getter
@Setter
@NoArgsConstructor
public class Product {
    private Integer id;
    private String name;
    private String avatar;
    private BigDecimal price;
    private Integer categoryId;
    private String series;
    private Integer isDeleted;
}
