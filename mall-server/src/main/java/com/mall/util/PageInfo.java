package com.mall.util;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class PageInfo {
    private Integer page;
    private Integer limit;

    PageInfo(Integer page, Integer limit) {
        this.page = page;
        this.limit = limit;
    }

    public int getOffset() {
        return (page - 1) * limit;
    }
}
