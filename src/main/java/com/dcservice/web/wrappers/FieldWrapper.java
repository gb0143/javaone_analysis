package com.dcservice.web.wrappers;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.dcservice.persistence.models.enums.FieldType;
import com.dcservice.persistence.models.fields.Field;
import com.dcservice.web.wrappers.base.BaseWrapper;

public class FieldWrapper extends BaseWrapper {

  private Long id;

  private FieldType type;

  private String label;

  private Boolean required = false;

  private List<SelectItem> options;

  private String answer;

  private Boolean booleanAnswer;

  private Date dateAnswer;

  public FieldWrapper(Field field) {
    this.id = field.getId();
    this.type = field.getType();
    this.label = field.getLabel();
    this.required = field.getRequired();
    if (this.type == FieldType.COMBOBOX) {
      this.options = new ArrayList<>();
      field.getOptions().forEach(item -> {
	options.add(new SelectItem(item.getId(), item.getLabel()));
      });
    }
    booleanAnswer = false;
  }

  public static FieldWrapper wrap(Field field) {
    BaseWrapper wrapper = new FieldWrapper(field);
    return (FieldWrapper) wrapper;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public FieldType getType() {
    return type;
  }

  public void setType(FieldType type) {
    this.type = type;
  }

  public String getLabel() {
    return label;
  }

  public void setLabel(String label) {
    this.label = label;
  }

  public Boolean getRequired() {
    return required;
  }

  public void setRequired(Boolean required) {
    this.required = required;
  }

  public List<SelectItem> getOptions() {
    return options;
  }

  public void setOptions(List<SelectItem> options) {
    this.options = options;
  }

  public String getAnswer() {
    return answer;
  }

  public void setAnswer(String answer) {
    this.answer = answer;
  }

  public boolean isBooleanAnswer() {
    return booleanAnswer;
  }

  public void setBooleanAnswer(boolean booleanAnswer) {
    this.booleanAnswer = booleanAnswer;
  }

  public Date getDateAnswer() {
    return dateAnswer;
  }

  public void setDateAnswer(Date dateAnswer) {
    this.dateAnswer = dateAnswer;
  }

}
