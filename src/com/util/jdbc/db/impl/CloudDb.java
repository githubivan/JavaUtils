package com.util.jdbc.db.impl;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.util.jdbc.core.CloudPool;
import com.util.jdbc.db.ICloudDb;
import com.util.jdbc.model.cloud.ModuleInfo;
import com.util.jdbc.model.cloud.ProcessConfInfo;
import com.util.jdbc.model.cloud.RunningTaskInfo;
import com.util.jdbc.model.cloud.ScannerInfo;
import com.util.jdbc.tool.Const;
import com.util.jdbc.tool.DateUtil;
import com.util.jdbc.tool.HexUtil;

public class CloudDb implements ICloudDb {
	// example for c3p0 pool
	CloudPool pool;
	public CloudDb() throws Exception {
		pool = CloudPool.getInstance();
	}
	
	@Override
	public List<ProcessConfInfo> loadProcessInfo() {
		String sql = "SELECT process_conf_info.id, fileType, module_timeout_list.value "
				+ " FROM process_conf_info LEFT JOIN module_timeout_list"
				+ " ON process_conf_info.moduleTimeoutListId = module_timeout_list.id";
		return pool.findMultiRefResult(sql, null, ProcessConfInfo.class);
	}

	@Override
	public List<ModuleInfo> loadModuleInfo() {
		String sql = "SELECT id, name FROM module_info";
		return pool.findMultiRefResult(sql, null, ModuleInfo.class);
	}
	
	public List<ScannerInfo> loadScannerInfo() {
		String sql = "SELECT id, name FROM scanner_info";
		return pool.findMultiRefResult(sql, null, ScannerInfo.class);
	}
	
	public List<RunningTaskInfo> getTaskList(Long instanceId, int status) {
		String sql = "SELECT HEX(sha256) AS sha256, processID, priority, address, moduleList"
				+ " FROM running_task_info WHERE instanceId = ? AND status = ? ORDER BY Priority";
		List<Object> params = new ArrayList<Object>();
		params.add(instanceId);
		params.add(status);
		return pool.findMultiRefResult(sql, params, RunningTaskInfo.class);
	}
	
	@Override
	public List<RunningTaskInfo> getDonwloadTaskList(Long instanceId) {
		return getTaskList(instanceId, Const.TASK_STATUS_WAITING);
	}
	
	@Override
	public List<RunningTaskInfo> getScanTaskList(Long instanceId) {
		return getTaskList(instanceId, Const.TASK_STATUS_ACTIVATED);
	}
	
	@Override
	public void insertLog(String sha256, Long processId, Long instanceId, Long hostIp, 
		Long scannerId, Date startTime, Date endTime, BigInteger errorCode) {
		String table = "log_info_" + DateUtil.getWeekNo();
        String sql = String.format("INSERT INTO %s (sha256, processId, instanceId, hostIp,"
        		+ " scannerId, startTime, endTime, errorCode) VALUE(?, ?, ?, ?, ?, ?, ?, ?)", table);
		List<Object> params = new ArrayList<Object>();
		params.add(HexUtil.hexStrToByteArray(sha256));
		params.add(processId);
		params.add(instanceId);
		params.add(hostIp);
		params.add(scannerId);
		params.add(startTime);
		params.add(endTime);
		params.add(errorCode);
		pool.updateByPreparedStatement(sql, params);
	}
	
	@Override
	public void updateTaskStatus(String sha256, Long processId, Long instanceId, 
			int status, String address) {
		String sql = "UPDATE running_task_info SET status = ?, address = ?"
				+ " WHERE sha256 = ? AND processID = ? AND instanceId = ?";
		List<Object> params = new ArrayList<Object>();
		params.add(status);
		params.add(address);
		params.add(HexUtil.hexStrToByteArray(sha256));
		params.add(processId);
		params.add(instanceId);
		pool.updateByPreparedStatement(sql, params);
	}
}
