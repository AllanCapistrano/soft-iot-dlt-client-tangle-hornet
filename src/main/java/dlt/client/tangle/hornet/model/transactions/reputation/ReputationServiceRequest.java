package dlt.client.tangle.hornet.model.transactions.reputation;

import dlt.client.tangle.hornet.enums.TransactionType;
import dlt.client.tangle.hornet.model.transactions.TargetedTransaction;

/**
 * Transaction requesting a service for a specific node.
 *
 * @author Allan Capistrano
 * @version 1.0.0
 */
public class ReputationServiceRequest extends TargetedTransaction {

  private final String serviceType;

  public ReputationServiceRequest(
    String source,
    String target,
    String group,
    TransactionType type,
    String serviceType
  ) {
    super(source, group, type, target);
    this.serviceType = serviceType;
  }

  public String getServiceType() {
    return serviceType;
  }
}
