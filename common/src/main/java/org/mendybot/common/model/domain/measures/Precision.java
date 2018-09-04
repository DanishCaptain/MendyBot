package org.mendybot.common.model.domain.measures;

public enum Precision
{
  ZERO("#0"),ONE("#0.0"),TWO("#0.00"),THREE("#0.000");
  
  private String filter;

  Precision(String filter) {
    this.filter = filter;
  }

  public String getFilter()
  {
    return filter;
  }
}
