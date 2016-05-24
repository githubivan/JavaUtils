package com.util.jdbc.db;

import java.util.List;

import com.util.jdbc.model.local.NetAccess;
import com.util.jdbc.model.local.NetWrsScore;

public interface ILocalDb {
	public void addNetAccess(List<NetAccess> netAccessList);
	public void addNetWrsScore(List<NetWrsScore> netWrsScoreList);
	public String getAppStoragePath(String sha256, String extension);
}