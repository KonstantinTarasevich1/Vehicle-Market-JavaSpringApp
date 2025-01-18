package my.vehiclemarket.model;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;

public class UserData extends User {

    private final String fullName;

    public UserData(String username,
                    String password,
                    Collection<? extends GrantedAuthority> authorities,
                    String fullName

    ) {
        super(username, password, authorities);
        this.fullName = fullName;
    }

    public String getFullName() {
        return fullName;
    }

}
