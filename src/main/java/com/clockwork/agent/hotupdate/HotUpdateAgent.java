package com.clockwork.agent.hotupdate;

import java.io.*;
import java.lang.instrument.ClassDefinition;
import java.lang.instrument.Instrumentation;
import java.lang.instrument.UnmodifiableClassException;

/**
 * Created by lingkingluck
 */
public class HotUpdateAgent {

    public static void agentmain(String agentArgs, Instrumentation inst) throws IOException, ClassNotFoundException, UnmodifiableClassException {
        String path = agentArgs;
        File file = new File(path);
        DataInputStream in = new DataInputStream(new FileInputStream(file));
        byte[] byteCode = new byte[(int) file.length()];
        in.readFully(byteCode);
        Class<?> clz = Class.forName(path);
        ClassDefinition classDefinition = new ClassDefinition(clz, byteCode);
        inst.redefineClasses(classDefinition);
    }
}
