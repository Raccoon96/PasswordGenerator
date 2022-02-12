package mu_gui;
import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.Color;

/**
 * @author Konrad_Rakoczy
 * @version 1.0
 * @since 05.02.2022
 */

public class Main extends JFrame {

    /**
     * @param powitanie Wiadomość powitalna
     * @param podaj_dlugosc Wiadomośc o podaniu długości hasła
     * @param slider Slider o zakresie 0 - 20 do wyboru długości hasła
     * @param wybrana_dlugosc Aktualizująca się wartość wyświetlająca aktualnie wybraną długość
     * @param opis_checkboxy Wiadomość o zaznaczeniu checkboxów
     * @param wielkie_check Checkbox dodający wielkie litery do puli
     * @param cyfry_check Checkbox dodający cyfry do puli
     * @param znaki_check Checkbox dodający znaki specjalne do puli
     * @param pole_tekstowe Pole tekstowe do wyświetlania hasła i wpisywania hasła
     * @param generuj Przycisk do generowania hasła
     * @param sprawdz Przycisk do sprawdzenia mocy hasła
     * @param moc_opis Wiadomość o mocy hasła
     * @param moc Wartość mocy hasła
     */

    JLabel powitanie;
    JLabel podaj_dlugosc;
    JSlider slider;
    JLabel wybrana_dlugosc;
    JLabel opis_checkboxy;
    JCheckBox wielkie_check;
    JCheckBox cyfry_check;
    JCheckBox znaki_check;
    JTextField pole_tekstowe;
    JButton generuj;
    JButton sprawdz;
    JLabel moc_opis;
    JLabel moc;

    Main() {

        JFrame Window = new JFrame();
        setTitle("Password Generator");
        setSize(350,550);

        JPanel panel = new JPanel();
        panel.setBorder(BorderFactory.createEmptyBorder(20, 10, 20, 10));
        panel.setLayout(new GridLayout(0,1));

        powitanie = new JLabel("Witaj w programie Password Generator!");
        powitanie.setForeground(new Color(10,120,210));
        panel.add(powitanie);

        podaj_dlugosc = new JLabel("Podaj długość generowanego hasła");
        podaj_dlugosc.setForeground(new Color(10,120,210));
        panel.add(podaj_dlugosc);

        slider = new JSlider(0,20,8);
        slider.setPaintTicks(true);
        slider.setMinorTickSpacing(1);
        slider.setPaintTrack(true);
        slider.setMajorTickSpacing(5);
        slider.setPaintLabels(true);
        panel.add(slider);

        wybrana_dlugosc = new JLabel();
        wybrana_dlugosc.setForeground(new Color(10,120,210));
        wybrana_dlugosc.setText("Wybrana długość " + slider.getValue());
        panel.add(wybrana_dlugosc);

        opis_checkboxy = new JLabel("Zaznacz czy chciałbyś te elementy w swoim haśle");
        opis_checkboxy.setForeground(new Color(10,120,210));
        panel.add(opis_checkboxy);

        wielkie_check = new JCheckBox("Duże litery");
        panel.add(wielkie_check);

        cyfry_check = new JCheckBox("Cyfry");
        panel.add(cyfry_check);

        znaki_check = new JCheckBox("Znaki specjalne");
        panel.add(znaki_check);

        pole_tekstowe = new JTextField();
        panel.add(pole_tekstowe);

        generuj = new JButton("Generuj");
        panel.add(generuj);

        sprawdz = new JButton("Sprawdź");
        panel.add(sprawdz);

        moc_opis = new JLabel("Jak silne jest hasło:");
        panel.add(moc_opis);

        moc = new JLabel("");
        panel.add(moc);

        add(panel);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);

        slider.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                wybrana_dlugosc.setText("Wybrana długość " + slider.getValue());
            }
        });

        sprawdz.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Check(moc, pole_tekstowe);
            }
        });

        generuj.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Generate(moc, pole_tekstowe, slider, wielkie_check, cyfry_check, znaki_check);
            }
        });
    }

    public static void main(String[] args) {
	    new Main();
    }

    private void Check(JLabel a, JTextField b) {
        String pass = b.getText();
        boolean digit, special, lower, upper;
        digit = special = lower = upper = false;
        for(int i = 0; i < pass.length(); i++)
        {
            char c = pass.charAt(i);
            if(Character.isLowerCase(c))
                lower = true;
            else if(Character.isUpperCase(c))
                upper = true;
            else if(Character.isDigit(c))
                digit = true;
            else if((c>=33 && c<=47) || (c>=58 && c<=64) || (c>=91 && c<=96) || (c>=123 && c<=126))
                special = true;
        }
        if(pass.length() <= 0) {
            a.setText("Niebezpiecznie jest nie ustawiać żadnego hasła!");
            a.setForeground(new Color(180,20,10));
        }
        else if(pass.length() > 0 && pass.length() <= 6) {
            a.setText("Słabe!");
            a.setForeground(new Color(180,20,10));
        }
        else if(pass.length() >= 12) {
            if(lower && upper && digit && special) {
                a.setText("Silne!");
                a.setForeground(new Color(50, 180, 50));
            }
            else {
                a.setText("Srednie...");
                a.setForeground(new Color(210,110,10));
            }
        }
        else {
            if(lower && upper && digit && special) {
                a.setText("Srednie...");
                a.setForeground(new Color(210, 110, 10));
            }
            else {
                a.setText("Słabe!");
                a.setForeground(new Color(180,20,10));
            }
        }
    }

    private void Generate(JLabel napis, JTextField pole, JSlider dlugosc, JCheckBox czy_litery, JCheckBox czy_cyfry, JCheckBox czy_znaki) {

        String pula = "qwertyuiopasdfghjklzxcvbnm";
        String wielkie_litery = "QWERTYUIOPASDFGHJKLZXCVBNM";
        String cyfry = "1234567890";
        String znaki = "!\"#$%&'()*+,-./:;<=>?@[\\]^_`{|}~";
        String haslo = "";

        if(czy_litery.isSelected())
            pula += wielkie_litery;
        if(czy_cyfry.isSelected())
            pula += cyfry;
        if(czy_znaki.isSelected())
            pula += znaki;

        for(int i = 0; i < dlugosc.getValue(); i++){
            int rand = (int)(Math.random() * pula.length());
            haslo += String.valueOf(pula.charAt(rand));
        }

        pole.setText(haslo);

        Check(napis, pole);
    }

}

// '0123456789' => 48 - 57
// 'abcdefghijklmnopqrstuvwxyz' => 97 - 122
// 'ABCDEFGHIJKLMNOPQRSTUVWXYZ' => 65 - 90
// '!"#$%&'()*+,-./' => 33 - 47
// ':;<=>?@' => 58 - 64
// '[\]^_`' => 91 - 96
// '{|}~' => 123 - 126