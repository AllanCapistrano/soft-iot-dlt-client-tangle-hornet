package dlt.client.tangle.hornet.model.transactions.reputation;

import dlt.client.tangle.hornet.enums.TransactionType;
import dlt.client.tangle.hornet.model.transactions.Transaction;

/**
 * Transaction to indicate that a service is needed.
 *
 * @author Allan Capistrano
 * @version 1.0.0
 */
public class ReputationService extends Transaction {

  public ReputationService(String source, String group, TransactionType type) {
    super(source, group, type);
  }
}
