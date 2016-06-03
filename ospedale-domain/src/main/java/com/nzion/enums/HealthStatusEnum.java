package com.nzion.enums;

public enum HealthStatusEnum {
	
	HEALTHY {
		public String toString() {
		return "Healthy";
		}
	},ACCIDENT {
		@Override
		public String toString() {
		return "Accident";
		}
	},HEREDITARY_DISEASE {
		@Override
		public String toString() {
		return "Hereditary Disease";
		}
	},NATURAL_CAUSE {
		@Override
		public String toString() {
		return "Natural Cause";
		}
	},OLD_AGE {
		@Override
		public String toString() {
		return "Old Age";
		}
	},OTHER_DISEASE {
		@Override
		public String toString() {
		return "Other Disease";
		}
	},UNKOWN {
		@Override
		public String toString() {
		return "Unknown";
		}
	};

	abstract public String toString();
	
}
