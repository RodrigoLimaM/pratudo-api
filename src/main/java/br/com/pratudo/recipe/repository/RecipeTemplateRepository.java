package br.com.pratudo.recipe.repository;

import br.com.pratudo.recipe.model.Recipe;
import br.com.pratudo.recipe.model.enums.Category;
import br.com.pratudo.recipe.model.enums.Difficulty;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import org.elasticsearch.search.sort.SortBuilders;
import org.elasticsearch.search.sort.SortMode;
import org.elasticsearch.search.sort.SortOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.core.DocumentOperations;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;
import org.springframework.data.elasticsearch.core.SearchHit;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.core.document.Document;
import org.springframework.data.elasticsearch.core.mapping.IndexCoordinates;
import org.springframework.data.elasticsearch.core.query.Criteria;
import org.springframework.data.elasticsearch.core.query.CriteriaQuery;
import org.springframework.data.elasticsearch.core.query.NativeSearchQuery;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.data.elasticsearch.core.query.UpdateQuery;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Repository
public class RecipeTemplateRepository {

    @Autowired
    ElasticsearchRestTemplate elasticsearchRestTemplate;

    @Autowired
    DocumentOperations documentOperations;

    @Autowired
    ObjectMapper objectMapper;

    public Page<Recipe> getRecipeByQuery(Pageable pageable, List<Category> categories, List<Difficulty> difficulties, Long serves, String name, List<String> ingredients) {
        Criteria criteria = new Criteria();

        if (!Objects.isNull(categories))
            criteria = criteria.and("categories").in(categories);

        if (!Objects.isNull(difficulties))
            criteria = criteria.and("difficulty").in(difficulties);

        if (!Objects.isNull(serves))
            criteria = criteria.and("serves").is(serves);

        if (!Objects.isNull(name))
            criteria = criteria.and("name").is(name);

        if (!Objects.isNull(ingredients))
            criteria = criteria.and("ingredients.items.name").in(ingredients);

        CriteriaQuery criteriaQuery = new CriteriaQuery(criteria);

        criteriaQuery.setPageable(pageable);

        SearchHits<Recipe> searchHits = elasticsearchRestTemplate.search(criteriaQuery, Recipe.class);

        return new PageImpl<>(extractRecipesFromSearchHits(searchHits));
    }

    @SneakyThrows
    public Recipe updateRecipe(Recipe newRecipe) {
        String _id = newRecipe.get_id();

        newRecipe.set_id(null);

        UpdateQuery updateQuery = UpdateQuery.builder(_id)
                .withDocument(Document.parse(objectMapper.writeValueAsString(newRecipe)))
                .build();

        documentOperations.update(updateQuery, IndexCoordinates.of("recipes"));

        return newRecipe;
    }

    public Page<Recipe> findAllByOrderByListFieldCountDesc(Pageable pageable, String field) {
        NativeSearchQuery nativeSearchQuery = new NativeSearchQueryBuilder().
                withSort(SortBuilders.fieldSort(field)
                        .order(SortOrder.DESC))
                .build();

        nativeSearchQuery.setPageable(pageable);

        SearchHits<Recipe> searchHits = elasticsearchRestTemplate.search(nativeSearchQuery, Recipe.class);

        return new PageImpl<>(extractRecipesFromSearchHits(searchHits));
    }

    public Page<Recipe> findAllByOrderByListFieldAverageCountDesc(Pageable pageable, String field) {
        NativeSearchQuery nativeSearchQuery = new NativeSearchQueryBuilder().
                withSort(SortBuilders.fieldSort(field)
                        .sortMode(SortMode.AVG)
                        .order(SortOrder.DESC))
                .build();

        nativeSearchQuery.setPageable(pageable);

        SearchHits<Recipe> searchHits = elasticsearchRestTemplate.search(nativeSearchQuery, Recipe.class);

        return new PageImpl<>(extractRecipesFromSearchHits(searchHits));
    }

    private List<Recipe> extractRecipesFromSearchHits(SearchHits<Recipe> searchHits) {
        return searchHits.getSearchHits()
                .stream()
                .map(SearchHit::getContent)
                .collect(Collectors.toList());
    }
}
