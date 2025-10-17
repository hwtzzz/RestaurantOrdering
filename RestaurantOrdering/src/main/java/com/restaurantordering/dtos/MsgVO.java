package com.restaurantordering.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MsgVO {

    private Integer id;

    private String name;

    private String avatar;

    private String message;

    private int count;

    private Integer role;

}
