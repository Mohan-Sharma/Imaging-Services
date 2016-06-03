package com.nzion.enums;

public enum Operator {
	LESS_THAN {
		@Override
		public boolean evaluate(String rhs, String lhs, String s) {
		int val = Integer.parseInt(s);
		int rhsVal = Integer.parseInt(rhs);
		return val < rhsVal;
		}
	},
	LESS_THAN_EQUAL {
		@Override
		public boolean evaluate(String rhs, String lhs, String s) {
		int val = Integer.parseInt(s);
		int rhsVal = Integer.parseInt(rhs);
		return val <= rhsVal;
		}
	},
	GREATER_THAN {
		@Override
		public boolean evaluate(String rhs, String lhs, String s) {
		int val = Integer.parseInt(s);
		int rhsVal = Integer.parseInt(rhs);
		return val>rhsVal;
		}
	},
	GREATER_THAN_EQUAL {
		@Override
		public boolean evaluate(String rhs, String lhs, String s) {
		int val = Integer.parseInt(s);
		int rhsVal = Integer.parseInt(rhs);
		return val>=rhsVal;
		}
	},
	EQUALS {
		@Override
		public boolean evaluate(String rhs, String lhs, String s) {
		int val = Integer.parseInt(s);
		try{
			int rhsVal = Integer.parseInt(rhs);
			return val==rhsVal;
		}catch(NumberFormatException nfe){
		}
		return s.equals(rhs);
		}
	},
	STARTS_WITH {
		@Override
		public boolean evaluate(String rhs, String lhs, String s) {
		return s.startsWith(rhs);
		}
	};
	public abstract boolean evaluate(String rhs, String lhs, String s);
}