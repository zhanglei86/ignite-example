package win.leizhang.demo.igniteexample.test.cache;

import com.alibaba.fastjson.JSON;
import org.apache.ignite.Ignite;
import org.apache.ignite.IgniteCache;
import org.apache.ignite.Ignition;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.cache.expiry.AccessedExpiryPolicy;
import javax.cache.expiry.Duration;
import javax.cache.expiry.ExpiryPolicy;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

/**
 * Created by zealous on 2019-02-20.
 */
public class EvtNode1Test {
    private static final Logger log = LoggerFactory.getLogger(EvtNode1Test.class);

    public static void main(String[] args) {
        consummer();
    }

    private static void consummer() {
        // 热点更新的策略要配置在消费端
        ExpiryPolicy exPolicy = new AccessedExpiryPolicy(new Duration(TimeUnit.SECONDS, 5));

        Ignite ignite = Ignition.start("spring/example-cache.xml");
        IgniteCache<Object, Object> cache = ignite.getOrCreateCache("evtCache").withExpiryPolicy(exPolicy);

        Object obj;
        int i = 1;
        while (true) {
            obj = cache.get("evt001");
            if (Objects.nonNull(obj)) {
                log.info("got it, counter={}", i);
                log.info("数据是==>{}", JSON.toJSONString(obj));
            }

            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException ite) {
                ite.printStackTrace();
            }
            i++;
        }
    }

}
