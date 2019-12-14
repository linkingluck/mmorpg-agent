package com.clockwork.agent.hotupdate.test;

import com.clockwork.agent.hotupdate.HotUpdateWatch;

import java.util.concurrent.TimeUnit;

public class TestMain {

    public static void main(String[] args) {
        try {
            HotUpdateWatch.watch("src/lib" , "src/script");

            TestHotUpdateClass test = new TestHotUpdateClass();
            while (true) {
                test.say();

                TimeUnit.SECONDS.sleep(3);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }



    }
}
