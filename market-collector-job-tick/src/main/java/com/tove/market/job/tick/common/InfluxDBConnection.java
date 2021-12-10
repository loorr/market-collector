package com.tove.market.job.tick.common;

import org.influxdb.InfluxDB;
import org.influxdb.InfluxDBFactory;
import org.influxdb.dto.*;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class InfluxDBConnection {
    // 用户名
    private String username;
    // 密码
    private String password;
    // 连接地址
    private String openurl;
    // 数据库名称
    private String dbName;
    // 保留策略
    private String retentionPolicy;

    private InfluxDB influxDB;

    public InfluxDB getInfluxDB(){
        return influxDB;
    }

    public InfluxDBConnection(String username, String password, String url,
                              String dbName, String retentionPolicy) {
        this.username = username;
        this.password = password;
        this.openurl = url;
        this.dbName = dbName;
        this.retentionPolicy = retentionPolicy == null || retentionPolicy.equals("") ? "autogen" : retentionPolicy;
        influxDbBuild();
    }

    /**
     * 创建数据库
     *
     * @param dbName
     */
    @SuppressWarnings("deprecation")
    public void createDB(String dbName) {
        influxDB.createDatabase(dbName);
    }

    /**
     * 删除数据库
     *
     * @param dbName
     */
    @SuppressWarnings("deprecation")
    public void deleteDB(String dbName) {
        influxDB.deleteDatabase(dbName);
    }

    /**
     * 测试连接是否正常
     *
     * @return true 正常
     */
    public boolean ping() {
        boolean isConnected = false;
        Pong pong;
        try {
            pong = influxDB.ping();
            if (pong != null) {
                isConnected = true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return isConnected;
    }

    /**
     * 连接时序数据库 ，若不存在则创建
     *
     * @return
     */
    public InfluxDB influxDbBuild() {
        if (influxDB == null) {
            influxDB = InfluxDBFactory.connect(openurl, username, password);
        }
        try {
            // if (!influxDB.databaseExists(database)) {
            // influxDB.createDatabase(database);
            // }
        } catch (Exception e) {
            // 该数据库可能设置动态代理，不支持创建数据库
            // e.printStackTrace();
        } finally {
            influxDB.setRetentionPolicy(retentionPolicy);
        }
        influxDB.setLogLevel(InfluxDB.LogLevel.NONE);
        return influxDB;
    }

    /**
     * 创建自定义保留策略
     *
     * @param policyName  策略名
     * @param days        保存天数
     * @param replication 保存副本数量
     * @param isDefault   是否设为默认保留策略
     */
    public void createRetentionPolicy(String dataBaseName, String policyName, int days, int replication,
                                      Boolean isDefault) {
        String sql = String.format("CREATE RETENTION POLICY \"%s\" ON \"%s\" DURATION %sd REPLICATION %s ", policyName,
                dataBaseName, days, replication);
        if (isDefault) {
            sql = sql + " DEFAULT";
        }
        query(sql);
    }

    /**
     * 创建默认的保留策略
     *
     * 策略名：hour，保存天数：30天，保存副本数量：1,设为默认保留策略
     */
    public void createDefaultRetentionPolicy() {
        String command = String
                .format("CREATE RETENTION POLICY \"%s\" ON \"%s\" DURATION %s REPLICATION %s DEFAULT", "hour", dbName,
                        "30d", 1);
        this.query(command);
    }

    /**
     * 查询
     *
     * @param command 查询语句
     * @return
     */
    public QueryResult query(String command) {
        return influxDB.query(new Query(command, dbName));
    }

    /**
     * 插入
     *
     * @param measurement 表
     * @param tags        标签
     * @param fields      字段
     */
    public void insert(String measurement, Map<String, String> tags, Map<String, Object> fields, long time,
                       TimeUnit timeUnit) {
        Point.Builder builder = Point.measurement(measurement);
        builder.tag(tags);
        builder.fields(fields);
        if (0 != time) {
            builder.time(time, timeUnit);
        }
        influxDB.write(dbName, retentionPolicy, builder.build());
    }

    /**
     * 批量写入测点
     *
     * @param batchPoints
     */
    public void batchInsert(BatchPoints batchPoints, TimeUnit timeUnit) {
        influxDB.write(batchPoints);
        // influxDB.enableGzip();
        // influxDB.enableBatch(2000,100,timeUnit);
        // influxDB.disableGzip();
        // influxDB.disableBatch();
    }



    /**
     * 删除
     *
     * @param command 删除语句
     * @return 返回错误信息
     */
    public String deleteMeasurementData(String command) {
        QueryResult result = influxDB.query(new Query(command, dbName));
        return result.getError();
    }

    /**
     * 关闭数据库
     */
    public void close() {
        influxDB.close();
    }

    /**
     * 构建Point
     *
     * @param measurement
     * @param time
     * @param fields
     * @return
     */
    public Point pointBuilder(String measurement, long time, TimeUnit timeUnit, Map<String, String> tags,
                              Map<String, Object> fields) {
        Point point = Point.measurement(measurement).time(time, timeUnit).tag(tags).fields(fields).build();
        return point;
    }

    public static void main(String[] args) {
//        InfluxDBConnection influxDBConnection = new InfluxDBConnection("jove", "12345678",
//                "127.0.0.1", "bucket1", "hour");
//        Map<String, String> tags = new HashMap<>();
//        tags.put("tag1", "标签值");
//        Map<String, Object> fields1 = new HashMap<String, Object>();
//        fields1.put("field1", "abc");
//        // 数值型，InfluxDB的字段类型，由第一天插入的值得类型决定
//        fields1.put("field2", 123456);
//        Map<String, Object> fields2 = new HashMap<String, Object>();
//        fields2.put("field1", "String类型");
//        fields2.put("field2", 3.141592657);
//
//        influxDBConnection.getInfluxDB().
//        // 一条记录值
//        Point point1 = influxDBConnection.pointBuilder("test1", System.currentTimeMillis(), tags, fields1);
//        Point point2 = influxDBConnection.pointBuilder("test1", System.currentTimeMillis(), tags, fields2);
//        influxDBConnection.insert();
    }
}
