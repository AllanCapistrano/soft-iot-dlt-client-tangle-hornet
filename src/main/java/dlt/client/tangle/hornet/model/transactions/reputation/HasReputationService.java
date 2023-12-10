package dlt.client.tangle.hornet.model.transactions.reputation;

import dlt.client.tangle.hornet.enums.TransactionType;
import dlt.client.tangle.hornet.model.transactions.Transaction;

/**
 * Transaction to ask who has a specific service.
 * 
 * @author Allan Capistrano
 * @version 1.0.0
 */
public class HasReputationService extends Transaction {

  private final String service;

  public HasReputationService(
    String source,
    String group,
    String service,
    TransactionType type
  ) {
    super(source, group, type);
    this.service = service;
  }

  public String getService() {
    return service;
  }
}
