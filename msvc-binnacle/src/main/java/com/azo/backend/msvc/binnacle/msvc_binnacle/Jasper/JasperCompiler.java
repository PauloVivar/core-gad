package com.azo.backend.msvc.binnacle.msvc_binnacle.Jasper;

import net.sf.jasperreports.engine.JasperCompileManager;

public class JasperCompiler {
  public static void main(String[] args) {
    try {
        String sourceFileName = "src/main/resources/reports/subdivision_certificate.jrxml";
        String destFileName = "src/main/resources/reports/subdivision_certificate.jasper";
        
        JasperCompileManager.compileReportToFile(sourceFileName, destFileName);
        
        System.out.println("Compilation Done!");
    } catch (Exception e) {
        e.printStackTrace();
    }
  }
}
