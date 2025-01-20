package com.simsimbookstore.frontserver.config;

import org.springframework.scheduling.annotation.SchedulingConfigurer;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;

public class SchedulerConfig implements SchedulingConfigurer {
    // 스케줄러에서 ThreadPool 사용하도록 설정
    @Override
    public void configureTasks(ScheduledTaskRegistrar taskRegistrar) {
        ThreadPoolTaskScheduler threadPool = new ThreadPoolTaskScheduler();

        // core 개수
        int coreNum = Runtime.getRuntime().availableProcessors();
        // ThreadPool의 Thread개수 설정
        threadPool.setPoolSize(coreNum+1);
        threadPool.initialize();

        // 스케줄러에서 쓰레드를 사용
        taskRegistrar.setTaskScheduler(threadPool);
    }
}
