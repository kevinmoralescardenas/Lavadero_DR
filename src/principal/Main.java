package principal;

import javax.swing.SwingUtilities;
import vista.FormLogin;

public class Main {

    public static void main(String[] args) {

        SwingUtilities.invokeLater(new Runnable() {

            @Override
            public void run() {
                new FormLogin();
            }
        });
    }
}
