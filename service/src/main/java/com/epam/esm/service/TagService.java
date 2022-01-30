package com.epam.esm.service;


import com.epam.esm.repository.dto.TagDto;

import java.util.List;

public interface TagService {

    /**
     * Create tag.
     *
     * @param tag the tag
     * @return the tag
     */
    TagDto create(TagDto tag);

    /**
     * Read tag.
     *
     * @param id the id
     * @return the tag
     */
    TagDto read(int id);

    /**
     * Read all list.
     *
     * @return the list
     */
    List<TagDto> readAll(int page, int size);

    /**
     * Delete.
     *
     * @param id the id
     */
    void delete(int id);

    TagDto readMostWidelyTagFromUserWithHighestCostOrders();

}
