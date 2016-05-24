package com.util.main;

import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.util.jdbc.db.impl.CloudDb;
import com.util.jdbc.db.impl.LocalDb;
import com.util.jdbc.model.cloud.RunningTaskInfo;
import com.util.jdbc.model.local.NetAccess;
import com.util.jdbc.tool.Const;

public class Main {
	private static final Logger logger = LoggerFactory.getLogger(Main.class);
	public static void main(String[] args) throws Exception {
		if (args.length == 1 && args[0].equalsIgnoreCase("c3p0")) {
			//test for c3p0 JDBC
			CloudDb cloudDb = new CloudDb();
			String sha256 = "EA50DEB17EC28F68A6C2A2F6DDF43E7974BCC63317BE86A7847A3AE73EE51AC1";
			cloudDb.updateTaskStatus(sha256, 0l, 0l, Const.TASK_STATUS_WAITING, "/test/path");
			List<RunningTaskInfo> runningTaskInfoList = cloudDb.getDonwloadTaskList(0l);
			for (RunningTaskInfo taskInfo : runningTaskInfoList) {
				logger.info(taskInfo.getAddress());
			}
		} else {
			//test for non-c3p0 JDBC
		    Properties properties = new Properties();  
	    	properties.load(new FileInputStream("conf/conf.properties"));
	    	String url = properties.getProperty("db.local.url");
			LocalDb localDb = new LocalDb(url);
			List<NetAccess> netAccessList = new ArrayList<NetAccess>();
			NetAccess netAccess = new NetAccess();
			netAccess.setAppSha256("EA50DEB17EC28F68A6C2A2F6DDF43E7974BCC63317BE86A7847A3AE73EE51AC1");
			netAccess.setUrlSha1("69EF45570BAA04FF819B414C73994EE8E6C953BE");
			netAccess.setOperation("1");
			netAccessList.add(netAccess);
			localDb.addNetAccess(netAccessList);
		}
	}
}