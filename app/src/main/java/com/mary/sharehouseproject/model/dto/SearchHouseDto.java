package com.mary.sharehouseproject.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SearchHouseDto {
    private int id;
    private int houseId;
    private String address;
    private double lat;
    private double lng;
}
