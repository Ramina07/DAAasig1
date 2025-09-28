package org.example.bench;

import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

public class RunJmh {
    public static void main(String[] args) throws Exception {
        Options opt = new OptionsBuilder()
                .include(SelectBench.class.getSimpleName()) // какой бенч запускать
                .forks(1)
                .warmupIterations(5)
                .measurementIterations(10)
                .build();
        new Runner(opt).run();
    }
}

