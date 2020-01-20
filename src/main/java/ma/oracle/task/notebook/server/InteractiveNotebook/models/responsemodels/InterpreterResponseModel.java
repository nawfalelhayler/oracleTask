package ma.oracle.task.notebook.server.InteractiveNotebook.models.responsemodels;

import org.springframework.stereotype.Component;

@Component
public class InterpreterResponseModel {

	private StringBuilder result;

	public StringBuilder getResult() {
		return result;
	}

	public void setResult(StringBuilder result) {
		this.result = result;
	}

}
