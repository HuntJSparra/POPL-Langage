# Hunt Sparra POPL Language
In the Fall 2020 Principles of Programming Languages class, we created a simple, dynamically typed language using ANTLR4 for Java.

## Features
* Boolean values with logic operations and comparisons
* Integer values with arithmetic operations and comparisons
* Branching
* Variables
* Functions (with recursion)

### Boolean Operations
A boolean value is either TRUE or FALSE (case-*in*sensitive). In infix notation, an operation would be `val1 OPERATION val2`, with the exception of `NOT`, which only takes 1 parameter.
* NOT
* AND
* OR

### Arithmetic Operations
Numbers are integers, both positive and negative. In infix notation, an operation would be `val1 OPERATION val2`.
* ADD
* SUB (SUBtract)
* NEG (NEGative)
* MULT (MULTiply)
* DIV (DIVide) -- *Division by 0 raises an error*

### Comparisons
Both arithmetic and boolean values can be compared, but only two values of the same type can be compared (i.e. a boolean and a boolean can be compared, but not a digit and a boolean). Comparisons return a boolean value. In infix notation, a comparison operation would be `val1 OPERATION val2`.
* EQ (Equal)
* NEQ (Not Equal)
* GT (Greater Than)
* LT (Less Than)
* GTE (Greater Than or Equal)
* LTE (Less Than or Equal)

### Expressions
Expressions are a series of operations that return a value. Just `TRUE` is a valid expression, as is `ADD 1 2`. Operations are written such that they can take an expression in place of a variable: ex. `ADD (ADD 1 1) (SUB 1 2)`. As seen in the last example, expressions can have optional parenthesis around them. `ADD ADD 1 1 SUB 1 2` would return the same result, but parenthesis make the code much more readable.

### Branching
The syntax for branching is `IF expression1 expression2 expression3`, which is equivalent to the ternary operator in other languages: `expression1 ? expression2 : expression3`. expression2 will be executed if expression1 is true, otherwise expression3 will be executed.

### Variables and Functions
**Variables** are stored using the operator `LET name expression1 expression2`: ex. `LET myNum 5 (EQ myNum 5)`. The result of `expression1` will be stored as `name`, which can then be used in `expression2`. Variables can store either numbers or booleans. A variable can then be referenced at any time by name: ex. `ADD myNum 4`. Variables are immutable. Variable names can be any string of letters.

**Functions** are stored using the operator `LAMBDA funcName params... expression1 expression2` and called/invoked with `CALL funcName params...`. The function defined as `expression1` is used in `expression2`. Functions always return a value, and functions are **not** values. Function names can be any string letters.

### Scope
The scope in this language is explicit by design. When variables and functions are defined they only "exist" within the last expression in their definition (`expression2`). Variable and function shadowing, or name hiding, occurs when a new variable is declared with the same name as a variable in the outer scope. That local variable only exists in `expession2`. Once `expression2` has finished executing the higher-scope variable or function will no longer be shadowed/hidden.

*Note on functions:* Variables in a function have the value from the scope the function is **called**, **not** from where the function is **declared**.

## Compiling
Just call `make`, it's that easy!
*Requires Java JDK and ANTLR4*

## Running
Just call `make run` or `java Main`

## Examples
Each example can be run using `java Main EXAMPLE_NAME`. The output of the example will be printed to the console.

* *aBoolean* -- Prints out a single boolean
* *andTrueFalse* -- Prints the result of `AND TRUE FALSE`
* *addFiveFour* -- Prints the result of `ADD 5 4`
* *nesting* -- Prints the result of a nested boolean expression `AND (OR TRUE FALSE) (NOT FALSE)`
* *variables* -- Prints the result of adding 5 to the variable `four`
* *functions* -- Prints the result of a function that performs the same behavior in nesting
* *shadowing* -- Prints the result of a branch where the value X is defined, then shadowed in `expression1`, then returned in `expression2` (which executes)
* *fibTen* -- Prints the tenth number in the fibonacci sequence using recursion
