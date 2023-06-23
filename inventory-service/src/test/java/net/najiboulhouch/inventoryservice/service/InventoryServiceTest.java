package net.najiboulhouch.inventoryservice.service;

import net.najiboulhouch.inventoryservice.dto.InventoryResponse;
import net.najiboulhouch.inventoryservice.model.Inventory;
import net.najiboulhouch.inventoryservice.repository.InventoryRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

import java.util.List;

@ExtendWith(MockitoExtension.class)
@Tag("unit")
public class InventoryServiceTest {

    Inventory  inventoryOne ;
    Inventory  inventoryTwo ;

    @Mock
    private InventoryRepository inventoryRepository;

    // @InjectMocks creates the mock object of the class and injects the mocks
    // that are marked with the annotations @Mock into it.
    @InjectMocks
    private InventoryService inventoryService;

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
    void givenInventory_whenInventoriesIsInStock_thenReturnIsInStock() {

        // given - precondition or setup
        List<String> skuList = List.of(new String[]{"P1", "P2"});
        List<Inventory> inventories = List.of(inventoryOne , inventoryTwo);

        given(inventoryRepository.findBySkuCodeIn(skuList)).willReturn(inventories);
        //when(inventoryRepository.findBySkuCodeIn(skuList)).thenReturn(inventories);

        // when - action or behaviour that we are going to test
        List<InventoryResponse> result = inventoryService.isInStock(skuList);

        // then - verify the output
        assertThat(result).isNotNull();
        assertThat(result).hasSize(2);
        assertThat(result.get(0).getSkuCode()).isEqualTo(inventoryOne.getSkuCode());
        assertThat(result.get(0).isInStock()).isEqualTo(true);

        verify(inventoryRepository, times(1)).findBySkuCodeIn(skuList);
    }



}
