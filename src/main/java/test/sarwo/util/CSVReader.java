package test.sarwo.util;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class CSVReader {

	private CSVReader() {
		// prevent the instance creation
	}

	public static List<String> extractData(String fileLocation, char separator) throws Exception {
		File file = null;
		Scanner scan = null;
		List<String> result = new ArrayList<>(0);
		try {
			file = new File(fileLocation);
			scan = new Scanner(file);
			while (scan.hasNext()) {
				result.addAll(parseLine(scan.nextLine(), separator));
			}
		} catch (Exception e) {
			throw new Exception("File Not Found!");
		} finally {
			scan.close();
		}
		return result;
	}

	public static List<String> parseLine(String cvsLine, char separator) {
		List<String> result = new ArrayList<>();

		if (cvsLine == null || cvsLine.isEmpty()) {
			return result;
		}

		StringBuffer data = new StringBuffer();

		boolean inQuote = false;
		boolean containDoubleQuote = false;

		char[] chars = cvsLine.toCharArray();
		for (char ch : chars) {
			if (inQuote) {

				if (ch == '\"') {
					if (!containDoubleQuote) {
						data.append(ch);
						containDoubleQuote = true;
					}
				} else {
					data.append(ch);
				}

			} else {
				if (ch == separator) {
					result.add(data.toString());
					data = new StringBuffer();

				} else if (ch == '\r') {
					continue;
				} else if (ch == '\n') {
					break;
				} else {
					data.append(ch);
				}
			}
		}
		result.add(data.toString());
		return result;
	}
}