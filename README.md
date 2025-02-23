# Deepest Text Extractor - Html Analyzer

## Overview

The **tml Analyzer** is a Java program that, given a URL, extracts the text contained in the deepest level of the HTML structure of the document. If the HTML is malformed, that is, there are tags that were not closed correctly, the progam returns the message `"malformed HTML"`.

## Code Structure

- **Main Method:** validates command-line arguments and calls the method responsible for extracting the deepest text in the document.
- **extractDeepestText(String urlString):** connects to the URL, reads the content line by line, uses a stack to determine the current depth of HTML tags, and uses it to compare and find the deepest one.
- **extractTagName(String tag):** takes a complete HTML tag and returns only the tag name, and this method was used to add modularization to the code, then improve code readability and maintainability.

## How to compile and run

1. **Compilation:**
Open a terminal in the directory where `HtmlAnalyzer.java` is located and compile the program with the following command:
```bash
javac HtmlAnalyzer.java
```
2. **Execution:**
After compilation, run the program by passing the URL as an argument:
```bash
java HtmlAnalyzer <URL>
```

### 🚀 | Developed by Pedro Gonçalves Correia