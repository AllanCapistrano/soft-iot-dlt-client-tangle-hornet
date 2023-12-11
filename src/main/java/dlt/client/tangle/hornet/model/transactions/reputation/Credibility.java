package dlt.client.tangle.hornet.model.transactions.reputation;

import dlt.client.tangle.hornet.enums.TransactionType;
import dlt.client.tangle.hornet.model.transactions.Transaction;

/**
 * Node credibility transaction.
 *
 * @author Allan Capistrano
 * @version 1.0.0
 */
public class Credibility extends Transaction {

  private final float value;

  public Credibility(
    String source,
    String group,
    TransactionType type,
    float value
  ) {
    super(source, group, type);
    this.value = value;
  }

  public float getValue() {
    return value;
  }
}
