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
public class Faq {
    private int id;
    private String title;
    private String content;
    private Timestamp createDate;
}
