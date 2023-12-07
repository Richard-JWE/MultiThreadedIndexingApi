package ie.atu.sw;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.concurrent.*;


/**
 * Starts the menu so the user is able to select an option Adds functionality to
 * the options in <i>MenuOptions</i>. Extends the <i>MenuTemplate</i> for some
 * core features
 *
 * @see ie.atu.sw.MenuTemplate
 * @see MenuOptions
 */
/**
 * @author richardjameson
 *
 */


/**
 * Represents a menu-driven text processing application.
 * Allows users to perform various operations on text files.
 */
public class Menu extends abstractFiles {
	private boolean keepRunning = true;
	private int lineNumber;
	private String inputFileName;
	private String outputFileName;
	private String excludedWordsFile = "./google-1000.txt";
	private String dictionaryFile = "./dictionary.csv";
	private Map<String, String> idx = new ConcurrentSkipListMap<>();
	private Map<String, String> dictionaryList = new ConcurrentSkipListMap<>();
	private Set<String> excludedWords = new ConcurrentSkipListSet<>();
	private Map<String, List<Integer>> wordsCount = new TreeMap<>();
	private Scanner scannerChoice = new Scanner(System.in);
	private int page;

	/**
	 * @return inputFileName true/false
	 */
	private boolean isFileLoaded() {
	    return inputFileName != null;
	}

	/**
	 * Checks if output file name has been set
	 *
	 * @return outputFileNameSet true/false
	 */
	private boolean outputFileNameSet() {
		return outputFileName != null;
	}
	
	/**
	 * @return true/false 
	 */
	private boolean requirements() {
		return isFileLoaded() && outputFileNameSet();
			
	}

	/**
     * Starts the menu loop, allowing users to choose various options.
     * Handles user input and invokes corresponding methods.
     *
     * @throws InterruptedException if interrupted during thread operations
     * @throws IOException          if there's an issue with IO operations
     */
	public void start() throws InterruptedException, IOException {
	    while (keepRunning) {
	        MenuOptions.show();
	        int choice = scannerChoice.nextInt();
	        switch (choice) {
	            case 1:
	                loadTextFile();
	                break;
	            case 2:
	                configureDictionary(dictionaryFile);
	                break;
	            case 3:
	                configureExcludedWords(excludedWordsFile);
	                break;
	            case 4:
	                setOutputFileName();
	                break;
	            case 5:
	                buildIndex(inputFileName);
	                break;
	            case 6:
	                System.out.println("Shutting down...please wait...");
	                keepRunning = false;
	                break;
	            default:
	                System.out.println("Error: Invalid input. Please enter a number between 1 and 6.");
	                break;
	        }
	    }
	}
	
	 /**
     * Processes the text file to build an index based on user's configuration.
     *
     * @param book The path of the input text file
     * @throws IOException if there's an issue with IO operations
     */
	private void buildIndex(String book) throws IOException {
		if (requirements()) {
			long time = System.currentTimeMillis();
			
			time = System.currentTimeMillis() - time;
			
			System.out.println("Index Creating");
			parseBook(book);
			writeToTextFile(idx, outputFileName);
			System.out.println("[Index file " + outputFileName + " completed");
	

		}
		System.out.println("Make sure requirements are met");
		System.out.println("Input file: " + isFileLoaded());
		System.out.println("Output file: " + outputFileNameSet());
	}



	/**
	 * @return
	 */
	private String setOutputFileName() {
		System.out.println("[Please enter output file name");
		@SuppressWarnings("resource")
		Scanner outFile = new Scanner(System.in);
		outputFileName = outFile.nextLine();
		System.out.println("Your index file name is " + outputFileName);
		return outputFileName;
		
	}

	/**
	 * @param file
	 * @throws IOException
	 */
	private void configureExcludedWords(String file) throws IOException {
		Files.lines(Path.of(file)).forEach(line -> Thread.startVirtualThread(() -> processStopWords(line)));
		System.out.println("Excluded Words configured");
	}

	/**
	 * @param dictionary
	 * @throws IOException
	 */
	private void configureDictionary(String dictionary) throws IOException {
		Files.lines(Path.of(dictionary)).forEach(line -> Thread.startVirtualThread(() -> processDictionary(line)));
		System.out.println("Added " + dictionary  );

	}

	/**
	 * @return
	 */
	private String loadTextFile() {
		@SuppressWarnings("resource")
		Scanner scanner = new Scanner(System.in);

		System.out.println(" Enter file path to load text from");
		String fileName = scanner.nextLine();
		File file = new File(fileName);
		inputFileName = file.toString();
		System.out.println(" Text file " + inputFileName + " loaded successfully.");
		
		return inputFileName.toString();
		
	}

	/**
	 * @param line
	 */
	private void processStopWords(String line) {
		Arrays.stream(line.split("\\s+")).forEach(w -> excludedWords.add(w));
	}
	
	  /**
     * Writes the index to a text file with metadata and words.
     *
     * @param index         The index of words and definitions
     * @param outputFileName The name of the output text file
     */
	private void writeToTextFile(Map<String, String> index, String outputFileName) {
		long time = System.currentTimeMillis();
		Map<String, String> temp = new TreeMap<>(idx);
		System.out.println(" Writing list to text file");
		File file = new File(outputFileName);
		time = System.currentTimeMillis() - time;
		try (BufferedWriter bw = new BufferedWriter(new FileWriter(file))) {
	
			  bw.write("| Build Time: " + String.format("%dms", time) + String.format("%" + (107 - String.format("%dms", time).length()) + "s", "|") + "\n");
		        bw.write("| File Name: " + inputFileName + "###" + String.format("%" + (100 - inputFileName.length()) + "s", "|") + "\n");
		        bw.write("| Unique words: " + temp.size() + String.format("%" + (102 - String.valueOf(temp.size()).length()) + "s", "|") + "\n");;
	           
		        bw.write("+----------------------+-------------------------------------------------------------------------------------------------------------------------+\n");
		        bw.write("|        word          | definition                                                                                               |\n");
		        bw.write("+----------------------+-------------------------------------------------------------------------------------------------------------------------+\n");
	            for (Map.Entry<String, String> word : temp.entrySet()) {
	            	
	      
	                bw.write(String.format("| %-20s | %s%n", word.getKey(), word.getValue() )); 
		            bw.write("------------------------------------------------------------------------------------------------------------------------------------------------------\n");

	            }
	            
	            bw.write("------------------------------------------------------------------------------------------------------------------------------------------------------\n");
	            
	            System.out.println("[INFO] Writing completed.");

	        } catch (IOException e) {
	            e.printStackTrace();
	        }	
	}

	/**
	 * @param line
	 */
	private void processDictionary(String line) {
		String[] str = line.split(",");
		String key = str[0];
		String val = str[1];
		for (String s : str) {
			dictionaryList.put(key, val);
		}
	}
	
	/**
	 * @param word
	 * @throws Exception
	 * Method to count occurrences
	 */
	@SuppressWarnings("unused")
	private void count(String word) throws Exception {
		int count = 1;
		List<Integer> list;
		if (wordsCount.containsKey(word)) {
			list = wordsCount.get(word);
		} else {
			list = new ArrayList<>();
		}
		list.add(count);
		wordsCount.put(word, list);
		this.dictionaryList.get(word);
	}

	/**
	 * @param book
	 * @throws IOException
	 */
	private void parseBook(String book) throws IOException {
		System.out.println("Processing book...");
		Files.lines(Path.of(book)).forEach(line -> {
			lineNumber++;
			if (lineNumber % 40 == 0) {
				page++;
			}
			processLineFromBook(line);
		});
		
	}
	

	/**
	 * @param line
	 */
	private void processLineFromBook(String line) {
		Arrays.stream(line.split("\\s+")).forEach(w -> {
			try {
				addToLists(w,page);
			} catch (Exception e) {
				throw new RuntimeException(e);
			}
		});
		
	}

	/**
	 * @param word
	 * @param page
	 * @throws Exception
	 * values to stream
	 */
	private void addToLists(String word, int page) throws Exception {
		if (excludedWords.contains(word))
			return;

		if (!idx.containsKey(word) && dictionaryList.containsKey(word)) {
			idx.put(word.toLowerCase(), dictionaryList.get(word) + " (Page " + page + ")"); 
			
		}
		
	}
	



}
