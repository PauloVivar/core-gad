package com.azo.backend.msvc.binnacle.msvc_binnacle.services;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.azo.backend.msvc.binnacle.msvc_binnacle.models.entities.Request;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.export.ooxml.JRXlsxExporter;
import net.sf.jasperreports.export.SimpleExporterInput;
import net.sf.jasperreports.export.SimpleOutputStreamExporterOutput;
import net.sf.jasperreports.export.SimpleXlsxReportConfiguration;

@Service
public class JasperReportsService {
  public byte[] generatePdfReport(String reportName, Map<String, Object> parameters, List<Request> dataList) throws JRException {
      JasperPrint jasperPrint = generateJasperPrint(reportName, parameters, dataList);
      return JasperExportManager.exportReportToPdf(jasperPrint);
  }

  public byte[] generateExcelReport(String reportName, Map<String, Object> parameters, List<Request> dataList) throws JRException {
      JasperPrint jasperPrint = generateJasperPrint(reportName, parameters, dataList);
      
      ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
      JRXlsxExporter exporter = new JRXlsxExporter();
      
      exporter.setExporterInput(new SimpleExporterInput(jasperPrint));
      exporter.setExporterOutput(new SimpleOutputStreamExporterOutput(outputStream));
      
      SimpleXlsxReportConfiguration configuration = new SimpleXlsxReportConfiguration();
      configuration.setOnePagePerSheet(false);
      configuration.setRemoveEmptySpaceBetweenColumns(true);
      configuration.setRemoveEmptySpaceBetweenRows(true);
      exporter.setConfiguration(configuration);
      
      exporter.exportReport();
      
      return outputStream.toByteArray();
  }

    private JasperPrint generateJasperPrint(String reportName, Map<String, Object> parameters, List<Request> dataList) throws JRException {
        JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(dataList);
        InputStream inputStream = this.getClass().getResourceAsStream("/reports/" + reportName + ".jrxml");
        JasperReport jasperReport = JasperCompileManager.compileReport(inputStream);
        return JasperFillManager.fillReport(jasperReport, parameters, dataSource);
    }
  
}
