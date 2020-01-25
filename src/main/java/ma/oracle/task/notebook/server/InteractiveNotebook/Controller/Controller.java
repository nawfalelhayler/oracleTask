package ma.oracle.task.notebook.server.interactivenotebook.Controller;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ma.oracle.task.notebook.server.interactivenotebook.models.requestmodels.InterpreterRequestModel;
import ma.oracle.task.notebook.server.interactivenotebook.models.responsemodels.InterpreterResponseModel;
import ma.oracle.task.notebook.server.interactivenotebook.service.InteractiveNotebookServices;

@RestController
@RequestMapping("/interpreter")
public class Controller {

	@Autowired
	InteractiveNotebookServices interactivenotebookservices;

	@PostMapping("/execute")
	public ResponseEntity<InterpreterResponseModel> executecommand(@RequestBody InterpreterRequestModel command)
			throws InterruptedException, IOException {
		return ResponseEntity.status(HttpStatus.ACCEPTED).body(interactivenotebookservices.executecommand(command));
	}

}
