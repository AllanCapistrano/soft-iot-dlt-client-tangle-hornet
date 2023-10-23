package dlt.client.tangle.hornet.model.transactions.reputation;

import dlt.client.tangle.hornet.enums.TransactionType;
import dlt.client.tangle.hornet.model.transactions.TargetedTransaction;

/**
 * Transaction to indicate that a service can be provided.
 *
 * @author Allan Capistrano
 * @version 1.0.0
 */
public class ReputationServiceReply extends TargetedTransaction {

  public ReputationServiceReply(
    String source,
    String target,
    String group,
    TransactionType type
  ) {
    super(source, group, type, target);
  }
}
