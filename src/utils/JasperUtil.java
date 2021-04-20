package utils;

import java.io.File;
import java.sql.Connection;
import java.util.HashMap;
import java.util.Map;

import entities.Configuration;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.export.*;
import net.sf.jasperreports.engine.export.*;


public class JasperUtil {
	/*
	 * Variables used to find reports path
	 */
	private static final String folderPath = System.getProperty("user.dir") + "//Resources//Reports//";
	private static final String FIRST_REPORT_SRC = folderPath + "FirstReport.jrxml";
	private static final String SECOND_REPORT_SRC = folderPath + "SecondReport.jrxml";
	private static final String THIRD_REPORT_SRC = folderPath + "ThirdReport.jrxml";
	/*
	 * Generates report from given parameters. Obtains connection from given configuration
	 */
	public static void generateFirstReport(String department, String filename, Configuration configuration) throws JRException {
		//Compile report
		JasperReport jasperReport = JasperCompileManager.compileReport(FIRST_REPORT_SRC);
		Connection conn = DBServiceUtil.getConnection(
				configuration.getIp(), 
				Integer.valueOf(configuration.getPort()), 
				configuration.getUsername(), 
				configuration.getPassword()
				);
		// Parameters for the report
		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("DEPARTMENT_NO", department);
		JasperPrint print = JasperFillManager.fillReport(jasperReport, parameters, conn);
		// Export to: 
		if(configuration.isFirstPDF()) exportToPDF(print, configuration.getPath(), filename);
		if(configuration.isFirstCSV()) exportToCSV(print, configuration.getPath(), filename);
		if(configuration.isFirstHTML()) exportToHTML(print, configuration.getPath(), filename);
	}
	/*
	 * Generates report from given parameters. Obtains connection from given configuration
	 */
	public static void generateSecondReport(Integer employeeNumber, String filename, Configuration configuration) throws JRException {
		//Compile report
		JasperReport jasperReport = JasperCompileManager.compileReport(SECOND_REPORT_SRC);
		Connection conn = DBServiceUtil.getConnection(
				configuration.getIp(), 
				Integer.valueOf(configuration.getPort()), 
				configuration.getUsername(), 
				configuration.getPassword()
				);
		// Parameters for the report
		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("employeeNumber", employeeNumber);
		JasperPrint print = JasperFillManager.fillReport(jasperReport, parameters, conn);
		// Export to: 
		if(configuration.isSecondPDF()) exportToPDF(print, configuration.getPath(), filename);
		if(configuration.isSecondCSV()) exportToCSV(print, configuration.getPath(), filename);
		if(configuration.isSecondHTML()) exportToHTML(print, configuration.getPath(), filename);
	}
	/*
	 * Generates report from given parameters. Obtains connection from given configuration
	 */
	public static void generateThirdReport(String dateFrom, String dateTo, String dateFromShow, String dateToShow, String filename, Configuration configuration) throws JRException {
		//Compile report
		JasperReport jasperReport = JasperCompileManager.compileReport(THIRD_REPORT_SRC);
		Connection conn = DBServiceUtil.getConnection(
				configuration.getIp(), 
				Integer.valueOf(configuration.getPort()), 
				configuration.getUsername(), 
				configuration.getPassword()
				);
		// Parameters for the report
		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("inputDateFrom", dateFrom);
		parameters.put("inputDateTo", dateTo);
		parameters.put("dateFrom", dateFromShow);
		parameters.put("dateTo", dateToShow);
		JasperPrint print = JasperFillManager.fillReport(jasperReport, parameters, conn);
		// Export to: 
		createDir(configuration.getPath());
		if(configuration.isThirdPDF()) exportToPDF(print, configuration.getPath(), filename);
		if(configuration.isThirdCSV()) exportToCSV(print, configuration.getPath(), filename);
		if(configuration.isThirdHTML()) exportToHTML(print, configuration.getPath(), filename);
	}
	// Creates a directory in the given path
	private static void createDir(String path) {
		File outDir = new File(path);
		outDir.mkdirs();
	}
	/*
	 * Exports Jasperprint to PDF using JRPdfExporter
	 */
	private static void exportToPDF(JasperPrint jasperPrint, String outputPath, String filename) throws JRException {
		JRPdfExporter exporter = new JRPdfExporter();
		ExporterInput exporterInput = new SimpleExporterInput(jasperPrint);
		exporter.setExporterInput(exporterInput);
		filename = filename != null ? filename : "file";
		OutputStreamExporterOutput exporterOutput = new SimpleOutputStreamExporterOutput(
				outputPath + "/"+ filename +".pdf");
		exporter.setExporterOutput(exporterOutput);
		SimplePdfExporterConfiguration config = new SimplePdfExporterConfiguration();
		exporter.setConfiguration(config);
		exporter.exportReport();
	}
	/*
	 * Exports Jasperprint to CSV using JRCsvExporter
	 */
	private static void exportToCSV(JasperPrint jasperPrint, String outputPath, String filename) throws JRException {
		JRCsvExporter exporter = new JRCsvExporter();
		exporter.setExporterInput(new SimpleExporterInput(jasperPrint));
		filename = filename != null ? filename : "file";
		exporter.setExporterOutput(new SimpleWriterExporterOutput(new File(outputPath + "/" + filename +".csv")));
	    SimpleCsvReportConfiguration configuration = new SimpleCsvReportConfiguration();
	    exporter.setConfiguration(configuration);
	    exporter.exportReport();
	}
	/*
	 * Exports Jasperprint to HTML using HtmlExporter
	 */
	private static void exportToHTML(JasperPrint jasperPrint, String outputPath, String filename) throws JRException {
		HtmlExporter exporter = new HtmlExporter();
	    exporter.setExporterInput(new SimpleExporterInput(jasperPrint));
		exporter.setExporterInput(new SimpleExporterInput(jasperPrint));
		filename = filename != null ? filename : "file";
		exporter.setExporterOutput(new SimpleHtmlExporterOutput(new File(outputPath + "/"+ filename +".html")));
	    exporter.exportReport();
	}
}
