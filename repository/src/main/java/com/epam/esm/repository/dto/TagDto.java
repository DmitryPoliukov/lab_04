package com.epam.esm.repository.dto;

import com.epam.esm.repository.entity.Tag;
import org.springframework.hateoas.RepresentationModel;

import javax.validation.constraints.Size;

public class TagDto extends RepresentationModel<TagDto> {

    private int id;

    @Size(min=1,max = 45, message = "Tag name length should be from 1 to 45")
    private String name;

    public Tag toEntity() {
        Tag entityTag = new Tag();
        entityTag.setId(this.id);
        entityTag.setName(this.name);
        return entityTag;
    }

    public TagDto() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Tag{");
        sb.append("id=").append(id);
        sb.append(", name='").append(name).append('\'');
        sb.append('}');
        return sb.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TagDto tagDto = (TagDto) o;
        if (id != tagDto.id) return false;
        return name != null ? name.equals(tagDto.name) : tagDto.name == null;
    }

    @Override
    public int hashCode() {
        int result = 1;
        int prime = 31;
        result = prime * result + id;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        return result;
    }
}
