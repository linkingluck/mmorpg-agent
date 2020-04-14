package com.clockwork.agent.hotUpdate;

import com.clockwork.agent.hotupdate.HotUpdateWatch;
import org.junit.Test;

import java.util.concurrent.TimeUnit;

/**
 * Created by lingkingluck
 */
public class TestHotUpdate {

    @Test
    public void test() {
        System.out.println("test framwork test!");
    }

    @Test
    public void testHotUpdate() {
        try {
            HotUpdateWatch.watch("src/lib", "src/script");

            SayHello sayHello = new SayHello();
            while (true) {
                sayHello.say();

                TimeUnit.SECONDS.sleep(3);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}

