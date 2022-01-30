package com.epam.esm.repository.entity;

import com.epam.esm.repository.dto.TagDto;
import org.hibernate.envers.Audited;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;

@Entity
@Audited
@Table(name = "tag")
public class Tag {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public int id;

    @Column(name = "name")
    private String name;

    @ManyToMany(fetch = FetchType.LAZY, mappedBy = "tags")
    private List<Certificate> certificates = new ArrayList<>();

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

    public Tag() {
    }

    public Tag(int id, String name) {
        this.id = id;
        this.name = name;
    }


    public Tag(@Size(min = 1, max = 45) String name) {
        this.name = name;
    }

    public Tag(int id) {
        this.id = id;
    }

    public TagDto toDto() {
        TagDto tagDto = new TagDto();
        tagDto.setId(this.id);
        tagDto.setName(this.name);
        return tagDto;
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
        Tag tag = (Tag) o;
        if (id != tag.id) return false;
        return name != null ? name.equals(tag.name) : tag.name == null;
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
