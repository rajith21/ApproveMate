package com.am.approvemate.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class LabelData {

    @JsonProperty("ItemType")
    private String itemType;

    @JsonProperty("LabelType")
    private String labelType;

    @JsonProperty("PrinterName")
    private String printerName;

    @JsonProperty("zpl_format")
    private String zplFormat;

    @JsonProperty("WorkArea")
    private String workArea;

    // Getters and Setters
    public String getItemType() {
        return itemType;
    }

    public void setItemType(String itemType) {
        this.itemType = itemType;
    }

    public String getLabelType() {
        return labelType;
    }

    public void setLabelType(String labelType) {
        this.labelType = labelType;
    }

    public String getPrinterName() {
        return printerName;
    }

    public void setPrinterName(String printerName) {
        this.printerName = printerName;
    }

    public String getZplFormat() {
        return zplFormat;
    }

    public void setZplFormat(String zplFormat) {
        this.zplFormat = zplFormat;
    }

    public String getWorkArea() {
        return workArea;
    }

    public void setWorkArea(String workArea) {
        this.workArea = workArea;
    }
}
