package by.yurhilevich.WebApp.models;

import jakarta.persistence.*;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@Table(name = "product_groups")
public class Group {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long groupId;

    @Column(name = "name", nullable = false)
    private String name;

    @OneToMany(mappedBy = "group")
    private List<Product> products = new ArrayList<>();
}
