package com.simple.integration;

import java.util.List;

public class InsertResponse {
	public String id;
	public String success;
	public List<String> errors;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getSuccess() {
		return success;
	}

	public void setSuccess(String success) {
		this.success = success;
	}

	public List<String> getErrors() {
		return errors;
	}

	public void setErrors(List<String> errors) {
		this.errors = errors;
	}

	@Override
	public String toString() {
		return "InsertResponse [id=" + id + ", success=" + success + ", errors=" + errors + "]";
	}
}
