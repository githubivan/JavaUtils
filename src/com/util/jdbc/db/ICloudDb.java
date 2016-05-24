package com.util.jdbc.db;

import java.math.BigInteger;
import java.util.Date;
import java.util.List;

import com.util.jdbc.model.cloud.ModuleInfo;
import com.util.jdbc.model.cloud.ProcessConfInfo;
import com.util.jdbc.model.cloud.RunningTaskInfo;
import com.util.jdbc.model.cloud.ScannerInfo;


public interface ICloudDb {
	public List<ProcessConfInfo> loadProcessInfo();
	public List<ModuleInfo> loadModuleInfo();
	public List<ScannerInfo> loadScannerInfo();
	public List<RunningTaskInfo> getDonwloadTaskList(Long instanceId);
	public List<RunningTaskInfo> getScanTaskList(Long instanceId);
	public void insertLog(String sha256, Long processId, Long instanceId, Long hostIP, 
			Long scannerId, Date startTime, Date endTime, BigInteger errorCode);
	public void updateTaskStatus(String sha256, Long processId,  Long instanceId, 
			int status, String address);
}
