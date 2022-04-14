JFLAGS = -g
JC = javac
J = java
.SUFFIXES: .java .class
.java.class:
	@$(JC) $(JFLAGS) $(CLASSES)
	@echo "all classes are compiled"

# This uses the line continuation character (\) for readability
# You can list these all on a single line, separated by a space instead.
# If your version of make can't handle the leading tabs on each
# line, just remove them (these are also just added for readability).
CLASSES = Testing.java Functions.java
EXEC = Testing

default: classes

classes: $(CLASSES:.java=.class)

clean:
	$(RM) *.class

tc-rd:
	java Testing 0

tc-pw:
	java Testing 1
	
test-rd:
	java Testing 2

test-pw:
	java Testing 3
