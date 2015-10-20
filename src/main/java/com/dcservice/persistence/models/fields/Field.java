package com.dcservice.persistence.models.fields;

import java.io.Serializable;
import java.util.Set;

import com.dcservice.persistence.models.FieldResponse;
import com.dcservice.persistence.models.Option;
import com.dcservice.persistence.models.base.BaseModel;
import com.dcservice.persistence.models.enums.FieldType;

@Entity
@Table
public class Field extends BaseModel implements Serializable {

    private static final long serialVersionUID = -5222710508176943878L;

    @Column(nullable = false)
    public FieldType type;

    @Column(nullable = false)
    public String label;

    @Column(nullable = false)
    public Boolean required = false;

    @Column(nullable = false)
    public Boolean active = true;

    @OneToMany(mappedBy = "field")
    @OnDelete(action = OnDeleteAction.CASCADE)
    public Set<Option> options;

    @OneToMany(mappedBy = "field")
    @OnDelete(action = OnDeleteAction.CASCADE)
    public Set<FieldResponse> responses;

    public int getResponsesCount() {

	return getResponsesCount();
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

    public Boolean getActive() {
	return active;
    }

    public void setActive(Boolean active) {
	this.active = active;
    }

    public Set<Option> getOptions() {
	return options;
    }

    public void setOptions(Set<Option> options) {
	this.options = options;
    }

    public Set<FieldResponse> getResponses() {
	return responses;
    }

    public void setResponses(Set<FieldResponse> responses) {
	this.responses = responses;
    }

}
