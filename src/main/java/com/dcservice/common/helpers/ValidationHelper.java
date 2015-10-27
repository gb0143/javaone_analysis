package com.dcservice.common.helpers;

import java.util.Collection;
import java.util.Date;

import javax.persistence.Persistence;

import org.hibernate.Session;

import com.dcservice.all.base.BaseBaseClass;
import com.dcservice.common.exceptions.PersistenceBeanException;
import com.dcservice.persistence.PersistenceSession;
import com.dcservice.persistence.models.base.BaseModel;
import com.ocpsoft.shade.org.apache.commons.logging.Log;
import com.ocpsoft.shade.org.apache.commons.logging.LogFactory;

public class ValidationHelper  extends BaseBaseClass  {

	public transient final static Log log = LogFactory
			.getLog(ValidationHelper.class);

	public static boolean isNullOrEmpty(String str) {
		return str == null || str.trim().isEmpty();
	}

	public static boolean isNullOrEmpty(Object str) {
		return str == null;
	}

	public static boolean isNullOrEmpty(Object[] mas) {
		return mas == null || mas.length == 0;
	}

	public static boolean isNullOrEmpty(Boolean str) {
		return str == null;
	}

	public static boolean isNullOrEmpty(Date str) {
		return str == null;
	}

	public static boolean isNullOrEmpty(Collection<?> collection) {
		return collection == null || collection.isEmpty();
	}

	public static boolean isNullOrEmpty(Long str) {
		return str == null || str == 0l;
	}

	public static boolean isNullOrEmpty(Integer str) {
		return str == null || str == 0;
	}

	public static boolean isNullOrEmpty(Double str) {
		return str == null || str == 0.0;
	}

	public static boolean isNullOrEmpty(BaseModel obj) {
		return obj == null || isNullOrEmpty(obj.getId());
	}
	
	public static boolean isEquals(Double obj1, Double  obj2) {
		return obj1 == obj2;
	}
	
	public static boolean isEquals(Float obj1, Float  obj2) {
		return obj1 == obj2;
	}
	
	public static boolean isEquals(int obj1, int  obj2) {
		return obj1 == obj2;
	}
	
	public static Session checkSession(Session session) throws PersistenceBeanException{
		if(session == null){
			return PersistenceSession.getSessionWithCheck();
		}
		return session;
	}

}
