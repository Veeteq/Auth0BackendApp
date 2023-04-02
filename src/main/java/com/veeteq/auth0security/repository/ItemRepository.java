package com.veeteq.auth0security.repository;

import com.veeteq.auth0security.model.Item;
import org.springframework.data.repository.CrudRepository;

public interface ItemRepository extends CrudRepository<Item, Long> {
}
