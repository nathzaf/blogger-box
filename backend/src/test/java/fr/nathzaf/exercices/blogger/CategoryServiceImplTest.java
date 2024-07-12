package fr.nathzaf.exercices.blogger;

import fr.nathzaf.exercices.blogger.exceptions.CategoryNameAlreadyExistsException;
import fr.nathzaf.exercices.blogger.exceptions.CategoryNotFoundException;
import fr.nathzaf.exercices.blogger.models.Category;
import fr.nathzaf.exercices.blogger.repository.CategoryRepository;
import fr.nathzaf.exercices.blogger.service.impl.CategoryServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CategoryServiceImplTest {

    @InjectMocks
    private CategoryServiceImpl categoryService;

    @Mock
    private CategoryRepository categoryRepository;

    @Test
    void shouldReturnAllCategoriesWhenGetAll() {
        List<Category> expectedCategories = List.of(
                new Category("Category 1"),
                new Category("Category 2"),
                new Category("Category 3"));
        when(categoryRepository.findAll()).thenReturn(expectedCategories);

        List<Category> actualCategories = categoryService.getAll();

        assertEquals(expectedCategories, actualCategories);
        verify(categoryRepository).findAll();
    }

    @Test
    void shouldReturnCategoryWhenGetById() throws CategoryNotFoundException {
        UUID id = UUID.randomUUID();
        Category expectedCategory = new Category("Category");
        when(categoryRepository.findById(id)).thenReturn(Optional.of(expectedCategory));

        Category actualCategory = categoryService.getById(id);

        assertEquals(expectedCategory, actualCategory);
        verify(categoryRepository).findById(id);
    }

    @Test
    void shouldThrowExceptionWhenNotFoundById() {
        UUID id = UUID.randomUUID();
        when(categoryRepository.findById(id)).thenReturn(Optional.empty());

        CategoryNotFoundException e = assertThrows(CategoryNotFoundException.class,
                () -> categoryService.getById(id));
        verify(categoryRepository).findById(id);

        assertEquals("No Category of id " + id + " found.", e.getMessage());
    }

    @Test
    void shouldReturnCategoryWhenCreate() throws CategoryNameAlreadyExistsException {
        String name = "Category";
        Category expectedCategory = new Category(name);
        when(categoryRepository.findCategoriesByName(name)).thenReturn(new ArrayList<>());
        when(categoryRepository.save(any())).thenReturn(expectedCategory);

        Category actualCategory = categoryService.create(name);

        assertEquals(expectedCategory, actualCategory);
        verify(categoryRepository).findCategoriesByName(name);
        verify(categoryRepository).save(any());
    }

    @Test
    void shouldThrowExceptionWhenCreateWithAlreadyExistsName() {
        String name = "Category";
        Category category = new Category(name);
        when(categoryRepository.findCategoriesByName(name)).thenReturn(List.of(category));

        CategoryNameAlreadyExistsException e = assertThrows(CategoryNameAlreadyExistsException.class,
                () -> categoryService.create(name));

        assertEquals("A Category named " + name + " already exists.", e.getMessage());
        verify(categoryRepository).findCategoriesByName(name);
        verify(categoryRepository, never()).save(any());
    }

    @Test
    void shouldUpdateCategoryWhenUpdate() throws CategoryNameAlreadyExistsException, CategoryNotFoundException {
        UUID id = UUID.randomUUID();
        String oldCategoryName = "Category";
        String expectedCategoryName = "Updated Category";
        Category oldCategory = new Category(oldCategoryName);
        Category expectedCategory = new Category(expectedCategoryName);
        when(categoryRepository.findById(id)).thenReturn(Optional.of(oldCategory));
        when(categoryRepository.findCategoriesByName(expectedCategoryName)).thenReturn(new ArrayList<>());
        when(categoryRepository.save(any())).thenReturn(expectedCategory);

        Category actualCategory = categoryService.updateName(id, expectedCategoryName);

        assertEquals(expectedCategory.getName(), actualCategory.getName());
        verify(categoryRepository).findById(id);
        verify(categoryRepository).findCategoriesByName(expectedCategoryName);
        verify(categoryRepository).save(any());
    }

    @Test
    void shouldThrowCategoryNotFoundExceptionWhenNotFoundByIdOnUpdate() {
        UUID id = UUID.randomUUID();
        when(categoryRepository.findById(id)).thenReturn(Optional.empty());

        CategoryNotFoundException e = assertThrows(CategoryNotFoundException.class,
                () -> categoryService.updateName(id, "New name"));

        assertEquals("No Category of id " + id + " found.", e.getMessage());
        verify(categoryRepository).findById(id);
        verify(categoryRepository, never()).findCategoriesByName(any());
        verify(categoryRepository, never()).save(any());
    }

    @Test
    void shouldThrowCategoryNameAlreadyExistsExceptionWhenUpdateWithAlreadyExistsName() {
        UUID id = UUID.randomUUID();
        String updatedCategoryName = "Updated Category";
        Category category = new Category("Category");

        when(categoryRepository.findById(id)).thenReturn(Optional.of(category));
        when(categoryRepository.findCategoriesByName(updatedCategoryName)).thenReturn(List.of(new Category(updatedCategoryName)));

        CategoryNameAlreadyExistsException e = assertThrows(CategoryNameAlreadyExistsException.class,
                () -> categoryService.updateName(id, updatedCategoryName));

        assertEquals("A Category named " + updatedCategoryName + " already exists.", e.getMessage());
        verify(categoryRepository).findById(id);
        verify(categoryRepository).findCategoriesByName(updatedCategoryName);
        verify(categoryRepository, never()).save(any());
    }

    @Test
    void shouldDeleteWhenDelete() throws CategoryNotFoundException {
        UUID id = UUID.randomUUID();
        Category category = new Category("Category");

        when(categoryRepository.findById(id)).thenReturn(Optional.of(category));

        categoryService.deleteById(id);

        verify(categoryRepository).deleteById(id);
    }

    @Test
    void shouldThrowCategoryNotFoundExceptionWhenNotFoundByIdOnDelete() {
        UUID id = UUID.randomUUID();

        when(categoryRepository.findById(id)).thenReturn(Optional.empty());

        CategoryNotFoundException e = assertThrows(CategoryNotFoundException.class,
                () -> categoryService.deleteById(id));

        assertEquals("No Category of id " + id + " found.", e.getMessage());
        verify(categoryRepository).findById(id);
        verify(categoryRepository, never()).deleteById(any());
    }

}
