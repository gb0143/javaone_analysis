package com.dcservice.common.helpers;

import java.io.IOException;

import com.dcservice.all.base.BaseBaseClass;
import com.dcservice.common.enums.PagesTypes;

import sun.rmi.runtime.Log;

public class RedirectHelper extends BaseBaseClass {

  public static final Log log = LogFactory.getLog(RedirectHelper.class);
  public static final String ID_PARAMETER = "id";

  public static void goTo(PagesTypes type) {
    try {
      if (type != null) {
	sendRedirect(getLink(type));
      }
    } catch (Exception e) {
      log.error(e.getMessage(), e);
    }
  }

  public static void goTo(PagesTypes type, Long id) {
    try {
      if (type != null) {
	sendRedirect(getLink(type).concat(id.toString()));
      }
    } catch (Exception e) {
      log.error(e.getMessage(), e);
    }
  }

  public static void sendRedirect(String linkTemplate, Object... params) {
    String link = String.format(linkTemplate, params);
    sendRedirect(link);
  }

  public static String getLink(PagesTypes type) {
    return type.getPagesContext();
  }

  public static void sendRedirect(String url) {
    HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance()
	.getExternalContext().getRequest();
    String sb = new String("");
    sb = sb + (getServerURL()) + request.getContextPath();
    if (!url.startsWith("/")) {
      sb = sb + "/";
    }
    sb = sb + url;
    try {
      FacesContext.getCurrentInstance().getExternalContext().redirect(sb);
    } catch (IOException e) {
      log.error(e.getMessage(), e);
    }
  }

  public static String getServerURL() {
    return getServerURL(
	(HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest());
  }

  public static String getServerURL(HttpServletRequest request) {
    return request.getRequestURL().substring(0,
	request.getRequestURL().indexOf(request.getContextPath()));
  }

}
