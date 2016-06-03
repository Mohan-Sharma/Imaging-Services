package com.nzion.domain;

public enum SearchOperator {

	LESS_THAN {
		@Override
		public String toString() {
		return "Less than";
		};
	},
	GREATER_THAN {
		@Override
		public String toString() {
		return "begins with";
		};
	},
	LESS_THAN_EQUAL_TO {
		@Override
		public String toString() {
		return "less than equal to";
		};
	},
	GREATER_THAN_EQUAL_TO {
		@Override
		public String toString() {
		return "greater than equal to";
		};
	},
	STARTS_WITH {
		@Override
		public String toString() {
		return "starts with";
		};
	},
	ENDS_WITH {
		@Override
		public String toString() {
		return "ends with";
		};
	},
	CONTAINS {
		@Override
		public String toString() {
		return "contains";
		};
	},
	EQUALS {
		@Override
		public String toString() {
		return "equals";
		};
	};

	public static SearchOperator[] getStringOperators() {
	return new SearchOperator[] { CONTAINS, ENDS_WITH, STARTS_WITH };
	}

	public static SearchOperator[] getNumericOperators() {
	return new SearchOperator[] { LESS_THAN, GREATER_THAN, GREATER_THAN_EQUAL_TO, LESS_THAN, EQUALS };
	}

}
