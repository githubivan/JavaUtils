package com.util.jdbc.core;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.mchange.v2.c3p0.ComboPooledDataSource;

public class CloudPool {
	private static final Logger logger = LoggerFactory.getLogger(CloudPool.class);
	
	private static final String c3p0Config = "c3p0.properties";
    private static CloudPool pool;
    private DataSource dataSource;

    private CloudPool() {
    	if (c3p0Config != null) {
    		dataSource = new ComboPooledDataSource(c3p0Config);
    	} else {
    		dataSource = new ComboPooledDataSource();
    	}
    }
    
    public static synchronized final CloudPool getInstance() {  
        if (pool == null) {
            try {  
                pool = new CloudPool();
            } catch (Exception e) {
            	logger.error("Get cloud DB pool instance error", e);
            }  
        }
        return pool;  
    }  
    
    public synchronized final Connection getConnection() {  
        try {  
            return dataSource.getConnection(); 
        } catch (SQLException e) {
        	logger.error("Get cloud DB connection error", e);  
        }
        return null;  
    }
    
    public boolean updateByPreparedStatement(String sql, List<Object>params) {  
        boolean flag = false; 
        ResultSet rs = null;
        PreparedStatement pstmt = null;
        Connection conn = getConnection();
        if (conn != null) {
            try {
                int result = -1;
                pstmt = conn.prepareStatement(sql);  
                int index = 1;  
                if (params != null && !params.isEmpty()) {  
                    for (int i = 0; i < params.size(); i++) {  
                        pstmt.setObject(index++, params.get(i));  
                    }  
                }
                result = pstmt.executeUpdate();
                flag = result > 0 ? true : false;  
            } catch (SQLException e) {  
            	logger.error("Update by prepared statement [" + sql + "] error:", e);
            } finally {
            	release(rs, pstmt, conn);
            }
        }
        return flag;  
    }  
	  
    public Map<String, Object> findSingleResult(String sql, List<Object> params) {  
        Map<String, Object> map = new HashMap<String, Object>();
        getConnection();
        ResultSet resultSet = null;
        PreparedStatement pstmt = null; 
        Connection conn = getConnection();
        if (conn != null) {
            try {
                int index  = 1;  
                pstmt = conn.prepareStatement(sql);  
                if (params != null && !params.isEmpty()) {  
                    for( int i = 0; i < params.size(); i++) {  
                        pstmt.setObject(index++, params.get(i));  
                    }  
                }  
                resultSet = pstmt.executeQuery();
                ResultSetMetaData metaData = resultSet.getMetaData();  
                int colLen = metaData.getColumnCount();  
                while (resultSet.next()) {  
                    for (int i = 0; i < colLen; i++ ) {  
                        String colName = metaData.getColumnName(i+1);  
                        Object colValue = resultSet.getObject(colName);   
                        map.put(colName, colValue);  
                    }  
                }  
            } catch (SQLException e) {  
            	logger.error("Find single result [" + sql + "] error:", e);
            } finally {
            	release(resultSet, pstmt, conn);
            }
        }
        return map;  
    }  
    
    public List<Map<String, Object>> findMultiResult(String sql, List<Object> params) {  
        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
        getConnection();
        ResultSet resultSet = null;
        PreparedStatement pstmt = null;
        Connection conn = getConnection();
        if (conn != null) {
            try {
                int index = 1;  
                pstmt = conn.prepareStatement(sql);  
                if (params != null && !params.isEmpty()) {  
                    for (int i = 0; i < params.size(); i++) {  
                        pstmt.setObject(index++, params.get(i));  
                    }  
                }  
                resultSet = pstmt.executeQuery();  
                ResultSetMetaData metaData = resultSet.getMetaData();  
                int colLen = metaData.getColumnCount();  
                while (resultSet.next()) {  
                    Map<String, Object> map = new HashMap<String, Object>();  
                    for (int i = 0; i < colLen; i++) {  
                        String colName = metaData.getColumnName(i+1);
                        Object colValue = resultSet.getObject(colName);
                        map.put(colName, colValue);  
                    }  
                    list.add(map);  
                }  
            } catch (SQLException e) {  
            	logger.error("Find multi result [" + sql + "] error:", e);
            } finally {
            	release(resultSet, pstmt, conn);
            }
        }
        return list;  
    }  
    
    public <T> T findSingleRefResult(String sql, List<Object> params, Class<T> cls) {  
        T resultObject = null;
        getConnection();
        ResultSet resultSet = null;
        PreparedStatement pstmt = null;
        Connection conn = getConnection();
        if (conn != null) {
            try {
                int index = 1;  
                pstmt = conn.prepareStatement(sql);  
                if (params != null && !params.isEmpty()) {  
                    for (int i = 0; i < params.size(); i++) {  
                        pstmt.setObject(index++, params.get(i));  
                    }  
                }  
                resultSet = pstmt.executeQuery();  
                ResultSetMetaData metaData  = resultSet.getMetaData();  
                int colLen = metaData.getColumnCount();  
                if (resultSet.next()) {
                    resultObject = cls.newInstance();  
                    for (int i = 0; i < colLen; i++) {  
                        String colName = metaData.getColumnName(i+1);  
                        Object colsValue = resultSet.getObject(colName); 
                        Field field = cls.getDeclaredField(lowerFirstChar(colName));  
                        field.setAccessible(true);
                        field.set(resultObject, colsValue);  
                    }  
                }  
            } catch (Exception e) {  
            	logger.error("Find single ref result [" + sql + "] error:", e);
            } finally {
            	release(resultSet, pstmt, conn);
            }
        }
        return resultObject;
    }  
    
    public <T> List<T> findMultiRefResult(String sql, List<Object> params, Class<T> cls) {  
        List<T> list = new ArrayList<T>();
        getConnection();
        ResultSet resultSet = null;
        PreparedStatement pstmt = null; 
        Connection conn = getConnection();
        if (conn != null) {
            try {
                int index = 1;  
                pstmt = conn.prepareStatement(sql);  
                if (params != null && !params.isEmpty()) {  
                    for(int i = 0; i < params.size(); i++){ 
                        pstmt.setObject(index++, params.get(i));  
                    }  
                }  
                resultSet = pstmt.executeQuery();  
                ResultSetMetaData metaData  = resultSet.getMetaData();  
                int colLen = metaData.getColumnCount();  
                while (resultSet.next()) {  
                    T resultObject = cls.newInstance();  
                    for (int i = 0; i < colLen; i++) {  
                        String colName = metaData.getColumnName(i+1);  
                        Object colValue = resultSet.getObject(colName);
                        Field field = cls.getDeclaredField(lowerFirstChar(colName));  
                        field.setAccessible(true);
                        field.set(resultObject, colValue);  
                    }  
                    list.add(resultObject);  
                }  
            } catch (Exception e) {  
            	logger.error("Find multi ref result [" + sql + "] error:", e);
            } finally {
            	release(resultSet, pstmt, conn);
            }
        }
        return list;  
    } 
    
    public void release(ResultSet rs, 
    		PreparedStatement pstmt, Connection conn) {
        if (rs != null) {  
            try {  
            	rs.close();  
            } catch(SQLException e){  
            	logger.error("Result set release error:", e);
            }  
        }
        if (pstmt != null) {  
            try {  
            	pstmt.close();  
            } catch(SQLException e){  
            	logger.error("Prepared statement release error:", e);
            }  
        }  
        if (conn != null) {  
            try {  
            	conn.close();  
            } catch(SQLException e){  
            	logger.error("Connection release error:", e);
            }  
        }
    }  
    
    private String lowerFirstChar(String s)
    {
        if (Character.isLowerCase(s.charAt(0))) {
            return s;
        } else {
        	 return (new StringBuilder()).append(Character.toLowerCase(s.charAt(0)))
        			 .append(s.substring(1)).toString();
        }  
    }
}