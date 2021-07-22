package bht.expense.email.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * @author 姚轶文
 * @create 2020- 02-25 17:05
 * @update 2020- 10-20 17:05 修改bean taskExecutor名称为 hrmsTaskExecutor 解决与activiti冲突
 */
@EnableAsync
@Configuration
public class ThreadPoolConfig {

    //使用时在需要异步执行的方法上加上@Async("taskExecutor")
    @Bean("expenseTaskExecutor")
    public Executor taskExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(200); //核心线程数200：线程池创建时候初始化的线程数
        executor.setMaxPoolSize(500); //最大线程数500：线程池最大的线程数，只有在缓冲队列满了之后才会申请超过核心线程数的线程
        executor.setQueueCapacity(10000); //缓冲队列10000：用来缓冲执行任务的队列
        executor.setKeepAliveSeconds(60); //允许线程的空闲时间60秒：当超过了核心线程出之外的线程在空闲时间到达之后会被销毁
        executor.setThreadNamePrefix("expense--mail--");  //线程池名的前缀
        executor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy()); //线程池对拒绝任务的处理策略：这里采用了CallerRunsPolicy策略，当线程池没有处理能力的时候，该策略会直接在 execute 方法的调用线程中运行被拒绝的任务；如果执行程序已关闭，则会丢弃该任务
        executor.setWaitForTasksToCompleteOnShutdown(true); //用来设置线程池关闭的时候等待所有任务都完成再继续销毁其他的Bean
        executor.setAwaitTerminationSeconds(60); //该方法用来设置线程池中任务的等待时间，如果超过这个时候还没有销毁就强制销毁，以确保应用最后能够被关闭，而不是阻塞住。
        return executor;
    }

}
