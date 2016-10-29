package test.sarwo.service.impl;

import java.io.File;
import java.io.FileWriter;
import java.util.List;
import java.util.Scanner;
import java.util.TimeZone;

import org.joda.time.DateTime;

import test.sarwo.service.LocalDateResolver;
import test.sarwo.service.ProcessData;
import test.sarwo.util.CSVReader;
import test.sarwo.util.CVSWriter;

public class ProcessDataImpl implements ProcessData {

	private static final char SEPARATOR = ',';
	
	private LocalDateResolver localDate = new LocalDateResolverImpl();
	


	@Override
	public void processData(String fileInputLocation, String fileOutputLocation) throws Exception {
		File fileInput = null;
		File fileOutput = null; //intentionally split the file, easier for user to check the result
		Scanner scan = null;
		FileWriter writer = null;
		try {
			fileInput = new File(fileInputLocation);
			scan = new Scanner(fileInput);
			fileOutput = new File(fileOutputLocation);
			if(!fileOutput.exists()) {
				fileOutput.createNewFile();
			}
			writer = new FileWriter(fileOutput);
			
			while (scan.hasNext()) {
				String data = scan.nextLine();
				CVSWriter.writeLine(writer, getLocalDateAppended(data));
			}
		} catch (Exception e) {
			throw new Exception("File Not Found!"); 
		} finally {
			scan.close();
			writer.flush();
			writer.close();
		}
	}



	private List<String> getLocalDateAppended(String nextLine) throws Exception {
		List<String> datas = CSVReader.parseLine(nextLine, SEPARATOR);
		
		if(datas == null || datas.isEmpty()) {
			throw new Exception("Data is corrupted");
		}
		
		
		double latitude = Double.valueOf(datas.get(1));
		double longitude = Double.valueOf(datas.get(2));
		
		TimeZone tz = localDate.resolveTimeZone(latitude, longitude);
		
		datas.add(tz.getID());
		
		DateTime dateTime = localDate.resolveDateTime(tz.getID());
		
		datas.add(dateTime.toString());
		
		return datas;
	}

}
