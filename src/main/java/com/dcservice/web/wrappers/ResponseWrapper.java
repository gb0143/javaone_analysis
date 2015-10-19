package com.dcservice.web.wrappers;

import static com.dcservice.common.helpers.ValidationHelper.isNullOrEmpty;

import java.util.HashMap;
import java.util.Map;

import com.dcservice.persistence.models.responses.Response;

public class ResponseWrapper {

	private Map<String, String> values;

	public ResponseWrapper(Response response) {
		values = new HashMap<String, String>();
		if (!isNullOrEmpty(response.getFieldResponses())) {
			response.getFieldResponses().forEach(item -> {
				values.put(item.getField().getLabel(), item.getAnswer());
			});
		}
	}

	public String get(String code){
		return values.get(code);
	}
	
	public Map<String, String> getValues() {
		return values;
	}

	public void setValues(Map<String, String> values) {
		this.values = values;
	}

}
