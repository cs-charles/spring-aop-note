package com.orange.springaoptest.model;

import lombok.Data;

/**
 * @author xieyong
 * @date 2020/1/19
 * @Description:
 */
@Data
public class UserDTO {
    private Long userId;

    private String userName;

    private String address;
}
