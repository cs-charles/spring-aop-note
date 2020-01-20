package com.orange.springaoptest.service.impl;

import com.orange.springaoptest.annotation.IsLogin;
import com.orange.springaoptest.model.UserDTO;
import com.orange.springaoptest.service.UserService;
import org.springframework.stereotype.Service;

/**
 * @author xieyong
 * @date 2020/1/19
 * @Description:
 */
@Service
public class UserServiceImpl implements UserService {

    @Override
    @IsLogin
    public UserDTO searchUserInfo(Long userId) throws NullPointerException {
        if(userId == null){
            throw new  NullPointerException();
        }
        UserDTO userDTO = new UserDTO();
        userDTO.setUserId(userId);
        userDTO.setUserName("张飞");
        userDTO.setAddress("杭州市余杭区");
        return userDTO;
    }
}
