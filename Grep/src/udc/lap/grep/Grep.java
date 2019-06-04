package udc.lap.grep;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class Grep {
	private static boolean ignoreCase;
	private static boolean inputEncoding;
	private static boolean moreFile;
	private static String encodingStr;
	private static String patternStr;
	private static List<String> pathnameStr = new ArrayList<>();

	public static void main(String[] args) {

		try {
			argsIllegal(args);					// captura los argumentos y almacena en las variables estaticas
												// comprueba que sean correctas
			for (String string : Grep.search()) {
				System.out.println(string);		// busca "Grep.grepStr" e imprime los rengroles que lo contengan
			}
		} catch (IllegalArgumentEx e) {			// manejo de excepciones
			System.out.println(e.getMessage());
		} catch (FileNotFoundException e) {
			System.out.println("No encontro el Archivo");
			System.out.println(e.getMessage());
		} catch (UnsupportedEncodingException e) {
			System.out.println("encoding " + e.getMessage() + " Invalido");
		} catch (IOException e) {
			System.out.println(e.getMessage());
		}
	}

	private static void argsIllegal(String[] args) throws IllegalArgumentEx {
		int m = 0, n = 0;
		if (args.length < 2)
			throw new IllegalArgumentEx(1);
		for (String string : args) {
			if (string.startsWith("-")) {		// analisa las [OPTIONS].
				switch (string) {
				case "-i":
				case "--ignore-case":
					Grep.ignoreCase = true;		// que ignore si son mayusculas o minusculas
					break;
				case "-ie":
				case "--input-encoding":
					Grep.inputEncoding = true;	// contiene encoding
					n = m + 1;					// se asegura que encoding este en el argunento siguiente de la cadena args
					break;
				default:
					Grep.ignoreCase = false;
					Grep.inputEncoding = false;
					break;
				}
			}
			if (!(string.startsWith(".") || string.contains("*") || string.contains("/") || string.startsWith("-"))) {
				if (inputEncoding && n == m)	// se asegura que encoding este en el argunento siguiente de la cadena args
					Grep.encodingStr = string;	// captura el "encodingStr"
				else
					Grep.patternStr = string;	// captura "patternStr"
				if (!inputEncoding)
					Grep.encodingStr = null;
			}
			if (string.startsWith(".") || string.contains("*") || string.contains("/"))
				Grep.pathnameStr.add(string);	// captura el "pathnameStr" de [FILE...]
			m++;
		}
		if (Grep.pathnameStr.size() >= 2)
			Grep.moreFile = true;
		if (Grep.pathnameStr.size() == 1)
			Grep.moreFile = false;
		if (Grep.pathnameStr.size() == 0)
			throw new IllegalArgumentEx(2);
		if (Grep.patternStr == null)
			throw new IllegalArgumentEx(3);
	}

	private static List<String> search() throws IOException {
		List<String> output = new ArrayList<>();

		for (int i = 0; i < Grep.pathnameStr.size(); i++) {
			File file = new File(Grep.pathnameStr.get(i));
			Path path = Paths.get(file.getParent(),file.getName());
			
			Charset charset;
			if (Grep.inputEncoding)
				charset = Charset.forName(Grep.encodingStr);
			else
				charset = Charset.defaultCharset();

			for (String line : Files.readAllLines(path, charset)) {
				if (Grep.contains(line)) {								// busca la coisidencia sea el caso "isIgnoreCase"
					if (Grep.moreFile)									// devuelve verdadeto si lee mas de un archivo
						output.add(file.getName() + ": " + line);		// guarda en un lista linea por linea las conisidencia
					else
						output.add(line);
				}
			}
		}
		return output;
	}

	private static boolean contains(String str) {
		if (str == null || Grep.patternStr == null)				// verifica que los argumentos no sean nulos
			return false;

		final int length = Grep.patternStr.length();			// verifica que "searchStr" contentiene algun caracter
		if (length == 0)
			return true;

		for (int i = str.length() - length; i >= 0; i--) {		// verifiva que "str" contentiene a "searchStr"
			if (str.regionMatches(Grep.ignoreCase, i, Grep.patternStr, 0, length))
				return true;
		}
		return false;
	}

}
