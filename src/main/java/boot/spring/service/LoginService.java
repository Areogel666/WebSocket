package boot.spring.service;


public interface LoginService {

    String getpwdbyname(String name);

    String getUidbyname(String name);

    String getnamebyid(String id);
}
