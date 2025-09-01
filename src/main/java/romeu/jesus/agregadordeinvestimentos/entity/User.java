package romeu.jesus.agregadordeinvestimentos.entity;

import jakarta.persistence.*;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "tb_users")
public class User {
    @Id
    @Column(name = "userid")
    private UUID userId;

    @Column(name = "username")
    private String userName;

    @Column(name = "email")
    private String email;

    @Column(name = "password")
    private String password;

    @Column(name = "creationTimeStamp")
    private Instant creationTimeStamp;

    @Column(name = "updatedtimestamp")
    private Instant createdTimeStamp;

    @OneToMany(mappedBy = "user")
    private List<Account> accounts;

    public User() {
    }


    public User(UUID userId, String userName, String email, String password, Instant creationTimeStamp, Instant createdTimeStamp, List<Account> accounts) {
        this.userId = userId;
        this.userName = userName;
        this.email = email;
        this.password = password;
        this.creationTimeStamp = creationTimeStamp;
        this.createdTimeStamp = createdTimeStamp;
        this.accounts = accounts;
    }

    public UUID getUserId() {
        return userId;
    }

    public void setUserId(UUID userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
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

    public Instant getCreationTimeStamp() {
        return creationTimeStamp;
    }

    public void setCreationTimeStamp(Instant creationTimeStamp) {
        this.creationTimeStamp = creationTimeStamp;
    }

    public Instant getCreatedTimeStamp() {
        return createdTimeStamp;
    }

    public void setCreatedTimeStamp(Instant createdTimeStamp) {
        this.createdTimeStamp = createdTimeStamp;
    }

    public List<Account> getAccounts() {
        return accounts;
    }

    public void setAccounts(List<Account> accounts) {
        this.accounts = accounts;
    }
}
