package io.github.followsclosley.brick.data.loader;

import io.github.followsclosley.brick.data.entity.*;
import io.github.followsclosley.brick.data.repository.*;
import lombok.RequiredArgsConstructor;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;


@Configuration
@RequiredArgsConstructor
public class BasicLoaderFactory {

    private final CSVFormat csvParser;
    private final ChangeLogRepository changeLogRepository;
    private final LegoColorRepository legoColorRepository;
    private final LegoCategoryRepository legoCategoryRepository;
    private final LegoThemeRepository legoThemeRepository;
    private final LegoPartRepository legoPartRepository;
    private final LegoSetRepository legoSetRepository;
    private final LegoInventoryRepository legoInventoryRepository;
    private final LegoElementRepository legoElementRepository;
    private final LegoSetRepository setRepository;
    private final LegoMinifigRepository legoMinifigRepository;

//    @Bean
//    public BasicAbstractLoader<LegoColor, String> legoColorLoader(@Value("${catalog.rebrickable.colors}") String uri) {
//        return new BasicAbstractLoader<LegoColor, String>("Color", uri, csvParser, legoColorRepository, changeLogRepository) {
//            @Override
//            LegoColor map(CSVRecord record) {
//                LegoColor legoColor = new LegoColor();
//                legoColor.setId(record.get(0));
//                legoColor.setName(record.get(1));
//                legoColor.setRgb(record.get(2));
//                legoColor.setTransparent("t".equalsIgnoreCase(record.get(3)));
//                return legoColor;
//            }
//
//            @Override
//            String getId(LegoColor color) {
//                return color.getId();
//            }
//        };
//    }

//    @Bean
//    public BasicAbstractLoader<LegoCategory, String> legoCategoryLoader(@Value("${catalog.rebrickable.part-categories}") String uri) {
//        return new BasicAbstractLoader<LegoCategory, String>("Category", uri, csvParser, legoCategoryRepository, changeLogRepository) {
//            @Override
//            LegoCategory map(CSVRecord record) {
//                LegoCategory category = new LegoCategory();
//                category.setId(record.get(0));
//                category.setName(record.get(1));
//                return category;
//            }
//
//            @Override
//            String getId(LegoCategory category) {
//                return category.getId();
//            }
//        };
//    }

    @Bean
    public BasicAbstractLoader<LegoTheme, String> legoThemeLoader(@Value("${catalog.rebrickable.themes}") String uri) {
        return new BasicAbstractLoader<LegoTheme, String>("Theme", uri, csvParser, legoThemeRepository, changeLogRepository) {

            Map<String, LegoTheme> cache = new HashMap<>();

            @Override
            LegoTheme map(CSVRecord record) {

                LegoTheme legoTheme = new LegoTheme();
                legoTheme.setId(record.get(0));
                legoTheme.setName(record.get(1));

                String parentId = record.get(2);
                if (StringUtils.hasText(parentId)) {
                    LegoTheme parent = cache.get(parentId);
                    legoTheme.setParent(parent);
                }

                //Place the theme in the cache so that parent lookup can occur.
                cache.put(legoTheme.getId(), legoTheme);

                return legoTheme;
            }

            @Override
            String getId(LegoTheme theme) {
                return theme.getId();
            }

            @Override
            public void postLoad() {
                cache.clear();
            }
        };
    }

    @Bean
    public BasicAbstractLoader<LegoSet, String> legoSetLoader(@Value("${catalog.rebrickable.sets}") String uri) {
        return new BasicAbstractLoader<LegoSet, String>("Set", uri, csvParser, legoSetRepository, changeLogRepository) {

            private Map<String, LegoTheme> themes;

            @Override
            public void preLoad() {
                this.themes = legoThemeRepository.findAll().stream().collect(Collectors.toMap(LegoTheme::getId, t -> t));
            }

            @Override
            LegoSet map(CSVRecord record) {
                LegoSet legoSet = new LegoSet();
                legoSet.setId(record.get(0));
                legoSet.setName(record.get(1));
                legoSet.setReleaseYear(Integer.parseInt(record.get(2)));
                legoSet.setLegoTheme(themes.get(record.get(3)));
                legoSet.setPartCount(Integer.parseInt(record.get(4)));
                legoSet.setImageUrl(record.get(5));
                return legoSet;
            }

            @Override
            String getId(LegoSet legoSet) {
                return legoSet.getId();
            }

            @Override
            public void postLoad() {
                themes.clear();
            }
        };
    }

    @Bean
    public BasicAbstractLoader<LegoPart, String> legoPartLoader(@Value("${catalog.rebrickable.parts}") String uri) {
        return new BasicAbstractLoader<LegoPart, String>("Part", uri, csvParser, legoPartRepository, changeLogRepository) {


            private Map<String, LegoCategory> categories = null;

            @Override
            public void preLoad() {
                this.categories = legoCategoryRepository.findAll().stream().collect(Collectors.toMap(LegoCategory::getId, c -> c));
            }

            @Override
            LegoPart map(CSVRecord record) {
                //id,name,parent_id
                LegoPart legoPart = new LegoPart();
                legoPart.setId(record.get(0));
                legoPart.setName(record.get(1));
                legoPart.setLegoCategory(categories.get(record.get(2)));
                legoPart.setMaterial(record.get(3));
                return legoPart;
            }

            @Override
            String getId(LegoPart part) {
                return part.getId();
            }

            @Override
            public void postLoad() {
                this.categories.clear();
            }
        };
    }

    @Bean
    public BasicAbstractLoader<LegoInventory, String> legoInventoryLoader(@Value("${catalog.rebrickable.inventories}") String uri) {
        return new BasicAbstractLoader<LegoInventory, String>("Inventory", uri, csvParser, legoInventoryRepository, changeLogRepository) {

            @Override
            LegoInventory map(CSVRecord record) {
                //id,version,set_num

                //retrieve the inventory from the database so we do not truncate the parts and minifigs.
                Optional<LegoInventory> optional = legoInventoryRepository.findById(record.get(0));
                LegoInventory legoInventory = optional.orElseGet(LegoInventory::new);
                legoInventory.setId(record.get(0));
                legoInventory.setVersion(record.get(1));
                setRepository.findById(record.get(2)).ifPresent(legoInventory::setLegoSet);
                return legoInventory;
            }

            @Override
            String getId(LegoInventory inventory) {
                return inventory.getId();
            }
        };
    }

    @Bean
    public BasicAbstractLoader<LegoElement, String> legoElementLoader(@Value("${catalog.rebrickable.elements}") String uri) {
        return new BasicAbstractLoader<LegoElement, String>("Lego Elements", uri, csvParser, legoElementRepository, changeLogRepository) {

            Map<String, LegoColor> colors = null;

            @Override
            public void preLoad() {
                this.colors = legoColorRepository.findAll().stream().collect(Collectors.toMap(LegoColor::getId, c -> c));
            }

            @Override
            LegoElement map(CSVRecord record) {
                LegoElement legoElement = new LegoElement();
                legoElement.setId(record.get(0));
                legoPartRepository.findById(record.get(1)).ifPresent(legoElement::setLegoPart);
                legoElement.setLegoColor(colors.get(record.get(2)));
                legoElement.setDesign(record.get(3));

                return legoElement;
            }

            @Override
            String getId(LegoElement element) {
                return element.getId();
            }

            @Override
            public void postLoad() {
                this.colors.clear();
            }
        };
    }

    @Bean
    public BasicAbstractLoader<LegoMinifig, String> legoMinifigLoader(@Value("${catalog.rebrickable.minifigs}") String uri) {
        return new BasicAbstractLoader<LegoMinifig, String>("Minifigure", uri, csvParser, legoMinifigRepository, changeLogRepository) {

            @Override
            LegoMinifig map(CSVRecord record) {
                //fig_num,name,num_parts,img_url
                LegoMinifig minifig = new LegoMinifig();
                minifig.setId(record.get(0));
                minifig.setName(record.get(1));
                minifig.setPartCount(Integer.parseInt(record.get(2)));
                minifig.setImageUrl(record.get(3));
                return minifig;
            }

            @Override
            String getId(LegoMinifig minifig) {
                return minifig.getId();
            }
        };
    }
}
