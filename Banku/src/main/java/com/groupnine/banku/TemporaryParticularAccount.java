package com.groupnine.banku;

import java.time.LocalDateTime;

public class TemporaryParticularAccount {
    private LocalDateTime creationDate;
    private LocalDateTime expirationDate;
    private Boolean boost;

    public LocalDate getCreationDate() {
        return creationDate;
    }

    public LocalDate getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(LocalDate expirationDate) {
        this.expirationDate = expirationDate;
    }
}
