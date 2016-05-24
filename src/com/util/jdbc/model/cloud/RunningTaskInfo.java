package com.util.jdbc.model.cloud;

import java.util.Date;
import com.util.jdbc.model.Model;

public class RunningTaskInfo extends Model {
	private String sha256;
	private Long processID;
	private Integer status;
	private Integer priority;
	private Long scannerId;
	private Long instanceId;
	private Integer tryTimes;
	private String address;
	private String moduleList;
	private Date assignTime;
	public String getSha256() {
		return sha256;
	}
	public void setSha256(String sha256) {
		this.sha256 = sha256;
	}
	public Long getProcessID() {
		return processID;
	}
	public void setProcessID(Long processID) {
		this.processID = processID;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public Integer getPriority() {
		return priority;
	}
	public void setPriority(Integer priority) {
		this.priority = priority;
	}
	public Long getInstanceId() {
		return instanceId;
	}
	public void setInstanceId(Long instanceId) {
		this.instanceId = instanceId;
	}
	public Integer getTryTimes() {
		return tryTimes;
	}
	public void setTryTimes(Integer tryTimes) {
		this.tryTimes = tryTimes;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getModuleList() {
		return moduleList;
	}
	public void setModuleList(String moduleList) {
		this.moduleList = moduleList;
	}
	public Date getAssignTime() {
		return assignTime;
	}
	public void setAssignTime(Date assignTime) {
		this.assignTime = assignTime;
	}
	public Long getScannerId() {
		return scannerId;
	}
	public void setScannerId(Long scannerId) {
		this.scannerId = scannerId;
	}
	@Override
	public boolean isEmpty() {
		// TODO Auto-generated method stub
		return false;
	}
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return null;
	}
}
