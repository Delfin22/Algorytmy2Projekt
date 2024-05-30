package classes.problem1;

import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class ConvexHull {

    public static double calculateDet(Point p1, Point p2, Point p3){
        double result;

//        p1.x p1.y 1
//        p2.x p2.y 1
//        p3.x p3.y 1

        result = p1.getX()*p2.getY() +
                p2.getX()*p3.getY() +
                p3.getX()*p1.getY() -
                p2.getY()*p3.getX() -
                p3.getY()*p1.getX() -
                p1.getY()*p2.getX();
        return result;
    }

    public static Comparator<Point> getComparator(Point q){
        return (p1,p2) -> {
            //jesli porownujemy punkt statyczny to wrzucamy go na start bo czmeu by nie
            if (p1.equals(q)) return -1; // p1 is the base point, so it comes first
            if (p2.equals(q)) return 1;  // p2 is the base point, so p1 comes first

            double det = calculateDet(q,p1,p2); //trzeba cos zrobic w przypadku remisow zeby usuwac ten blizsy punkt ale pomyslu nie mam poki co (moze nowa lsite zrobic z punktami do usuniecia?)

            return Double.compare(det,0);
        };
    }

    public static List<Point> sortViaAngle(List<Point> list){
        Point q = list.getFirst();
        for(Point p : list){ //szukamy punktu o najmniejszym y i jesli remis to najmniejszym x
            if(p.getY() < q.getY()){
                q = p;
            }
            else if(p.getY() == q.getY()){
                if(p.getX() < q.getX()){
                    q = p;
                }
            }
        }

        list.sort(getComparator(q)); //sortujemy se uzywajac tego wyzej i dostajemy punkty posortowane ze wzgledu na kąt od najwiekszego do najmniejszego
        return list;
    }


    public static List<Point> findConvexHull(List<Point> list){ //do implementacji
       return null;
    }
}
