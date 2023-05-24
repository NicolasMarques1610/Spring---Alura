package med.voll.api.infra.exception;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.sql.SQLIntegrityConstraintViolationException;

@RestControllerAdvice
public class TratadorDeErros {

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity tratarErro404IdInexistente() {
        return ResponseEntity.notFound().build();
    }

    @ExceptionHandler(NullPointerException.class)
    public ResponseEntity tratarErro404AtivoFalse() {
        return ResponseEntity.notFound().build();
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity tratarErro400(MethodArgumentNotValidException exception) {
        var erros = exception.getFieldErrors();

        return ResponseEntity.badRequest().body(erros.stream().map(ErrosValidacaoDTO::new).toList());
    }

//    422 Unprocessable Entity indica que o servidor entende o tipo de conteúdo da entidade da requisição,
//    e a sintaxe da requisição esta correta, mas não foi possível processar as instruções presentes.
    @ExceptionHandler(SQLIntegrityConstraintViolationException.class)
    public ResponseEntity tratarErro422() {
        return ResponseEntity.unprocessableEntity().body("Usuário já cadastrado!");
    }

    private record ErrosValidacaoDTO(String campo, String msg) {
        public ErrosValidacaoDTO(FieldError erro) {
            this(erro.getField(), erro.getDefaultMessage());
        }
    }
}
