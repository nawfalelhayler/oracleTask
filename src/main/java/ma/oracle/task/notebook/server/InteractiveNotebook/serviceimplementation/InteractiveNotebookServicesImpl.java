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
		 * Taking the command by removing %<interpreterName><whitespace> using regex
		 * pattern
		 */
		String interpreter = commonservices.retrieveInterpreter(interpreterrequestmodel);
		commonservices.commandRegex(interpreterrequestmodel);
		
		/*
		 * Executing the Script created in the pathTofile ( the value of this variable
		 * is outsourced in bootstrap.properties
		 */
		StringBuilder result = commonservices.executeScript(interpreter,  interpreterrequestmodel);
		interpreterresponsemodel.setResult(result);
		return interpreterresponsemodel;
	}
}
