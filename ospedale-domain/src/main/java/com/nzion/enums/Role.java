package com.nzion.enums;


public enum Role {
    ADMIN{
        @Override
        public String toString() {
            return "ADMIN";
        }
    }, RECEPTION{
        @Override
        public String toString() {
            return "RECEPTION";
        }
    }, NURSE{
        @Override
        public String toString() {
            return "NURSE";
        }
    }, BILLING{
        @Override
        public String toString() {
            return "BILLING";
        }
    }, PATIENT{
        @Override
        public String toString() {
            return "PATIENT";
        }
    }, PROVIDER{
        @Override
        public String toString() {
            return "PROVIDER";
        }
    }, ADJUSTER{
        @Override
        public String toString() {
            return "ADJUSTER";
        }
    }, HOUSE_KEEPING{
        @Override
        public String toString() {
            return "HOUSE_KEEPING";
        }
    }, MEDICAL_ASSISTANT{
        @Override
        public String toString() {
            return "MEDICAL_ASSISTANT";
        }
    }, TECHNICIAN{
        @Override
        public String toString() {
            return "TECHNICIAN";
        }
    }, CASE_MANAGER{
        @Override
        public String toString() {
            return "CASE_MANAGER";
        }
    }, SUPER_ADMIN{
        @Override
        public String toString() {
            return "SUPER_ADMIN";
        }
    }, PHLEBOTOMIST{
        @Override
        public String toString() {
            return "PHLEBOTOMIST";
        }
    }
}
