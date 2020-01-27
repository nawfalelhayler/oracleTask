package ma.oracle.task.notebook.server.interactivenotebook.serviceimplementation;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ma.oracle.task.notebook.server.interactivenotebook.common.CommonServices;
import ma.oracle.task.notebook.server.interactivenotebook.models.requestmodels.InterpreterRequestModel;
import ma.oracle.task.notebook.server.interactivenotebook.models.responsemodels.InterpreterResponseModel;
import ma.oracle.task.notebook.server.interactivenotebook.service.InteractiveNotebookServices;

@Service
public class InteractiveNotebookServicesImpl implements InteractiveNotebookServices {

	InterpreterResponseModel interpreterresponsemodel = new InterpreterResponseModel();
	CommonServices commonservices;

	@Autowired
	public InteractiveNotebookServicesImpl(CommonServices commonservice) {
		this.commonservices = commonservice;
	}

	@Override
	public InterpreterResponseModel executecommand(InterpreterRequestModel interpreterrequestmodel)
			throws InterruptedException, IOException {
		/*
		 * Taking the interpreter
		 */
		String interpreter = commonservices.retrieveInterpreter(interpreterrequestmodel);
		/*
		 * Taking the code to be executed by removing %<interpreterName><whitespace> using regex
		 * pattern
		 */
		String codeToBeExecuted = commonservices.commandRegex(interpreterrequestmodel);

		/*
		 * Executing the code
		 */
		StringBuilder result = commonservices.executeScript(interpreter, codeToBeExecuted);
		/*
		 * setting the result in the response model
		 */
		interpreterresponsemodel.setResult(result);
		return interpreterresponsemodel;
	}
}
