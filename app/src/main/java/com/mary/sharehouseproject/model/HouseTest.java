package com.mary.sharehouseproject.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ted.gun0912.clustering.clustering.TedClusterItem;
import ted.gun0912.clustering.geometry.TedLatLng;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class HouseTest implements TedClusterItem {
    private double locationX;
    private double locationY;

    @Override
    public TedLatLng getTedLatLng() {
        return new TedLatLng(locationX,locationY);
    }
}
