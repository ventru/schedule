package translate;

public class TranslateText {

    private char[] incomingString;
    private String transformedResult;

    public TranslateText(String incomingString) {
        this.incomingString = incomingString.toCharArray();
        if (!this.incomingString.equals("")) {
            translateRuToEn(this.incomingString);
        } else {
            System.out.println("Nothing translate, null incoming string");
        }
    }

    public String getTransformedResult() {
        return transformedResult;
    }

    //а, б, в, г, д, е, ё, ж, з, и, й, к, л, м, н, о, п, р, с, т, у, ф, х, ц, ч, ш, щ, ъ, ы, ь, э, ю, я
    private void translateRuToEn(char[] incomingString) {
        String result = "";
        for (int i = 0; i < incomingString.length; i++) {
            switch (incomingString[i]){
                case 'а':{result += "a"; break;}
                case 'б':{result += "b"; break;}
                case 'в':{result += "v"; break;}
                case 'г':{result += "g"; break;}
                case 'д':{result += "d"; break;}
                case 'ж':{result += "zh"; break;}
                case 'з':{result += "z"; break;}
                case 'е':{result += "e"; break;}
                case 'ё':{result += "jo"; break;}
                case 'и':{result += "i"; break;}
                case 'к':{result += "k"; break;}
                case 'л':{result += "l"; break;}
                case 'м':{result += "m"; break;}
                case 'н':{result += "n"; break;}
                case 'о':{result += "o"; break;}
                case 'п':{result += "p"; break;}
                case 'р':{result += "r"; break;}
                case 'с':{result += "s"; break;}
                case 'т':{result += "t"; break;}
                case 'ф':{result += "f"; break;}
                case 'х':{result += "kh"; break;}
                case 'ц':{result += "c"; break;}
                case 'э':{result += "e"; break;}
                case 'ю':{result += "yu"; break;}
                case 'я':{result += "ya"; break;}
                case 'й':{result += "y"; break;}
                case 'ш':{result += "sh"; break;}
                case 'щ':{result += "shh"; break;}
                case 'ь':{result += "jh"; break;}
                case 'у':{result += "u"; break;}
                case 'ч':{result += "ch"; break;}
                case 'ы':{result += "ih"; break;}
                case 'ъ':{result += "jhh"; break;}

                case 'А':{result += "A"; break;}
                case 'A':{result += "A"; break;} //en to en
                case 'Б':{result += "B"; break;}
                case 'В':{result += "V"; break;}
                case 'Г':{result += "G"; break;}
                case 'Д':{result += "D"; break;}
                case 'Ж':{result += "ZH"; break;}
                case 'З':{result += "Z"; break;}
                case 'Е':{result += "E"; break;}
                case 'Ё':{result += "JO"; break;}
                case 'И':{result += "I"; break;}
                case 'К':{result += "K"; break;}
                case 'Л':{result += "L"; break;}
                case 'М':{result += "M"; break;}
                case 'M':{result += "M"; break;} //en to en
                case 'Н':{result += "N"; break;}
                case 'О':{result += "O"; break;}
                case 'П':{result += "P"; break;}
                case 'Р':{result += "R"; break;}
                case 'С':{result += "S"; break;}
                case 'Т':{result += "T"; break;}
                case 'T':{result += "T"; break;} //en to en
                case 'Ф':{result += "F"; break;}
                case 'Х':{result += "H"; break;}
                case 'X':{result += "KH"; break;} //en to en
                case 'Ц':{result += "C"; break;}
                case 'Э':{result += "EH"; break;}
                case 'Ю':{result += "YU"; break;}
                case 'Я':{result += "YA"; break;}
                case 'Й':{result += "Y"; break;}
                case 'Ш':{result += "SH"; break;}
                case 'Щ':{result += "SHH"; break;}
                case 'Ь':{result += "JH"; break;}
                case 'У':{result += "U"; break;}
                case 'Ч':{result += "CH"; break;}
                case 'Ы':{result += "IH"; break;}
                case 'Ъ':{result += "JHH"; break;}

                case '0':{result += "0"; break;}
                case '1':{result += "1"; break;}
                case '2':{result += "2"; break;}
                case '3':{result += "3"; break;}
                case '4':{result += "4"; break;}
                case '5':{result += "5"; break;}
                case '6':{result += "6"; break;}
                case '7':{result += "7"; break;}
                case '8':{result += "8"; break;}
                case '9':{result += "9"; break;}

                case '-':{result += "-"; break;}
                case '.':{result += "."; break;}
                case '/':{result += "/"; break;}
                case ' ':{result += " "; break;}
                case ',':{result += ","; break;}
                default: {System.out.println("Not found character: [" + incomingString[i] + "]");}
            }
        }
        this.transformedResult = result;
    }

}
