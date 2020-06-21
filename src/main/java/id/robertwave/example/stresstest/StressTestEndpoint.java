package id.robertwave.example.stresstest;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.logging.Level;
import java.util.logging.Logger;

@RestController
public class StressTestEndpoint {
    Logger logger = Logger.getLogger("StressTestEndpoint");

    List<AtomicInteger> counterList = new ArrayList<>();

    @RequestMapping(value = "/start")
    public Map<String, String> start() {
        CompletableFuture.runAsync(() -> {
            AtomicInteger counter = new AtomicInteger(0);
            counterList.add(counter);

            while (counter.get() >= 0) {
                synchronized (counter) {
                    int newVal = counter.incrementAndGet();
                    if (newVal > 100) {
                        counter.set(0);
                    }
                }
            }
        });
        logger.log(Level.INFO, "started");
        return Collections.singletonMap("started", "true");
    }

    @RequestMapping("/stop")
    public Map<String, String> stop() {
        Iterator<AtomicInteger> counterIter = counterList.iterator();
        while (counterIter.hasNext()) {
            AtomicInteger counter = counterIter.next();
            synchronized(counter) {
                counter.set(Integer.MIN_VALUE);
                counterIter.remove();
            };
        }
        logger.log(Level.INFO, "stopped");
        return Collections.singletonMap("stopped", "true");
    }

}