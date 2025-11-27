import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

public class Main {
    static void main() {
        class Publicacio{
            private int nPagines;
            private Date dataPubli;
            Publicacio(){

            }
            Publicacio (int nPagines, Date dataPubli){
                this.nPagines = nPagines;
                this.dataPubli = dataPubli;
            }
            void setnPagines(int n){
                if (n > 0 && n < 10000) {
                    this.nPagines = n;
                }
            }
        }
        class llibre extends Publicacio{
            private String titol;
            private String isbn;
            llibre(int nPagines, Date dataPubli, String titol, String isbn){
                super(nPagines, dataPubli);
                this.titol = titol;
                this.isbn = isbn;
            }

        }
        class revista extends Publicacio{
            private String marca;
            private String serie;
            revista(int nPagines, Date dataPubli, String marca, String serie){
                super(nPagines, dataPubli);
                this.marca = marca;
                this.serie = serie;
            }

        }
        class Biblioteca{
            List<Publicacio> publicacions = new ArrayList<>();
            void nouPublicacio(Publicacio p){
                this.publicacions.add(p);
            }
            void mostraLlistaPub(){
                for(Publicacio p : this.publicacions){
                    System.out.println(publicacions.get(p.nPagines));
                }
            }
        }
    }
}
