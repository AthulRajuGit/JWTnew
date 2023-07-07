package com.springsec.example.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Products {

	
	private int productId;
    private String name;
    private int qty;
    private double price;
}
