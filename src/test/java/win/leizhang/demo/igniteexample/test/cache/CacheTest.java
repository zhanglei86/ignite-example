package win.leizhang.demo.igniteexample.test.cache;

import org.apache.ignite.Ignite;
import org.apache.ignite.IgniteCache;
import org.apache.ignite.Ignition;
import org.apache.ignite.transactions.Transaction;

import java.util.concurrent.locks.Lock;


/**
 * Created by zealous on 2018/12/27.
 */
public class CacheTest {

    public static void main(String[] args) {
        testPut();
        //testAtomOpt();
        testLock();
    }

    private static void testPut() {
        try (Ignite ignite = Ignition.start("spring/example-cache.xml")) {
            IgniteCache<String, String> cache = ignite.getOrCreateCache("myCacheName");

            // Store keys in cache (values will end up on different cache nodes).
            for (int i = 0; i < 10; i++)
                cache.put(String.valueOf(i), Integer.toString(i));

            for (int i = 0; i < 10; i++)
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

    private static void testLock() {
        try (Ignite ignite = Ignition.start("spring/example-cache.xml")) {
            IgniteCache<String, Integer> cache = ignite.getOrCreateCache("myCacheName");
            // Lock cache key "Hello".
            Lock lock = cache.lock("Hello");
            lock.lock();
            try {
                cache.put("Hello", 11);
                cache.put("World", 22);
            } finally {
                lock.unlock();
            }
        }
    }

}
