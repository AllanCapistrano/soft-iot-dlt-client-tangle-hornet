package br.uefs.larsid.iot.soft.model;

import br.uefs.larsid.iot.soft.model.tangle.hornet.ApiMessage;
import br.uefs.larsid.iot.soft.model.tangle.hornet.Message;
import br.uefs.larsid.iot.soft.model.transactions.Transaction;
import br.uefs.larsid.iot.soft.services.ILedgerReader;
import br.uefs.larsid.iot.soft.services.ILedgerSubscriber;
import com.google.gson.Gson;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.logging.Logger;

/**
 * @author Allan Capistrano, Antonio Crispim, Uellington Damasceno
 * @version 1.0.0
 */
public class LedgerReader implements ILedgerReader, Runnable {

  private boolean debugModeValue;

  private String urlApi;
  private Thread DLTInboundMonitor;
  private final Map<String, Set<ILedgerSubscriber>> topics;
  private ZMQServer server;

  private static final Logger logger = Logger.getLogger(
    LedgerReader.class.getName()
  );

  private static final String ENDPOINT = "message";
  private static final String ENDPOINT_MESSAGE_ID = "message/messageId";

  public LedgerReader(String protocol, String url, int port) {
    this.urlApi = String.format("%s://%s:%s", protocol, url, port);
    this.topics = new HashMap<String, Set<ILedgerSubscriber>>();
  }

  public void start() {
    // TODO: Temporário, remover depois
    logger.info(this.getMessagesByIndex("LB_STATUS"));
    logger.info(
      this.getMessageByMessageId(
          "fc765ed8c6a95ca200997cd0cb18e811294ee7a9c84ea0ec9d6544ed16495328"
        )
        .getType()
        .name()
    );

    if (this.DLTInboundMonitor == null) {
      this.DLTInboundMonitor = new Thread(this);
      this.DLTInboundMonitor.setName("CLIENT_TANGLE/DLT_INBOUND_MONITOR");
      this.DLTInboundMonitor.start();
    }
  }

  public void stop() {
    this.server.stop();
    this.DLTInboundMonitor.interrupt();
  }

  /**
   * Get all messages by a given index.
   *
   * @param index String - Message index
   */
  @Override
  public String getMessagesByIndex(String index) { // TODO: Renomear para transactions
    String response = null;

    try {
      URL url = new URL(
        String.format("%s/%s/%s", this.urlApi, ENDPOINT, index)
      );
      HttpURLConnection conn = (HttpURLConnection) url.openConnection();

      if (conn.getResponseCode() != HttpURLConnection.HTTP_OK) {
        throw new RuntimeException(
          "HTTP error code : " + conn.getResponseCode()
        );
      }

      BufferedReader br = new BufferedReader(
        new InputStreamReader((conn.getInputStream()))
      );

      String temp = null;

      while ((temp = br.readLine()) != null) {
        response = temp;
      }

      conn.disconnect();

      return response;
    } catch (MalformedURLException mue) {
      if (debugModeValue) {
        logger.severe(mue.getMessage());
      }
    } catch (IOException ioe) {
      if (debugModeValue) {
        logger.severe(ioe.getMessage());
      }
    }

    return response;
  }

  /**
   * Get a message by a given message ID.
   *
   * @param messageId String - Message ID.
   */
  @Override
  public Transaction getMessageByMessageId(String messageId) { // TODO: Renomear para transactions
    String response = null;

    try {
      Gson gson = new Gson();
      URL url = new URL(
        String.format("%s/%s/%s/", this.urlApi, ENDPOINT_MESSAGE_ID, messageId)
      );
      HttpURLConnection conn = (HttpURLConnection) url.openConnection();

      if (conn.getResponseCode() != HttpURLConnection.HTTP_OK) {
        throw new RuntimeException(
          "HTTP error code : " + conn.getResponseCode()
        );
      }

      BufferedReader br = new BufferedReader(
        new InputStreamReader((conn.getInputStream()))
      );

      String temp = null;

      while ((temp = br.readLine()) != null) {
        response = temp;
      }

      conn.disconnect();

      ApiMessage message = gson.fromJson(response, ApiMessage.class);

      return Transaction.getTransactionObjectByType(message.getContent());
    } catch (MalformedURLException mue) {
      if (debugModeValue) {
        logger.severe(mue.getMessage());
      }
    } catch (IOException ioe) {
      if (debugModeValue) {
        logger.severe(ioe.getMessage());
      }
    }

    return null;
  }

  @Override
  public void subscribe(String topic, ILedgerSubscriber subscriber) {
    if (topic != null) {
      Set<ILedgerSubscriber> subscribers = this.topics.get(topic);
      if (subscribers != null) {
        subscribers.add(subscriber);
      } else {
        subscribers = new HashSet<ILedgerSubscriber>();
        subscribers.add(subscriber);
        this.topics.put(topic, subscribers);
        this.server.subscribe(topic);
      }
    }
  }

  @Override
  public void unsubscribe(String topic, ILedgerSubscriber subscriber) {
    if (topic != null) {
      Set<ILedgerSubscriber> subscribers = this.topics.get(topic);
      if (subscribers != null && !subscribers.isEmpty()) {
        subscribers.remove(subscriber);
        if (subscribers.isEmpty()) {
          this.server.unsubscribe(topic);
          this.topics.remove(topic);
        }
      }
    }
  }

  @Override
  public void run() {
    while (!this.DLTInboundMonitor.isInterrupted()) {
      try {
        String receivedMessage = this.server.take();

        if (receivedMessage != null && receivedMessage.contains("/")) {
          Gson gson = new Gson();
          String[] data = receivedMessage.split("/");
          String topic = data[0];

          Message message = gson.fromJson(data[1], Message.class);

          notifyAll(
            topic,
            Transaction.getTransactionObjectByType(
              message.getPayload().getData()
            ),
            message.getId()
          );
        }
      } catch (InterruptedException ex) {
        logger.info(ex.getMessage());
        this.DLTInboundMonitor.interrupt();
      }
    }
  }

  private void notifyAll(String topic, Object object, Object object2) {
    if (topic != null && !topic.isEmpty()) {
      Set<ILedgerSubscriber> subscribers = this.topics.get(topic);
      if (subscribers != null && !subscribers.isEmpty()) {
        subscribers.forEach(sub -> sub.update(object, object2));
      }
    }
  }

  public String getUrlApi() {
    return urlApi;
  }

  public void setUrlApi(String urlApi) {
    this.urlApi = urlApi;
  }

  public void setServer(ZMQServer server) {
    this.server = server;
  }

  public void setDebugModeValue(boolean debugModeValue) {
    this.debugModeValue = debugModeValue;
  }
}
