package com.tannerlee;

import java.util.Arrays;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * com.tannerlee
 *
 * @author liht
 * @desc <p>通过阻塞队列</p>
 * @date 2021/4/15
 */
public class HomeWork07 {

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        BlockingQueue<Integer> blockingQueue = new LinkedBlockingQueue<Integer>();
        long start = System.currentTimeMillis();
        // 在这里创建一个线程或线程池，
        // 异步执行 下面方法
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                Integer result = sum();
                System.out.println(Thread.currentThread().getName() + ":" + result);
                blockingQueue.add(result);
            }
        }, "homework07-");
        thread.start();
        while (blockingQueue.size() == 0) {
//            Thread.sleep(10);
            System.out.println("blockingQueue size : " + blockingQueue.size());
        }
        // 确保  拿到result 并输出
        System.out.println("主线程获得异步计算结果为：" + blockingQueue.take());
        System.out.println("使用时间：" + (System.currentTimeMillis() - start) + " ms");
        // 然后退出main线程
    }

    private static int sum() {
        return fibo(10);
    }

    private static int fibo(int a) {
        if (a < 2) {
            return 1;
        }
        return fibo(a - 1) + fibo(a - 2);
    }

}
