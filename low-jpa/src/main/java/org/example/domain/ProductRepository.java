package org.example.domain;

import java.util.Optional;
import org.springframework.data.repository.Repository;

public interface ProductRepository extends Repository<Product,Long> {

    Product save(Product product);

    Optional<Product> findById(Long id);

    void delete(Product product);
}
