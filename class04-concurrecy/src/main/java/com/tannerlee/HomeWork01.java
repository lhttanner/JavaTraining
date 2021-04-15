package com.tannerlee;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

/**
 * com.tannerlee
 *
 * @author liht
 * @desc <p>通过FutureTask</p>
 * @date 2021/4/15
 */
public class HomeWork01 {
    public static void main(String[] args) throws ExecutionException, InterruptedException {

        long start = System.currentTimeMillis();

        // 在这里创建一个线程或线程池，
        // 异步执行 下面方法
        FutureTask<Integer> task = new FutureTask<Integer>(new Callable<Integer>() {
            @Override
            public Integer call() throws Exception {
                int a = sum();
                System.out.println(Thread.currentThread().getName() + "异步计算结果为：" + a);
                return a;
            }
        });
        Thread thread = new Thread(task, "homework1");
        thread.start();
        Integer result = 0;
        // 确保  拿到result 并输出
        result = task.get();
        System.out.println("主线程获得异步计算结果为：" + result);
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
