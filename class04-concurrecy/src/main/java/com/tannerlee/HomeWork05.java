package com.tannerlee;

import java.util.concurrent.Callable;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutionException;

/**
 * com.tannerlee
 *
 * @author liht
 * @desc <p>通过CountDownLatch</p>
 * @date 2021/4/15
 */
public class HomeWork05 {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        final Integer[] result = {0};
        CountDownLatch countDownLatch = new CountDownLatch(1);
        long start = System.currentTimeMillis();
        // 在这里创建一个线程或线程池，
        // 异步执行 下面方法
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                result[0] = sum();
                System.out.println(Thread.currentThread().getName() + ":" + result[0]);
                countDownLatch.countDown();
            }
        }, "homework03-");
        thread.start();
        countDownLatch.await();
        // 确保  拿到result 并输出
        System.out.println("主线程获得异步计算结果为：" + result[0]);
        System.out.println("使用时间：" + (System.currentTimeMillis() - start) + " ms");
        // 然后退出main线程
    }

    static class MyThread implements Callable {
        @Override
        public Object call() throws Exception {
            return sum();
        }
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
