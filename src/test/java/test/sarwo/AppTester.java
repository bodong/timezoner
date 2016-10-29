package test.sarwo;

import org.testng.annotations.Test;

import test.sarwo.service.ProcessData;
import test.sarwo.service.impl.ProcessDataImpl;

public class AppTester {
	
	@Test(priority = 1)
	public void testApp() throws Exception {
		String input = "src/main/resources/data.csv";
		String output = "src/main/resources/data_out.csv";
		
		ProcessData process = new ProcessDataImpl();
		process.processData(input, output);
		
	}
}
