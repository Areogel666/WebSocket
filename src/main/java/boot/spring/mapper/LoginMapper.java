package boot.spring.mapper;

import boot.spring.po.Staff;
import org.springframework.stereotype.Repository;

@Repository
public interface LoginMapper {

    Staff getpwdbyname(String name);

    Staff getnamebyid(String id);
}
