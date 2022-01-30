package com.epam.esm.repository.dao;

import com.epam.esm.repository.entity.Certificate;
import com.epam.esm.repository.entity.Tag;

import java.util.List;
import java.util.Optional;

public interface CertificateDao {

    /**
     * Create certificate.
     *
     * @param certificate the certificate
     * @return the certificate
     */
    Certificate create(Certificate certificate);

    /**
     * Read optional.
     *
     * @param certificateId the certificateId
     * @return the optional
     */
    Optional<Certificate> read(int certificateId);

    /**
     * Readl all certificates
     *
     * @return list of certificates
     */
    List<Certificate> readAll(int page, int size);


    List<Certificate> readCertificateWithParams(String tagName, String descriptionOrNamePart,
                                                String sortParameters, boolean ascending,
                                                int page, int size);

    /**
     * Update int.
     *
     * @param certificate the certificate
     * @return the int
     */
    void update(Certificate certificate);

    /**
     * Read certificate tags list.
     *
     * @param certificateId the certificate id
     * @return the list
     */
    List<Tag> readCertificateTags(int certificateId);

    /**
     * Delete certificate by certificate id.
     *
     * @param certificateId the certificate id
     * @return the int
     */
    int delete(int certificateId);

    /**
     * Delete certificate tags by tag id.
     *
     * @param tagId the tag id
     */
    int deleteBondingTagsByTagId(int tagId);

    /**
     * Delete certificate tags by certificate id.
     *
     * @param certificateId the certificate id
     */
    int deleteBondingTagsByCertificateId(int certificateId);

    /**
     * Read certificates by some tags (AND conditions)
     *
     * @param tags
     * @return
     */
    List<Certificate> readCertificatesByTagNames(List<String> tags, int page, int size);












}
