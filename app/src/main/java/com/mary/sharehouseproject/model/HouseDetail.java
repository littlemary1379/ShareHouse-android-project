package com.mary.sharehouseproject.model;

import com.google.firebase.Timestamp;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class HouseDetail {
    private String imageUri;
    private String content;
    private String hashTag;
    private String address;
    private Timestamp contractEndDate;
    private int maxPerson;
    private String construction;
    private String constImg;
}
