package com.paywastex.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor

public class ZoneResponse {
    private String zoneCode;
    private String zoneName;
    private String description;
    private String active;
}

