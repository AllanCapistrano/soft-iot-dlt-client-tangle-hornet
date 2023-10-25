package dlt.client.tangle.hornet.model.transactions.reputation;

import dlt.client.tangle.hornet.enums.TransactionType;
import dlt.client.tangle.hornet.model.transactions.TargetedTransaction;

/**
 * Transaction evaluating the service received.
 *
 * @author Allan Capistrano
 * @version 1.0.0
 */
public class Evaluation extends TargetedTransaction {

  private final int value;

  public Evaluation(
    String source,
    String target,
    String group,
    TransactionType type,
    int value
  ) {
    super(source, group, type, target);
    this.value = value;
  }

  public int getValue() {
    return value;
  }
}
