package com.clockwork.agent.hotupdate;

import com.sun.tools.attach.AgentInitializationException;
import com.sun.tools.attach.AgentLoadException;
import com.sun.tools.attach.AttachNotSupportedException;
import com.sun.tools.attach.VirtualMachine;
import org.apache.commons.io.monitor.FileAlterationListenerAdaptor;
import org.apache.commons.io.monitor.FileAlterationMonitor;
import org.apache.commons.io.monitor.FileAlterationObserver;

import java.io.File;
import java.io.IOException;
import java.lang.management.ManagementFactory;
import java.util.Date;
import java.util.logging.FileHandler;

/**
 * Created by lingkingluck
 */
public class HotUpdateWatch {

    public static final String JAR_PATH = "mmorpg-agent.jar";
    private String hotUpdateAgentJarPath;

    public static void watch(String scriptDirPath) throws Exception {
        File file = new File(JAR_PATH);
        if (!file.exists()) {
            throw new RuntimeException("not have hotupdtae agent");
        }

        HotUpdateWatch watch = new HotUpdateWatch();
        watch.hotUpdateAgentJarPath = file.getAbsolutePath();
        watch.startWatch(scriptDirPath);
    }

    private void startWatch(String scriptDirPath) throws Exception {
        File dirPath = new File(scriptDirPath);
        if (!dirPath.exists() || !dirPath.isDirectory()) {
            throw new RuntimeException("do no find this dir:" + scriptDirPath);
        }

        FileAlterationObserver observer = new FileAlterationObserver(dirPath);
        observer.addListener(new FileAlterationListenerAdaptor() {
            @Override
            public void onFileCreate(File file) {
                System.err.println(new Date() + " onFileCreate" + file.getName());

                updateClass(file);
            }

            @Override
            public void onFileChange(File file) {
                System.err.println(new Date() + " onFileChange" + file.getName());

                updateClass(file);
            }
        });

        FileAlterationMonitor monitor = new FileAlterationMonitor();
        monitor.addObserver(observer);
        monitor.start();

        System.out.println("hotupdata watch dir:" + scriptDirPath);
    }

    private void updateClass(File file) {
        try {
            String pid = ManagementFactory.getRuntimeMXBean().getName().split("@")[0];
            VirtualMachine vm = VirtualMachine.attach(pid);
            vm.loadAgent(hotUpdateAgentJarPath, file.getAbsolutePath());
        } catch (AttachNotSupportedException e) {
            e.printStackTrace();
        } catch (AgentLoadException e) {
            e.printStackTrace();
        } catch (AgentInitializationException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
