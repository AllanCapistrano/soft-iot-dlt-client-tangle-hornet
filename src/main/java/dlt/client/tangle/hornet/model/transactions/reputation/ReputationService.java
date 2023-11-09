package dlt.client.tangle.hornet.model.transactions.reputation;

import dlt.client.tangle.hornet.enums.TransactionType;
import dlt.client.tangle.hornet.model.DeviceSensorId;
import dlt.client.tangle.hornet.model.transactions.TargetedTransaction;
import java.util.List;

/**
 * Transaction to indicate which services the node has.
 *
 * @author Allan Capistrano
 * @version 1.1.0
 */
public class ReputationService extends TargetedTransaction {

  private final String sourceIp;
  private final List<DeviceSensorId> services;

  public ReputationService(
    String source,
    String sourceIp,
    String target,
    List<DeviceSensorId> services,
    String group,
    TransactionType type
  ) {
    super(source, group, type, target);
    this.sourceIp = sourceIp;
    this.services = services;
  }

  public String getSourceIp() {
    return sourceIp;
  }

  public List<DeviceSensorId> getServices() {
    return services;
  }
}
