package com.util.jdbc.model.local;

import com.util.jdbc.model.Model;

public class NetWrsScore extends Model {
	private String urlSha1;
	private String category;
	private String risk;
	private String score;
	private String domain;
	private String allStr;
	private String resultCode;
	private String netWrsScoreJson;
	public String getUrlSha1() {
		return urlSha1;
	}
	public void setUrlSha1(String urlSha1) {
		this.urlSha1 = urlSha1;
	}
	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
	}
	public String getRisk() {
		return risk;
	}
	public void setRisk(String risk) {
		this.risk = risk;
	}
	public String getScore() {
		return score;
	}
	public void setScore(String score) {
		this.score = score;
	}
	public String getDomain() {
		return domain;
	}
	public void setDomain(String domain) {
		this.domain = domain;
	}
	public String getAllStr() {
		return allStr;
	}
	public void setAllStr(String allStr) {
		this.allStr = allStr;
	}
	public String getResultCode() {
		return resultCode;
	}
	public void setResultCode(String resultCode) {
		this.resultCode = resultCode;
	}
	public String getNetWrsScoreJson() {
		return netWrsScoreJson;
	}
	public void setNetWrsScoreJson(String netWrsScoreJson) {
		this.netWrsScoreJson = netWrsScoreJson;
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
