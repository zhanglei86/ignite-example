package win.leizhang.demo.igniteexample.test.cache;

import org.apache.ignite.Ignite;
import org.apache.ignite.IgniteCache;
import org.apache.ignite.Ignition;
import org.apache.ignite.cache.CacheAtomicityMode;
import org.apache.ignite.configuration.CacheConfiguration;
import org.apache.ignite.configuration.IgniteConfiguration;
import org.apache.ignite.configuration.TransactionConfiguration;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;

/**
 * Created by zealous on 2018/12/28.
 */
public class LockTest {

    public static void main(String[] args) {
        //testIsLocked();
        testLock1();
    }

    private static void testLock1() {
        Ignite ig = Ignition.start("spring/example-cache.xml");
        IgniteCache<String, Integer> cache = ig.getOrCreateCache("myLock");
        Lock lock = cache.lock("lockKey");

        boolean flag1 = cache.isLocalLocked("lockKey", true);
        System.out.println("1" + flag1);

        try {
            lock.tryLock(300, TimeUnit.SECONDS);
            // logic
            System.out.println("拿到锁了，开始操作");
            boolean flag2 = cache.isLocalLocked("lockKey", true);
            System.out.println("2" + flag2);

            TimeUnit.MINUTES.sleep(1);
            System.out.println("完成，准备解锁！");
        } catch (InterruptedException ite) {
            ite.printStackTrace();
        } finally {
            lock.unlock();
        }

        boolean flag3 = cache.isLocalLocked("lockKey", true);
        System.out.println("3" + flag3);

    }

    private static void testIsLocked() {
        Ignite ig = Ignition.start("spring/example-cache.xml");
        IgniteCache<String, Integer> cache = ig.getOrCreateCache("myLock");
        boolean flag = cache.isLocalLocked("lockKey", true);
        System.out.println("当前锁状态是" + flag);
        ig.close();
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
