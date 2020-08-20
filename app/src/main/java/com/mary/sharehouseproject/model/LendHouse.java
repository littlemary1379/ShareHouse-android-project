package com.mary.sharehouseproject.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LendHouse {
    private String address;
    private int houseNumber;
    private String imageUri;
    private String roomId;
}
