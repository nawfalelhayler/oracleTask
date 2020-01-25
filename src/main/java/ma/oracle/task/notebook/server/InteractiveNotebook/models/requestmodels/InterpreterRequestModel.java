package ma.oracle.task.notebook.server.interactivenotebook.models.requestmodels;

import org.springframework.stereotype.Component;

@Component
public class InterpreterRequestModel {

	private String code;

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

}
