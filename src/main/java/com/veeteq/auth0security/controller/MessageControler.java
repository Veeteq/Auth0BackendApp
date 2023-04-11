package com.veeteq.auth0security.controller;

import java.text.MessageFormat;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.veeteq.auth0security.config.CloudConfig;
import com.veeteq.auth0security.dto.Message;

@RestController
@RequestMapping(path = "/api", produces = MediaType.APPLICATION_JSON_VALUE)
@CrossOrigin(origins = "*")
public class MessageControler {
  private final Logger LOG = LoggerFactory.getLogger(ItemController.class);

  @Value("${auth0.audience}")
  private String audience;

  @Value("${spring.security.oauth2.resourceserver.jwt.issuer-uri}")
  private String issuer;

  private final CloudConfig cloudConfig;

  @Autowired
  public MessageControler(CloudConfig cloudConfig) {
    this.cloudConfig = cloudConfig;
  }

  @GetMapping(value = "/public")
  public Message publicEndpoint() {
    LOG.info("retrieve public message");

    String pattern = cloudConfig.getMsgPattern().get("public");
    Message message = createMessage(pattern);
    return message;
  }

  @GetMapping(value = "/private")
  public Message privateEndpoint() {
    LOG.info("retrieve public message");

    String pattern = cloudConfig.getMsgPattern().get("private");
    Message message = createMessage(pattern);
    return message;
  }

  @GetMapping(value = "/private-scoped")
  public Message privateScopedEndpoint() {
    LOG.info("retrieve public message");

    String pattern = cloudConfig.getMsgPattern().get("scoped");
    Message message = createMessage(pattern);
    return message;
  }

  private Message createMessage(String pattern) {
    MessageFormat formatter = new MessageFormat(pattern);
    Message message = new Message(formatter.format(new Object[] { audience, issuer }));
    return message;
  }
}
