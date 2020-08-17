package com.mary.sharehouseproject.model;

import com.google.firebase.Timestamp;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ted.gun0912.clustering.clustering.TedClusterItem;
import ted.gun0912.clustering.geometry.TedLatLng;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder

public class House implements TedClusterItem {
    private int id;
    private String discountType;
    private String nearStation;
    private int houseNumber;
    private double lat;
    private double lng;
    private String area;
    private String gender;
    private String reservation;
    private String houseForm;
    private Timestamp createDate;

    @Override
    public TedLatLng getTedLatLng() {
        return new TedLatLng(lat,lng);
    }
}
