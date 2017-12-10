
package wad.domain;

import java.security.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.AbstractPersistable;

@AllArgsConstructor
@Data
@Entity
public class Uutinen extends AbstractPersistable<Long> {
    @ManyToMany
    private List<Kirjoittaja> kirjoittajat;
    @ManyToMany
    private List<Kategoria> kategoriat;
    private String otsikko;
    private String ingressi;
    private String teksti;
    private LocalDateTime julkaisuaika;
    private Integer lukukertoja;
    
    public Uutinen() {
        this.julkaisuaika = LocalDateTime.now();
    }
}
