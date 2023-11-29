# 
# ----- How to execute TermoGraf Pro on Linux-MacOS -----
#
# Instructions:
# 1) Go to the end of this file.
# 2) Delete the 'echo' line.
# 3) On the '#java' line, delete the '#' character and change 'PATH_TERMOGRAF_FOLDER' to the path of the TermoGraf folder.
#
# Example:
#	java -jar "/home/myname/TermoGraf Pro/bin/termograf.jar"
#
# FAQ:
# - Can I execute TermoGraf without this script file?
#	Yes, you can right-click on "termograf.jar" and select the "Run" menu
# - When I double-click this file I get this text and I cannot open TermoGraf.
#	Right-click this file and open the "Properties" window. 
#	In the "Permissions" tab, ensure that the "Allow executing file as program" option is selected.
# - The terminal says: JRE is not recognized
#	It is necessary to install Java on the computer.
#	You can download the official version in http://www.java.com 
# - The terminal says: Path not found
#	Change "PATH_TERMOGRAF_FOLDER" according to its location.
# - I want to open TermoGraf with another java interpreter.
#	Change the default "java" command to address the path to your favourite java interpreter.
#	Example:
#	/usr/lib/jvm/java-6-sun/jre/bin/java -jar "/home/myname/TermoGraf/bin/termograf.jar"

java -jar "/PATH_TERMOGRAF_FOLDER/bin/termograf.jar"
