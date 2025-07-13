package ru.itis.vhsroni.auth.data.mapper;

import org.springframework.stereotype.Component;
import ru.itis.vhsroni.auth.data.entity.UserCredentials;
import ru.itis.vhsroni.auth.data.entity.UserData;
import ru.itis.vhsroni.auth.data.enums.AuthProvider;

@Component
public class UserCredentialsMapper {

    public UserCredentials toLocalUserCredentials(UserData userData, String providerKey, String hashedPassword){
        return UserCredentials.builder()
                .userData(userData)
                .providerType(AuthProvider.LOCAL)
                .providerKey(providerKey)
                .hashPassword(hashedPassword)
                .build();
    }
}
