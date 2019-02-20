package win.leizhang.demo.igniteexample.test.cache;

import org.apache.ignite.Ignite;
import org.apache.ignite.IgniteCache;
import org.apache.ignite.Ignition;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import win.leizhang.demo.igniteexample.bo.EvtType;

import javax.cache.expiry.Duration;
import javax.cache.expiry.ExpiryPolicy;
import javax.cache.expiry.ModifiedExpiryPolicy;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.TimeUnit;

/**
 * Created by zealous on 2019-02-20.
 */
public class EvtNode2Test {
    private static final Logger log = LoggerFactory.getLogger(EvtNode2Test.class);

    private static final DateTimeFormatter DEFAULT_FORMATTER_LDT_A1 = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss-SSS");
    private static final ExpiryPolicy exPolicy = new ModifiedExpiryPolicy(new Duration(TimeUnit.SECONDS, 30));

    public static void main(String[] args) {
        producer();
    }

    private static void producer() {
        Ignite ignite = Ignition.start("spring/example-cache.xml");
        IgniteCache<Object, Object> cache = ignite.getOrCreateCache("evtCache").withExpiryPolicy(exPolicy);

        EvtType obj = new EvtType();
        obj.setEvtCode("001");
        obj.setEvtName("第一个");
        obj.setStatus(1);
        obj.setSyncTime(DEFAULT_FORMATTER_LDT_A1.format(LocalDateTime.now()));

        cache.put("evt001", obj);
        log.info("初始化完成1");

        // 阻塞5秒
        try {
            TimeUnit.SECONDS.sleep(5);
        } catch (InterruptedException ite) {
            ite.printStackTrace();
        }

        ExpiryPolicy exPolicy2 = new ModifiedExpiryPolicy(new Duration(TimeUnit.SECONDS, 10));
        IgniteCache<Object, Object> cache2 = ignite.getOrCreateCache("evtCache").withExpiryPolicy(exPolicy2);
        obj.setSyncTime(DEFAULT_FORMATTER_LDT_A1.format(LocalDateTime.now()));
        cache2.put("evt001", obj);
        log.info("初始化完成2");

        ignite.close();
    }

}
