package ma.oracle.task.notebook.server.InteractiveNotebook.serviceimplementation;

import org.springframework.stereotype.Service;

import ma.oracle.task.notebook.server.InteractiveNotebook.models.requestmodels.InterpreterRequestModel;
import ma.oracle.task.notebook.server.InteractiveNotebook.models.responsemodels.InterpreterResponseModel;
import ma.oracle.task.notebook.server.InteractiveNotebook.service.InteractiveNotebookServices;

@Service
public class InteractiveNotebookServicesImpl implements InteractiveNotebookServices {

	@Override
	public InterpreterResponseModel executecommand(InterpreterRequestModel interpreterrequestmodel) {
		return null;
	}

}
