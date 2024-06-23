MD tempjava;
FOR /R "src" %%a IN (*.java) DO copy "%%a" "tempjava"
cd tempjava
javac -d ../bin *.java
 
cd ../bin 

jar cvf myspring.jar mg
jar cvf "..\..\..\workspace 4\application\bin\myspring.jar" mg
jar cvf ..\..\web\lib\myspring.jar mg