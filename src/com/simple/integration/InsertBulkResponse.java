package com.simple.integration;

import java.util.List;

public class InsertBulkResponse {
	public String hasErrors;
	public List<InsertBulkInnerResponse> results;

	public String getHasErrors() {
		return hasErrors;
	}

	public void setHasErrors(String hasErrors) {
		this.hasErrors = hasErrors;
	}
	
	public List<InsertBulkInnerResponse> getResults() {
		return results;
	}

	public void setResults(List<InsertBulkInnerResponse> results) {
		this.results = results;
	}
	
	@Override
	public String toString() {
		return "InsertBulkResponse [hasErrors=" + hasErrors + ", results=" + results + "]";
	}
	
	public class InsertBulkInnerResponse {
		public String referenceId;
		public String id;

		public String getReferenceId() {
			return referenceId;
		}

		public void setReferenceId(String referenceId) {
			this.referenceId = referenceId;
		}

		public String getId() {
			return id;
		}

		public void setId(String id) {
			this.id = id;
		}

		@Override
		public String toString() {
			return "InsertBulkInnerResponse [referenceId=" + referenceId + ", id=" + id + "]";
		}
	}
}
