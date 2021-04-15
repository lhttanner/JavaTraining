package com.tannerlee;

import java.util.concurrent.Callable;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutionException;

/**
 * com.tannerlee
 *
 * @author liht
 * @desc <p>通过CompletableFuture</p>
 * @date 2021/4/15
 */
public class HomeWork06 {

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        long start = System.currentTimeMillis();
        // 在这里创建一个线程或线程池，
        // 异步执行 下面方法
        CompletableFuture<Integer> result = CompletableFuture.supplyAsync(() -> {
            Integer rs = sum();
            System.out.println(Thread.currentThread().getName() + ":" + rs);
            return rs;
        });

        // 确保  拿到result 并输出
        System.out.println("主线程获得异步计算结果为：" + result.get());
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
