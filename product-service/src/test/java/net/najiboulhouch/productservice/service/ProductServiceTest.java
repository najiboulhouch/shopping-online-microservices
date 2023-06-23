package net.najiboulhouch.productservice.service;

import net.najiboulhouch.productservice.dto.ProductRequest;
import net.najiboulhouch.productservice.model.Product;
import net.najiboulhouch.productservice.repository.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
@Tag("unit")
public class ProductServiceTest {

    Product product ;

    ProductRequest productRequest;
    @Mock
    private ProductRepository productRepository;

    // @InjectMocks creates the mock object of the class and injects the mocks
    // that are marked with the annotations @Mock into it.
    @InjectMocks
    private ProductService productService;

    @BeforeEach
    void setUp() {
        product = new Product();
        product.setDescription("DESC P1");
        product.setName("P1");
        product.setPrice(new BigDecimal(100));

        productRequest = new ProductRequest();
        productRequest.setDescription("DESC P1");
        productRequest.setName("P1");
        productRequest.setPrice(new BigDecimal(100));
    }

    @Test
    void givenProduct_whenCreatedProduct_thenReturnProduct() {

        // given - precondition or setup
        given(productRepository.save(product)).willReturn(product);


        // when - action or behaviour that we are going to test
        productService.createProduct(productRequest);

        // then - verify the output
        verify(productRepository, times(1)).save(product);
    }



}
