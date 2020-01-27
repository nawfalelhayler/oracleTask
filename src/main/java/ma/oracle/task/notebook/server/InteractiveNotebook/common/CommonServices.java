package ma.oracle.task.notebook.server.interactivenotebook.common;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.RandomAccessFile;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.annotation.PostConstruct;

import org.springframework.stereotype.Component;

import ma.oracle.task.notebook.server.interactivenotebook.exceptions.CodeCannotBeParsedException;
import ma.oracle.task.notebook.server.interactivenotebook.exceptions.InterpreterNotKnownException;
import ma.oracle.task.notebook.server.interactivenotebook.models.requestmodels.InterpreterRequestModel;
import ma.oracle.task.notebook.server.interactivenotebook.models.responsemodels.InterpreterResponseModel;

@Component
public class CommonServices {

	private static final Logger LOGGER = Logger.getLogger(CommonServices.class.getName());
	InterpreterResponseModel interpreterresponsemodel = new InterpreterResponseModel();
	InterpreterRequestModel interpreterrequestmodel = new InterpreterRequestModel();
	String pathToFile = System.getProperty("user.dir") + "\\src\\main\\resources\\scripts\\script.txt";

	/*
	 * This method is for deleting the script file reserved to a user after the
	 * application startup
	 */
	@PostConstruct
	public void init() {
		File file = new File(pathToFile);
		if (file.delete()) {
			LOGGER.log(Level.FINE, "The script file is deleted successfully");

		} else {
			LOGGER.log(Level.WARNING, "Failed to delete the script file");
		}
	}

	public String retrieveInterpreter(InterpreterRequestModel code) {
		String interpreter = null;
		String request = code.getCode();
		Pattern checkRegex = Pattern.compile("^%[a-zA-Z]*");
		Matcher regexMatcher = checkRegex.matcher(request);
		while (regexMatcher.find()) {
			if (regexMatcher.group().length() != 0) {
				interpreter = regexMatcher.group().replace("%", "");
			}
		}
		if (!interpreter.equals("python")) {
			throw new InterpreterNotKnownException(
					"InterpreterNotKnownException: the interpreter is not handled by the application");

		}
		return interpreter;

	}

	/*
	 * Function that takes the input and retrieve the command using Regex, the goal
	 * is to have the command without %,interpreter name and the white space
	 */
	public String commandRegex(InterpreterRequestModel code) {
		String resultRegex = null;
		String command = code.getCode();
		if (!Pattern.matches("^\\%[^\\s]+[ ].*", command)) {
			throw new CodeCannotBeParsedException(
					"CodeCannotBeParsedException: Wrong request form, please make sure the request follow the following from : %<interpreter-name><whitespace><code>");
		}
		Pattern checkRegex = Pattern.compile("\\s(.*)");
		Matcher regexMatcher = checkRegex.matcher(command);
		while (regexMatcher.find()) {
			if (regexMatcher.group().length() != 0) {
				resultRegex = regexMatcher.group().replaceAll("^\\s+", "");
			}
		}
		return resultRegex;
	}

	/*
	 * Function that execute the script sent by a user,the process of the
	 * interpreter should be running and added to the path
	 */
	public StringBuilder executeScript(String interpreter, String codeToBeExecuted)
			throws IOException, InterruptedException {
		// Insert in the file
		BufferedWriter writer = new BufferedWriter(new FileWriter(pathToFile, true));
		writer.append(codeToBeExecuted + "\r\n");
		writer.close();
		// Execute All the file
		Process p = Runtime.getRuntime().exec(interpreter + " " + pathToFile);
		p.waitFor();
		StringBuilder resultScript = new StringBuilder();
		// Taking the error sent by the process
		String line;
		BufferedReader error = new BufferedReader(new InputStreamReader(p.getErrorStream()));
		while ((line = error.readLine()) != null) {
			resultScript.append(line + "\n");
		}
		// Taking the response sent by the process
		BufferedReader input = new BufferedReader(new InputStreamReader(p.getInputStream()));
		while ((line = input.readLine()) != null) {
			resultScript.append(line + "\n");
		}

		// Control if the code is to be reused or not , ie: affectation or an import etc
		if (resultScript.length() != 0) {
			RandomAccessFile raf = null;
			try {
				raf = new RandomAccessFile(pathToFile, "rw");
				raf.seek(0);
				raf.setLength(raf.length() - interpreterrequestmodel.getCode().length() - 2);
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				raf.close();
			}

		}
		return resultScript;
	}
}
