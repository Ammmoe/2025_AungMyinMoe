package com.oracle.assessment.backend.service;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;
import java.util.Arrays;
import java.util.ArrayList;
import java.util.Collections;


public class CoinCalculatorService {
	
	// Allowed coin denomination values
	private static final List<Double> ALLOWED_DENOMINATIONS = List.of(
		0.01, 0.05, 0.1, 0.2, 0.5, 1.0, 2.0, 5.0, 10.0, 50.0, 100.0, 1000.0
	);
	
	// Calculate the minimum number of coins
	public List<Double> calculateMinCoins(double targetAmount, List<Double> coinDenominations) {

		validateArguments(targetAmount, coinDenominations);
		
		long targetInCents = Math.round(targetAmount * 100);
		List<Long> coinsInCents = coinDenominations.stream()
				.map(coin -> Math.round(coin * 100))
				.collect(Collectors.toList());
		
		long[] dp = new long[(int)targetInCents + 1];
		long[] coinTrack = new long[(int)targetInCents + 1];
		
		Arrays.fill(dp, Long.MAX_VALUE);
		Arrays.fill(coinTrack, -1);
		
		dp[0] = 0;
		for (int i = 0; i <= targetInCents; i++) {
			for (long coin : coinsInCents) {
				if (coin <= i && dp[(int)(i - coin)] != Long.MAX_VALUE) {
					if (dp[i] > dp[(int)(i - coin)] + 1) {
						dp[i] = dp[(int)(i - coin)] + 1;
						coinTrack[i] = coin;
					}
				}
			}
		}
		
		if (dp[(int)targetInCents] == Long.MAX_VALUE) {
			throw new IllegalArgumentException("Target amount cannot be made with given coin denominations");
		}
		
		// Reconstruct the list of coins used
		List<Double> result = new ArrayList<>();
		long remaining = targetInCents;
		
		while (remaining > 0) {
			long usedCoin = coinTrack[(int)remaining];
			result.add(usedCoin / 100.0);
			remaining -= usedCoin;
		}
		
		Collections.sort(result);
		return result;
	}
	
	// Validate target amount and coin denominations
	private void validateArguments(double targetAmount, List<Double> coinDenominations) {
		BigDecimal amountDecimal = BigDecimal.valueOf(targetAmount);

		if (amountDecimal.scale() > 2) {
		    throw new IllegalArgumentException("Target amount cannot have more than 2 decimal places");
		}
		
		if (targetAmount < 0 || targetAmount > 10000) {
			throw new IllegalArgumentException("Target amount must be between 0 and 10000");
		}
		
		if (coinDenominations == null || coinDenominations.isEmpty()) {
	        throw new IllegalArgumentException("At least one valid coin denomination must be provided");
	    }
		
		for (double d : coinDenominations) {
			if (!ALLOWED_DENOMINATIONS.contains(d)) {
				throw new IllegalArgumentException("Invalid coin denomination: " + d);
			}
		}
	}
		
}
