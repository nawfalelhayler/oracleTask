package ma.oracle.task.notebook.server.InteractiveNotebook.service;

import java.io.IOException;

import ma.oracle.task.notebook.server.InteractiveNotebook.models.requestmodels.InterpreterRequestModel;
import ma.oracle.task.notebook.server.InteractiveNotebook.models.responsemodels.InterpreterResponseModel;

public interface InteractiveNotebookServices {

	InterpreterResponseModel executecommand(InterpreterRequestModel interpreterrequestmodel) throws InterruptedException, IOException;

}
