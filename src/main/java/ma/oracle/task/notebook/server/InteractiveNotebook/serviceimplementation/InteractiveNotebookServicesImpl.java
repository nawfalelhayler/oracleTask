package ma.oracle.task.notebook.server.interactivenotebook.serviceimplementation;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;

import ma.oracle.task.notebook.server.interactivenotebook.common.CommonServices;
import ma.oracle.task.notebook.server.interactivenotebook.models.requestmodels.InterpreterRequestModel;
import ma.oracle.task.notebook.server.interactivenotebook.models.responsemodels.InterpreterResponseModel;
import ma.oracle.task.notebook.server.interactivenotebook.service.InteractiveNotebookServices;

@Service
@PropertySource("classpath:bootstrap.properties")
public class InteractiveNotebookServicesImpl implements InteractiveNotebookServices {

	InterpreterResponseModel interpreterresponsemodel = new InterpreterResponseModel();
	CommonServices commonservices;
	@Value("${path.file.used}")
	String pathToFile;

	@Autowired
	public InteractiveNotebookServicesImpl(CommonServices commonservice) {
		this.commonservices = commonservice;
	}

	@Override
	public InterpreterResponseModel executecommand(InterpreterRequestModel interpreterrequestmodel)
			throws InterruptedException, IOException {
		// Taking the command by removing %<interpreterName><whitespace> using regex
		// pattern
		commonservices.commandRegex(interpreterrequestmodel);
		StringBuilder result = commonservices.executeScript(pathToFile, interpreterrequestmodel);
		interpreterresponsemodel.setResult(result);
		return interpreterresponsemodel;

	}
}
