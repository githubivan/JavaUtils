package com.util.jdbc.model.local;

import com.util.jdbc.model.Model;

public class NetAccess extends Model {
	private String appSha256;
	private String urlSha1;
	private String operation;
	private String netAccessJson;
	public String getAppSha256() {
		return appSha256;
	}
	public void setAppSha256(String appSha256) {
		this.appSha256 = appSha256;
	}
	public String getUrlSha1() {
		return urlSha1;
	}
	public void setUrlSha1(String urlSha1) {
		this.urlSha1 = urlSha1;
	}
	public String getOperation() {
		return operation;
	}
	public void setOperation(String operation) {
		this.operation = operation;
	}
	public String getNetAccessJson() {
		return netAccessJson;
	}
	public void setNetAccessJson(String netAccessJson) {
		this.netAccessJson = netAccessJson;
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
