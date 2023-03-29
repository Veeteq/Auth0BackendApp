package com.ey.gds.solutionarchitect.auth0security.repository;

import com.ey.gds.solutionarchitect.auth0security.model.Item;
import org.springframework.data.repository.CrudRepository;

public interface ItemRepository extends CrudRepository<Item, Long> {
}
