package com.dcservice.web;

import static com.dcservice.common.helpers.ValidationHelper.isNullOrEmpty;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import com.dcservice.common.helpers.ResourcesHelper;
import com.dcservice.common.helpers.ValidatorHelper;

import sun.rmi.runtime.Log;

public abstract class BaseValidationBean extends BasePageBean {

  public static final Log log = LogFactory.getLog(BaseValidationBean.class);

  private static final long serialVersionUID = 7678294618389233017L;

  private static final String LABEL_METHOD = "getLabel";

  private static final String REQUIRED_MESAGE = "requiredField";

  private List<String> exceptions = new ArrayList<>(0);

  private List<String> markedIvalidFields = new ArrayList<>(0);

  private boolean isFormValid = true;

  public void addFieldExeption(String id, String message) {
    id = fixComponentId(id);
    this.addFieldExeption(getComponentById(id), message);
    this.isFormValid = false;
  }

  public void addException(String message) {
    getExceptions().add(ResourcesHelper.getString(message));
    this.isFormValid = false;
  }

  public void addRequiredFieldExeption(String id) {
    id = fixComponentId(id);
    this.isFormValid = false;
    this.addRequiredFieldExeption(getComponentById(id));
  }

  public String fixComponentId(String id) {
    if (!id.contains(":")) {
      id = "form:" + id;
    }
    return id;
  }

  public UIComponent getComponentById(String id) {
    return getContext().getViewRoot().findComponent(id);

  }

  public void addFieldExeption(UIComponent component, String message) {
    if (component == null) {
      return;
    }
    getMarkedIvalidFields().add(completeId(component));
    String msg = ResourcesHelper.getString(message);
    ValidatorHelper.markNotValid(component, isNullOrEmpty(msg) ? message : msg, getContext());
    getExceptions().add(isNullOrEmpty(msg) ? message : msg);
  }

  private String completeId(UIComponent component) {
    String sb = new String();
    UIComponent parent = component.getParent();
    while (parent != null) {
      if (parent.getClass().equals(HtmlForm.class) || UIComponent.isCompositeComponent(parent)) {
	sb = sb + String.format("%s:", parent.getId());
      }

      parent = parent.getParent();
    }
    sb = sb + component.getId();
    return sb;
  }

  public void cleanValidation() {
    for (String id : getMarkedIvalidFields()) {
      try {
	ValidatorHelper.markValid(getComponentById(fixComponentId(id)), getContext());
      } catch (Exception e) {
	log.error(e.getMessage(), e);
      }
    }

    this.isFormValid = true;
    setExceptions(new ArrayList<String>());
  }

  public void addRequiredFieldExeption(UIComponent component) {
    if (component == null) {
      return;
    }
    try {
      Method method = null;

      try {
	method = component.getClass().getMethod(LABEL_METHOD, new Class[0]);
      } catch (Exception e) {
      }

      if (method == null) {
	try {
	  method = component.getClass().getDeclaredMethod(LABEL_METHOD, new Class[0]);
	} catch (Exception e) {
	}
      }

      if (method != null) {
	String label = (String) method.invoke(component, new Object[0]);
	if (!isNullOrEmpty(label)) {
	  String msg = String.format("%s %s", label, ResourcesHelper.getString("isRequiredField"));
	  getExceptions().add(msg);
	  this.markInvalid(component, REQUIRED_MESAGE);
	  return;
	}
      }

    } catch (Exception e) {
      log.error(e.getMessage(), e);
    }

    this.addFieldExeption(component, REQUIRED_MESAGE);
  }

  public void markInvalid(String id, String message) {
    id = fixComponentId(id);
    UIComponent component = getComponentById(id);
    markInvalid(component, message);
  }

  public void markInvalid(UIComponent component, String message) {
    getMarkedIvalidFields().add(completeId(component));
    this.isFormValid = false;
    ValidatorHelper.markNotValid(component, ResourcesHelper.getString(message), getContext());
  }

  public List<String> getExceptions() {
    return exceptions;
  }

  public void setExceptions(List<String> exceptions) {
    this.exceptions = exceptions;
  }

  public List<String> getMarkedIvalidFields() {
    return markedIvalidFields;
  }

  public void setMarkedIvalidFields(List<String> markedIvalidFields) {
    this.markedIvalidFields = markedIvalidFields;
  }

  public boolean isFormValid() {
    return isFormValid;
  }

  public void setFormValid(boolean isFormValid) {
    this.isFormValid = isFormValid;
  }

}
