package io.github.followsclosley.brick.web.controller;

import io.github.followsclosley.brick.data.entity.*;
import io.github.followsclosley.brick.data.loader.BasicAbstractLoader;
import io.github.followsclosley.brick.data.loader.LegoInventoryMinifigLoader;
import io.github.followsclosley.brick.data.loader.LegoInventoryPartLoader;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@Slf4j
@RestController
@RequiredArgsConstructor
public class LegoLoaderController {
    /**
     * This is ugly and I could inject a map of these loaders,
     * but I want to have the ability to skip and order the loaders
     **/
    private final BasicAbstractLoader<LegoColor, String> colorLoader;
    private final BasicAbstractLoader<LegoTheme, String> themeLoader;
    private final BasicAbstractLoader<LegoCategory, String> categoryLoader;
    private final BasicAbstractLoader<LegoPart, String> partLoader;
    private final BasicAbstractLoader<LegoElement, String> elementLoader;
    private final BasicAbstractLoader<LegoSet, String> setLoader;
    private final BasicAbstractLoader<LegoMinifig, String> minifigLoader;

    private final BasicAbstractLoader<LegoInventory, String> inventoryLoader;
    private final LegoInventoryPartLoader inventoryPartLoader;
    private final LegoInventoryMinifigLoader inventoryMinifigLoader;

    @GetMapping(value = "/loader")
    void load() {
        new Thread(() -> {
            try {
                colorLoader.process();
                themeLoader.process();
                categoryLoader.process();
                partLoader.process();
                elementLoader.process();
                setLoader.process();
                minifigLoader.process();
                //log.info("Complete.");
                //if (System.currentTimeMillis() > 0) return;
                inventoryLoader.process();
                inventoryPartLoader.process();
                inventoryMinifigLoader.process();

                log.info("Complete.");
            } catch (IOException e) {
                log.error("Error loading data", e);
            }
        }).start();

    }

}
