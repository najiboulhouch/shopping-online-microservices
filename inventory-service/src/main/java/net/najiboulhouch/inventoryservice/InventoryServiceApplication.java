package net.najiboulhouch.inventoryservice;

import net.najiboulhouch.inventoryservice.model.Inventory;
import net.najiboulhouch.inventoryservice.repository.InventoryRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@EnableEurekaClient
public class InventoryServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(InventoryServiceApplication.class, args);
	}

	@Bean
	CommandLineRunner loadDataFromDB(InventoryRepository inventoryRepository){
		return args -> {
			Inventory inventory = new Inventory();
			inventory.setSkuCode("Iphone_13");
			inventory.setQuantity(120);

			Inventory secondInventory = new Inventory();
			secondInventory.setSkuCode("Iphone_13_red");
			secondInventory.setQuantity(0);

			Inventory thirdInventory = new Inventory();
			thirdInventory.setSkuCode("Iphone_11_red");
			thirdInventory.setQuantity(1);

			inventoryRepository.save(inventory);
			inventoryRepository.save(secondInventory);
			inventoryRepository.save(thirdInventory);
		};
	}

}
