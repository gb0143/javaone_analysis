package com.dcservice.web.common;

import com.dcservice.persistence.dao.DaoManager;
import com.dcservice.web.BasePageBean;

@ManagedBean
@SessionScoped
public class HeaderBean extends BasePageBean {

  private static final long serialVersionUID = 7844098857850505763L;

  private String icon;

  static int responsesCount;

  static {
    responsesCount = 0;
  }

  @Override
  protected void onConstruct() {
    try {
      updateResponsesCount();
    } catch (Exception e) {
      e.printStackTrace();
    }
    icon = "http://softarex.com/wp-content/themes/softarex/img/logo.svg";
  }

  public static void updateResponsesCount() throws Exception {
    responsesCount = (int) DaoManager.query().from(QResponse.response).count();
  }

  @Override
  protected boolean isPageBean() {
    return false;
  }

  public String getIcon() {
    return icon;
  }

  public void setIcon(String icon) {
    this.icon = icon;
  }

}
