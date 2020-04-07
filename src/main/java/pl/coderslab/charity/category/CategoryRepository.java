package pl.coderslab.charity.category;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.coderslab.charity.model.Category;

public interface CategoryRepository extends JpaRepository<Category, Long> {



}
