package com.dcservice.common.enums;

import com.dcservice.common.helpers.ResourcesHelper;

public enum PagesTypes {

  TEST("/"), FIELD_LIST("/fields"), FIELD_NEW("/fields/new"), FIELD_EDIT("/fields/"), RESPONSES(
      "/responses");

  private String page;

  public String getName() {
    switch (this) {
    case TEST:
      return ResourcesHelper.getEnum("TEST");
    case FIELD_LIST:
      return ResourcesHelper.getEnum("FIELD_LIST");
    case FIELD_NEW:
      return ResourcesHelper.getEnum("FIELD_NEW");
    case FIELD_EDIT:
      return ResourcesHelper.getEnum("FIELD_EDIT");
    case RESPONSES:
      return ResourcesHelper.getEnum("RESPONSES");
    default:
      return "";
    }
  }

  private PagesTypes(String page) {
    this.page = page;
  }

  public String getPage() {
    return page;
  }

  public void setPage(String page) {
    this.page = page;
  }

  public String getPagesContext() {
    return page;
  }

}
