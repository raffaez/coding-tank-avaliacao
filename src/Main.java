import java.util.InputMismatchException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args){
        try {
            Scanner scan = new Scanner(System.in);

            System.out.print("Digite a quantidade de temperaturas a serem transformadas: ");
            int qtdTemperaturas = scan.nextInt();
            if(qtdTemperaturas < 1){
                throw new IllegalArgumentException("A quantidade de temperaturas deve ser maior que 0.");
            }

            scan.nextLine();

            System.out.print("Digite a unidade de origem (C, F ou K): ");
            char unidadeOrigem = Character.toUpperCase(scan.nextLine().charAt(0));
            if(unidadeOrigem != 'C' && unidadeOrigem != 'K' && unidadeOrigem != 'F'){
                throw new IllegalArgumentException("Unidade inválida. A unidade de origem deve ser C, F ou K.");
            }

            System.out.print("Digite a unidade final da temperatura (C, F ou K): ");
            char unidadeFinal = Character.toUpperCase(scan.nextLine().charAt(0));
            if(unidadeFinal != 'C' && unidadeFinal != 'K' && unidadeFinal != 'F'){
                throw new IllegalArgumentException("Unidade inválida. A unidade final deve ser C, F ou K.");
            }

            double[] temperaturas = lerTemperaturas(scan, qtdTemperaturas);

            double[] temperaturasConvertidas = switch (unidadeFinal) {
                case 'C' -> converterParaC(temperaturas, unidadeOrigem);
                case 'F' -> converterParaF(temperaturas, unidadeOrigem);
                case 'K' -> converterParaK(temperaturas, unidadeOrigem);
                default -> new double[temperaturas.length];
            };

            mostrarTemperaturas(temperaturas, temperaturasConvertidas, unidadeOrigem, unidadeFinal);

            double mediaTemperaturas = calcularMedia(temperaturas);
            double mediaTemperaturasConvertidas = calcularMedia(temperaturasConvertidas);

            System.out.printf("\nMédia das temperaturas iniciais: %.1fº%c\n", mediaTemperaturas, unidadeOrigem);
            System.out.printf("Média das temperaturas convertidas: %.1fº%c\n", mediaTemperaturasConvertidas, unidadeFinal);

        } catch (IllegalArgumentException | IllegalStateException e) {
            System.err.println(e.getMessage());
        } catch (InputMismatchException e){
            System.err.println("Entrada inválida.");
        }
    }

    public static double[] lerTemperaturas(Scanner scan, int qtdTemperaturas){
        double[] temperaturas = new double[qtdTemperaturas];
        for(int i = 0; i < qtdTemperaturas; i++) {
            System.out.printf("Digite a %dª temperatura: ", i+1);
            temperaturas[i] = scan.nextDouble();
        }

        return temperaturas;
    }

    public static double[] converterParaC(double[] temperaturas, char unidadeOrigem){
        double[] temperaturasConvertidas = new double[temperaturas.length];
        switch(unidadeOrigem){
            case 'F':
                for(int i=0; i<temperaturas.length; i++){
                    temperaturasConvertidas[i] = (temperaturas[i]-32)/1.8;
                }
                break;
            case 'K':
                for(int i=0; i<temperaturas.length; i++){
                    temperaturasConvertidas[i] = temperaturas[i]-273;
                }
                break;
        }

        return temperaturasConvertidas;
    }

    public static double[] converterParaF(double[] temperaturas, char unidadeOrigem){
        double[] temperaturasConvertidas = new double[temperaturas.length];
        switch(unidadeOrigem){
            case 'C':
                for(int i=0; i<temperaturas.length; i++){
                    temperaturasConvertidas[i] = (1.8*temperaturas[i])+32;
                }
                break;
            case 'K':
                for(int i=0; i<temperaturas.length; i++){
                    temperaturasConvertidas[i] = 1.8*(temperaturas[i]-273.15)+32;
                }
                break;
        }

        return temperaturasConvertidas;
    }

    public static double[] converterParaK(double[] temperaturas, char unidadeOrigem){
        double[] temperaturasConvertidas = new double[temperaturas.length];
        switch(unidadeOrigem){
            case 'C':
                for(int i=0; i<temperaturas.length; i++){
                    temperaturasConvertidas[i] = temperaturas[i]+273;
                }
                break;
            case 'F':
                for(int i=0; i<temperaturas.length; i++){
                    temperaturasConvertidas[i] = (temperaturas[i]+459.67)*1.8;
                }
                break;
        }

        return temperaturasConvertidas;
    }

    public static void mostrarTemperaturas(double[] temperaturas, double[] temperaturasConvertidas, char unidadeOrigem, char unidadeFinal){
        String u1 = getUnidade(unidadeOrigem);
        String u2 = getUnidade(unidadeFinal);

        System.out.printf("\n----- CONVERSÃO DE %s PARA %s -----\n\n", u1, u2);

        for(int i = 0; i < temperaturas.length; i++){
            System.out.printf("%.1fº%c -> %.1fº%c\n", temperaturas[i], unidadeOrigem, temperaturasConvertidas[i], unidadeFinal);
        }
    }

    public static String getUnidade(char unidade){

        return switch(unidade){
            case 'C' -> "Celsius";
            case 'F' -> "Fahrenheit";
            case 'K' -> "Kelvin";
            default -> throw new IllegalStateException("Valor não esperado: " + unidade);
        };
    }

    public static double calcularMedia(double[] temperaturas){
        double total = 0;
        for(double temperatura : temperaturas){
            total+=temperatura;
        }

        return total/temperaturas.length;
    }
}
