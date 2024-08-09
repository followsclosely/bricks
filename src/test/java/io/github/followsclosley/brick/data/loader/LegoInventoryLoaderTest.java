package io.github.followsclosley.brick.data.loader;

import io.github.followsclosley.brick.data.LegoInventory;
import io.github.followsclosley.brick.data.LegoInventoryPart;
import io.github.followsclosley.brick.data.repository.LegoColorRepository;
import io.github.followsclosley.brick.data.repository.LegoInventoryRepository;
import io.github.followsclosley.brick.data.repository.LegoPartRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class LegoInventoryLoaderTest {

    @Autowired
    LegoInventoryRepository repository;

    @Autowired
    LegoPartRepository legoPartRepository;

    @Autowired
    LegoColorRepository legoColorRepository;

    @Test
    void process() {
       LegoInventory legoInventory = new LegoInventory();
       legoInventory.setId("1");
       legoInventory.setVersion("1.0");

       LegoInventoryPart part = new LegoInventoryPart();
       //part.setId(new LegoInventoryPart.InventoryPartId("1","48379c04","72", false));
       //legoInventory.getParts().add(part);

       //repository.save(legoInventory);
    }
}