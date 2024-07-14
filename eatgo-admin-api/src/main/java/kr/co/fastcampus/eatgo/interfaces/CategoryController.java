package kr.co.fastcampus.eatgo.interfaces;

import jakarta.validation.Valid;
import kr.co.fastcampus.eatgo.application.CategoryService;
import kr.co.fastcampus.eatgo.domain.Category;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;


@RestController
@RequestMapping("/categories")
public class CategoryController {

    private final CategoryService categoryService;

    @Autowired
    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping
    public List<Category> list() {
        List<Category> categories = categoryService.getCategories();

        return categories;
    }

    @PostMapping
    public ResponseEntity<?> create(
            @Valid @RequestBody Category resource) throws URISyntaxException {
        Category saved = categoryService.addCategory(resource.getName());

        URI uri = new URI("/categories/" + saved.getId());
        return ResponseEntity.created(uri).body("{}");
    }

}
