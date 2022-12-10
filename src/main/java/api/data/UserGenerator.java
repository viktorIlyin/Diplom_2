package api.data;

import org.apache.commons.lang3.RandomStringUtils;

public class UserGenerator {
    private String email;
    private String password;
    private String name;

    public UserGenerator(String email, String password, String name) {
        this.email = email;
        this.password = password;
        this.name = name;
    }

    public static UserGenerator generateUser() {
        String email = RandomStringUtils.randomAlphabetic(3, 8) + "@me.com";
        String password = RandomStringUtils.randomAlphabetic(8);
        String name = RandomStringUtils.randomAlphabetic(3, 8);

        return new UserGenerator(email, password, name);
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
