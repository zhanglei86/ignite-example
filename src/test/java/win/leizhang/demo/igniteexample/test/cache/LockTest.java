package win.leizhang.demo.igniteexample.test.cache;

import org.apache.ignite.Ignite;
import org.apache.ignite.IgniteCache;
import org.apache.ignite.Ignition;
import org.apache.ignite.cache.CacheAtomicityMode;
import org.apache.ignite.configuration.CacheConfiguration;
import org.apache.ignite.configuration.IgniteConfiguration;
import org.apache.ignite.configuration.TransactionConfiguration;

import java.util.concurrent.locks.Lock;

/**
 * Created by zealous on 2018/12/28.
 */
public class LockTest {

    public static void main(String[] args) {
        testLock();
    }

    private static void testLock() {
        CacheConfiguration cacheCfg = new CacheConfiguration();
        cacheCfg.setName("cacheName-lock");
        cacheCfg.setAtomicityMode(CacheAtomicityMode.TRANSACTIONAL);
        IgniteConfiguration cfg = new IgniteConfiguration();
        cfg.setCacheConfiguration(cacheCfg);
        // Optional transaction configuration. Configure TM lookup here.
        TransactionConfiguration txCfg = new TransactionConfiguration();
        cfg.setTransactionConfiguration(txCfg);

        // Start Ignite node.
        try (Ignite ignite = Ignition.start(cfg)) {
            // cfg
            IgniteConfiguration obj = ignite.configuration();
            CacheConfiguration[] obj2 = obj.getCacheConfiguration();

            IgniteCache<String, Integer> cache = ignite.getOrCreateCache("cacheName-lock");
            // Create a lock for the given key
            Lock lock = cache.lock("keyLock");
            // Acquire the lock
            lock.lock();
            try {
                cache.put("keyLock", 8888);
                cache.put("Hello", 11);
                cache.put("World", 22);
            } finally {
                lock.unlock();
            }
        }

        System.out.println("ok");
    }

}
