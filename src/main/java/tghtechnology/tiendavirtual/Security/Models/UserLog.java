package tghtechnology.tiendavirtual.Security.Models;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "user_log")
public class UserLog {

    @Id
    @Column(length = 30)
    private String username;
    
    @Column(nullable = false)
    private Short failedAttempts;
    
    @Column(nullable = false)
    private LocalDateTime lastAttempt;
    
    @Column(nullable = false)
    private Boolean successful;

    
}
