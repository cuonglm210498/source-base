package com.lecuong.sourcebase.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "category")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Category extends BaseEntity {

    @Column
    private String name;

    @OneToMany(mappedBy = "category")
    private List<Blog> blogs = new ArrayList<>();

//    @OneToMany(mappedBy = "category")
//    private List<Product> products = new ArrayList<>();
}
