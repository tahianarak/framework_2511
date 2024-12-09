MD tempjava;
FOR /R "src" %%a IN (*.java) DO copy "%%a" "tempjava"
cd tempjava
javac -d ../bin *.java
 
cd ../bin 

jar cf myspring.jar com mg
jar cf "D:\tahiana\s4\web-naina\final_project\workspace 4\application\bin\myspring.jar" mg com
jar cf ..\..\web\lib\myspring.jar mg com