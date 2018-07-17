package portfolioweightcalculator;

import java.util.List;

import org.junit.Test;
import static org.junit.Assert.*;


public class PortfolioStructureTest {
	
	String[] expected = {
			"A,D,0.167",
			"A,E,0.083",
			"A,F,0.083",
			"A,G,0.333",
			"A,H,0.333", 
			"B,D,0.500",
			"B,E,0.250",
			"B,F,0.250",
			"C,G,0.500",
			"C,H,0.500"
	};
	
	@Test
	public void testOrderTopToBottom() {
		List<String> fundWeight = new Portfolio()
			.build("A", "B", 1000)
			.build("A", "C", 2000)
			.build("B", "D", 500)
			.build("B", "E", 250)
			.build("B", "F", 250)
			.build("C", "G", 1000)
			.build("C", "H", 1000)
		.calculateFundWeight();
		assertArrayEquals(expected, fundWeight.stream().toArray(String[]::new));
	}
	
	@Test
	public void testOrderBottomToTop() {
		List<String> fundWeight = new Portfolio()
			.build("C", "G", 1000)
			.build("C", "H", 1000)
			.build("B", "D", 500)
			.build("B", "E", 250)
			.build("B", "F", 250)
			.build("A", "B", 1000)
			.build("A", "C", 2000)
		.calculateFundWeight();
		assertArrayEquals(expected, fundWeight.stream().toArray(String[]::new));
	}
	
	@Test
	public void testMultiParentFunds() {
		List<String> fundWeight = new Portfolio()
			.build("X", "Z", 100)
			.build("Y", "Z", 100)
		.calculateFundWeight();
		assertArrayEquals(new String[]{"X,Z,1.000", "Y,Z,1.000"}, fundWeight.stream().toArray(String[]::new));
	}
	
}
