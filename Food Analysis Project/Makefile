run: 
	java -jar executable.jar
	jar -cMf food.zip .


nosrc:  Main.java
	javac -d . *.java
	jar -cfm executable.jar manifest.txt application


srcbin: Main.java
	javac -d bin *.java
	jar -cfm executable.jar manifest_srcbin.txt -C bin application

clean:
	rm application/*.class


cleanbin:
	rm bin/application/*.class
