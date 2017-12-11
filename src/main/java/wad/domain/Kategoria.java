package wad.domain;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.AbstractPersistable;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
public class Kategoria extends AbstractPersistable<Long> {

    @ManyToMany(mappedBy = "kategoriat")
    private List<Uutinen> uutiset;
    private String nimi;

    public void lisaaUutinen(Uutinen u) {
        if (this.uutiset == null) {
            this.uutiset = new ArrayList<>();
        }
        if (!this.uutiset.contains(u)) {
            uutiset.add(u);
        }
    }
}
