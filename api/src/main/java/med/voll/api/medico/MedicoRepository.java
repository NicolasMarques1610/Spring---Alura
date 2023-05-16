package med.voll.api.medico;

import org.springframework.data.jpa.repository.JpaRepository;

public interface MedicoRepository extends JpaRepository<Medico, Long> { //Generics <primeiro é o tipo da entidade, o tipo de atributo da chave primária>
}
