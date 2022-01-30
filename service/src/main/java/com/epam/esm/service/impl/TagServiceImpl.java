package com.epam.esm.service.impl;


import com.epam.esm.repository.dao.CertificateDao;
import com.epam.esm.repository.dao.TagDao;
import com.epam.esm.repository.dto.TagDto;
import com.epam.esm.repository.entity.Tag;
import com.epam.esm.service.TagService;
import com.epam.esm.service.exception.IncorrectParameterException;
import com.epam.esm.service.exception.ResourceException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class TagServiceImpl implements TagService {

    private final TagDao tagDao;
    private final CertificateDao certificateDao;

    public TagServiceImpl(TagDao tagDao, CertificateDao certificateDao) {
        this.tagDao = tagDao;
        this.certificateDao = certificateDao;
    }

    @Override
    public TagDto create(TagDto inputTag) {
        if (inputTag == null) {
            throw new IncorrectParameterException("Null parameter in create tag");
        }
        Optional<Tag> existingTag = tagDao.readByName(inputTag.getName());
        return existingTag.orElseGet(() -> tagDao.create(inputTag.toEntity())).toDto();
    }

    @Override
    public TagDto read(int id) {
        Optional<Tag> tag = tagDao.read(id);
        return tag.orElseThrow(ResourceException.notFoundWithTagId(id)).toDto();
    }

    @Override
    public List<TagDto> readAll(int page, int size) {
        List<Tag> entityList = tagDao.readAll(page, size);
        return entityList.stream()
                .map(Tag::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public void delete(int id) {
        certificateDao.deleteBondingTagsByTagId(id);
        tagDao.delete(id);
    }

    @Override
    public TagDto readMostWidelyTagFromUserWithHighestCostOrders() {
        return tagDao.readMostWidelyTagFromUserWithHighestCostOrders().toDto();
    }

}
