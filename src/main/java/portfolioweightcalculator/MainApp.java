package portfolioweightcalculator;

public class MainApp {

	public static void main(String[] args) {
		new Portfolio().buildFromFile(args[0]).calculateFundWeight().forEach(System.out::println);
	}

}