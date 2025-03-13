package by.yurhilevich.WebApp.models;

import jakarta.persistence.*;
import lombok.Data;

import java.util.HashSet;
import java.util.Set;

@Data
@Entity
@Table(name = "product")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long productId;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "sort",nullable = false)
    private String sort;

    @OneToMany(mappedBy = "product") //удаление цены должно удалять продукт
    private Set<Price> prices = new HashSet<>();

    @ManyToOne
    @JoinColumn(name = "group_id") // Предположим, это имя столбца в таблице
    private Group group;
}

