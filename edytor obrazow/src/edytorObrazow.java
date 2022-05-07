import javax.swing.*;
import java.awt.*;
import javax.imageio.ImageIO;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.image.ColorModel;
import java.awt.image.WritableRaster;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class edytorObrazow {

    public static JFrame frame;
    public static Okno okno = new Okno();

    public static BufferedImage obraz1;
    public static BufferedImage obraz2;
    public static BufferedImage wynik;

    public static Color window = new Color(31, 31, 31);
    public static String[] efekty = {"Wybierz","Kontrast", "Rozjasnianie", "Przyciemnianie", "Suma", "Roznica", "Odejmowanie", "Mnozenie",
            "Mnozenie odwrotne", "Negacja", "Nakladka", "Wypalanie", "Reflective mode", "Opacity"};
    public static String wybor = " ";

    public static void main(String[] args) {

        frame = new JFrame("Edytor obrazów");
        frame.setSize(1200, 800);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JComboBox<String> jComboBox = new JComboBox<>(efekty);
        jComboBox.setBounds(500, 25, 200, 25);

        JTextArea dane1 = new JTextArea();
        JTextArea dane2 = new JTextArea();

        dane1.setBounds(375, 85, 200, 25);
        dane2.setBounds(625, 85, 200, 25);

        frame.add(dane1);
        frame.add(dane2);

        try {
            obraz1 = ImageIO.read(new File("1.jpg"));
            obraz2 = ImageIO.read(new File("2.jpg"));
        } catch (IOException e) {
        }

        jComboBox.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                wybor = (String) jComboBox.getSelectedItem();

                if (wybor == null){
                    return;
                }

                switch (wybor) {
                    case "Opacity" -> {
                        dane1.setBackground(window);
                        dane1.setText("0.5");
                        dane2.setBackground(window);
                        dane2.setText("");
                    }
                    case "Rozjasnianie" -> {
                        dane1.setBackground(window);
                        dane1.setText("50");
                        dane2.setBackground(window);
                        dane2.setText("1.0");
                    }
                    case "Przyciemnianie" -> {
                        dane1.setBackground(window);
                        dane1.setText("50");
                        dane2.setBackground(window);
                        dane2.setText("1.0");
                    }
                    case "Kontrast" -> {
                        dane1.setBackground(window);
                        dane1.setText("40");
                        dane2.setBackground(window);
                        dane2.setText("");
                    }
                    case "Filtr Medianowy" -> {
                        dane1.setBackground(window);
                        dane1.setText("1");
                        dane2.setBackground(window);
                        dane2.setText("");
                    }
                }
            }
        });
        frame.add(jComboBox);

        JButton Refresh_button = new JButton();
        Refresh_button.setText("Wykonaj");
        Refresh_button.setBounds(450, 140, 300, 35);
        Refresh_button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                switch (wybor) {
                    case "Suma" -> {
                        try {
                            Suma();
                        } catch (IOException ex) {
                            ex.printStackTrace();
                        }
                    }
                    case "Odejmowanie" -> {
                        try {
                            Odejmowanie();
                        } catch (IOException ex) {
                            ex.printStackTrace();
                        }
                    }
                    case "Roznica" -> {
                        try {
                            Roznica();
                        } catch (IOException ex) {
                            ex.printStackTrace();
                        }
                    }
                    case "Mnozenie odwrotne" -> {
                        try {
                            MnozenieOdwrotne();
                        } catch (IOException ex) {
                            ex.printStackTrace();
                        }
                    }
                    case "Mnozenie" -> {
                        try {
                            Mnozenie();
                        } catch (IOException ex) {
                            ex.printStackTrace();
                        }
                    }
                    case "Negacja" -> {
                        try {
                            Negacja();
                        } catch (IOException ex) {
                            ex.printStackTrace();
                        }
                    }
                    case "Nakladka" -> {
                        try {
                            Nakladka();
                        } catch (IOException ex) {
                            ex.printStackTrace();
                        }
                    }
                    case "Wypalanie" -> {
                        try {
                            Wypalanie();
                        } catch (IOException ex) {
                            ex.printStackTrace();
                        }
                    }
                    case "Reflective mode" -> {
                        try {
                            Reflect_mode();
                        } catch (IOException ex) {
                            ex.printStackTrace();
                        }
                    }
                    case "Opacity" -> {
                        double c = 1.0;
                        c = Double.parseDouble(dane1.getText());
                        try {
                            Opacity(c);
                        } catch (IOException ex) {
                            ex.printStackTrace();
                        }
                    }
                    case "Kontrast" -> {
                        double c = 1.0;
                        try {
                            c = Double.parseDouble(dane1.getText());
                            if (c > 127)
                                c = 127;
                            else if (c < -128)
                                c = -128;
                        } catch (Exception expt) {
                        }
                        kontrast(c);
                    }
                    case "Rozjasnianie" -> {
                        int c = Integer.parseInt(dane1.getText());
                        double d = Double.parseDouble(dane2.getText());
                        try {
                            Rozjasnianie(d, c);
                        } catch (IOException ex) {
                            ex.printStackTrace();
                        }
                    }
                    case "Przyciemnianie" -> {
                        int c = Integer.parseInt(dane1.getText());
                        double d = Double.parseDouble(dane2.getText());
                        try {
                            Przyciemnianie(d, c);
                        } catch (IOException ex) {
                            ex.printStackTrace();
                        }
                    }
                }
            }
        });
        frame.add(Refresh_button);
        frame.add(okno);
        frame.setVisible(true);
    }

    public static BufferedImage copy_img(BufferedImage buff_img) {
        ColorModel cm = buff_img.getColorModel();
        boolean isAlphaPremultiplied = cm.isAlphaPremultiplied();
        WritableRaster raster = buff_img.copyData(null);
        return new BufferedImage(cm, raster, isAlphaPremultiplied, null);
    }

    public static void kontrast(double kontrast) {
        wynik = copy_img(obraz1);

        for (int i = 0; i < wynik.getWidth(); i++) {
            for (int j = 0; j < wynik.getHeight(); j++) {
                Color color = new Color(wynik.getRGB(i, j));

                if (kontrast >= 0) {
                    int red = (int) ((127 / (127 - kontrast)) * (color.getRed() - kontrast));
                    if (red > 255) red = 255;
                    else if (red < 0) red = 0;
                    int green = (int) ((127 / (127 - kontrast)) * (color.getGreen() - kontrast));
                    if (green > 255) green = 255;
                    else if (green < 0) green = 0;
                    int blue = (int) ((127 / (127 - kontrast)) * (color.getBlue() - kontrast));
                    if (blue > 255) blue = 255;
                    else if (blue < 0) blue = 0;

                    Color fin_col = new Color(red, green, blue);
                    wynik.setRGB(i, j, fin_col.getRGB());
                } else {
                    int red = (int) (((127 + kontrast) / 127) * color.getRed() - kontrast);
                    if (red > 255) red = 255;
                    else if (red < 0) red = 0;
                    int green = (int) (((127 + kontrast) / 127) * color.getGreen() - kontrast);
                    if (green > 255) green = 255;
                    else if (green < 0) green = 0;
                    int blue = (int) (((127 + kontrast) / 127) * color.getBlue() - kontrast);
                    if (blue > 255) blue = 255;
                    else if (blue < 0) blue = 0;

                    Color fin_col = new Color(red, green, blue);
                    wynik.setRGB(i, j, fin_col.getRGB());
                }
            }
        }
        okno.repaint();
    }

    public static void Suma() throws IOException {
        wynik = copy_img(obraz1);
        for (int x = 0; x < wynik.getWidth(); x++) {
            for (int y = 0; y < wynik.getHeight(); y++) {
                Color color1 = new Color(wynik.getRGB(x, y));
                Color color2 = new Color(obraz2.getRGB(x, y));

                int Red = color1.getRed() + color2.getRed();
                if (Red > 255) {
                    Red = 255;
                } else if (Red < 0) {
                    Red = 0;
                }
                int Green = color1.getGreen() + color2.getGreen();
                if (Green > 255) {
                    Green = 255;
                } else if (Green < 0) {
                    Green = 0;
                }
                int Blue = color1.getBlue() + color2.getBlue();
                if (Blue > 255) {
                    Blue = 255;
                } else if (Blue < 0) {
                    Blue = 0;
                }

                Color col = new Color(Red, Green, Blue);
                wynik.setRGB(x, y, col.getRGB());
            }
        }
        okno.repaint();
    }

    public static void Odejmowanie() throws IOException {
        wynik = copy_img(obraz1);
        for (int x = 0; x < wynik.getWidth(); x++) {
            for (int y = 0; y < wynik.getHeight(); y++) {
                Color color1 = new Color(wynik.getRGB(x, y));
                Color color2 = new Color(obraz2.getRGB(x, y));

                int Red = color1.getRed() + color2.getRed() - 1;
                if (Red > 255) {
                    Red = 255;
                } else if (Red < 0) {
                    Red = 0;
                }
                int Green = color1.getGreen() + color2.getGreen() - 1;
                if (Green > 255) {
                    Green = 255;
                } else if (Green < 0) {
                    Green = 0;
                }
                int Blue = color1.getBlue() + color2.getBlue() - 1;
                if (Blue > 255) {
                    Blue = 255;
                } else if (Blue < 0) {
                    Blue = 0;
                }

                Color col = new Color(Red, Green, Blue);
                wynik.setRGB(x, y, col.getRGB());
            }
        }
        okno.repaint();
    }

    public static void Roznica() throws IOException {
        wynik = copy_img(obraz1);
        for (int x = 0; x < wynik.getWidth(); x++) {
            for (int y = 0; y < wynik.getHeight(); y++) {
                Color color1 = new Color(wynik.getRGB(x, y));
                Color color2 = new Color(obraz2.getRGB(x, y));

                int Red = Math.abs(color1.getRed() - color2.getRed());
                if (Red > 255) {
                    Red = 255;
                } else if (Red < 0) {
                    Red = 0;
                }
                int Green = Math.abs(color1.getGreen() + color2.getGreen());
                if (Green > 255) {
                    Green = 255;
                } else if (Green < 0) {
                    Green = 0;
                }
                int Blue = Math.abs(color1.getBlue() + color2.getBlue());
                if (Blue > 255) {
                    Blue = 255;
                } else if (Blue < 0) {
                    Blue = 0;
                }

                Color col = new Color(Red, Green, Blue);
                wynik.setRGB(x, y, col.getRGB());
            }
        }
        okno.repaint();
    }

    public static void Mnozenie() throws IOException {
        wynik = copy_img(obraz1);
        for (int x = 0; x < wynik.getWidth(); x++) {
            for (int y = 0; y < wynik.getHeight(); y++) {
                Color color1 = new Color(wynik.getRGB(x, y));
                Color color2 = new Color(obraz2.getRGB(x, y));

                int Red = color1.getRed() * color2.getRed();
                if (Red > 255) {
                    Red = 255;
                } else if (Red < 0) {
                    Red = 0;
                }
                int Green = color1.getGreen() * color2.getGreen();
                if (Green > 255) {
                    Green = 255;
                } else if (Green < 0) {
                    Green = 0;
                }
                int Blue = color1.getBlue() * color2.getBlue();
                if (Blue > 255) {
                    Blue = 255;
                } else if (Blue < 0) {
                    Blue = 0;
                }

                Color col = new Color(Red, Green, Blue);
                wynik.setRGB(x, y, col.getRGB());
            }
        }
        okno.repaint();
    }

    public static void MnozenieOdwrotne() throws IOException {
        wynik = copy_img(obraz1);
        for (int x = 0; x < wynik.getWidth(); x++) {
            for (int y = 0; y < wynik.getHeight(); y++) {
                Color color1 = new Color(wynik.getRGB(x, y));
                Color color2 = new Color(obraz2.getRGB(x, y));

                int Red = (1 - (1 - color1.getRed()) * (1 - color2.getRed()));
                if (Red > 255) {
                    Red = 255;
                } else if (Red < 0) {
                    Red = 0;
                }
                int Green = (1 - (1 - color1.getGreen()) * (1 - color2.getGreen()));
                if (Green > 255) {
                    Green = 255;
                } else if (Green < 0) {
                    Green = 0;
                }
                int Blue = (1 - (1 - color1.getBlue()) * (1 - color2.getBlue()));
                if (Blue > 255) {
                    Blue = 255;
                } else if (Blue < 0) {
                    Blue = 0;
                }

                Color col = new Color(Red, Green, Blue);
                wynik.setRGB(x, y, col.getRGB());
            }
        }
        okno.repaint();
    }

    public static void Negacja() throws IOException {
        wynik = copy_img(obraz1);
        for (int x = 0; x < wynik.getWidth(); x++) {
            for (int y = 0; y < wynik.getHeight(); y++) {
                Color color1 = new Color(wynik.getRGB(x, y));
                Color color2 = new Color(obraz2.getRGB(x, y));

                int Red = 1 - Math.abs(1 - color1.getRed() - color2.getRed());
                if (Red > 255) {
                    Red = 255;
                } else if (Red < 0) {
                    Red = 0;
                }
                int Green = 1 - Math.abs(1 - color1.getGreen() - color2.getGreen());
                if (Green > 255) {
                    Green = 255;
                } else if (Green < 0) {
                    Green = 0;
                }
                int Blue = 1 - Math.abs(1 - color1.getBlue() - color2.getBlue());
                if (Blue > 255) {
                    Blue = 255;
                } else if (Blue < 0) {
                    Blue = 0;
                }

                Color col = new Color(Red, Green, Blue);
                wynik.setRGB(x, y, col.getRGB());
            }
        }
        okno.repaint();
    }

    public static void Nakladka() throws IOException {
        wynik = copy_img(obraz1);
        for (int x = 0; x < wynik.getWidth(); x++) {
            for (int y = 0; y < wynik.getHeight(); y++) {
                Color color1 = new Color(wynik.getRGB(x, y));
                Color color2 = new Color(obraz2.getRGB(x, y));

                int Red = 1 - (2 * (1 - color1.getRed()) * (1 - color1.getRed()));
                if (color1.getRed() < 255 * 0.5) {
                    Red = 2 * color1.getRed() * color2.getRed();
                }
                if (Red > 255) {
                    Red = 255;
                } else if (Red < 0) {
                    Red = 0;
                }
                int Green = 1 - (2 * (1 - color1.getGreen()) * (1 - color1.getGreen()));
                if (color1.getGreen() < 255 * 0.5) {
                    Green = 2 * color1.getGreen() * color2.getGreen();
                }
                if (Green > 255) {
                    Green = 255;
                } else if (Green < 0) {
                    Green = 0;
                }
                int Blue = 1 - (2 * (1 - color1.getBlue()) * (1 - color1.getBlue()));
                if (color1.getBlue() < 255 * 0.5) {
                    Blue = 2 * color1.getBlue() * color2.getBlue();
                }
                if (Blue > 255) {
                    Blue = 255;
                } else if (Blue < 0) {
                    Blue = 0;
                }

                Color col = new Color(Red, Green, Blue);
                wynik.setRGB(x, y, col.getRGB());
            }
        }
        okno.repaint();
    }

    public static void Wypalanie() throws IOException {
        wynik = copy_img(obraz1);
        for (int x = 0; x < wynik.getWidth(); x++) {
            for (int y = 0; y < wynik.getHeight(); y++) {
                Color color1 = new Color(wynik.getRGB(x, y));
                Color color2 = new Color(obraz2.getRGB(x, y));

                int Red = 1 - (1 - color1.getRed()) / (color2.getRed() + 1);
                if (Red > 255) {
                    Red = 255;
                } else if (Red < 0) {
                    Red = 0;
                }
                int Green = 1 - (1 - color1.getGreen()) / (color2.getGreen() + 1);
                if (Green > 255) {
                    Green = 255;
                } else if (Green < 0) {
                    Green = 0;
                }
                int Blue = 1 - (1 - color1.getBlue()) / (color2.getBlue() + 1);
                if (Blue > 255) {
                    Blue = 255;
                } else if (Blue < 0) {
                    Blue = 0;
                }

                Color col = new Color(Red, Green, Blue);
                wynik.setRGB(x, y, col.getRGB());
            }
        }
        okno.repaint();
    }

    public static void Reflect_mode() throws IOException {
        wynik = copy_img(obraz1);
        for (int x = 0; x < wynik.getWidth(); x++) {
            for (int y = 0; y < wynik.getHeight(); y++) {
                Color color1 = new Color(wynik.getRGB(x, y));
                Color color2 = new Color(obraz2.getRGB(x, y));

                int Red = (int) (Math.pow(color1.getRed(), 2) / (1 - color2.getRed()));
                if (Red > 255) {
                    Red = 255;
                } else if (Red < 0) {
                    Red = 0;
                }
                int Green = (int) (Math.pow(color1.getGreen(), 2) / (1 - color2.getGreen()));
                if (Green > 255) {
                    Green = 255;
                } else if (Green < 0) {
                    Green = 0;
                }
                int Blue = (int) (Math.pow(color1.getBlue(), 2) / (1 - color2.getBlue()));
                if (Blue > 255) {
                    Blue = 255;
                } else if (Blue < 0) {
                    Blue = 0;
                }

                Color col = new Color(Red, Green, Blue);
                wynik.setRGB(x, y, col.getRGB());
            }
        }
        okno.repaint();
    }

    public static void Opacity(double alf) throws IOException {
        wynik = copy_img(obraz1);
        for (int x = 0; x < wynik.getWidth(); x++) {
            for (int y = 0; y < wynik.getHeight(); y++) {
                Color color1 = new Color(wynik.getRGB(x, y));
                Color color2 = new Color(obraz2.getRGB(x, y));

                int Red = (int) ((1 - alf) * color2.getRed() + alf * color1.getRed());
                if (Red > 255) {
                    Red = 255;
                } else if (Red < 0) {
                    Red = 0;
                }
                int Green = (int) ((1 - alf) * color2.getGreen() + alf * color1.getGreen());
                if (Green > 255) {
                    Green = 255;
                } else if (Green < 0) {
                    Green = 0;
                }
                int Blue = (int) ((1 - alf) * color2.getBlue() + alf * color1.getBlue());
                if (Blue > 255) {
                    Blue = 255;
                } else if (Blue < 0) {
                    Blue = 0;
                }

                Color col = new Color(Red, Green, Blue);
                wynik.setRGB(x, y, col.getRGB());
            }
        }
        okno.repaint();
    }

    public static void Rozjasnianie(double a, int b) throws IOException {
        wynik = copy_img(obraz1);
        for (int x = 0; x < wynik.getWidth(); x++) {
            for (int y = 0; y < wynik.getHeight(); y++) {
                Color color1 = new Color(wynik.getRGB(x, y));

                int Red = (int) (color1.getRed() * a) + b;
                if (Red > 255) {
                    Red = 255;
                } else if (Red < 0) {
                    Red = 0;
                }
                int Green = (int) (color1.getGreen() * a) + b;
                if (Green > 255) {
                    Green = 255;
                } else if (Green < 0) {
                    Green = 0;
                }
                int Blue = (int) (color1.getBlue() * a) + b;
                if (Blue > 255) {
                    Blue = 255;
                } else if (Blue < 0) {
                    Blue = 0;
                }

                Color col = new Color(Red, Green, Blue);
                wynik.setRGB(x, y, col.getRGB());
            }
        }
        okno.repaint();
    }

    public static void Przyciemnianie(double a, int b) throws IOException {
        wynik = copy_img(obraz1);
        for (int x = 0; x < wynik.getWidth(); x++) {
            for (int y = 0; y < wynik.getHeight(); y++) {
                Color color1 = new Color(wynik.getRGB(x, y));

                int Red = (int) (color1.getRed() * a) - b;
                if (Red > 255) {
                    Red = 255;
                } else if (Red < 0) {
                    Red = 0;
                }
                int Green = (int) (color1.getGreen() * a) - b;
                if (Green > 255) {
                    Green = 255;
                } else if (Green < 0) {
                    Green = 0;
                }
                int Blue = (int) (color1.getBlue() * a) - b;
                if (Blue > 255) {
                    Blue = 255;
                } else if (Blue < 0) {
                    Blue = 0;
                }

                Color col = new Color(Red, Green, Blue);
                wynik.setRGB(x, y, col.getRGB());
            }
        }
        okno.repaint();
    }
}

class Okno extends Canvas {
    @Override
    public void paint(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        setBackground(new Color(43,43,43));
        g2d.drawImage(edytorObrazow.obraz1, 75, 200, null);
        if (edytorObrazow.wynik != null)
            g2d.drawImage(edytorObrazow.wynik, 625, 200, null);

    }
}