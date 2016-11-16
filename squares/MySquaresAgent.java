/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package squares;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import unalcol.agents.Action;
import unalcol.agents.AgentProgram;
import unalcol.agents.Percept;
import unalcol.agents.examples.reversi.Clock;
import unalcol.types.collection.vector.Vector;
import java.util.PriorityQueue;

/**
 *
 * @author dulfrey
 */
public class MySquaresAgent implements AgentProgram {

    protected String myColor;
    protected String myClock;
    protected Clock oponentClock;
    protected int size;
    protected Target myTarget;
    protected ArrayList<Cuadrito> cuadritosArray;
    
    public MySquaresAgent( String color ) {
        this.myColor = color;
        this.size = 0;
        myTarget = new Target( 0, 0, "" );

    }

    //agregar paquete .si2.dulfreyhernandez
    //recordar ver el reloj 
    // mi tiempo y el de mi oponente
    @Override
    public Action compute( Percept p ) {
        if ( size == 0 ) {
            size = Integer.parseInt( (String) p.getAttribute( Squares.SIZE ) );
            cuadritosArray = new ArrayList<Cuadrito>();
            initializeCuadritos();
        }
        long time = (long) (300 * Math.random());
        try {
            Thread.sleep( time );
        } catch (Exception e) {
            System.out.println( "error en el thread de mi agente" );
        }
        

        //si es mi turno
        if ( p.getAttribute( Squares.TURN ).equals( myColor ) ) {
            updateSquares( p );
            //si mi oponente es negro 
//            if ( getOponentColor().equals( Squares.BLACK ) ) {
//                myClock = (String) p.getAttribute( Squares.WHITE + "_" + Squares.TIME );
//            } else {
//                myClock = (String) p.getAttribute( Squares.BLACK + "_" + Squares.TIME );
//            }
            //hay un cuadro para llenar

//            for ( int i = 0; i < cuadritosArray.size(); i++ ) {
//                System.out.println(cuadritosArray.get( i ));
//            }
            if ( isSquareToFill( p ) ) {
                System.out.println( myTarget.i + ":" + myTarget.j + ":" + myTarget.side );
                return new Action( myTarget.i + ":" + myTarget.j + ":" + myTarget.side );
            }
            Cuadrito lastCuadrito = cuadritosArray.get( cuadritosArray.size() - 1 );

            switch (lastCuadrito.sides.size()) {

                case 0:
                    playLast( p, lastCuadrito );
                    break;
                case 1:
                    playLast1( p, lastCuadrito );
                    break;
                case 2:
                    playLast2( p, lastCuadrito );
                    break;

            }
        }
        //System.out.println(queue.toString());
    
        return new Action( Squares.PASS );
    }

    //devuelve el Color del oponente
    public String getOponentColor() {
        if ( this.myColor.equals( Squares.BLACK ) ) {
            return Squares.WHITE;
        } else {
            return Squares.BLACK;
        }
    }

    @Override
    public void init() {
        throw new UnsupportedOperationException( "Not supported yet." ); //To change body of generated methods, choose Tools | Templates.
    }

    private boolean isSquareToFill( Percept p ) {

        if ( cuadritosArray.get( 0 ).sides.size() == 3 ) {

            Cuadrito c = cuadritosArray.remove( cuadritosArray.size() - 1 );
            myTarget = new Target( c.i, c.j, c.availableRandomSide() );
            return true;
        }
        return false;
//        for ( int i = 0; i < size; i++ ) {
//            for ( int j = 0; j < size; j++ ) {
//                if ( numberOfLines( i, j, p ) == 3 ) {
//                    return true;
//                }
//            }
//
//        }

    }

    private int numberOfLines( Cuadrito cuadrito ) {
        return cuadrito.sides.size();
    }

    private int numberOfLines( int i, int j, Percept p ) {

        Vector<String> v = new Vector<String>();
        String side = "";

        if ( ((String) p.getAttribute( i + ":" + j + ":" + Squares.LEFT )).equals( Squares.TRUE ) ) {
            v.add( Squares.LEFT );
        } else {
            side = Squares.LEFT;
        }

        if ( ((String) p.getAttribute( i + ":" + j + ":" + Squares.TOP )).equals( Squares.TRUE ) ) {
            v.add( Squares.TOP );
        } else {
            side = Squares.TOP;
        }
        if ( ((String) p.getAttribute( i + ":" + j + ":" + Squares.BOTTOM )).equals( Squares.TRUE ) ) {
            v.add( Squares.BOTTOM );
        } else {
            side = Squares.BOTTOM;
        }
        if ( ((String) p.getAttribute( i + ":" + j + ":" + Squares.RIGHT )).equals( Squares.TRUE ) ) {
            v.add( Squares.RIGHT );
        } else {
            side = Squares.RIGHT;
        }
        myTarget = new Target( i, j, side );

        //cuadritos
        return v.size();
    }

    private void initializeCuadritos() {

        for ( int i = 0; i < size; i++ ) {
            for ( int j = 0; j < size; j++ ) {
                Vector<String> v = new Vector<String>();
                if ( i == 0 && j == 0 ) {
                    v.add( Squares.TOP );
                    v.add( Squares.LEFT );
                    cuadritosArray.add( new Cuadrito( i, j, v ) );
                }
                if ( i == 0 && j == size - 1 ) {
                    v.add( Squares.TOP );
                    v.add( Squares.RIGHT );
                    cuadritosArray.add( new Cuadrito( i, j, v ) );
                }
                if ( i == size - 1 && j == 0 ) {
                    v.add( Squares.BOTTOM );
                    v.add( Squares.LEFT );
                    cuadritosArray.add( new Cuadrito( i, j, v ) );
                }
                if ( i == size - 1 && j == size - 1 ) {
                    v.add( Squares.BOTTOM );
                    v.add( Squares.RIGHT );
                    cuadritosArray.add( new Cuadrito( i, j, v ) );
                }
                if ( i == 0 && j != 0 && j != size - 1 ) {
                    v.add( Squares.TOP );
                    cuadritosArray.add( new Cuadrito( i, j, v ) );
                }
                if ( i == size - 1 && j != 0 && j != size - 1 ) {
                    v.add( Squares.BOTTOM );
                    cuadritosArray.add( new Cuadrito( i, j, v ) );
                }
                if ( j == 0 && i != 0 && i != size - 1 ) {
                    v.add( Squares.LEFT );
                    cuadritosArray.add( new Cuadrito( i, j, v ) );
                }
                if ( j == size - 1 && i != 0 && i != size - 1 ) {
                    v.add( Squares.RIGHT );
                    cuadritosArray.add( new Cuadrito( i, j, v ) );
                }
                if ( j != size - 1 && j != 0 && i != 0 && i != size - 1 ) {
                    cuadritosArray.add( new Cuadrito( i, j, v ) );
                }

            }

        }

        cuadritosArray.sort( new Cuadrito() );
    }

    private void updateSquares( Percept p ) {

        for ( int i = 0; i < cuadritosArray.size(); i++ ) {
            Cuadrito next = cuadritosArray.remove( i );
            Vector<String> sides = new Vector<String>();

            if ( ((String) p.getAttribute( next.i + ":" + next.j + ":" + Squares.LEFT )).equals( Squares.TRUE ) ) {
                sides.add( Squares.LEFT );
            }

            if ( ((String) p.getAttribute( next.i + ":" + next.j + ":" + Squares.TOP )).equals( Squares.TRUE ) ) {
                sides.add( Squares.TOP );
            }
            if ( ((String) p.getAttribute( next.i + ":" + next.j + ":" + Squares.BOTTOM )).equals( Squares.TRUE ) ) {
                sides.add( Squares.BOTTOM );
            }
            if ( ((String) p.getAttribute( next.i + ":" + next.j + ":" + Squares.RIGHT )).equals( Squares.TRUE ) ) {
                sides.add( Squares.RIGHT );
            }
            //el cuadro está completo  
            if ( sides.size() == 4 ) {

            } else {
                cuadritosArray.add( new Cuadrito( next.i, next.j, sides ) );
                cuadritosArray.sort( new Cuadrito() );
            }

        }
    }

    private Action playLast( Percept p, Cuadrito lastCuadrito ) {
        String side = "";
        if ( lastCuadrito.i > 1 && lastCuadrito.i < size - 2 && lastCuadrito.j > 1 && lastCuadrito.j < size - 2 ) {
            if ( lastCuadrito.availableRandomSide() != null ) {
                side = lastCuadrito.availableRandomSide();
   
                buscarArriba(lastCuadrito,side,p );
                buscarAbajo(lastCuadrito,side,p );
                buscarDere(lastCuadrito,side,p );
                buscarIzq(lastCuadrito,side,p );
                
                //System.out.println( myTarget.i + ":" + myTarget.j + ":" + myTarget.side );
                return new Action( myTarget.i + ":" + myTarget.j + ":" + myTarget.side );
            }
        }
        //System.out.println( myTarget.i + ":" + myTarget.j + ":" + myTarget.side );
        return new Action( myTarget.i + ":" + myTarget.j + ":" + myTarget.side );
    }

    private void buscarArriba( Cuadrito lastCuadrito, String side, Percept p ) {
        if ( side.equals( Squares.TOP ) ) {
            if ( numberOfLines( lastCuadrito.i, lastCuadrito.j - 1, p ) < 2 ) {
                myTarget = new Target( lastCuadrito.i, lastCuadrito.j, side );
            }
        }
    }

    private void buscarAbajo( Cuadrito lastCuadrito, String side, Percept p ) {
        if ( side.equals( Squares.BOTTOM ) ) {
            if ( numberOfLines( lastCuadrito.i, lastCuadrito.j + 1, p ) < 2 ) {
                myTarget = new Target( lastCuadrito.i, lastCuadrito.j, side );
            }
        }
    }

    private void buscarDere( Cuadrito lastCuadrito, String side, Percept p ) {
        if ( side.equals( Squares.RIGHT ) ) {
            if ( numberOfLines( lastCuadrito.i + 1, lastCuadrito.j, p ) < 2 ) {
                myTarget = new Target( lastCuadrito.i, lastCuadrito.j, side );
            }
        }
    }

    private void buscarIzq( Cuadrito lastCuadrito, String side, Percept p ) {
        if ( side.equals( Squares.LEFT ) ) {
            if ( numberOfLines( lastCuadrito.i - 1, lastCuadrito.j, p ) < 2 ) {
                myTarget = new Target( lastCuadrito.i, lastCuadrito.j, side );
            }
        }
    }

    private Action playLast1( Percept p, Cuadrito lastCuadrito ) {

        String side = "";
        side = lastCuadrito.sides.get(0);
        
        if ( side.equals( Squares.TOP)) {
            buscarDere( lastCuadrito, myColor, p );
                    buscarIzq( lastCuadrito, myColor, p );
                    buscarAbajo( lastCuadrito, myColor, p );
        }
        if ( side.equals( Squares.LEFT)) {
            buscarDere( lastCuadrito, myColor, p );
                    buscarArriba(lastCuadrito, myColor, p );
                    buscarAbajo( lastCuadrito, myColor, p );
        }
        if ( side.equals( Squares.RIGHT)) {
            buscarIzq(lastCuadrito, myColor, p );
                    buscarArriba(lastCuadrito, myColor, p );
                    buscarAbajo( lastCuadrito, myColor, p );
        }
        if ( side.equals( Squares.BOTTOM)) {
            buscarIzq(lastCuadrito, myColor, p );
                    buscarArriba(lastCuadrito, myColor, p );
                    buscarDere(lastCuadrito, myColor, p );
        }
       
       

        
        //System.out.println( myTarget.i + ":" + myTarget.j + ":" + myTarget.side );
        return new Action( myTarget.i + ":" + myTarget.j + ":" + myTarget.side );
    }

    private Action playLast2( Percept p, Cuadrito lastCuadrito ) {
        String side = "";
        side = lastCuadrito.sides.get(0);
        side2 = lastCuadrito.sides.get( 0 );
        if ( side.equals( Squares.TOP)) {
            buscarDere( lastCuadrito, myColor, p );
                    buscarIzq( lastCuadrito, myColor, p );
                    buscarAbajo( lastCuadrito, myColor, p );
        }
        if ( side.equals( Squares.LEFT)) {
            buscarDere( lastCuadrito, myColor, p );
                    buscarArriba(lastCuadrito, myColor, p );
                    buscarAbajo( lastCuadrito, myColor, p );
        }
        if ( side.equals( Squares.RIGHT)) {
            buscarIzq(lastCuadrito, myColor, p );
                    buscarArriba(lastCuadrito, myColor, p );
                    buscarAbajo( lastCuadrito, myColor, p );
        }
        if ( side.equals( Squares.BOTTOM)) {
            buscarIzq(lastCuadrito, myColor, p );
                    buscarArriba(lastCuadrito, myColor, p );
                    buscarDere(lastCuadrito, myColor, p );
        }
       
       

        
        //System.out.println( myTarget.i + ":" + myTarget.j + ":" + myTarget.side );
        return new Action( myTarget.i + ":" + myTarget.j + ":" + myTarget.side );
    }

    private static class Cuadrito implements Comparable<Cuadrito>, Comparator<Cuadrito> {

        protected int i;
        protected int j;
        protected Map<Boolean, String> map = new HashMap<Boolean, String>();

        protected Vector<String> sides = new Vector<String>();

        public String availableRandomSide() {

            Vector<String> avaliabbleSides = new Vector<String>();
            if ( !sides.contains( Squares.TOP ) ) {
                avaliabbleSides.add( Squares.TOP );
            }
            if ( !sides.contains( Squares.RIGHT ) ) {
                avaliabbleSides.add( Squares.RIGHT );
            }
            if ( !sides.contains( Squares.BOTTOM ) ) {
                avaliabbleSides.add( Squares.BOTTOM );
            }
            if ( !sides.contains( Squares.LEFT ) ) {
                avaliabbleSides.add( Squares.LEFT );
            }
            if ( avaliabbleSides.size() != 0 ) {
                int c = (int) (avaliabbleSides.size() * Math.random());
                System.out.println( c );
                return avaliabbleSides.get( c );
            }
            return null;
        }

        public Cuadrito( int i, int j, Vector<String> values ) {
            this.i = i;
            this.j = j;
            this.sides = values;

        }

        public String toString() {
            String s = i + " " + j;
            for ( Iterator<String> iterator = sides.iterator(); iterator.hasNext(); ) {
                String next = iterator.next();
                s = s + next + " ";
            }

            return s;
        }

        public Cuadrito() {

        }

        @Override
        public int compare( Cuadrito c1, Cuadrito c2 ) {
            if ( c1.sides.size() > c2.sides.size() ) {
                return -1;
            }
            if ( c1.sides.size() < c2.sides.size() ) {
                return 1;
            }
            return 0;
        }

        @Override
        public int compareTo( Cuadrito o ) {
            if ( o.sides.size() > this.sides.size() ) {
                return -1;
            }
            if ( o.sides.size() < this.sides.size() ) {
                return 1;
            }
            return 0;
        }

    }

    private static class Target {

        private int i;
        private int j;
        private String side;

        public Target( int i, int j, String side ) {
            this.i = i;
            this.j = j;
            this.side = side;
        }
    }

}
