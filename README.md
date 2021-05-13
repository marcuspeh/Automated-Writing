#Automated Writing

Writing-intensive modules can be hard: so many 10-page essays, and not nearly enough time to catch up on the latest e-lectures (please watch them!). CS2040S is here to help. For this problem set, you will develop an automatic writing program that can easily produce pages and pages of new text. And it will adapt to your chosen style. If you use an old essay as input, your new essay will sound just like it was written by you! If you use Shakespeare as input, your new essay will sound as if it was written by the Bard himself.

The basic idea is to take an input text and calculate its statistical properties. For example, given a specific string “prope”, what is the probability that the next letter is an ‘r’? What is the probability that the next letter is a ‘q’? Your program will take a text as input, calculate this statistical information, and then use it to produce a reasonable output text. Claude Shannon first suggested this technique in a seminal paper A Mathematical Theory of Communication (1948). This paper contained many revolutionary ideas, but one of them was to use a Markov Chain to model the statistical properties of a particular text. Markov Chains are now used everywhere; for example, the Google PageRank algorithm is built on ideas related to Markov Chains.

## How it works
The generator will take in sample text at first and form Markov Model based on the next letter/word that comes after it. Using the Markov Model, it will then generate the text of certain length as specified by the user.

## Running the codes
Run ModifiedTextGenerator.java to generate text. The text generator class takes three input parameters, i.e., the main method has
argument (k, n, filename):
• k, the order of the Markov model;
• n, the number of characters to generate;
• the filename of the text to use as a model.

Compile in terminal and run the file with 3 input. For eg,
```
javac *.java
java ModifiedTextGenerator 3 100 ./samples/Alice.txt
```