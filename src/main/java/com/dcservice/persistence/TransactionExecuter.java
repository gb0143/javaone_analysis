package com.dcservice.persistence;

import com.dcservice.all.base.BaseBaseClass;
import com.dcservice.persistence.dao.DaoManager;

public class TransactionExecuter extends BaseBaseClass {
    private static Object monitor = new Object();

    public static void execute(IAction action) throws Exception {
	execute(action, DaoManager.getSession());
    }

    public static synchronized void execute(IAction action, Session session) throws Exception {
	synchronized (monitor) {
	    Transaction tr = null;
	    try {
		tr = session.beginTransaction();

		action.execute(session);
	    } catch (Exception e) {
		if (tr != null && tr.getStatus() == TransactionStatus.ACTIVE) {
		    tr.rollback();
		}

		action.onException(e);
	    } finally {
		if (tr != null && tr.getStatus() == TransactionStatus.ACTIVE
			&& tr.getStatus() != TransactionStatus.ROLLED_BACK) {
		    try {
			tr.commit();
		    } catch (Exception e) {
			action.onException(e);
		    }
		}
		action.onExecuted();
	    }
	}
    }
}
