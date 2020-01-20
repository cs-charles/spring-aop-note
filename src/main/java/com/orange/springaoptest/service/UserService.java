package com.orange.springaoptest.service;

import com.orange.springaoptest.model.UserDTO;

/**
 * @author xieyong
 * @date 2020/1/19
 * @Description:
 */
public interface UserService {

    /**
     *  查询个人信息
     * @param userId
     * @return
     */
    public UserDTO searchUserInfo(Long userId) throws NullPointerException;
}
