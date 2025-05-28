package com.am.approvemate.model;

import jdk.jfr.DataAmount;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Library {
    public String plsTechnologyName;
    public String versionedArtifact;
    public String groupid;
    public String name;
    public String version;
    public String language;
    public String swType;
    public String copyright_holder;
    public String vendorLatestVersion;
    public String plsLatestVersion;
    public String sha1cksum;
    public String techReleaseDate;
    public boolean competitive;
    public boolean cryptoFunction;
    public boolean banned;
    public boolean sourceExists;
    public boolean builtAtOracle;
}
