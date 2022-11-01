package io.github.followsclosley.brick.web.converter;

import io.github.followsclosley.brick.jpa.Category;
import io.github.followsclosley.brick.web.pojo.CategoryDto;
import org.springframework.stereotype.Component;

@Component
public class CategoryConverter {
    public CategoryDto convert(Category category) {
        return new CategoryDto(category.getId(), category.getName());
    }
}
