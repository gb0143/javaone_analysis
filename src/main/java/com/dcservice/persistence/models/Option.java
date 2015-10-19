package com.dcservice.persistence.models;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.dcservice.persistence.models.base.BaseModel;
import com.dcservice.persistence.models.fields.Field;

@Entity
@Table
public class Option extends BaseModel implements Serializable {
	
	private static final long serialVersionUID = 4210698261930953201L;

	@Column
	private String label;

	@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(nullable = false)
    private Field field;
	
	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public Field getField() {
		return field;
	}

	public void setField(Field field) {
		this.field = field;
	}
	
}
