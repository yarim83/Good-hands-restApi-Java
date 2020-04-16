package pl.coderslab.charity.category;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import pl.coderslab.charity.converter.CategoryConverter;
import pl.coderslab.charity.dto.CategoryDto;
import pl.coderslab.charity.model.Category;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@Log4j
@RequestMapping("/api/category")
public class CategoryController {

    private final CategoryRepository categoryRepository;
    private final CategoryConverter categoryConverter;
    private HttpHeaders httpHeaders;

    @GetMapping("/")
    @ResponseStatus(HttpStatus.OK)
    public List<Category> getAll() {
        try {
            return categoryRepository.findAll();
        } catch (NullPointerException e) {
            log.error(e);
            return new ArrayList<>();
        }
    }

    @GetMapping("/count/")
    public int getNumberOfCategory() {
        try {
            return categoryRepository.findAll().size();
        } catch (NullPointerException e) {
            log.error(e);
            return 0;
        }
    }

    @GetMapping("/{id}")
    public CategoryDto getById(@PathVariable Long id) {
        Optional<Category> categoryOptional = categoryRepository.findById(id);
        return categoryOptional.map(categoryConverter::toDto).orElse(null);
    }

    @PostMapping("/")
    public ResponseEntity<Category> add(@RequestBody Category Category, BindingResult bindingResult) {
        if (bindingResult.hasErrors() || Category == null) {
            httpHeaders.add("errors", "can't add entity");
            log.error("can't add entity" + Category);
            return new ResponseEntity<Category>(httpHeaders, HttpStatus.BAD_REQUEST);
        }
        categoryRepository.save(Category);
        return new ResponseEntity<Category>(Category, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Category> update(@RequestBody Category Category, BindingResult bindingResult) {
        if (!categoryRepository.findById(Category.getId()).isPresent() ||
                bindingResult.hasErrors()) {
            httpHeaders.add("errors", "can't add entity");
            log.error("can't add entity" + Category);
            return new ResponseEntity<Category>(httpHeaders, HttpStatus.BAD_REQUEST);
        }
        categoryRepository.save(Category);
        return new ResponseEntity<Category>(Category, HttpStatus.CREATED);
    }
}
