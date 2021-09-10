package com.coderscampus.assignment6;

import java.util.ArrayList;
import java.util.IntSummaryStatistics;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.io.IOException;
import java.io.File;
import java.io.BufferedReader;
import java.io.FileReader;

public class AnalyzingSalesApplication {

	public static void main(String[] args) throws IOException {
		
		List<List<Integer>> model3 = readFromFile(new File("model3.txt"));
		List<List<Integer>> modelS = readFromFile(new File("modelS.txt"));
		List<List<Integer>> modelX = readFromFile(new File("modelX.txt"));
		
		analyzeModel (model3, 17, "3");
		analyzeModel (modelS, 16, "S");
		analyzeModel (modelX, 16, "X");	
		
	}
	
	public static List<List<Integer>> readFromFile(File file) throws IOException {
		BufferedReader reader = null;
		List<List<Integer>> listOfSales = null;
		
		try {
			reader = new BufferedReader(new FileReader(file));
			listOfSales = new ArrayList<>();
			reader.readLine();
			
			String currentLine = reader.readLine();
			while (currentLine != null) {
				String[] line = currentLine.split("-");
				List<Integer> list = new ArrayList<>();
				
				switch(line[0]) {
				case "Jan":
					line[0] = "1";
					break;
				case "Feb":
					line[0] = "2";
					break;
				case "Mar":
					line[0] = "3";
					break;
				case "Apr":
					line[0] = "4";
					break;
				case "May":
					line[0] = "5";
					break;
				case "Jun":
					line[0] = "6";
					break;
				case "Jul":
					line[0] = "7";
					break;
				case "Aug":
					line[0] = "8";
					break;
				case "Sep":
					line[0] = "9";
					break;
				case "Oct":
					line[0] = "10";
					break;
				case "Nov":
					line[0] = "11";
					break;
				case "Dec":
					line[0] = "12";
					break;
				}

				list.add(Integer.parseInt(line[0]));
				line = line[1].split(",");
				list.add(Integer.parseInt(line[0]));
				list.add(Integer.parseInt(line[1]));
				
				listOfSales.add(list);
				currentLine = reader.readLine();
			}
		} finally {
			if (reader != null) reader.close();
		}
		
		return listOfSales;
	}
	
	private static int findSum(Integer year, Map<Object, List<List<Integer>>> groupedByYear){
		return groupedByYear.get(year)
				   .stream()	   
				   .flatMap(x -> x.stream())
				   .filter(x -> x > year)
				   .mapToInt(d -> d.intValue())
				   .sum();
	}
	
	public static void analyzeModel (List<List<Integer>> model, int year, String type) {
		Map<Object, List<List<Integer>>> groupedByYear = model.stream()
				.collect(Collectors.groupingBy(x -> x.get(1)));

		IntSummaryStatistics sumStats = model.stream()
				.flatMap(x -> x.stream())
				.filter(x -> x > 19)
				.mapToInt(x -> x.intValue())
				.summaryStatistics();

		Integer max = sumStats.getMax();
		Integer min = sumStats.getMin();

		List<Integer> liMax = null;
		List<Integer> liMin = null;

		for (int k=year; k<20; k++) {
			for (int i=0; i<groupedByYear.get(k).size(); i++) {
				for (int j=0; j<groupedByYear.get(k).get(i).size(); j++) {
					if (groupedByYear.get(k).get(i).get(j).equals(max)) {
						liMax = groupedByYear.get(k).get(i);
					}
					if (groupedByYear.get(k).get(i).get(j).equals(min)) {
						liMin = groupedByYear.get(k).get(i);
					}
				}
			}
		}


		System.out.println("Model " + type + " Yearly Sales Report");
		System.out.println("---------------------------");
		for (int i = year; i < 20; i++) {
			int sum = findSum(i, groupedByYear);
			System.out.println("20" + i + " -> " + sum);
		}
		System.out.println();
		
		String result = "" + liMax.get(0);
		if (liMax.get(0) < 10) {
			result = "0"+liMax.get(0);
		}
		System.out.println("The best month for Model 3 was: 20" + liMax.get(1) + "-" + result);
		result = "" + liMin.get(0);
		if (liMin.get(0) < 10) {
			result = "0"+liMin.get(0);
		}
		System.out.println("The worst month for Model 3 was: 20" + liMin.get(1) + "-" + result);
		System.out.println();
	}

}
