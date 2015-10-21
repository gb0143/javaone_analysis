package com.dcservice.common.helpers;

import static com.dcservice.common.helpers.ValidationHelper.isNullOrEmpty;

import java.text.MessageFormat;
import java.util.Locale;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

import com.dcservice.all.base.BaseBaseClass;

/**
 *
 * Resources helper class
 * 
 */
public class ResourcesHelper extends BaseBaseClass {
  private static final Logger logger = Logger.getLogger(ResourcesHelper.class);

  private static Locale locale = Locale.ENGLISH;

  private static final String MESSAGES = "Messages";

  private static final String ENUMS = "Enums";

  /**
   * 
   * @param bundleName
   * @param resourceId
   * @param params
   * @return
   */
  public static FacesMessage getMessage(String bundleName, String resourceId, Object[] params) {
    FacesContext context = FacesContext.getCurrentInstance();
    Application app = context.getApplication();
    String appBundle = app.getMessageBundle();
    Locale locale = ResourcesHelper.locale;
    ClassLoader loader = getClassLoader();
    String summary = getString(appBundle, bundleName, resourceId, locale, loader, params);
    if (summary == null) {
      summary = "???" + resourceId + "???";
    }
    String detail = getString(appBundle, bundleName, resourceId + "_detail", locale, loader,
	params);
    return new FacesMessage(summary, detail);
  }

  /**
   * 
   * @param bundle
   * @param resourceId
   * @param params
   * @return
   */
  public static String getString(String bundle, String resourceId, Object[] params) {
    String appBundle = FacesContext.getCurrentInstance().getApplication().getMessageBundle();
    ClassLoader loader = getClassLoader();
    return getString(appBundle, bundle, resourceId,
	FacesContext.getCurrentInstance().getApplication().getDefaultLocale(), loader, params);
  }

  public static String getEnums(String bundle, String resourceId, Object[] params) {
    String appBundle = FacesContext.getCurrentInstance().getApplication().getMessageBundle();
    ClassLoader loader = getClassLoader();
    return getString(appBundle, bundle, resourceId,
	FacesContext.getCurrentInstance().getApplication().getDefaultLocale(), loader, params);
  }

  public static String getString(String resourceId) {
    String str = getString(MESSAGES, resourceId, null);
    return isNullOrEmpty(str) ? String.format("??%s??", resourceId) : str;
  }

  public static String getEnum(String resourceId) {
    String str = getEnums(ENUMS, resourceId, null);
    return isNullOrEmpty(str) ? String.format("??%s??", resourceId) : str;
  }

  public static String getString(String resourceId, Object[] params) {
    String str = getString(MESSAGES, resourceId, params);
    return isNullOrEmpty(str) ? String.format("??%s??", resourceId) : str;
  }

  public static String getEnums(String resourceId, Object[] params) {
    String str = getString(ENUMS, resourceId, params);
    return isNullOrEmpty(str) ? String.format("??%s??", resourceId) : str;
  }

  /**
   * 
   * @param bundle1
   * @param bundle2
   * @param resourceId
   * @param locale
   * @param loader
   * @param params
   * @return
   */
  public static String getString(String bundle1, String bundle2, String resourceId, Locale locale,
      ClassLoader loader, Object[] params) {
    String resource = null;
    ResourceBundle bundle;

    if (bundle1 != null) {
      bundle = ResourceBundle.getBundle(bundle1, locale, loader);
      if (bundle != null) {
	try {
	  resource = bundle.getString(resourceId);
	} catch (MissingResourceException ex) {
	  logger.warn("Utils.getString : " + ex);
	}
      }
    }

    if (resource == null) {
      bundle = ResourceBundle.getBundle(bundle2, locale, loader);
      if (bundle != null) {
	try {
	  resource = bundle.getString(resourceId);
	} catch (MissingResourceException ex) {
	  logger.warn("Utils.getString : " + ex);
	}
      }
    }

    if (resource == null) {
      return null; // no match
    }
    if (params == null) {
      return resource;
    }

    MessageFormat formatter = new MessageFormat(resource, locale);
    return formatter.format(params);
  }

  public static String getEnums(String bundle1, String bundle2, String resourceId, Locale locale,
      ClassLoader loader, Object[] params) {
    String resource = null;
    ResourceBundle bundle;

    if (bundle1 != null) {
      bundle = ResourceBundle.getBundle(bundle1, locale, loader);
      if (bundle != null) {
	try {
	  resource = bundle.getString(resourceId);
	} catch (MissingResourceException ex) {
	  logger.warn("Utils.getString : " + ex);
	}
      }
    }

    if (resource == null) {
      bundle = ResourceBundle.getBundle(bundle2, locale, loader);
      if (bundle != null) {
	try {
	  resource = bundle.getString(resourceId);
	} catch (MissingResourceException ex) {
	  logger.warn("Utils.getString : " + ex);
	}
      }
    }

    if (resource == null) {
      return null; // no match
    }
    if (params == null) {
      return resource;
    }

    MessageFormat formatter = new MessageFormat(resource, locale);
    return formatter.format(params);
  }

  /**
  */
  public static ClassLoader getClassLoader() {
    ClassLoader loader = Thread.currentThread().getContextClassLoader();
    if (loader == null) {
      loader = ClassLoader.getSystemClassLoader();
    }
    return loader;
  }
}
