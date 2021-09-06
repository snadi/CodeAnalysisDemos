# About

This repo contains a bunch of demos related to tools for analyzing source code. These are simple tools & use cases just to demonstrate available options and how to get started.

The demos are prepared by [Sarah Nadi](https://sarahnadi.org) and are mainly used in CMPUT 663 at the University of Alberta.

# Running the Demos

The repo comes with a Dockerfile that takes care of using the correct version of python and setting up dependencies. 

To build the Docker image (this may take a bit of time):

```
docker build --tag=ca-demo:f19 .
```

To run the docker image:

```
docker run --name CADemo -it --rm ca-demo:f19 /bin/bash
```

Unless otherwise specified, all instructions below are based on running from within the Docker image (which means that all most code for the demos has been compiled already)

## Using JDT for AST Processing

We use the Eclipse JDT APIs to process the AST of corresponding code. The `MethodInvPrinter` prints out all invocations found in `resources/test-inv.java`. The `MethodiInvocationCounter` counts how many times each method is called. `VarDeclCounter` prints out all declarations found in `resources/test.java`.

```
/ca-demo# cd ASTDemos/
/ca-demo/ASTDemos# java -cp target/AST_JDT-1.0-SNAPSHOT-jar-with-dependencies.jar MethodInvPrinter
test:
	st_test.indexOf(["ell"])
	st_test.substring([2, 4])
test2:
	arrayList.add(["Hello"])
	arrayList.add([1, "World"])
	arrayList.get([0])
	testStr.substring([2, 4])

/ca-demo/ASTDemos# java -cp target/AST_JDT-1.0-SNAPSHOT-jar-with-dependencies.jar MethodInvocationCounter
add is called 2 times
indexOf is called 1 times
substring is called 2 times

/ca-demo/ASTDemos# java -cp target/AST_JDT-1.0-SNAPSHOT-jar-with-dependencies.jar VarDeclCounter
Declaration of 'i' at line2
Declaration of 'j' at line3
Declaration of 'al' at line4
```


## ChangeDistiller Demo

[ChangeDistiller](https://bitbucket.org/sealuzh/tools-changedistiller/wiki/Home) analyzes two versions of a piece of code and detects the change operations that occurred. The current demo simply prins the edit actions that occurred between programs `examples/Test.java_v1` and  `examples/Test.java_v2`

```
/ca-demo# cd ChangeDistillerDemo/
/ca-demo/ChangeDistillerDemo# java -jar target/ChangeDistillerDemo-1.0-SNAPSHOT-jar-with-dependencies.jar 
WARNING: ...
size: 3
METHOD_RENAMING METHOD: Test.foo()
STATEMENT_INSERT VARIABLE_DECLARATION_STATEMENT: int x = 5;
ADDITIONAL_FUNCTIONALITY METHOD: Test.newMethod()
```

If you are interested, check out [GumTree](https://github.com/GumTreeDiff/gumtree), which is a newer tool with a similar purpose.

## Spoon Demo

This is based on the examples here [https://github.com/SpoonLabs/spoon-examples](https://github.com/SpoonLabs/spoon-examples). This repo is already added as a submodule here. You can import the mvn project into your favorit IDE if you want to browse through. We will just execute one test here.

```
/ca-demo# cd spoon-examples/
```

1. Look at `src/main/java/fr/inria/gforge/spoon/analysis/CatchProcessor.java` which looks for empty catch clauses.
2. Run the corresponding test `mvn -Dtest=CatchProcessorTest test`, which looks for empty catch clauses in the programs in `src/test/resources/src`. Right now, there are two such clauses and the assertion checks for 2 clauses accordingly.
3. . Add another empty catch block in one of the java test files under `src/test/resources/src` and re-run the test. It should fail now, since number of empty catch blocks is now 3 not 2

## BOA Demo

Not part of the Docker container

Boa homepage is [http://boa.cs.iastate.edu](http://boa.cs.iastate.edu) and contains all necessary documentation. You need an account to run queries.

1. View the example at [http://boa.cs.iastate.edu/examples/index.php#null-check](http://boa.cs.iastate.edu/examples/index.php#null-check)
2. Run the example on the small dataset (so it finishes quickly)
3. Go to your list of jobs to view the result

## SrcML

Not part of the Docker container. 

This is based on [http://www.srcml.org/tutorials/creating-srcml.html](http://www.srcml.org/tutorials/creating-srcml.html)

1. Download and install srcML from  [http://www.srcml.org/#download](http://www.srcml.org/#download) 
2. Create a file called `rotate.cpp` in your srcML directory with the following code

```
#include "rotate.h"
// rotate three values
void rotate(int& n1, int& n2, int& n3)
{
 // copy original values
 int tn1 = n1, tn2 = n2, tn3 = n3;
 // move
 n1 = tn3;
 n2 = tn1;
 n3 = tn2;
}
```

3. Run `srcml rotate.cpp -o rotate.xml` -- This will create the rotate.xml file
4. Display `rotate.xml`. If your editor does not properly format the xml, you can use [https://www.freeformatter.com/xml-formatter.html](https://www.freeformatter.com/xml-formatter.html)
5. Let's start querying!
	* `srcml --xpath "//src:function/src:name" rotate.xml` -- lists the names of all functions
	* `srcml --xpath "//src:function[src:parameter_list[count(src:parameter) = 2]]/src:name" rotate.xml` -- lists the functions with exactly 2 parameters. Should not have any output in current example
