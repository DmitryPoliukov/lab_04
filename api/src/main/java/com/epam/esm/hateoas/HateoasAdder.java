package com.epam.esm.hateoas;

import org.springframework.hateoas.RepresentationModel;

/**
 * The interface HateoasAdder describes abstract behavior for adding hateoas to objects.
 *
 * @param <T> the type parameter
 */
public interface HateoasAdder<T extends RepresentationModel<T>> {

    void addLinks(T entity);
}
