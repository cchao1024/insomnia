package com.cchao.insomnia.util;

import org.springframework.data.domain.Sort;

public class SortHelper {

    public static Sort basicSortId() {
        return basicUpSort("id");
    }

    public static Sort basicUpSort(String orderField) {
        Sort sort = new Sort(Sort.Direction.ASC, orderField);
        return sort;
    }

    public static Sort basicDownSort(String orderField) {
        Sort sort = new Sort(Sort.Direction.DESC, orderField);
        return sort;
    }
}
