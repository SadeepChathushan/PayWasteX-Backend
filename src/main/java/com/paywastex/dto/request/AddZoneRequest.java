package com.paywastex.dto.request;

import jakarta.persistence.Column;
import lombok.Data;

@Data
public class AddZoneRequest {

    private String zoneCode;
    private String zoneName;
    private String description;

}
