package br.com.pratudo.recipe.model.mapper;

import br.com.pratudo.recipe.model.Comment;
import br.com.pratudo.recipe.model.dto.ContentDTO;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CommentMapper {

    @Autowired
    ModelMapper modelMapper;

    public Comment convertCommentDTOToComment(ContentDTO contentDTO) {
        return modelMapper.map(contentDTO, Comment.class);
    }

}
