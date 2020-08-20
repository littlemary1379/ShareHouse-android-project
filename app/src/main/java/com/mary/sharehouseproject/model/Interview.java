package com.mary.sharehouseproject.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Interview {
    private int id;
    private String imageUri;
    private String title;
    private String interviewee;
    private String blogUri;
}
