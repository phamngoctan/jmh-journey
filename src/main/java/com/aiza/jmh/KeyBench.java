package com.aiza.jmh;

import java.util.concurrent.TimeUnit;

import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.BenchmarkMode;
import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.annotations.OutputTimeUnit;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.State;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

import net.spy.memcached.util.StringUtils;

@State(Scope.Thread)
@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.NANOSECONDS)
public class KeyBench {

	/**
	 * 12 chars long
	 */
	private static final String SHORT_KEY = "user:michael";

	/**
	 * 240 chars long
	 */
	private static final String LONG_KEY = "thisIsAFunkyKeyWith_underscores_AndAlso334"
			+ "3252545345NumberslthisIsAFunkyKeyWith_underscores_AndAlso3343252545345Numbe"
			+ "rslthisIsAFunkyKeyWith_underscores_AndAlso3343252545345NumberslthisIsAFunkyK"
			+ "eyWith_underscores_AndAlso3343252545345Numbersl";

	@Benchmark
	public void validateShortKeyBinary() {
		StringUtils.validateKey(SHORT_KEY, true);
	}

	@Benchmark
	public void validateShortKeyAscii() {
		StringUtils.validateKey(SHORT_KEY, false);
	}

	@Benchmark
	public void validateLongKeyBinary() {
		StringUtils.validateKey(LONG_KEY, true);
	}

	@Benchmark
	public void validateLongKeyAscii() {
		StringUtils.validateKey(LONG_KEY, false);
	}
	
	public static void main(String[] args) throws RunnerException {
        Options opt = new OptionsBuilder()
                .include(KeyBench.class.getSimpleName())
                .warmupIterations(5)
                .measurementIterations(5)
                .forks(1)
                .build();

        new Runner(opt).run();
    }
}