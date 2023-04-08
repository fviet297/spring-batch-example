package com.vietdp.spring.dto;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class ProductListDto {
    private List<DsEcomItem> dsEcomItem = new ArrayList<>();

    private String fileName;

    public void addProduct(DsEcomItem dsEcomItem) {
        this.dsEcomItem.add(dsEcomItem);
    }
}