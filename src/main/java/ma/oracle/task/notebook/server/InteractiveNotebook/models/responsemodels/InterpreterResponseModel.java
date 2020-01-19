package ma.oracle.task.notebook.server.InteractiveNotebook.models.responsemodels;

import org.springframework.stereotype.Component;

@Component
public class InterpreterResponseModel {

	private String result;

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}

}
