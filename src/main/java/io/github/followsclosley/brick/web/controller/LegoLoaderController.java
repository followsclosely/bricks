package io.github.followsclosley.brick.web.controller;

import io.github.followsclosley.brick.data.loader.*;
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
    private final LegoColorLoader colorLoader;
    private final LegoThemeLoader themeLoader;
    private final LegoCategoryLoader categoryLoader;
    private final LegoPartLoader partLoader;
    private final LegoElementLoader elementLoader;
    private final LegoSetLoader setLoader;
    private final LegoMinifigLoader minifigLoader;
    private final LegoInventoryMinifigLoader inventoryMinifigLoader;
    private final LegoInventoryLoader inventoryLoader;
    private final LegoInventoryPartLoader inventoryPartLoader;

    @GetMapping(value = "/loader")
    void load() throws IOException {
        new Thread(() -> {
            try{
                colorLoader.process();

                //if(System.currentTimeMillis() > 0) return;

                themeLoader.process();
                categoryLoader.process();

                partLoader.process();
                elementLoader.process();

                setLoader.process();
                inventoryLoader.process();
                inventoryPartLoader.process();

                minifigLoader.process();
                inventoryMinifigLoader.process();

                log.info("Complete.");
            } catch (IOException e) {
                log.error("Error loading data", e);
            }
        }).start();

    }

}
