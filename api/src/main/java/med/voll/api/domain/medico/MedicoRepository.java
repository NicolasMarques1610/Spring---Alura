package med.voll.api.domain.medico;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MedicoRepository extends JpaRepository<Medico, Long> { //Generics <primeiro é o tipo da entidade, o tipo de atributo da chave primária>
    Page<Medico> findAllByAtivoTrue(Pageable paginacao);

    Medico findByIdAndAtivoTrue(Long id);
}
