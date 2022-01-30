package com.epam.esm.hateoas.impl;

import com.epam.esm.controller.TagController;
import com.epam.esm.hateoas.HateoasAdder;
import com.epam.esm.repository.dto.TagDto;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

/**
 * Class {@code TagHateoasAdder} is implementation of interface {@link HateoasAdder}
 * and intended to work with {@link TagDto} objects.
 *
 * @author Dmitry Poliukov
 */
@Component
public class TagHateoasAdder implements HateoasAdder<TagDto> {

    private static final Class<TagController> TAG_CONTROLLER = TagController.class;

    @Override
    public void addLinks(TagDto tagDto) {
        tagDto.add(linkTo(methodOn(TAG_CONTROLLER).readTag(tagDto.getId())).withSelfRel());
        tagDto.add(linkTo(methodOn(TAG_CONTROLLER).deleteTag(tagDto.getId())).withRel("delete"));
        tagDto.add(linkTo(methodOn(TAG_CONTROLLER).createTag(tagDto)).withRel("create"));
    }
}
