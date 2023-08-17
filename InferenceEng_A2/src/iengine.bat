@echo OFF 
del *.class
javac *.java
java App %1 %2