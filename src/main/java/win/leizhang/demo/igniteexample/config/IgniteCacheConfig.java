package win.leizhang.demo.igniteexample.config;

import org.apache.ignite.Ignite;
import org.apache.ignite.IgniteCache;
import org.apache.ignite.Ignition;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Created by zealous on 2018/12/29.
 */
@Configuration
public class IgniteCacheConfig {

    @Bean
    public IgniteCache<Object, Object> instance() {
        Ignite ignite = Ignition.start("spring/example-cache.xml");
        IgniteCache<Object, Object> cache = ignite.getOrCreateCache("myCacheName");
        return cache;
    }
}
