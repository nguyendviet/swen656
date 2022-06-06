package edu.umgc.swen656.aop2;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;

import org.aspectj.lang.annotation.After;

public aspect Logger {
	
	private String LOG_FILE = "./src/edu/umgc/swen656/aop2/logs.txt";
	
	@After("execution(* edu.umgc.swen656.aop2.AddressBook.*Contact(..))")
	public void logToFileAfterAddContact() {
		String log = prepareLogMessage(AddressBook.logMessage);
		saveLog(log);
	}
	
	private String prepareLogMessage(String message) {
		String timeStamp = new SimpleDateFormat("yyyy-MM-dd HH.mm.ss").format(new java.util.Date());
		String logMessage = timeStamp + " ";
		logMessage += message;
		return logMessage;
	}
	
	private void saveLog(String log) {
		try (PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter(LOG_FILE, true)))) {
		    out.println(log);
		    System.out.println(log);
		} catch (IOException e) {
		    System.err.println(e);
		}
	}
}
