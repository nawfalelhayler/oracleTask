package ma.oracle.task.notebook.server.InteractiveNotebook.Controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ma.oracle.task.notebook.server.InteractiveNotebook.models.requestmodels.InterpreterRequestModel;
import ma.oracle.task.notebook.server.InteractiveNotebook.models.responsemodels.InterpreterResponseModel;

@RestController
@RequestMapping("/interpreter")
public class Controller {
	
	@PostMapping
	private ResponseEntity<InterpreterResponseModel> executecommand(@RequestBody InterpreterRequestModel command){
		InterpreterResponseModel interpreterresponsemodel = null;
		return ResponseEntity.status(HttpStatus.ACCEPTED).body(interpreterresponsemodel);
	}

}
