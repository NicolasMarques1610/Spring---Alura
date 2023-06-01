package med.voll.api.domain.medico;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;

public interface MedicoRepository extends JpaRepository<Medico, Long> { //Generics <primeiro é o tipo da entidade, o tipo de atributo da chave primária>
    Page<Medico> findAllByAtivoTrue(Pageable paginacao);

    Medico findByIdAndAtivoTrue(Long id);

    Boolean existsByIdAndAtivoTrue(Long id);

    // é colocado todas essas aspas para nao precisarmos ficar colocando + na string, estamos selecionando a tabela
    // Medico que tenha o ativo igual a 1 e que a especialidade seja igual a especialidade recebida por isso tem os
    // dois pontos que quer dizer que esta recebendo por parametro por isso tem que ser o nome igual ao parametro recebido.
    // depois vemos os ids dos medicos que nao estao na lista de consultas, ali na parte que fazemos uma sub consulta na tabela de Consulta.
    // entao ordenamos de forma aleatoria para pegar um medico aleatorio e depois limitamos para recebermos apenas 1 medico.
    @Query("""
            select m from Medico m
            where
            m.ativo = 1
            and
            m.especialidade = :especialidade
            and
            m.id not in(
                select c.medico.id from Consulta c
                where
                c.data = :data
                and
                c.motivoCancelamento is null
            )
            order by rand()
            limit 1
            """)
    Medico escolherMedicoAleatorioLivreNaData(Especialidade especialidade, LocalDateTime data);

    // metodo feito para dizer se o medico/paciente esta ativo no sistema atraves do id, esse metodo tem a mesma
    // logica que o existByIdAndAtivoTrue
//    @Query("""
//            select m.ativo
//            from Medico m
//            where
//            m.id = :id
//            """)
//    Boolean findAtivoById(Long id);
}
