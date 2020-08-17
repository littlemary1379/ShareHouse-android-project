package com.mary.sharehouseproject.model;



import com.google.firebase.Timestamp;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Room {
    private String roomName;
    private String gender;
    private String type;
    private String roomArea;
    private String deposit;
    private String monthly;
    private Timestamp moveInDate;

}
