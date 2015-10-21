package com.dcservice.persistence;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.imageio.spi.ServiceRegistry;
import javax.security.auth.login.Configuration;

import com.dcservice.all.base.BaseBaseClass;
import com.dcservice.persistence.models.FieldResponse;
import com.dcservice.persistence.models.Option;
import com.dcservice.persistence.models.fields.Field;
import com.dcservice.persistence.models.responses.Response;

public class HibernateUtil extends BaseBaseClass implements IConnectionManager {

  private static SessionFactory sessionFactory;

  private static HibernateUtil instance;

  private static ServiceRegistry serviceRegistry;

  private static List<IConnectionListner> connectionListners = new ArrayList<IConnectionListner>();

  public static void addAnnotatedClasses(Configuration config) {
    config.addAnnotatedClass(Field.class);
    config.addAnnotatedClass(Option.class);
    config.addAnnotatedClass(Response.class);
    config.addAnnotatedClass(FieldResponse.class);

  }

  public static List<String> getViewClasses() {
    List<String> viewClasses = new ArrayList<String>();

    return viewClasses;
  }

  public static void addConnectionListener(IConnectionListner listener) {
    connectionListners.add(listener);
  }

  public static void removeConnectionListener(IConnectionListner listener) {
    connectionListners.remove(listener);
  }

  public static synchronized SessionFactory getSessionFactory()
      throws ExceptionInInitializerError, Exception {
    if (sessionFactory == null) {
      createSessionFactory();
    }

    return sessionFactory;
  }

  public static HibernateUtil getInstance() {
    if (instance == null) {
      instance = new HibernateUtil();
    }

    return instance;
  }

  public static Map<String, String> connectionSettings = new HashMap<String, String>();

  private static void createSessionFactory() throws ExceptionInInitializerError, Exception {
    try {
      Date d1 = new Date();
      System.out.println("HibernateUtil: Opening DB connection.");
      Configuration config = new Configuration();
      HibernateUtil.addAnnotatedClasses(config);
      config.configure();

      Map<String, String> params = new HashMap<String, String>();

      params.put("host", String.valueOf(config.getProperties().get("hibernate.connection.host")));
      params.put("database",
	  String.valueOf(config.getProperties().get("hibernate.connection.database")));

      connectionSettings.put("url",
	  String.valueOf(config.getProperties().get("hibernate.connection.url")));
      connectionSettings.put("username", config.getProperty("hibernate.connection.username"));
      connectionSettings.put("password", config.getProperty("hibernate.connection.password"));

      config.getProperties().put("hibernate.connection.url", connectionSettings.get("url"));

      System.out.println("HOST: " + config.getProperty("hibernate.connection.host"));
      System.out.println("DB: " + config.getProperty("hibernate.connection.database"));
      System.out.println("USER: " + connectionSettings.get("username"));
      System.out.println("PASSWORD: " + connectionSettings.get("password"));

      serviceRegistry = new StandardServiceRegistryBuilder().applySettings(config.getProperties())
	  .build();

      sessionFactory = config.buildSessionFactory(serviceRegistry);

      checkConnection(d1);
    } catch (Exception ex) {
      onConnectionFail();
      System.err.println("Failed to create sessionFactory object." + ex);

      throw new Exception(ex);

    }
  }

  public static void checkConnection() {
    Session s = null;
    try {
      s = sessionFactory.openSession();
    } catch (Exception e) {
      e.printStackTrace();
    }
    s.beginTransaction().commit();
    SessionTracker.getInstance().sessionOpening("HibernateUtil");
    s.close();
    SessionTracker.getInstance().sessionClosing("HibernateUtil");
    s = null;

  }

  private static void checkConnection(Date d1) {
    try {
      checkConnection();
    } catch (Exception e) {
      System.out.println("Opening a DB connection... Failed");
      System.out.println(e);
      onConnectionFail();
      return;
    }

    System.out.println(String.format("Connection was successfully opened in %d seconds",
	(new Date().getTime() - d1.getTime()) / 1000));
    onSuccessConnection();
  }

  private static void onSuccessConnection() {
    if (connectionListners == null) {
      return;
    }
    for (IConnectionListner item : connectionListners) {
      item.fireConnetionEstablished();
    }
  }

  private static void onConnectionFail() {
    if (connectionListners == null) {
      return;
    }
    for (IConnectionListner item : connectionListners) {
      item.fireConnetionResufed();
    }
  }

  public static void shutdown() {
    onConnectionFail();
    // Close caches and connection pools
    if (sessionFactory != null) {
      sessionFactory.close();
      sessionFactory = null;
    }

  }

  @Override
  public void handleConfigFileChange() {
    HibernateUtil.shutdown();
    try {
      createSessionFactory();
    } catch (Exception e) {
      e.printStackTrace();
    }
    System.out.println("SessionFactory recreated");
  }

}
