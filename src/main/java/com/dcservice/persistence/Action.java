package com.dcservice.persistence;

public abstract class Action implements IAction {
	@Override
	public void onBeforeExecute() {
		return;
	}

	@Override
	public void onExecuted() {
		return;
	}

	@Override
	public void onException(Exception e) throws Exception {
		throw e;
	}

	@Override
	public void execute(Object obj) throws Exception {
		this.execute();
	}

	public abstract void execute() throws Exception;
}
