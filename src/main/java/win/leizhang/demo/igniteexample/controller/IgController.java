package win.leizhang.demo.igniteexample.controller;

import org.apache.ignite.IgniteCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.cache.expiry.CreatedExpiryPolicy;
import javax.cache.expiry.Duration;
import javax.cache.expiry.ExpiryPolicy;
import java.util.concurrent.TimeUnit;

/**
 * Created by zealous on 2019/1/7.
 */
@RestController
@RequestMapping("ig")
public class IgController {

    @Autowired
    private IgniteCache<Object, Object> ig;

    @PostMapping("put1")
    public String testPut1() {
        //Ignite ignite = Ignition.start("spring/example-cache.xml");
        ExpiryPolicy exPolicy = new CreatedExpiryPolicy(new Duration(TimeUnit.SECONDS, 30));
        IgniteCache<Object, Object> cache = ig.withExpiryPolicy(exPolicy);

        // Store keys in cache (values will end up on different cache nodes).
        for (int i = 0; i < 10; i++) {
            cache.put(String.valueOf(i), Integer.toString(i));
        }

        return "put1 0-10";
    }

    @PostMapping("put2")
    public String testPut2() {
        ExpiryPolicy exPolicy = new CreatedExpiryPolicy(new Duration(TimeUnit.SECONDS, 50));
        IgniteCache<Object, Object> cache = ig.withExpiryPolicy(exPolicy);

        for (int i = 20; i < 25; i++) {
            cache.put(String.valueOf(i), Integer.toString(i));
        }

        return "put1 20-25";
    }

    @PostMapping("put3")
    public String testPut3() {
        ExpiryPolicy exPolicy = new CreatedExpiryPolicy(new Duration(TimeUnit.SECONDS, 70));
        IgniteCache<Object, Object> cache = ig.withExpiryPolicy(exPolicy);

        for (int i = 30; i < 35; i++) {
            cache.put(String.valueOf(i), Integer.toString(i));
        }

        return "put1 30-35";
    }
}
