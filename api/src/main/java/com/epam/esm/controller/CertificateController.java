package com.epam.esm.controller;

import com.epam.esm.hateoas.HateoasAdder;
import com.epam.esm.repository.dto.CertificateDto;
import com.epam.esm.repository.dto.TagDto;
import com.epam.esm.service.CertificateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Class {@code CertificateController} is an endpoint of the API
 * which allows to perform CRUD operations on gift certificates.
 * Annotated by {@link RestController} with no parameters to provide an answer in application/json.
 * Annotated by {@link RequestMapping} with parameter value = "/certificates".
 * So that {@code CertificateController} is accessed by sending request to /certificates.
 *
 * @author Dmitry Poliukov
 */

@RestController
@RequestMapping("/certificates")
public class CertificateController {

    private final CertificateService certificateService;
    private final HateoasAdder<CertificateDto> certificateHateoasAdder;
    private final HateoasAdder<TagDto> tagDtoHateoasAdder;

    @Autowired
    public CertificateController(CertificateService certificateService,
                                 HateoasAdder<CertificateDto> certificateHateoasAdder,
                                 HateoasAdder<TagDto> tagDtoHateoasAdder) {
        this.certificateService = certificateService;
        this.certificateHateoasAdder = certificateHateoasAdder;
        this.tagDtoHateoasAdder = tagDtoHateoasAdder;
    }

    /**
     * Method for getting gift certificate by ID.
     *
     * @param id ID of gift certificate
     * @return gift certificate entity with hateoas
     */
    @GetMapping("/{id}")
    public CertificateDto readCertificate(@PathVariable int id) {
        CertificateDto certificate = certificateService.read(id);
        certificateHateoasAdder.addLinks(certificate);
        certificate.getTagsDto()
                .forEach(tagDtoHateoasAdder::addLinks);
        return certificate;
    }

    /**
     * Method for getting list of gift certificates
     *
     * @param page the number of page for pagination
     * @param size the size of page for pagination
     * @return List of gift certificates with hateoas
     */
    @GetMapping
    public List<CertificateDto> readCertificates(@RequestParam(value = "page", defaultValue = "1", required = false) @Min(1) int page,
                                                 @RequestParam(value = "size", defaultValue = "5", required = false) @Min(1) int size) {
        return certificateService.readAll(page,size).stream()
                .peek(certificateDto -> certificateDto.getTagsDto().forEach(tagDtoHateoasAdder::addLinks))
                .peek(certificateHateoasAdder::addLinks)
                .collect(Collectors.toList());
    }

    /**
     * Method for getting list of gift certificates from data source by special filter.
     *
     * @param tagName               the name of tag
     * @param descriptionOrNamePart part of name or description gift certificate
     * @param sortParameter         sort type (date or name gift certificate)
     * @param ascending             boolean sort ascending
     * @return List of found gift certificates with hateoas
     */
    @GetMapping("/search")
    public List<CertificateDto> readCertificateWithParams(@RequestParam(required = false) String tagName, @RequestParam(required = false) String descriptionOrNamePart,
                                                          @RequestParam(required = false) String sortParameter, @RequestParam(required = false) boolean ascending,
                                                          @RequestParam(value = "page", defaultValue = "1", required = false) @Min(1) int page,
                                                          @RequestParam(value = "size", defaultValue = "5", required = false) @Min(1) int size) {

        return certificateService.readCertificateWithParams(tagName, descriptionOrNamePart, sortParameter, ascending, page, size).stream()
                .peek(certificateDto -> certificateDto.getTagsDto().forEach(tagDtoHateoasAdder::addLinks))
                .peek(certificateHateoasAdder::addLinks)
                .collect(Collectors.toList());

    }

    /**
     * Method for saving new gift certificate.
     *
     * @param certificateDto gift certificate
     * @return created gift certificate with hateoas
     */
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CertificateDto createCertificate(
            @Valid @RequestBody CertificateDto certificateDto) {

        CertificateDto createdCertificate = certificateService.create(certificateDto);
        certificateHateoasAdder.addLinks(createdCertificate);
        certificateDto.getTagsDto()
                .forEach(tagDtoHateoasAdder::addLinks);
        return createdCertificate;
    }

    /**
     * Method for updating one or more fields the gift certificate.
     *
     * @param id ID of gift certificate
     * @param certificateDto gift certificate entity, which include information to update
     * @return updated gift certificate with hateoas
     */
    @PatchMapping("/{id}")
    public ResponseEntity<String> updateCertificate(@PathVariable int id,
                                                        @RequestBody CertificateDto certificateDto) {
        certificateService.update(id, certificateDto);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    /**
     * Method for removing gift certificate by ID.
     *
     * @param id ID of gift certificate
     * @return NO_CONTENT HttpStatus
     */
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<Void> deleteCertificate(@PathVariable int id) {
        certificateService.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    /**
     * Method for getting list of gift certificates from data source by several tags name (“and” condition).
     *
     * @param tag one or more tag's name
     * @param page the number of page for pagination
     * @param size the size of page for pagination
     * @return List of found gift certificates with hateoas
     */

    @GetMapping("/some-tags")
    @ResponseStatus(HttpStatus.OK)
    public List<CertificateDto> readBySomeTags(@RequestParam("tag") Optional<String[]> tag,
                                            @RequestParam(value = "page", defaultValue = "1", required = false) @Min(1) int page,
                                            @RequestParam(value = "size", defaultValue = "5", required = false) @Min(1) int size) {
    List<String> tags = Arrays.asList(tag.get());
        return certificateService.readBySomeTags(tags, page, size).stream()
                .peek(certificateDto -> certificateDto.getTagsDto().forEach(tagDtoHateoasAdder::addLinks))
                .peek(certificateHateoasAdder::addLinks)
                .collect(Collectors.toList());
    }
}
