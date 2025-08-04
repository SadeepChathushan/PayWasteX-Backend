package com.paywastex.dto.request;

import jakarta.persistence.Column;
import lombok.Data;

@Data
public class AddZoneRequest {

    private String zoneCode;
    private String zoneName;
    private String description;

//    public String getZoneCode() {return zoneCode;}
//    public String getZoneName() {
//        return zoneName;
//    }
//    public String getDescription() {return description;}
//
//    public void setZoneCode(String zoneCode) {
//        this.zoneCode = zoneCode;
//    }
//    public void setZoneName(String zoneName) {
//        this.zoneName = zoneName;
//    }
//    public void setDescription(String description) {
//        this.description = description;
//    }
}
