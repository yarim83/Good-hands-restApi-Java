package pl.coderslab.charity.converter;

import org.springframework.stereotype.Component;
import pl.coderslab.charity.dto.CategoryDto;
import pl.coderslab.charity.model.Category;

@Component
public class CategoryConverter {

    public CategoryDto toDto(Category category){
        CategoryDto categoryDto = new CategoryDto();

        categoryDto.setId(category.getId());
        categoryDto.setName(category.getName());

        return categoryDto;
    }

    public Category fromDto(CategoryDto categoryDto){
        return null;
    }
}
