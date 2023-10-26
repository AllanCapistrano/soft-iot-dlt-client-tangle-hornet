package dlt.client.tangle.hornet.model.transactions.reputation;

import dlt.client.tangle.hornet.enums.TransactionType;
import dlt.client.tangle.hornet.model.DeviceSensorId;
import dlt.client.tangle.hornet.model.transactions.Transaction;
import java.util.List;

/**
 * Transaction to indicate that a service is needed.
 *
 * @author Allan Capistrano
 * @version 1.1.0
 */
public class ReputationService extends Transaction {

  private final List<DeviceSensorId> services;

  public ReputationService(
    String source,
    String group,
    List<DeviceSensorId> services,
    TransactionType type
  ) {
    super(source, group, type);
    this.services = services;
  }

  public List<DeviceSensorId> getServices() {
    return services;
  }
}
