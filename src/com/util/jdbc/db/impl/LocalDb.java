package com.util.jdbc.db.impl;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.util.jdbc.core.Jdbc;
import com.util.jdbc.db.ILocalDb;
import com.util.jdbc.model.local.NetAccess;
import com.util.jdbc.model.local.NetWrsScore;
import com.util.jdbc.tool.Const;
import com.util.jdbc.tool.HexUtil;


public class LocalDb implements ILocalDb {
	// example for non-c3p0 JDBC
	private Jdbc jdbc;
	public LocalDb(String url) throws Exception {
		jdbc = new Jdbc(url);
	}
	
	@Override
	public void addNetAccess(List<NetAccess> netAccessList) {
		if (netAccessList != null) {
			String sql = "INSERT apk_net_access (sha256, urlSha1, type) VALUE (?, ?, ?)"
					+ " ON DUPLICATE KEY UPDATE type = type | ?";
			for (NetAccess netAccess : netAccessList) {
				List<Object> params = new ArrayList<Object>();
				params.add(HexUtil.hexStrToByteArray(netAccess.getAppSha256()));
				params.add(HexUtil.hexStrToByteArray(netAccess.getUrlSha1()));
				params.add(netAccess.getOperation());
				params.add(netAccess.getOperation());
				jdbc.updateByPreparedStatement(sql, params);
			}
		}
	}

	@Override
	public void addNetWrsScore(List<NetWrsScore> netWrsScoreList) {
		if (netWrsScoreList != null) {
			String sql = "INSERT url_wrs_result (urlSha1, scanTime,"
					+ " firstScanTime, allString, domain, resultCode,"
					+ " category, risk, score) VALUE (?, now(), now(),"
					+ " ?, ?, ?, ?, ?, ?) ON DUPLICATE KEY UPDATE"
					+ " scanTime = now(), allString = ?, domain = ?,"
					+ " resultCode = ?, category = ?, risk = ?, score = ?";
			for (NetWrsScore netWrsScore : netWrsScoreList) {
				List<Object> params = new ArrayList<Object>();
				params.add(HexUtil.hexStrToByteArray(netWrsScore.getUrlSha1()));
				params.add(netWrsScore.getAllStr());
				params.add(netWrsScore.getDomain());
				params.add(netWrsScore.getResultCode());
				params.add(netWrsScore.getCategory());
				params.add(netWrsScore.getRisk());
				params.add(netWrsScore.getScore());
				params.add(netWrsScore.getAllStr());
				params.add(netWrsScore.getDomain());
				params.add(netWrsScore.getResultCode());
				params.add(netWrsScore.getCategory());
				params.add(netWrsScore.getRisk());
				params.add(netWrsScore.getScore());
				jdbc.updateByPreparedStatement(sql, params);
			}
		}
	}

	@Override
	public String getAppStoragePath(String sha256, String extension) {
		if (sha256 != null) {
			String table = "apk_scan_info";
			if (extension.equals(Const.EXTENSION_DEX)) {
				table = "dex_scan_info";
			} else if (extension.equals(Const.EXTENSION_IPA)) {
				table = "ipa_scan_info";
			}
			String sql = "SELECT storagePath FROM " + table + " WHERE sha256 = ?";
			List<Object> params = new ArrayList<Object>();
			params.add(HexUtil.hexStrToByteArray(sha256));
			Map<String, Object> result = jdbc.findSingleResult(sql, params);
			if (result != null) {
				return (String) result.get("storagePath");
			}
		}
		return null;
	}
}
