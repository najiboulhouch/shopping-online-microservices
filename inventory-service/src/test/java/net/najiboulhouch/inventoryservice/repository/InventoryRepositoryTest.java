package net.najiboulhouch.inventoryservice.repository;

import lombok.RequiredArgsConstructor;
import net.najiboulhouch.inventoryservice.model.Inventory;
import org.assertj.core.util.Arrays;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

@DataJpaTest
@ActiveProfiles("test")
@Tag("unit")
public class InventoryRepositoryTest {

    @Autowired
    private InventoryRepository inventoryRepository;

    Inventory  inventoryOne ;
    Inventory  inventoryTwo ;


    @BeforeEach
    void setUp() {

        inventoryOne = new Inventory();
        inventoryOne.setQuantity(10);
        inventoryOne.setSkuCode("P1");

        inventoryTwo = new Inventory();
        inventoryTwo.setQuantity(100);
        inventoryTwo.setSkuCode("P2");
    }

    @Test
    void givenValidInventoryBySkuCodeIn_whenFindByInventorySkuCodeIn_thenReturnInventories() {

        // given - precondition or setup
        inventoryRepository.save(inventoryOne);
        inventoryRepository.save(inventoryTwo);


        // when - action or behaviour that we are going to test
        List<Inventory>  inventoriesFromDb = inventoryRepository.findBySkuCodeIn(List.of(new String[]{"P1", "P2"}));

        // then - verify the output
        assertThat(inventoriesFromDb).isNotNull();
        assertThat(inventoriesFromDb).hasSize(2);
        assertThat(inventoriesFromDb.get(0).getSkuCode()).isEqualTo("P1");
    }

    @Test
    void givenInValidInventoryBySkuCodeIn_whenFindByInventorySkuCodeIn_thenReturnOneInventory() {

        // given - precondition or setup
        inventoryRepository.save(inventoryOne);
        inventoryRepository.save(inventoryTwo);


        // when - action or behaviour that we are going to test
        List<Inventory>  inventoriesFromDb = inventoryRepository.findBySkuCodeIn(List.of(new String[]{"P1", "P4"}));

        // then - verify the output
        assertThat(inventoriesFromDb).isNotNull();
        assertThat(inventoriesFromDb).hasSize(1);
    }
}
