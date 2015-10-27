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
import com.dcservice.persistence.models.responses.Response;

@Entity
@Table
public class FieldResponse extends BaseModel implements Serializable{

	private static final long serialVersionUID = 4391216983978279275L;

	@Column
	public String answer;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(nullable = false)
	public Field field;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(nullable = false)
	public Response response;

	public String getAnswer() {
		return answer;
	}

	public void setAnswer(String answer) {
		this.answer = answer;
	}

	public Field getField() {
		return field;
	}

	public void setField(Field field) {
		this.field = field;
	}

	public Response getResponse() {
		return response;
	}

	public void setResponse(Response response) {
		this.response = response;
	}

}
