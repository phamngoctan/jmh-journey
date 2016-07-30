package com.aiza.jmh.atomic;

import java.util.concurrent.atomic.AtomicLong;

import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.State;

//@State(Scope.Benchmark)
public class AtomicLongBenchmark {
    private AtomicLong atomicLong = new AtomicLong();
    
//    @Benchmark
    public long atomicLongBenchmark() {
        return atomicLong.getAndIncrement();
    }
}

