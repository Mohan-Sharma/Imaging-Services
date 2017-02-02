package com.nzion.superbill.dto;

public class CommunicationLogDto {
    private String receipentType;
    private String message;
    private String messageChannel;
    private String tenantType;
    private String tenantId;
    private String referenceId;
    private String referenceType;
    private String accountNumber;
    private String sentToEmailId;
    private String emailTarget;
    private String sentToMobileNumber;
    private String sentToDeviceId;
    private String isDelivered;
    private String request;
    private String response;
    private String eventName;

    public String getReceipentType() {
        return receipentType;
    }

    public void setReceipentType(String receipentType) {
        this.receipentType = receipentType;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getMessageChannel() {
        return messageChannel;
    }

    public void setMessageChannel(String messageChannel) {
        this.messageChannel = messageChannel;
    }

    public String getTenantType() {
        return tenantType;
    }

    public void setTenantType(String tenantType) {
        this.tenantType = tenantType;
    }

    public String getTenantId() {
        return tenantId;
    }

    public void setTenantId(String tenantId) {
        this.tenantId = tenantId;
    }

    public String getReferenceId() {
        return referenceId;
    }

    public void setReferenceId(String referenceId) {
        this.referenceId = referenceId;
    }

    public String getReferenceType() {
        return referenceType;
    }

    public void setReferenceType(String referenceType) {
        this.referenceType = referenceType;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public String getSentToEmailId() {
        return sentToEmailId;
    }

    public void setSentToEmailId(String sentToEmailId) {
        this.sentToEmailId = sentToEmailId;
    }

    public String getEmailTarget() {
        return emailTarget;
    }

    public void setEmailTarget(String emailTarget) {
        this.emailTarget = emailTarget;
    }

    public String getSentToMobileNumber() {
        return sentToMobileNumber;
    }

    public void setSentToMobileNumber(String sentToMobileNumber) {
        this.sentToMobileNumber = sentToMobileNumber;
    }

    public String getSentToDeviceId() {
        return sentToDeviceId;
    }

    public void setSentToDeviceId(String sentToDeviceId) {
        this.sentToDeviceId = sentToDeviceId;
    }

    public String getIsDelivered() {
        return isDelivered;
    }

    public void setIsDelivered(String isDelivered) {
        this.isDelivered = isDelivered;
    }

    public String getRequest() {
        return request;
    }

    public void setRequest(String request) {
        this.request = request;
    }

    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }

    public String getEventName() {
        return eventName;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }
}
