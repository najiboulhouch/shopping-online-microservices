package net.najiboulhouch.productservice.repository;


import net.najiboulhouch.productservice.model.Product;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;

@DataMongoTest
@ActiveProfiles("test")
@Tag("unit")
public class ProductRepositoryTest {

    @Autowired
    private ProductRepository productRepository;

    Product product ;


    @BeforeEach
    void setUp() {

        product = new Product();
        product.setDescription("DESC P1");
        product.setName("P1");
        product.setPrice(new BigDecimal(100));

    }

    @Test
    void givenValidProductIn_whenProduct_thenReturnCreatedProduct() {

        // when - action or behaviour that we are going to test
        Product  productFromDb = productRepository.save(product);

        // then - verify the output
        assertThat(productFromDb).isNotNull();
        assertThat(productFromDb.getName()).isEqualTo("P1");
        assertThat(productFromDb.getDescription()).isEqualTo("DESC P1");
    }

}
