package com.oracle.assessment.backend.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.Test;

public class CoinCalculatorServiceTest {
	
	private final CoinCalculatorService service = new CoinCalculatorService();
	
	/* ------- STANDARD TEST CASES ------- */
	@Test
	public void testStandard_Case1() {
		// It should return the correct list of minimum coins for 7.03 with given denominations
		List<Double> coins = service.calculateMinCoins(7.03, List.of(0.01, 0.5, 1.0, 5.0, 10.0));
		assertEquals(List.of(0.01, 0.01, 0.01, 1.0, 1.0, 5.0), coins);
	}
	
	@Test
    public void testStandard_Case2() {
        // It should return the correct list of minimum coins for 103 with given denominations
        List<Double> coins = service.calculateMinCoins(103.0, List.of(1.0, 2.0, 50.0));
        assertEquals(List.of(1.0, 2.0, 50.0, 50.0), coins);
    }
	
	@Test
	public void testStandard_Case3() {
	    // It should return correct minimum coins for 4.75 with given denominations
	    List<Double> coins = service.calculateMinCoins(4.75, List.of(0.01, 0.2, 0.5, 1.0, 5.0));
	    assertEquals(List.of(0.01, 0.01, 0.01, 0.01, 0.01, 0.2, 0.5, 1.0, 1.0, 1.0, 1.0), coins);
	}

	@Test
	public void testStandard_Case4() {
	    // It should return correct minimum coins for 12.30 with given denominations
	    List<Double> coins = service.calculateMinCoins(12.30, List.of(0.1, 0.2, 0.5, 1.0, 10.0));
	    assertEquals(List.of(0.1, 0.2, 1.0, 1.0, 10.0), coins);
	}
	
	@Test
	public void testStandard_Case5() {
	    // It should return correct minimum coins for 99.30 with given denominations
	    List<Double> coins = service.calculateMinCoins(99.30, List.of(0.01, 1.0, 5.0, 10.0, 50.0));
	    assertEquals(List.of(0.01, 0.01, 0.01, 0.01, 0.01, 0.01, 0.01, 0.01, 0.01, 0.01, 
	                         0.01, 0.01, 0.01, 0.01, 0.01, 0.01, 0.01, 0.01, 0.01, 0.01,
	                         0.01, 0.01, 0.01, 0.01, 0.01, 0.01, 0.01, 0.01, 0.01, 0.01,
	                         1.0, 1.0, 1.0, 1.0, 5.0, 10.0, 10.0, 10.0, 10.0, 50.0), coins);
	}

	/* ------- EDGE CASES ------- */
	@Test
    public void testEdge_ZeroTargetAmount() {
        // It should return an empty list for target amount 0
        List<Double> coins = service.calculateMinCoins(0.0, List.of(0.01, 0.05, 0.1, 0.2));
        assertEquals(List.of(), coins);
    }
	
	@Test
	public void testEdge_TargetAmountEqualsMaxLimit() {
	    // It should correctly handle target amount equal to the maximum allowed (10,000)
	    List<Double> coins = service.calculateMinCoins(10000.0, List.of(1000.0));
	    assertEquals(List.of(1000.0, 1000.0, 1000.0, 1000.0, 1000.0,
	                        1000.0, 1000.0, 1000.0, 1000.0, 1000.0), coins);
	}

    @Test
    public void testEdge_SmallestDenominationOnly() {
        // It should return only 0.01 coins to make up amount 0.03
        List<Double> coins = service.calculateMinCoins(0.03, List.of(0.01));
        assertEquals(List.of(0.01, 0.01, 0.01), coins);
    }
    
    @Test
    public void testEdge_LargestDenominationOnly() {
        // It should return only 100.0 coins to make up amount 300.0
        List<Double> coins = service.calculateMinCoins(300.0, List.of(0.01, 5.0, 10.0, 50.0, 100.0));
        assertEquals(List.of(100.0, 100.0, 100.0), coins);
    }
    
    @Test
    public void testEdge_TargetAmountEqualsSmallestDenomination() {
        // It should return one coin equal to the smallest denomination if target matches it
        List<Double> coins = service.calculateMinCoins(0.01, List.of(0.01, 0.05, 0.1));
        assertEquals(List.of(0.01), coins);
    }

    @Test
    public void testEdge_TargetAmountEqualsLargestDenomination() {
        // It should return one coin if target amount equals one of the denominations
        List<Double> coins = service.calculateMinCoins(50.0, List.of(0.01, 1.0, 2.0, 50.0));
        assertEquals(List.of(50.0), coins);
    }
    
    @Test
    public void testEdge_GreedyMethodWeakness() {
    	// It should return a list of only smaller coins as the target amount is only solvable with smaller coins
    	List<Double> coins = service.calculateMinCoins(0.8, List.of(0.2, 0.5));
    	assertEquals(List.of(0.2, 0.2, 0.2, 0.2), coins);
    }
    
    /* ------- DEFENSIVE TESTS ------- */
    @Test
    public void testDefensive_TargetAmountWithMoreThanTwoDecimals() {
        // It should throw IllegalArgumentException for target amount with more than 2 decimal places
        Exception exception = assertThrows(IllegalArgumentException.class, () ->
            service.calculateMinCoins(5.123, List.of(0.01, 0.05, 0.1, 0.5, 1.0))
        );
        assertTrue(exception.getMessage().contains("Target amount cannot have more than 2 decimal places"));
    }
    
    @Test
    public void testDefensive_NegativeAmount() {
        // It should throw IllegalArgumentException for negative target amount
        Exception exception = assertThrows(IllegalArgumentException.class, () ->
            service.calculateMinCoins(-5.0, List.of(0.5, 1.0))
        );
        assertTrue(exception.getMessage().contains("Target amount must be between 0 and 10000"));
    }
    
    @Test
    public void testDefensive_AmountExceedsLimit() {
        // It should throw IllegalArgumentException for target amount exceeding 10,000
        Exception exception = assertThrows(IllegalArgumentException.class, () ->
            service.calculateMinCoins(15000.0, List.of(0.5, 1.0, 5.0))
        );
        assertTrue(exception.getMessage().contains("Target amount must be between 0 and 10000"));
    }
    
    @Test
    public void testDefensive_EmptyDenominations() {
        // It should throw IllegalArgumentException if no coin denominations provided
        Exception exception = assertThrows(IllegalArgumentException.class, () ->
            service.calculateMinCoins(5.0, List.of())
        );
        assertTrue(exception.getMessage().contains("At least one valid coin denomination must be provided"));
    }
    
    @Test
    public void testDefensive_InvalidDenomination() {
        // It should throw IllegalArgumentException for invalid coin denominations
        Exception exception = assertThrows(IllegalArgumentException.class, () ->
            service.calculateMinCoins(10, List.of(0.03, 1.0))
        );
        assertTrue(exception.getMessage().contains("Invalid coin denomination"));
    }

    @Test
    public void testDefensive_ImpossibleAmount() {
        // It should throw IllegalArgumentException if amount cannot be made with given coins
        Exception exception = assertThrows(IllegalArgumentException.class, () ->
            service.calculateMinCoins(3, List.of(2.0))
        );
        assertTrue(exception.getMessage().contains("Target amount cannot be made with given coin denominations"));
    }
    
}
