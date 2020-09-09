package boot.spring.service.impl;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import boot.spring.mapper.LoginMapper;
import boot.spring.po.Staff;
import boot.spring.service.LoginService;

@Service("loginservice")
public class LoginServiceImpl implements LoginService {
    @Autowired
    LoginMapper loginMapper;

    public String getpwdbyname(String name) {
        Staff s = loginMapper.getpwdbyname(name);
        if (s != null)
            return s.getPassword();
        else
            return null;
    }

    public String getUidbyname(String name) {
        Staff s = loginMapper.getpwdbyname(name);
        if (s != null)
            return s.getStaff_id();
        else
            return null;
    }

    public String getnamebyid(String id) {
        Staff s = loginMapper.getnamebyid(id);
        if (s != null)
            return s.getUsername();
        else
            return null;
    }


}
