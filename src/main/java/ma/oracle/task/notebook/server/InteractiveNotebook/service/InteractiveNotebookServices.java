package ma.oracle.task.notebook.server.interactivenotebook.service;

import java.io.IOException;

import ma.oracle.task.notebook.server.interactivenotebook.models.requestmodels.InterpreterRequestModel;
import ma.oracle.task.notebook.server.interactivenotebook.models.responsemodels.InterpreterResponseModel;

public interface InteractiveNotebookServices {

	InterpreterResponseModel executecommand(InterpreterRequestModel interpreterrequestmodel) throws InterruptedException, IOException;

}
