package com.jpabook.jpahop.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
public class Member {
    @Id
    @GeneratedValue
    @Column(name = "member_id")
    private Long id;

    private String name;

    @Embedded //내장타입을 포함했다는 Annotation
    private Address address;

    @OneToMany(mappedBy="member")
    private List<Order> orders = new ArrayList<>();
}
