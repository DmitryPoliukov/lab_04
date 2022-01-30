package com.epam.esm.service.impl;

import com.epam.esm.repository.dao.CertificateDao;
import com.epam.esm.repository.dao.TagDao;
import com.epam.esm.repository.dao.impl.TagDaoImpl;
import com.epam.esm.repository.dao.impl.UserDaoImpl;
import com.epam.esm.repository.dto.TagDto;
import com.epam.esm.repository.entity.Tag;
import com.epam.esm.repository.entity.User;
import com.epam.esm.service.TagService;
import com.epam.esm.service.exception.ResourceNotFoundException;
import com.epam.esm.service.exception.ResourceValidationException;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class TagServiceImplTest {
    TagDao tagDao = mock(TagDao.class);
    CertificateDao certificateDao = mock(CertificateDao.class);

    private TagServiceImpl tagService = new TagServiceImpl(tagDao, certificateDao);

    private static final Tag TAG_1 = new Tag(1, "tagName1");
    private static final Tag TAG_2 = new Tag(2, "tagName2");
    private static final Tag TAG_3 = new Tag(3, "tagName3");
    private static final Tag TAG_4 = new Tag(4, "tagName4");
    private static final Tag TAG_5 = new Tag(5, "tagName5");

    private static final User USER_1 = new User("dmitry", "radoc");

    private static final String SORT_PARAMETER = "ASC";
    private static final int PAGE = 0;
    private static final int SIZE = 5;

    @Test
    void create() {
        when(tagDao.create(TAG_1)).thenReturn(TAG_1);

        Tag actual = tagService.create(TAG_1.toDto()).toEntity();
        Tag expected = TAG_1;

        assertEquals(expected, actual);
    }

    @Test
    void read() {
        when(tagDao.read(TAG_3.getId())).thenReturn(Optional.of(TAG_3));

        Tag actual = tagService.read(TAG_3.getId()).toEntity();
        Tag expected = TAG_3;

        assertEquals(expected, actual);

    }

    @Test
    void readAll() {

        List<Tag> tags = Arrays.asList(TAG_1, TAG_2, TAG_3, TAG_4, TAG_5);

        when(tagDao.readAll(PAGE, SIZE)).thenReturn(tags);

        List<Tag> actual = tagService.readAll(PAGE, SIZE).stream()
                .map(TagDto::toEntity).collect(Collectors.toList());
        List<Tag> expected = tags;

        assertEquals(expected, actual);
    }

    @Test
    void readMostWidelyTagFromUserWithHighestCostOrders() {
        when(tagDao.readMostWidelyTagFromUserWithHighestCostOrders()).thenReturn(TAG_1);

        Tag actual = tagService.readMostWidelyTagFromUserWithHighestCostOrders().toEntity();
        Tag expected = TAG_1;

        assertEquals(expected, actual);
    }


}