package com.am.approvemate.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@JsonIgnoreProperties(ignoreUnknown = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class LicensingRecord {

    @JsonProperty("ltID")
    private String ltID;

    @JsonProperty("baID")
    private String baID;

    @JsonProperty("Products (Scope of BA)")
    private String productsScopeOfBA;

    @JsonProperty("initialUseCase")
    private String initialUseCase;

}
