package controller;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import window.CompilerWindow;

public class CompilerController {
	private CompilerWindow parent;

	public CompilerController(CompilerWindow parentWindow) {
		parent = parentWindow;
	}

	public String getFile(String program) throws IOException {
		String file = "";
			BufferedReader in = new BufferedReader(new FileReader("src/"+program+".txt"));
			while(in.ready()){
				file+=in.readLine()+"\n";
			}
		
		return file;
	}
}
