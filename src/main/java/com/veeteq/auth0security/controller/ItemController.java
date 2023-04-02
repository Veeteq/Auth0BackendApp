package com.veeteq.auth0security.controller;

import com.veeteq.auth0security.exception.ResourceNotFoundException;
import com.veeteq.auth0security.model.Item;
import com.veeteq.auth0security.service.ItemService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.text.MessageFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Controller
@RequestMapping(path = "/api/menu/items", produces = MediaType.APPLICATION_JSON_VALUE)
public class ItemController {
  private final Logger LOG = LoggerFactory.getLogger(ItemController.class.getSimpleName());

  private final ItemService itemService;

  @Autowired
  public ItemController(ItemService itemService) {
    this.itemService = itemService;
  }

  @GetMapping
  public ResponseEntity<List<Item>> findAll() {
    LOG.info("retrieve all items");

    List<Item> items = itemService.findAll();
    return ResponseEntity.ok().body(items);
  }

  @GetMapping("/{id}")
  public ResponseEntity<Item> find(@PathVariable("id") Long id) {
    LOG.info("retrieve item with id: " + id);

    Optional<Item> item = itemService.find(id);
    return ResponseEntity.of(item);
  }

  @PostMapping
  public ResponseEntity<Item> create(@Valid @RequestBody Item item) {
    LOG.info("creating a new item");

    Item created = itemService.create(item);
    LOG.info(MessageFormat.format("item with id {0} created", created.getId()));

    URI location = ServletUriComponentsBuilder.fromCurrentRequest()
            .path("/{id}")
            .buildAndExpand(created.getId())
            .toUri();
    return ResponseEntity.created(location).body(created);
  }

  @PutMapping("/{id}")
  public ResponseEntity<Item> update(@PathVariable("id") Long id, @Valid @RequestBody Item item) {
    LOG.info("updating or creating an item with id: " + id);

    Optional<Item> updated = itemService.update(id, item);

    LOG.info(updated
            .map(value -> MessageFormat.format("item with id {0} updated", value.getId()))
            .orElse("No item to update, creating a new one"));

    return updated
            .map(value -> ResponseEntity.ok().body(value))
            .orElseGet(() -> {
              Item created = itemService.create(item);
              URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                      .path("/{id}")
                      .buildAndExpand(created.getId())
                      .toUri();
              return ResponseEntity.created(location).body(created);
            });
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Item> delete(@PathVariable("id") Long id) throws ResourceNotFoundException {
    LOG.info("deleting the item with id: " + id);

    itemService.delete(id);
    return ResponseEntity.noContent().build();
  }

  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ResponseEntity<Map<String, String>> handleValidationExceptions(MethodArgumentNotValidException ex) {
    List<ObjectError> errors = ex.getBindingResult().getAllErrors();
    Map<String, String> map = new HashMap<>(errors.size());
    errors.forEach((error) -> {
      String key = ((FieldError) error).getField();
      String val = error.getDefaultMessage();
      map.put(key, val);
    });
    return ResponseEntity.badRequest().body(map);
  }
}
