package com.veeteq.auth0security.service;

import com.veeteq.auth0security.exception.ResourceNotFoundException;
import com.veeteq.auth0security.model.Item;
import com.veeteq.auth0security.repository.ItemRepository;
import org.springframework.data.map.repository.config.EnableMapRepositories;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@EnableMapRepositories(basePackages = "com.veeteq.auth0security.repository")
public class ItemService {
  private final ItemRepository itemRepository;

  public ItemService(ItemRepository itemRepository) {
    this.itemRepository = itemRepository;
  }

  public List<Item> findAll() {
    List<Item> list = new ArrayList<>();
    Iterable<Item> items = itemRepository.findAll();
    items.forEach(list::add);
    return list;
  }

  public Optional<Item> find(Long id) {
    return itemRepository.findById(id);
  }

  public Item create(Item item) {
    // To ensure the item ID remains unique,
    // use the current timestamp.
    Item copy = new Item(
            new Date().getTime(),
            item.getName(),
            item.getPrice(),
            item.getDescription(),
            item.getImageUrl());
    return itemRepository.save(copy);
  }

  public Optional<Item> update( Long id, Item newItem) {
    // Only update an item if it can be found first.
    return itemRepository.findById(id)
            .map(oldItem -> {
              Item updated = oldItem.updateWith(newItem);
              return itemRepository.save(updated);
            });
  }

  public void delete(Long id) throws ResourceNotFoundException {
    Item savedItem = itemRepository
            .findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Item not found for this id :: " + id));

    itemRepository.delete(savedItem);
  }
}
