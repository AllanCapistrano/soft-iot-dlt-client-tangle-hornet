package dlt.client.tangle.hornet.model.transactions.reputation;

import dlt.client.tangle.hornet.enums.TransactionType;
import dlt.client.tangle.hornet.model.transactions.TargetedTransaction;

/**
 * Transaction responding to the requested service.
 *
 * @author Allan Capistrano
 * @version 1.0.0
 */
public class ReputationServiceResponse extends TargetedTransaction {

  private final String value;

  public ReputationServiceResponse(
    String source,
    String target,
    String group,
    TransactionType type,
    String value
  ) {
    super(source, group, type, target);
    this.value = value;
  }

  public String getValue() {
    return value;
  }
}
