package com.veeteq.auth0security.dataloader;

import java.text.MessageFormat;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import com.veeteq.auth0security.model.Item;
import com.veeteq.auth0security.repository.ItemRepository;

@Component
public class DataLoader implements ApplicationRunner {
  private final Logger LOG = LoggerFactory.getLogger(DataLoader.class.getSimpleName());

  private final ItemRepository itemRepository;

  @Autowired
  public DataLoader(ItemRepository itemRepository) {
    this.itemRepository = itemRepository;
  }

  @Override
  public void run(ApplicationArguments args) throws Exception {
    List<Item> items = List.of(
            new Item(1L, "Burger", 599L, "Tasty", "https://cdn.auth0.com/blog/whatabyte/burger-sm.png"),
            new Item(2L, "Pizza", 299L, "Cheesy", "https://cdn.auth0.com/blog/whatabyte/pizza-sm.png"),
            new Item(3L, "Tea", 199L, "Informative", "https://cdn.auth0.com/blog/whatabyte/tea-sm.png")
    );
    itemRepository.saveAll(items);
    LOG.info(MessageFormat.format("Loaded: {0} items", itemRepository.count()));
  }
}
