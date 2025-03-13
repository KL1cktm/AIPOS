package by.yurhilevich.WebApp.dto;

import lombok.Data;

@Data
public class ProductDTO {
    private Long id;
    private String name;
    private String sort;
    private String groupName; // Передаем только имя группы

    // геттеры и сеттеры
}