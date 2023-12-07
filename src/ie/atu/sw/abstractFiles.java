package ie.atu.sw;

import java.io.IOException;

abstract class abstractFiles {
	  /**
	 * @throws InterruptedException
	 * @throws IOException
	 */
	public void start() throws InterruptedException, IOException {

	    }

	    public String getOutputFileName() {
		return outputFileName;
	}
	/**
	 * @param outputFileName
	 */
	public void setOutputFileName(String outputFileName) {
		this.outputFileName = outputFileName;
	}

		public String getInputFileName() {
		return inputFileName;
	}

	/**
	 * @param inputFileName
	 */
	public void setInputFileName(String inputFileName) {
		this.inputFileName = inputFileName;
	}

		private String inputFileName = "input";
	    private String outputFileName = "output";
	}

