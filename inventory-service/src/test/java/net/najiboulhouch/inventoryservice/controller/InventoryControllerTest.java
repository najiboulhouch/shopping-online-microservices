package net.najiboulhouch.inventoryservice.controller;

import net.najiboulhouch.inventoryservice.dto.InventoryResponse;
import net.najiboulhouch.inventoryservice.service.InventoryService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.util.List;

import static org.mockito.BDDMockito.given;
import static org.hamcrest.CoreMatchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WebMvcTest(InventoryController.class)
@Tag("unit")
public class InventoryControllerTest {


    @MockBean
    private InventoryService inventoryService;

    @Autowired
    private MockMvc mockMvc;

    InventoryResponse inventoryResponseOne;
    InventoryResponse inventoryResponseTwo;

    @BeforeEach
    void setUp() {

        inventoryResponseOne = new InventoryResponse();
        inventoryResponseOne.setInStock(true);
        inventoryResponseOne.setSkuCode("P1");

        inventoryResponseTwo = new InventoryResponse();
        inventoryResponseTwo.setInStock(true);
        inventoryResponseTwo.setSkuCode("P2");
    }

    @Test
    void givenSkuCodesList_whenCheckSkuIsInStock_thenReturnListOfInventoryResponse() throws Exception {

        // given - precondition or setup

        List<String> skuList = List.of(new String[]{"P1", "P2"});
        List<InventoryResponse> inventories = List.of(inventoryResponseOne , inventoryResponseTwo);

        given(inventoryService.isInStock(skuList)).willReturn(inventories);

        // when - action or behaviour that we are going to test
        ResultActions response = mockMvc.perform(get("/api/inventory/{skuCode}", skuList)
                .contentType(MediaType.APPLICATION_JSON));

        // then - verify the output
        response.andDo(print())
                // verify the status code that is returned
                .andExpect(status().isOk())
                // verify the actual returned value and the expected value
                // $ - root member of a JSON structure whether it is an object or array
                .andExpect(jsonPath("$.results.departmentCode", is(inventoryResponseOne.getSkuCode())))
                .andExpect(jsonPath("$.results.departmentName", is(inventoryResponseOne.isInStock())));


    }
}
