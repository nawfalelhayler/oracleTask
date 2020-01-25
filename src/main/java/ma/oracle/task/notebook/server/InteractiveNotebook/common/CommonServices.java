package ma.oracle.task.notebook.server.interactivenotebook.common;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.RandomAccessFile;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.stereotype.Component;

import ma.oracle.task.notebook.server.interactivenotebook.models.requestmodels.InterpreterRequestModel;
import ma.oracle.task.notebook.server.interactivenotebook.models.responsemodels.InterpreterResponseModel;

@Component
public class CommonServices {

	private static final Logger LOGGER = Logger.getLogger(CommonServices.class.getName());
	InterpreterResponseModel interpreterresponsemodel = new InterpreterResponseModel();
	InterpreterRequestModel interpreterrequestmodel = new InterpreterRequestModel();

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
	 * Function that execute the script sent by a user,the process of the
	 * interpreter should be running and added to the path
	 */
	public StringBuilder executeScript(String script, InterpreterRequestModel interpreterrequestmodel)
			throws IOException, InterruptedException {
		// Insert in the file
		BufferedWriter writer = new BufferedWriter(
				new FileWriter("D:\\Microworkspace\\InteractiveNotebook\\ReusedScript.txt", true));
		BufferedReader reader = new BufferedReader(
				new FileReader("D:\\Microworkspace\\InteractiveNotebook\\ReusedScript.txt"));
		writer.append(interpreterrequestmodel.getCode() + "\r\n");
		writer.close();
		// Execute All the file
		Process p = Runtime.getRuntime().exec("python D:\\Microworkspace\\InteractiveNotebook\\ReusedScript.txt");
		// Process p =Runtime.getRuntime().exec("python " + script);
		p.waitFor();
		StringBuilder resultScript = new StringBuilder();
		BufferedReader error = new BufferedReader(new InputStreamReader(p.getErrorStream()));
		BufferedReader input = new BufferedReader(new InputStreamReader(p.getInputStream()));
		String line;
		while ((line = input.readLine()) != null) {
			resultScript.append(line + "\n");
		}

		while ((line = error.readLine()) != null) {
			resultScript.append(line + "\n");
		}
		return resultScript;
	}
}
