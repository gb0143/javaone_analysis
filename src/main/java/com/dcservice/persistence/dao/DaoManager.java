package com.dcservice.persistence.dao;

import java.util.Date;

import javax.faces.context.FacesContext;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.resource.transaction.spi.TransactionStatus;

import com.dcservice.persistence.PersistenceSessionManager;
import com.dcservice.persistence.models.base.BaseModel;
import com.mysema.query.jpa.hibernate.HibernateQuery;
import com.ocpsoft.shade.org.apache.commons.logging.Log;
import com.ocpsoft.shade.org.apache.commons.logging.LogFactory;

public class DaoManager {

	public final static Log log = LogFactory.getLog(DaoManager.class);

	public static Session getSession() throws Exception {
		if (FacesContext.getCurrentInstance() == null) {
			throw new Exception(
					"DaoManager: 44; \"FacesContext.getCurrentInstance() is null\"");
		}
		return PersistenceSessionManager.getBean().getSession();
	}

	public static HibernateQuery query() throws Exception {
		return new HibernateQuery(getSession());
	}

	public static void save(BaseModel object) {
		save(object, false);
	}

	public static void save(BaseModel object, boolean beginTransaction) {
		Transaction tr = null;
		try {
			if (beginTransaction) {
				tr = DaoManager.getSession().beginTransaction();
			}
			beforeSaveOrUpdate(object);

			if (getSession().contains(object)) {
				getSession().update(object);
			} else {
				getSession().saveOrUpdate(object);
			}
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			if (beginTransaction) {
				if (tr != null && tr.getStatus() == TransactionStatus.ACTIVE) {
					tr.rollback();

					try {
						if (getSession().isOpen()) {
							getSession().close();
						}

					} catch (Exception ex) {
						log.error(ex.getMessage(), ex);
					}
				}
			}
		} finally {
			if (beginTransaction) {
				if (tr != null && tr.getStatus() == TransactionStatus.ACTIVE
						&& tr.getStatus() != TransactionStatus.ROLLED_BACK) {
					tr.commit();
				}
			}
		}
	}

	public static void beforeSaveOrUpdate(BaseModel object) {
		if (object.getCreatedDate() == null) {
			object.setCreatedDate(new Date());
		} else {
			object.setUpdatedDate(new Date());
		}
	}

	public static <T extends BaseModel> void remove(T object) {
		remove(object, false);
	}

	public static <T extends BaseModel> void remove(T object,
			boolean beginTransaction) {
		Transaction tr = null;
		try {
			if (beginTransaction) {
				tr = DaoManager.getSession().beginTransaction();
			}

			getSession().delete(object);

		} catch (Exception e) {
			log.error(e);
			if (beginTransaction) {
				if (tr != null && tr.getStatus() == TransactionStatus.ACTIVE) {
					tr.rollback();
				}
			}
		} finally {
			if (beginTransaction) {
				if (tr != null && tr.getStatus() == TransactionStatus.ACTIVE
						&& tr.getStatus() != TransactionStatus.ROLLED_BACK) {
					tr.commit();
				}
			}
		}
	}

}
