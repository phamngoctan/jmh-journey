package com.aiza.jmh;

import java.math.BigDecimal;
import java.math.MathContext;
import java.util.Random;

import com.google.caliper.Runner;
import com.google.caliper.SimpleBenchmark;

public class CaliperTest {
	public static class Benchmark1 extends SimpleBenchmark {
		private static int ARRAY_SIZE = 131072;

		private Random r;

		private BigDecimal[][] bigValues = new BigDecimal[3][];
		private double[][] doubleValues = new double[3][];

		@Override
		protected void setUp() throws Exception {
			super.setUp();
			r = new Random();

			for (int i = 0; i < 3; i++) {
				bigValues[i] = new BigDecimal[ARRAY_SIZE];
				doubleValues[i] = new double[ARRAY_SIZE];

				for (int j = 0; j < ARRAY_SIZE; j++) {
					doubleValues[i][j] = r.nextDouble() * 1000000;
					bigValues[i][j] = BigDecimal.valueOf(doubleValues[i][j]);
				}
			}
		}

		public double timeDouble(int reps) {
			double returnValue = 0;
			for (int i = 0; i < reps; i++) {
				double a = doubleValues[0][reps & 131071];
				double b = doubleValues[1][reps & 131071];
				double c = doubleValues[2][reps & 131071];
				double division = a * (1 / b) * c;
				if ((i & 255) == 0)
					returnValue = division;
			}
			return returnValue;
		}

		public BigDecimal timeBigDecimal(int reps) {
			BigDecimal returnValue = BigDecimal.ZERO;
			for (int i = 0; i < reps; i++) {
				BigDecimal a = bigValues[0][reps & 131071];
				BigDecimal b = bigValues[1][reps & 131071];
				BigDecimal c = bigValues[2][reps & 131071];
				BigDecimal division = a.multiply(BigDecimal.ONE.divide(b, MathContext.DECIMAL64).multiply(c));
				if ((i & 255) == 0)
					returnValue = division;
			}
			return returnValue;
		}

		public BigDecimal timeBigDecNoRecip(int reps) {
			BigDecimal returnValue = BigDecimal.ZERO;
			for (int i = 0; i < reps; i++) {
				BigDecimal a = bigValues[0][reps & 131071];
				BigDecimal b = bigValues[1][reps & 131071];
				BigDecimal c = bigValues[2][reps & 131071];
				BigDecimal division = a.multiply(c.divide(b, MathContext.DECIMAL64));
				if ((i & 255) == 0)
					returnValue = division;
			}
			return returnValue;
		}
	}

	public static void main(String... args) {
		String[] str = {"-DwarmupMillis=1"};
		Runner.main(Benchmark1.class, str);
	}
}
