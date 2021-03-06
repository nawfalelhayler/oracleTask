package ma.oracle.task.notebook.server.interactivenotebook.exceptions;

import java.util.Date;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class InteractiveNotebookExceptionHandler extends ResponseEntityExceptionHandler {

	@ExceptionHandler(value = { Exception.class })
	public ResponseEntity<Object> handleanyexception(Exception ex, WebRequest request) {
		return new ResponseEntity<>(ex, new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@ExceptionHandler(value = { InterpreterNotKnownException.class })
	public ResponseEntity<Object> handleInterpreterNotKnownException(InterpreterNotKnownException ex, WebRequest request) {

		ErrorMessage errormessage = new ErrorMessage(new Date(), ex.getLocalizedMessage());
		return new ResponseEntity<>(errormessage, new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	@ExceptionHandler(value = { CodeCannotBeParsedException.class })
	public ResponseEntity<Object> handleCodeCannotBeParsedException(CodeCannotBeParsedException ex, WebRequest request) {

		ErrorMessage errormessage = new ErrorMessage(new Date(), ex.getLocalizedMessage());
		return new ResponseEntity<>(errormessage, new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR);
	}
}
