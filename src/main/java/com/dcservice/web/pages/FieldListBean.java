package com.dcservice.web.pages;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import com.dcservice.common.enums.PagesTypes;
import com.dcservice.common.helpers.ListHelper;
import com.dcservice.common.helpers.RedirectHelper;
import com.dcservice.persistence.dao.DaoManager;
import com.dcservice.persistence.models.fields.Field;
import com.dcservice.persistence.models.fields.QField;
import com.dcservice.web.BasePageBean;
import com.ocpsoft.shade.org.apache.commons.logging.Log;
import com.ocpsoft.shade.org.apache.commons.logging.LogFactory;

@ManagedBean
@ViewScoped
public class FieldListBean extends BasePageBean implements Serializable {

	public transient final Log log = LogFactory.getLog(getClass());

	private List<Field> fields;

	@Override
	protected void onConstruct() {
		try {
			fields = DaoManager.query().from(QField.field).fetchAll()
					.list(QField.field);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
	}

	public void add() {
		RedirectHelper.goTo(PagesTypes.FIELD_NEW);
	}

	public void edit(Long id) {
		RedirectHelper.goTo(PagesTypes.FIELD_EDIT, id);
	}

	public void delete(Long id) throws Exception {
		Field field = DaoManager.query().from(QField.field)
				.where(QField.field.id.eq(id)).uniqueResult(QField.field);
		ListHelper.remove(fields, field);
		DaoManager.remove(field, true);
	}

	public List<Field> getFields() {
		return fields;
	}

	public void setFields(List<Field> fields) {
		this.fields = fields;
	}

}
