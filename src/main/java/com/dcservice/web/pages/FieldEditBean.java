package com.dcservice.web.pages;

import static com.dcservice.common.helpers.ValidationHelper.isNullOrEmpty;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import com.dcservice.common.enums.PagesTypes;
import com.dcservice.common.exceptions.PersistenceBeanException;
import com.dcservice.common.helpers.ListHelper;
import com.dcservice.common.helpers.RedirectHelper;
import com.dcservice.persistence.Action;
import com.dcservice.persistence.TransactionExecuter;
import com.dcservice.persistence.dao.DaoManager;
import com.dcservice.persistence.models.Option;
import com.dcservice.persistence.models.base.BaseModel;
import com.dcservice.persistence.models.enums.FieldType;
import com.dcservice.persistence.models.fields.Field;
import com.dcservice.persistence.models.responses.Response;
import com.dcservice.web.BaseValidationBean;

@ManagedBean
@ViewScoped
public class FieldEditBean extends BaseValidationBean implements Serializable {

  private static final long serialVersionUID = -3739688369522159950L;

  private static final int MAX_OPTIONS_COUNT = 4;

  private Field field;

  private Set<SelectItem> fieldTypes;

  private boolean showOptions;

  private boolean canAddOptions;

  private Set<Option> options;

  private String option;

  private Long optionGeneratedId;

  @Override
  protected void onConstruct() {
    try {
      initField();
      initForm();
      optionGeneratedId = -1l;
    } catch (Exception e) {
    }

  }

  private void initField()
      throws NumberFormatException, IllegalAccessException, PersistenceBeanException {
    try {
      field = DaoManager.query().from(QField.field).leftJoin(QField.field.options).fetch()
	  .where(QField.field.id
	      .eq(Long.valueOf(getRequest().getParameter(RedirectHelper.ID_PARAMETER))))
	  .uniqueResult(QField.field);
      DaoManager.getSession().close();
    } catch (Exception e) {
    }
    if (field == null) {
      field = new Field();
      field.setType(FieldType.SINGLE_LINE_TEXT);
    }
  }

  private void initForm() {
    fieldTypes = new HashSet<SelectItem>();
    for (FieldType type : FieldType.values()) {
      fieldTypes.add(new SelectItem(type, type.toString()));
    }

    showOptions = !isNullOrEmpty(field) && field.getType() == FieldType.COMBOBOX;

    if ((Response) (BaseModel) field == null || isNullOrEmpty(field.getOptions())) {
      options = new HashSet<Option>(0);
    } else {
      options = new HashSet<Option>(field.getOptions());
    }
    checkCanAddOption();
  }

  public void typeChanged() {
    if (field.getType() == FieldType.COMBOBOX) {
      this.showOptions = true;
      checkCanAddOption();
    } else {
      this.showOptions = false;
      this.canAddOptions = false;
    }
    updateJS("options");
  }

  public void stateChange() {
    if (!field.getActive()) {
      field.setRequired(false);
      updateJS("required");
    }
  }

  public void addOption() {
    if (!isNullOrEmpty(option)) {
      Option optionItem = new Option();
      optionItem.setLabel(option);
      optionItem.setId(optionGeneratedId--);
      option = "";
      options.add(optionItem);
      checkCanAddOption();
      updateJS("options");
    }
  }

  public void deleteOption(Long id) {
    ListHelper.remove(options, id);
    checkCanAddOption();
    updateJS("options");
  }

  public void save() throws Exception {
    cleanValidation();
    validate();
    if (isFormValid()) {
      TransactionExecuter.execute(new Action() {
	@Override
	public void execute() throws Exception {
	  DaoManager.save(field);
	  saveOptions(field);
	}
      });
      afterSave();
    }
    updateJS("form");
  }

  private void afterSave() {
    RedirectHelper.goTo(PagesTypes.FIELD_LIST);
  }

  private void saveOptions(Field field) {
    if (field.getType() == FieldType.COMBOBOX) {
      if (!isNullOrEmpty(field.getOptions())) {
	Iterator<Option> oldIterator = field.getOptions().iterator();
	while (oldIterator.hasNext()) {
	  Iterator<Option> newIterator = options.iterator();
	  Option oldItem = oldIterator.next();
	  boolean toSaveList = false;
	  while (newIterator.hasNext()) {
	    Option newItem = newIterator.next();
	    if (oldItem.getId().equals(newItem.getId())) {

	      // TODO: Save old item if it edited
	      toSaveList = true;
	      break;
	    }
	  }
	  if (!toSaveList) {
	    DaoManager.remove(oldItem);
	  }
	}
	if (!options.isEmpty())
	  options.forEach(item -> {
	    if (item.getId() < 0l) {
	      item.setId(null);
	      item.setField(field);
	      DaoManager.save(item);
	    }
	  });
      } else {
	if (!options.isEmpty())
	  options.forEach(item -> {
	    item.setId(null);
	    item.setField(field);
	    DaoManager.save(item);
	  });
      }
    } else {
      if (!isNullOrEmpty(field.getOptions())) {
	field.getOptions().forEach(item -> {
	  DaoManager.remove(item);
	});
      }
    }
  }

  private void validate() {
    if (isNullOrEmpty(field.getLabel())) {
      addRequiredFieldExeption("field_label");
    }
    if (isNullOrEmpty(field.getType())) {
      addRequiredFieldExeption("field_type");
    } else if (field.getType() == FieldType.COMBOBOX)
      if (isNullOrEmpty(options)) {
	addRequiredFieldExeption("field_option_input");
	markInvalid("field_options", "");
      } else if (options.size() < 2) {
	addFieldExeption("field_options", "optionsLimit");
      }
  }

  private void checkCanAddOption() {
    canAddOptions = isNullOrEmpty(options) || options.size() < MAX_OPTIONS_COUNT;
  }

  public void cancel() {
    RedirectHelper.goTo(PagesTypes.FIELD_LIST);
  }

  public Field getField() {
    return field;
  }

  public void setField(Field field) {
    this.field = field;
  }

  public Set<SelectItem> getFieldTypes() {
    return fieldTypes;
  }

  public void setFieldTypes(Set<SelectItem> fieldTypes) {
    this.fieldTypes = fieldTypes;
  }

  public boolean isShowOptions() {
    return showOptions;
  }

  public void setShowOptions(boolean showOptions) {
    this.showOptions = showOptions;
  }

  public Set<Option> getOptions() {
    return options;
  }

  public void setOptions(Set<Option> options) {
    this.options = options;
  }

  public String getOption() {
    return option;
  }

  public void setOption(String option) {
    this.option = option;
  }

  public boolean isCanAddOptions() {
    return canAddOptions;
  }

  public void setCanAddOptions(boolean canAddOptions) {
    this.canAddOptions = canAddOptions;
  }

}
