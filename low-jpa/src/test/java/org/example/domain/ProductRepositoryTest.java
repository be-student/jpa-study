package org.example.domain;

import static org.junit.jupiter.api.Assertions.*;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
class ProductRepositoryTest {

    @PersistenceContext
    private EntityManager entityManager;

    @Test
    void 저장하고_준영속_상태로_만든다(){
        // given
        Product product = new Product();
        product.setName("테스트 상품");

        // when
        entityManager.persist(product);
        entityManager.flush();
        entityManager.clear();

        // then
        Product foundProduct = entityManager.find(Product.class, product.getId());
        assertEquals("테스트 상품", foundProduct.getName());
    }
    @Test
    void 저장하고_준영속_상태에서_머지를_시도한다(){
        // given
        Product product = new Product();
        product.setName("테스트 상품");

        // when
        entityManager.persist(product);
        entityManager.flush();
        entityManager.clear();
        product.setName("수정된 상품");
        entityManager.merge(product);
        entityManager.flush();
        entityManager.clear();
        // then
        Product foundProduct = entityManager.find(Product.class, product.getId());
        assertEquals("수정된 상품", foundProduct.getName());
    }
}
