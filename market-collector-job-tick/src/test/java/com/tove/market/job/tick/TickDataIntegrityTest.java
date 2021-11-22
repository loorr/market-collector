package com.tove.market.job.tick;

import com.tove.market.job.tick.common.ToStringUtils;
import com.tove.market.job.tick.model.StockSnapshot;
import org.junit.jupiter.api.*;
import org.redisson.api.RQueue;
import org.redisson.api.RSet;
import org.redisson.api.RedissonClient;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static com.tove.market.common.Constant.*;
import static com.tove.market.job.tick.TickApplicationConfig.REDIS_URL;

@DisplayName("Tick完整性测试")
public class TickDataIntegrityTest {
    public static RedissonClient redissonClient;

    @BeforeAll
    public static void init() {
        System.out.println("初始化数据");
        RedissonDataStore redissonDataStore = new RedissonDataStore(REDIS_URL);
        redissonClient = redissonDataStore.getRedissonClient();
    }

    @AfterAll
    public static void cleanup() {
        System.out.println("清理数据");
        redissonClient.shutdown();
    }

    @BeforeEach
    public void tearup() {
        System.out.println("当前测试方法开始");
    }

    @AfterEach
    public void tearDown() {
        System.out.println("当前测试方法结束");
    }

    private Set<String> getAllSymbol(){
        RSet<String> shSet = redissonClient.getSet(SH_COMPANY_KEY);
        RSet<String> szSet = redissonClient.getSet(SZ_COMPANY_KEY);

        Set<String> totalSetData = shSet.readAll();
        Set<String> szSetData = szSet.readAll();
        totalSetData.addAll(szSetData);
        return totalSetData;
    }

    private String getCode(String symbol){
        if (symbol.charAt(0) == '6'){
            return "0" + symbol;
        }
        return "1" + symbol;
    }

    /**
     * 0. 是否同一 symbol
     * 1. 乱序
     * 2. 重复
     * 3. 间隔
     * 4. 个数
     */
    private void staticTickList(String code, List<StockSnapshot> sspList){
        if (sspList == null || sspList.size() == 0){
            System.out.println(code + "is null");
        }
        StockSnapshot frist = sspList.get(0);
        Date prev = parseDate(frist.getTime());

        int missNum = 0;
        for (int i = 1; i < sspList.size(); i++) {
            StockSnapshot item = sspList.get(i);
            if (!item.getCode().equals(code)){
                System.out.println("消息混乱: " + i + code);
            }
            Date curr = parseDate(item.getTime());
            long timeDiff = getTimeDiff(prev, curr);
            if (timeDiff < 0){
                System.out.println("乱序: " + i + code);
            }
            if (timeDiff == 0){
                System.out.println("时间重复: " + i + code);
            }
            if (timeDiff > 6*1000){
                missNum ++;
                System.out.println("消息丢失: "+ i + " "  + code + " 间隔: " + timeDiff/1000);
            }
            frist = item;
            prev = curr;
        }
         System.out.println("检查完成: " + code + " mgs: " + missNum + " - "+ sspList.size());
    }

    private long getTimeDiff(Date prev, Date after){
        if (prev == null || after == null){
            return 0;
        }
        return after.getTime() - prev.getTime();
    }

    private Date parseDate(String dateString){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        try {
            return simpleDateFormat.parse(dateString);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    @DisplayName("我的第一个测试")
    @Test
    void testFirstTest() {
        Set<String> symbolSet = getAllSymbol();
        List<String> symbolList = new ArrayList<>(symbolSet);
        int index = 0;
        for(String symbol: symbolList){
            String key = getKey(TICK_DATE_SYMBOL,  "2021/11/19", getCode(symbol));
            RQueue<String> rQueue = redissonClient.getQueue(key);
            List<String> stockSnapshotList = rQueue.readAll();
            List<StockSnapshot> sspList = stockSnapshotList.stream().map(o->{
                StockSnapshot ss = null;
                try {
                    ss = ToStringUtils.toObject(o, StockSnapshot.class);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                return ss;
            }).collect(Collectors.toList());
            System.out.println(index + " / " + symbolSet.size() + " start check: " + getCode(symbol));
            staticTickList(getCode(symbol), sspList);
            index ++;
        }


    }

    @DisplayName("我的第二个测试")
    @Test
    void testSecondTest() {
        System.out.println("我的第二个测试开始测试");
    }

}
