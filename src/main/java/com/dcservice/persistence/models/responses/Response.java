package com.dcservice.persistence.models.responses;

import java.io.Serializable;
import java.util.Set;

import com.dcservice.persistence.models.FieldResponse;
import com.dcservice.persistence.models.base.BaseModel;
import com.dcservice.persistence.models.fields.Field;

@Entity
@Table
public class Response extends BaseModel implements Serializable {

  private static final long serialVersionUID = 754140410656222737L;

  @OneToMany(mappedBy = "response")
  @OnDelete(action = OnDeleteAction.CASCADE)
  private Set<FieldResponse> fieldResponses;

  public int getSummuryFieldResponses() {
    int count = 0;
    for (FieldResponse item : fieldResponses) {
      Field field = item.field;
      count += field.getResponsesCount();
    }
    return count;
  }

  public Set<FieldResponse> getFieldResponses() {
    return fieldResponses;
  }

  public void setFieldResponses(Set<FieldResponse> fieldResponses) {
    this.fieldResponses = fieldResponses;
  }

}
