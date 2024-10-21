package com.azo.backend.msvc.binnacle.msvc_binnacle.models.validations;

public class ValidationResult {
  private final boolean documentsValid;
  private final boolean paymentsValid;
  private final boolean statusValid;
  private final boolean isValid;

  public ValidationResult(boolean documentsValid, boolean paymentsValid, boolean statusValid, boolean isValid) {
      this.documentsValid = documentsValid;
      this.paymentsValid = paymentsValid;
      this.statusValid = statusValid;
      this.isValid = isValid;
  }

 public boolean isDocumentsValid() {
      return documentsValid;
  }

  public boolean isPaymentsValid() {
      return paymentsValid;
  }

  public boolean isStatusValid() {
      return statusValid;
  }

  public boolean isValid() {
      return isValid;
  }

}
