package win.leizhang.demo.igniteexample.service;

import org.apache.ignite.Ignite;
import org.apache.ignite.IgniteCache;
import org.apache.ignite.Ignition;
import org.apache.ignite.cache.CacheAtomicityMode;
import org.apache.ignite.cache.CacheMode;
import org.apache.ignite.configuration.CacheConfiguration;
import org.apache.ignite.configuration.IgniteConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.cache.expiry.CreatedExpiryPolicy;
import javax.cache.expiry.Duration;
import javax.cache.expiry.ExpiryPolicy;
import java.util.concurrent.TimeUnit;

/**
 * Created by zealous on 2018/12/28.
 */
@Component
public class JobGetData {

    private static final long START = System.currentTimeMillis();
    /*private static IgniteCache<Object, Object> cache;

    static {
        Ignite ignite = Ignition.start("spring/example-cache.xml");

        IgniteConfiguration obj = ignite.configuration();
        CacheConfiguration[] obj2 = obj.getCacheConfiguration();

        cache = ignite.getOrCreateCache("myCacheName");
    }*/

    @Autowired
    private IgniteCache<Object, Object> cache;

    @Scheduled(cron = "1/10 * * * * ?")
    public void testGet() {

        for (int i = 0; i < 10; i++) {
            System.out.println("Got [key=" + i + ", val=" + cache.get(String.valueOf(i)) + ']');
        }
        System.err.println("elapsedTime ==>" + (System.currentTimeMillis() - START) / 1000);
    }

}
