package ma.oracle.task.notebook.server.InteractiveNotebook.common;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintStream;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.*;

import ma.oracle.task.notebook.server.InteractiveNotebook.models.requestmodels.InterpreterRequestModel;
import ma.oracle.task.notebook.server.InteractiveNotebook.models.responsemodels.InterpreterResponseModel;

@Component
public class CommonServices {

	private static final Logger LOGGER = Logger.getLogger(CommonServices.class.getName());
	InterpreterResponseModel interpreterresponsemodel;
	InterpreterRequestModel interpreterrequestmodel;

	@Autowired
	public CommonServices(InterpreterResponseModel interpreterresponsemodel,
			InterpreterRequestModel interpreterrequestmodel) {
		this.interpreterresponsemodel = interpreterresponsemodel;
		this.interpreterrequestmodel = interpreterrequestmodel;
	}

	/*
	 * Function that takes the input and retrieve the command using Regex, the goal
	 * is to have the command without %,interpreter name and the white space
	 */
	public String commandRegex(String theRegex, InterpreterRequestModel code) {
		String resultRegex = null;
		String command = code.getCode();
		Pattern checkRegex = Pattern.compile(theRegex);
		Matcher regexMatcher = checkRegex.matcher(command);
		while (regexMatcher.find()) {
			if (regexMatcher.group().length() != 0) {
				resultRegex = regexMatcher.group().replaceAll("^\\s+", "");
			}
		}
		code.setCode(resultRegex);
		return resultRegex;
	}

	/*
	 * Function to create a script file base on the command thats will be sent to
	 * the application
	 */
	public void createScript(String scriptLocation, InterpreterRequestModel interpreterrequestmodel)
			throws IOException {
		try (FileWriter myWriter = new FileWriter(scriptLocation)) {
			myWriter.write(interpreterrequestmodel.getCode().replaceAll("^\\s+", ""));
			LOGGER.log(Level.FINE, "Successfully wrote to the file.");
		}
	}

	/*
	 * Function that excute the script sent by a user,the process of the interpreter
	 * should be running and aded to the path
	 */
	public String executeScript(String script) throws IOException, InterruptedException {
		Process p = Runtime.getRuntime().exec("python " + script);
		String line;
		p.waitFor();
		BufferedReader error = new BufferedReader(new InputStreamReader(p.getErrorStream()));
		error.close();
		BufferedReader input = new BufferedReader(new InputStreamReader(p.getInputStream()));
		line = input.readLine();
		LOGGER.log(Level.FINE, "the response is: {0}", line);
		OutputStream outputStream = p.getOutputStream();
		PrintStream printStream = new PrintStream(outputStream);
		printStream.println();
		printStream.flush();
		printStream.close();
		return line;
	}
}
