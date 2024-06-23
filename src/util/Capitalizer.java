package mg.ituprom16.util;
public class Capitalizer{


    public static  String capitalizeFirst(String str){
        char first=capitalize(str.charAt(0));
        char[] tetes=new char[1];
        tetes[0]=first;
        String ans = str.substring(1);
        return (new String(tetes)+ans);
    }
    public static char capitalize(char c){
        if(c=='a'){
            return 'A';
        }
        else if(c=='z'){
            return 'Z';
        }
            else if(c=='e'){
            return 'E' ;
        }
            else if(c=='r'){
            return 'R' ;
        }
            else if(c=='t'){
            return 'T' ;
        }
            else if(c=='y'){
            return 'Y' ;
        }
            else if(c=='u'){
            return 'U' ;
        }
            else if(c=='i'){
            return 'I' ;
        }
            else if(c=='o'){
            return 'O' ;
        }
            else if(c=='p'){
            return 'P' ;
        }
        else if(c=='q'){
            return 'Q' ;
        }
        else if(c=='s'){
            return 'S' ;
        }
            else if(c=='d'){
            return 'D' ;
        }
            else if(c=='f'){
            return 'F' ;
        }
            else if(c=='g'){
            return 'G' ;
        }
            else if(c=='h'){
            return 'H' ;
        }
            else if(c=='j'){
            return 'J' ;
        }
            else if(c=='k'){
            return 'K' ;
        }
            else if(c=='l'){
            return 'L' ;
        }
        else if(c=='m'){
            return 'M' ;
        }
            else if(c=='w'){
            return 'W' ;
        }
            else if(c=='x'){
            return 'X' ;
        }
            else if(c=='c'){
            return 'C' ;
        }
            else if(c=='v'){
            return 'V' ;
        }
            else if(c=='b'){
            return 'B' ;
        }
            else if(c=='n'){
            return 'N' ;
        }
           return c;
    } 
 
}