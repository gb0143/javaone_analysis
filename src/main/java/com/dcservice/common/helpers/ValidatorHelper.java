package com.dcservice.common.helpers;

import com.dcservice.all.base.BaseBaseClass;

public class ValidatorHelper extends BaseBaseClass {

    private final static String ERROR_CLASS = "ui-state-error";

    public static void markNotValid(UIComponent component, String tmpMessage, FacesContext context) {
	setStyleClass(component, ERROR_CLASS);
    }

    private static void setStyleClass(UIComponent component, String styleClass) {
	if (component.getAttributes().get("styleClass") == null) {
	    component.getAttributes().put("styleClass", styleClass);
	} else {
	    component.getAttributes().put("styleClass",
		    String.format("%s %s",
			    component.getAttributes().get("styleClass").toString().replaceAll(styleClass, "").trim(),
			    styleClass));
	}
    }

    public static void markValid(UIComponent component, FacesContext context) {

	if (component == null) {
	    return;
	}

	if (component.getAttributes() != null && component.getAttributes().get("styleClass") != null) {
	    component.getAttributes().put("styleClass",
		    component.getAttributes().get("styleClass").toString().replaceAll(ERROR_CLASS, "").trim());
	}
	component.getAttributes().put("title", "");

	while (component.getParent() != null) {
	    component = component.getParent();
	}
    }

}
