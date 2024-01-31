# Multithreaded Indexing Application

This Java application, developed using Java 19, is designed for efficient multithreaded indexing. It facilitates the creation of a word index from e-book (.txt) files provided with the project.

## Features

- **Word Index Creation:** Generate a word index from an e-book (.txt) file. The index includes a list of words, their corresponding page numbers, and defined meanings from a provided dictionary.
  
- **User-Defined Index:** Users can load e-book files, build custom word indices, and associate words with definitions and page numbers.

- **Performance Metrics:** The application records build time and unique words during the indexing process.

- **Menu Interface:** Users can interact with the application through a menu, selecting options using numerical values from 1 to 6. Exclusion of words from `google-100.txt` in the index is implemented.

## Usage

Follow these steps to run the application:

1. Use the menu options to perform indexing on text files.
2. Follow the prompts to configure dictionaries, load files, and build the index.
3. Ensure Java 19 is installed, and enable preview features in the environment.
4. Compile the code to run the application.

## Prerequisites

- [Java 19](https://www.oracle.com/java/technologies/javase-downloads.html) installed
- Enable preview features in the Java environment

## How to Run

1. Clone this repository.
    ```bash
    git clone https://github.com/your-username/multithreaded-indexing.git
    cd multithreaded-indexing
    ```

2. Compile the Java files.
    ```bash
    javac MultithreadedIndexing.java
    ```

3. Run the application.
    ```bash
    java MultithreadedIndexing
    ```

## Note

- The application relies on Java 19 features; therefore, enabling preview features is necessary.
- The exclusion of words from `google-100.txt` ensures a focused and meaningful index.

Feel free to explore the various features provided by the application and contribute to its improvement. Compile the code and run the application to experience efficient multithreaded indexing. Your feedback is appreciated!
