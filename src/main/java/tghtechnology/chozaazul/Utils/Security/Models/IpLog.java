package tghtechnology.chozaazul.Utils.Security.Models;

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
@Table(name = "ip_log")
public class IpLog {

    @Id
    @Column(length = 16)
    private String ip;
    
    @Column(nullable = false)
    private String username;
    
    @Column(nullable = false)
    private Short failedAttempts;
    
    @Column(nullable = false)
    private LocalDateTime lastAttempt;
    
    @Column(nullable = false)
    private Boolean successful;

    
}
