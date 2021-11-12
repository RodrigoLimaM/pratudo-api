package br.com.pratudo.recipe.repository;

import br.com.pratudo.recipe.model.Recipe;
import br.com.pratudo.recipe.model.enums.Category;
import br.com.pratudo.recipe.model.enums.Difficulty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;
import org.springframework.data.elasticsearch.core.SearchHit;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.core.query.Criteria;
import org.springframework.data.elasticsearch.core.query.CriteriaQuery;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Repository
public class RecipeTemplateRepository {

    @Autowired
    ElasticsearchRestTemplate elasticsearchRestTemplate;

    public Page<Recipe> getRecipeByQuery(Pageable pageable, List<Category> categories, List<Difficulty> difficulties, Long serves, String name, List<String> ingredients) {
        Criteria criteria = new Criteria();

        if(!Objects.isNull(categories))
            criteria = criteria.and("categories").in(categories);

        if(!Objects.isNull(difficulties))
            criteria = criteria.and("difficulty").in(difficulties);

        if(!Objects.isNull(serves))
            criteria = criteria.and("serves").is(serves);

        if(!Objects.isNull(name))
            criteria = criteria.and("name").is(name);

        if(!Objects.isNull(ingredients))
            criteria = criteria.and("ingredients.items.name").in(ingredients);

        CriteriaQuery criteriaQuery = new CriteriaQuery(criteria);

        criteriaQuery.setPageable(pageable);


        SearchHits<Recipe> searchHits = elasticsearchRestTemplate.search(criteriaQuery, Recipe.class);

        return new PageImpl<>(searchHits.getSearchHits().stream().map(SearchHit::getContent).collect(Collectors.toList()));
    }
}
