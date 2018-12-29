package win.leizhang.demo.igniteexample.config;

import org.apache.ignite.Ignite;
import org.apache.ignite.IgniteCache;
import org.apache.ignite.Ignition;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.cache.expiry.CreatedExpiryPolicy;
import javax.cache.expiry.Duration;
import javax.cache.expiry.ExpiryPolicy;
import javax.cache.expiry.ModifiedExpiryPolicy;
import java.util.concurrent.TimeUnit;

/**
 * Created by zealous on 2018/12/29.
 */
@Configuration
public class IgniteCacheConfig {

    @Bean
    public IgniteCache<Object, Object> instance() {
        Ignite ignite = Ignition.start("spring/example-cache.xml");
        ExpiryPolicy exPolicy = new CreatedExpiryPolicy(new Duration(TimeUnit.SECONDS, 50));
        IgniteCache<Object, Object> cache = ignite.getOrCreateCache("myCacheName").withExpiryPolicy(exPolicy);

        IgniteCache<Object, Object> cache2;
        return cache;
    }
}
