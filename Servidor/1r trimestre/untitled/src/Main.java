import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

interface MyFunction{
    int suma(int a, int b);
}

class City{
    String nom;
    int nhabit;

    public City(String nom, int nhabit) {
        this.nom = nom;
        this.nhabit = nhabit;
    }

    @Override
    public String toString() {
        return "City{" +
                "nom='" + nom + '\'' +
                ", nhabit=" + nhabit +
                '}';
    }
}

public class Main {
    static void main(String[] args) {
        //f y f2 es lo mismo
        MyFunction f = (a, b) -> a+b;
        f.suma(1,2);

        MyFunction f2 = new MyFunction() {
            @Override
            public int suma(int a, int b) {
                return a + b;
            }
        };
        /*
        List<City> list = new ArrayList<>();
        list.add(new City("Palma", 900_000));
        list.add(new City("Barcelona", 1_900_000));
        list.add(new City("Manacor", 50_000));
        list.add(new City("Valencia", 1_400_000));
        //Ordena por nHabit
        list.sort((a,b)->a.nhabit-b.nhabit);
        System.out.println(list);
         */

        List<City> list = new ArrayList<>();
        list.add(new City("Palma", 900_000));
        list.add(new City("Barcelona", 1_900_000));
        list.add(new City("Manacor", 50_000));
        list.add(new City("Valencia", 1_400_000));

        City firstCity = list.stream()
                .filter(city -> city.nhabit > 1_000_000 && city.nhabit > 1_500_000)
                .findFirst().get();

        //Stream es un stream y llista es llista, hem de passar primer a llista per que sigui valid
        List<City> cities = list.stream()
                .filter(city -> city.nhabit > 1_000_000 && city.nhabit > 1_500_000)
                .toList();

        System.out.println(firstCity);

        //convertir llista de cities a llista de Strings
        List<String> names = list.stream()
        .map(city -> city.nom)
                .toList();
        System.out.println(names);

        //pasamos de una lista tipo city a tipo nHabit(int) y despues sumamos todos los nHabit de la lista
        int totalHab = list.stream()
                .map(city -> city.nhabit)
                .reduce(0,(a,b) -> a + b);

        System.out.println(totalHab);
    }
}
