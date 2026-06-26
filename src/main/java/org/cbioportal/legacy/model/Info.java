package org.cbioportal.legacy.model;

import jakarta.validation.constraints.NotNull;
import java.io.Serializable;

public class Info implements Serializable {
  @NotNull private String portalVersion;
  @NotNull private String dbVersion;
  @NotNull private String derivedTableVersion;
  @NotNull private String genesetVersion;
  @NotNull private String geneTableVersion;
  @NotNull private String gitBranch;
  @NotNull private Boolean gitDirty;

  public String getGitBranch() {
    return this.gitBranch;
  }

  public void setGitBranch(String gitBranch) {
    this.gitBranch = gitBranch;
  }

  public Boolean getGitDirty() {
    return this.gitDirty;
  }

  public void isGitDirty(Boolean gitDirty) {
    this.gitDirty = gitDirty;
  }

  public String getPortalVersion() {
    return portalVersion;
  }

  public void setPortalVersion(String portalVersion) {
    this.portalVersion = portalVersion;
  }

  public String getDbVersion() {
    return dbVersion;
  }

  public void setDbVersion(String dbVersion) {
    this.dbVersion = dbVersion;
  }

  public String getDerivedTableVersion() {
    return derivedTableVersion;
  }

  public void setDerivedTableVersion(String derivedTableVersion) {
    this.derivedTableVersion = derivedTableVersion;
  }

  public String getGenesetVersion() {
    return genesetVersion;
  }

  public void setGenesetVersion(String genesetVersion) {
    this.genesetVersion = genesetVersion;
  }

  public String getGeneTableVersion() {
    return geneTableVersion;
  }

  public void setGeneTableVersion(String geneTableVersion) {
    this.geneTableVersion = geneTableVersion;
  }
}
