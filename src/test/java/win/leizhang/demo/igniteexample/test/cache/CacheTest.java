package win.leizhang.demo.igniteexample.test.cache;

import org.apache.ignite.Ignite;
import org.apache.ignite.IgniteCache;
import org.apache.ignite.Ignition;
import org.apache.ignite.transactions.Transaction;
import org.springframework.beans.factory.annotation.Autowired;

import javax.cache.expiry.CreatedExpiryPolicy;
import javax.cache.expiry.Duration;
import javax.cache.expiry.ExpiryPolicy;
import java.util.concurrent.TimeUnit;


/**
 * Created by zealous on 2018/12/27.
 */
public class CacheTest {

    @Autowired
    private IgniteCache<Object, Object> cache;

    public static void main(String[] args) {
        //testPutAndGet();
        testPutAndGet2();
        //testPutAndGet3();
        //testGet();
        //testAtomOpt();
    }

    private static void testPutAndGet() {
        Ignite ignite = Ignition.start("spring/example-cache.xml");
        ExpiryPolicy exPolicy = new CreatedExpiryPolicy(new Duration(TimeUnit.SECONDS, 40));
        IgniteCache<Object, Object> cache = ignite.cache("myCacheName").withExpiryPolicy(exPolicy);

        // Store keys in cache (values will end up on different cache nodes).
        for (int i = 0; i < 10; i++) {
            cache.put(String.valueOf(i), Integer.toString(i));
        }
        for (int i = 0; i < 10; i++) {
            System.out.println("Got [key=" + i + ", val=" + cache.get(String.valueOf(i)) + ']');
        }
    }

    private static void testPutAndGet2() {
        Ignite ignite = Ignition.start("spring/example-cache.xml");
        ExpiryPolicy exPolicy = new CreatedExpiryPolicy(new Duration(TimeUnit.SECONDS, 60));
        IgniteCache<Object, Object> cache = ignite.cache("myCacheName").withExpiryPolicy(exPolicy);

        for (int i = 100; i < 105; i++) {
            cache.put(String.valueOf(i), Integer.toString(i));
        }
        for (int i = 100; i < 105; i++) {
            System.out.println("Got [key=" + i + ", val=" + cache.get(String.valueOf(i)) + ']');
        }
    }

    private static void testPutAndGet3() {
        Ignite ignite = Ignition.start("spring/example-cache.xml");
        ExpiryPolicy exPolicy = new CreatedExpiryPolicy(new Duration(TimeUnit.SECONDS, 100));
        IgniteCache<Object, Object> cache = ignite.cache("myCacheName").withExpiryPolicy(exPolicy);

        for (int i = 106; i < 110; i++) {
            cache.put(String.valueOf(i), Integer.toString(i));
        }
        for (int i = 106; i < 110; i++) {
            System.out.println("Got [key=" + i + ", val=" + cache.get(String.valueOf(i)) + ']');
        }
    }

    private static void testGet() {
        Ignite ignite = Ignition.start("spring/example-cache.xml");
        IgniteCache<String, String> cache = ignite.cache("myCacheName");

        for (int i = 0; i < 10; i++) {
            System.out.println("Got [key=" + i + ", val=" + cache.get(String.valueOf(i)) + ']');
        }

        for (int i = 100; i < 105; i++) {
            System.out.println("Got [key=" + i + ", val=" + cache.get(String.valueOf(i)) + ']');
        }

    }

    private static void testAtomOpt() {
        try (Ignite ignite = Ignition.start("spring/example-cache.xml")) {
            IgniteCache<String, Integer> cache = ignite.getOrCreateCache("myCacheName");

            // Put-if-absent which returns previous value.
            Integer oldVal = cache.getAndPutIfAbsent("Hello", 11);
            boolean success = cache.putIfAbsent("World", 22);

            oldVal = cache.getAndReplace("Hello", 11);
            success = cache.replace("World", 22);

            success = cache.replace("World", 2, 22);
            success = cache.remove("Hello", 1);

            // txn
            try (Transaction tx = ignite.transactions().txStart()) {
                Integer hello = cache.get("Hello");
                if (hello == 1) {
                    cache.put("Hello", 11);
                }
                cache.put("World", 22);
                tx.commit();
            }
        }
    }

}
