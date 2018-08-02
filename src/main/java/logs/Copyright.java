package logs;

public class Copyright {

    private String copyrightMessage = "|*********************************************************|\n" +
                                      "|                     Schedule Service                    |\n" +
                                      "|            It's free, open source software              |\n" +
                                      "|                        v. 0.2.1                         |\n" +
                                      "|              Author: Rostovshchikov K.N.                |\n" +
                                      "|                         2018                            |\n" +
                                      "|*********************************************************|";

    public Copyright(){
        System.out.println(this.copyrightMessage);
    }

}
