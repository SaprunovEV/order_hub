package by.sapra.orderHub.orders.infrastructure.data.metrics.acpect;

import by.sapra.orderHub.orders.infrastructure.data.metrics.annotation.BusinessMetrics;
import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.Tags;
import io.micrometer.core.instrument.Timer;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;

@Slf4j
@Aspect
@Component
@RequiredArgsConstructor
public class BusinessMetricsAspect {

    private final MeterRegistry meterRegistry;

    private final Map<String, Timer> timerMap = new ConcurrentHashMap<>();

    @Around("@annotation(metrics)")
    public Object handleBusinessMetrics(ProceedingJoinPoint joinPoint, BusinessMetrics metrics) throws Throwable {
        long startTime = System.nanoTime();

        String status = "success";

        try {
            return joinPoint.proceed();
        } catch (Throwable e) {
            status = "error";
            throw e;
        } finally {
            long duration = System.nanoTime() - startTime;

            recordMetrics(metrics, joinPoint, duration, status);
        }
    }

    private void recordMetrics(BusinessMetrics metrics, ProceedingJoinPoint joinPoint, long duration, String status) {
        String metricsName = metrics.name();

        String className = joinPoint.getTarget().getClass().getSimpleName();

        Tags allTags = Tags.of("class", className, "status", status);


        allTags = addCustomTags(allTags, metrics.tags());

        Counter.builder("%s.total".formatted(metricsName))
                .tags(allTags)
                .description("Total Calls")
                .register(meterRegistry)
                .increment();

        String timerKey = buildTimerKey(metricsName, className, metrics.tags(), status);

        Timer timer = timerMap.computeIfAbsent(timerKey, _ -> {
            Tags timerTags = Tags.of("class", className, "status", status);

            timerTags = addCustomTags(timerTags, metrics.tags());

            return Timer.builder("%s.duration".formatted(metricsName))
                    .tags(timerTags)
                    .publishPercentileHistogram()
                    .description("Timer metrics")
                    .register(meterRegistry);
        });

        timer.record(duration, TimeUnit.NANOSECONDS);
    }

    private String buildTimerKey(
            String metricsName,
            String className,
            String[] tags,
            String status
    ) {
        StringBuilder key = new StringBuilder();

        appendTag(className, key.append(metricsName));

        if (tags != null && tags.length > 0){
            String[] sorted = tags.clone();

            Arrays.sort(sorted);

            for (var tag : sorted) {
                appendTag(tag, key);
            }
        }

        appendTag(status, key);

        return key.toString();
    }

    private static void appendTag(String tag, StringBuilder key) {
        key.append('.').append(tag);
    }

    private Tags addCustomTags(Tags allTags, String[] tags) {
        if (tags == null || tags.length == 0) return allTags;

        Tags result = allTags;

        for (var tag : tags) {
            int splitter = tag.indexOf('=');

            if (splitter > 0) {
                var key = tag.substring(0, splitter);
                var value = tag.substring(splitter + 1);

                result = result.and(key, value);
            }
        }

        return result;
    }
}
