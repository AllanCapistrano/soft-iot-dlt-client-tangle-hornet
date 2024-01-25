package dlt.client.tangle.hornet.model.transactions.reputation;

import dlt.client.tangle.hornet.enums.TransactionType;
import dlt.client.tangle.hornet.model.transactions.TargetedTransaction;

/**
 * Transaction evaluating the service received.
 *
 * @author Allan Capistrano
 * @version 1.1.0
 */
public class Evaluation extends TargetedTransaction {

  private final float nodeCredibility;
  private final int serviceEvaluation;
  private final float value;

  public Evaluation(
    String source,
    String target,
    String group,
    TransactionType type,
    float nodeCredibility,
    int serviceEvaluation,
    float value
  ) {
    super(source, group, type, target);
    this.nodeCredibility = nodeCredibility;
    this.serviceEvaluation = serviceEvaluation;
    this.value = value;
  }

  public int getServiceEvaluation() {
    return serviceEvaluation;
  }

  public float getNodeCredibility() {
    return nodeCredibility;
  }

  public float getValue() {
    return value;
  }
}
