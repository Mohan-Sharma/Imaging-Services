package com.nzion.diagnostic;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name = "WEBSITE_STATS")
public class ZKRequestMonitor implements Serializable {


	private static final long serialVersionUID = 1L;
	private String contextPath;

	private String desktopId;
	private String requestId;
	private String ipAddress;
	private String userLoginId;

	private Timestamp accessedOn;

	private Long timeStartAtClient;
	private Long timeStartAtServer;
	private Long timeCompleteAtServer;
	private Long timeRecieveAtClient;
	private Long timeCompleteAtClient;

	private Long id;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	public Long getId() {
	return id;
	}

	public void setId(Long id) {
	this.id = id;
	}

	public ZKRequestMonitor() {
	}

	public ZKRequestMonitor(String requestId, String desktopId, String contextPath, String ipAddress, String userLoginId) {
	this.requestId = requestId;
	this.desktopId = desktopId;
	this.contextPath = contextPath;
	this.accessedOn = new Timestamp(new Date().getTime());
	this.ipAddress = ipAddress;
	this.userLoginId = userLoginId;
	}

	@Column(name = "ACCESSED_ON")
	public Timestamp getAccessedOn() {
	return accessedOn;
	}

	/**
	 * Browser javascript execution
	 * @return
	 */
	@Transient
	public Long getBrowserExecution() {
	if (timeRecieveAtClient == 0 || timeCompleteAtClient == 0) {
		return null;
	} else {
		return (timeCompleteAtClient - timeRecieveAtClient);
	}
	}

	@Column(name = "CONTEXT_PATH")
	public String getContextPath() {
	return contextPath;
	}

	@Column(name = "DESKTOP_ID")
	public String getDesktopId() {
	return desktopId;
	}

	@Column(name = "CLIENT_IP")
	public String getIpAddress() {
	return ipAddress;
	}

	/**
	 * Client time and server time may not be synchronized, caluclate as overall minus client
	 *
	 * @return
	 */
	@Transient
	public Long getNetworkLatency() {
	if (timeStartAtClient == 0 || timeCompleteAtClient == 0 || timeStartAtServer == 0 || timeCompleteAtServer == 0) {
		return null;
	} else {
		return (timeCompleteAtClient - timeStartAtClient) - (timeCompleteAtServer - timeStartAtServer);
	}

	}

	/**
	 * Overall request duration (estimated if not all data are set)
	 *
	 * @return
	 */
	@Transient
	public Long getOverallDuration() {
	if (timeStartAtClient == 0 || timeCompleteAtClient == 0) {
		if (timeStartAtServer != 0 && timeCompleteAtServer != 0) {
			return timeCompleteAtServer - timeStartAtServer;
		} else {
			return null;
		}
	} else {
		return timeCompleteAtClient - timeStartAtClient;
	}
	}

	/**
	 * @return the requestId
	 */
	@Column(name = "REQUEST_ID")
	public String getRequestId() {
	return requestId;
	}

	/**
	 * @return the timeCompleteAtClient
	 */
	public Long getTimeCompleteAtClient() {
	return timeCompleteAtClient;
	}

	/**
	 * @return the timeCompleteAtServer
	 */
	public Long getTimeCompleteAtServer() {
	return timeCompleteAtServer;
	}

	/**
	 * @return the timeRecieveAtClient
	 */
	public Long getTimeRecieveAtClient() {
	return timeRecieveAtClient;
	}

	/**
	 * @return the timeStartAtClient
	 */
	public Long getTimeStartAtClient() {
	return timeStartAtClient;
	}

	/**
	 * @return the timeStartAtServer
	 */
	public Long getTimeStartAtServer() {
	return timeStartAtServer;
	}

	@Column(name = "USERLOGIN_ID")
	public String getUserLoginId() {
	return userLoginId;
	}

	public void setAccessedOn(Timestamp accessedOn) {
	this.accessedOn = accessedOn;
	}

	public void setContextPath(String contextPath) {
	this.contextPath = contextPath;
	}

	public void setDesktopId(String desktopId) {
	this.desktopId = desktopId;
	}

	public void setIpAddress(String ipAddress) {
	this.ipAddress = ipAddress;
	}

	/**
	 * @param requestId the requestId to set
	 */
	public void setRequestId(String requestId) {
	this.requestId = requestId;
	}

	/**
	 * @param timeCompleteAtClient the timeCompleteAtClient to set
	 */
	public void setTimeCompleteAtClient(Long timeCompleteAtClient) {
	this.timeCompleteAtClient = timeCompleteAtClient;
	}

	/**
	 * @param timeCompleteAtServer the timeCompleteAtServer to set
	 */
	public void setTimeCompleteAtServer(Long timeCompleteAtServer) {
	this.timeCompleteAtServer = timeCompleteAtServer;
	}

	/**
	 * @param timeRecieveAtClient the timeRecieveAtClient to set
	 */
	public void setTimeRecieveAtClient(Long timeRecieveAtClient) {
	this.timeRecieveAtClient = timeRecieveAtClient;
	}

	/**
	 * @param timeStartAtClient the timeStartAtClient to set
	 */
	public void setTimeStartAtClient(Long timeStartAtClient) {
	this.timeStartAtClient = timeStartAtClient;
	}

	/**
	 * @param timeStartAtServer the timeStartAtServer to set
	 */
	public void setTimeStartAtServer(Long timeStartAtServer) {
	this.timeStartAtServer = timeStartAtServer;
	}

	public void setUserLoginId(String userLoginId) {
	this.userLoginId = userLoginId;
	}

}