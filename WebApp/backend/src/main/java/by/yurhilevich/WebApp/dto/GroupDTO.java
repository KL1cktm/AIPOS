package by.yurhilevich.WebApp.dto;

import by.yurhilevich.WebApp.models.Product;
import jakarta.persistence.Column;
import jakarta.persistence.OneToMany;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class GroupDTO {
    private Long groupId;
    private String name;
}
