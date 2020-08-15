package com.mary.sharehouseproject.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User {
    private String email;
    private String name;
    private String gender;
    private String phone;
    private String role;
    private String account;

}
