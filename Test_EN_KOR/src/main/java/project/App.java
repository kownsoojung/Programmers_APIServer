package project;

import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class App {

	public static void main(String[] args) {
        String input = "gotqlcdl tjsaudgkrp skantdlvdmf gkfxrhdlTdjTek.";
        String output = EngToKorConverter.convert(input);
        System.out.println(output);
    }
}
