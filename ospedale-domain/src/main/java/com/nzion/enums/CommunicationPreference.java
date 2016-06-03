package com.nzion.enums;

public enum CommunicationPreference {

	PATIENT_PORTAL {
		@Override
		public String toString() {
		return "Patient Portal";
		}
	},
	EMAIL {
		@Override
		public String toString() {
		return "Mail";
		}
	},
	SMS {
		@Override
		public String toString() {
		return "SMS";
		}
	},
	CALL {
		@Override
		public String toString() {
		return "Call";
		}
	}
}
