package ma.oracle.task.notebook.server.InteractiveNotebook.serviceimplementation;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ma.oracle.task.notebook.server.InteractiveNotebook.common.CommonServices;
import ma.oracle.task.notebook.server.InteractiveNotebook.models.requestmodels.InterpreterRequestModel;
import ma.oracle.task.notebook.server.InteractiveNotebook.models.responsemodels.InterpreterResponseModel;
import ma.oracle.task.notebook.server.InteractiveNotebook.service.InteractiveNotebookServices;

@Service
public class InteractiveNotebookServicesImpl implements InteractiveNotebookServices {

	InterpreterResponseModel interpreterresponsemodel;
	CommonServices commonservices;

	@Autowired
	public InteractiveNotebookServicesImpl(InterpreterResponseModel interpreterresponsemodel,
			CommonServices commonservice) {
		this.interpreterresponsemodel = interpreterresponsemodel;
		this.commonservices = commonservice;
	}

	@Override
	public InterpreterResponseModel executecommand(InterpreterRequestModel interpreterrequestmodel)
			throws InterruptedException, IOException {
		commonservices.commandRegex("\\s(.*)", interpreterrequestmodel);
		commonservices.createScript("D:\\Microworkspace\\InteractiveNotebook\\script.py", interpreterrequestmodel);
		StringBuilder resultat = commonservices.executeScript("D:\\Microworkspace\\InteractiveNotebook\\script.py");
		interpreterresponsemodel.setResult(resultat);
		return interpreterresponsemodel;

	}
}
