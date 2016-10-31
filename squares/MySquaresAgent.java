/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package squares;

import java.util.Collection;
import java.util.Comparator;
import java.util.Iterator;
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

    private PriorityQueue<Cuadrito> queue = new PriorityQueue<Cuadrito>();
    protected String myColor;
    protected String myClock;
    protected Clock oponentClock;
    protected int size;
    protected Target t;

    public MySquaresAgent( String color ) {
        this.myColor = color;
        this.size = 0;
        t = new Target( 0, 0, "" );

    }

    //agregar paquete .si2.dulfreyhernandez
    //recordar ver el reloj 
    // mi tiempo y el de mi oponente
    @Override
    public Action compute( Percept p ) {
        if ( size == 0 ) {
            size = Integer.parseInt( (String) p.getAttribute( Squares.SIZE ) );
            queue = new PriorityQueue<>( size * size, new Cuadrito() );
            initializeCuadritos();
        }
        long time = (long) (200 * Math.random());
        try {
            Thread.sleep( time );
        } catch (Exception e) {
            System.out.println( "error en el thread de mi agente" );
        }
        updateSquares( p );

//        System.out.println( "cuadritos");
//        for ( int i = 0; i < size * size; i++ ) {
//                System.out.println( queue.poll());
//            }
        //si es mi turno
        if ( p.getAttribute( Squares.TURN ).equals( myColor ) ) {
            //hay un cuadro para llenar
            if ( isSquareToFill( p ) ) {
                return new Action( t.i + ":" + t.j + ":" + t.side );
            }

            //marcar linea 
            int asize = Integer.parseInt( (String) p.getAttribute( Squares.SIZE ) );
            int i = 0;
            int j = 0;
            Vector<String> v = new Vector<String>();
            while ( v.size() == 0 ) {
                i = (int) (asize * Math.random());
                j = (int) (asize * Math.random());
                if ( ((String) p.getAttribute( i + ":" + j + ":" + Squares.LEFT )).equals( Squares.FALSE ) ) {
                    v.add( Squares.LEFT );
                }
                if ( ((String) p.getAttribute( i + ":" + j + ":" + Squares.TOP )).equals( Squares.FALSE ) ) {
                    v.add( Squares.TOP );
                }
                if ( ((String) p.getAttribute( i + ":" + j + ":" + Squares.BOTTOM )).equals( Squares.FALSE ) ) {
                    v.add( Squares.BOTTOM );
                }
                if ( ((String) p.getAttribute( i + ":" + j + ":" + Squares.RIGHT )).equals( Squares.FALSE ) ) {
                    v.add( Squares.RIGHT );
                }
            }

            return new Action( i + ":" + j + ":" + v.get( (int) (Math.random() * v.size()) ) );
        }
        //si mi oponente es negro 
        if ( getOponentColor().equals( Squares.BLACK ) ) {
            myClock = (String) p.getAttribute( Squares.WHITE + "_" + Squares.TIME );
        } else {
            myClock = (String) p.getAttribute( Squares.BLACK + "_" + Squares.TIME );
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

        if ( queue.peek().v.size() == 3 ) {
            Cuadrito c = queue.poll();
            t = new Target( c.i, c.j, c.available() );
           return true;
        }
//        for ( int i = 0; i < size; i++ ) {
//            for ( int j = 0; j < size; j++ ) {
//                if ( numberOfLines( i, j, p ) == 3 ) {
//                    return true;
//                }
//            }
//
//        }

        return true;
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
        t = new Target( i, j, side );

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
                    queue.add( new Cuadrito( i, j, v ) );
                }
                if ( i == 0 && j == size - 1 ) {
                    v.add( Squares.TOP );
                    v.add( Squares.RIGHT );
                    queue.add( new Cuadrito( i, j, v ) );
                }
                if ( i == size - 1 && j == 0 ) {
                    v.add( Squares.BOTTOM );
                    v.add( Squares.LEFT );
                    queue.add( new Cuadrito( i, j, v ) );
                }
                if ( i == size - 1 && j == size - 1 ) {
                    v.add( Squares.BOTTOM );
                    v.add( Squares.RIGHT );
                    queue.add( new Cuadrito( i, j, v ) );
                }
                if ( i == 0 && j != 0 && j != size - 1 ) {
                    v.add( Squares.TOP );
                    queue.add( new Cuadrito( i, j, v ) );
                }
                if ( i == size - 1 && j != 0 && j != size - 1 ) {
                    v.add( Squares.BOTTOM );
                    queue.add( new Cuadrito( i, j, v ) );
                }
                if ( j == 0 && i != 0 && i != size - 1 ) {
                    v.add( Squares.LEFT );
                    queue.add( new Cuadrito( i, j, v ) );
                }
                if ( j == size - 1 && i != 0 && i != size - 1 ) {
                    v.add( Squares.RIGHT );
                    queue.add( new Cuadrito( i, j, v ) );
                }
                if ( j != size - 1 && j != 0 && i != 0 && i != size - 1 ) {
                    queue.add( new Cuadrito( i, j, v ) );
                }

            }

        }
    }

    private void updateSquares( Percept p ) {
        PriorityQueue<Cuadrito> newQueue = new PriorityQueue<Cuadrito>();
        for ( Iterator<Cuadrito> iterator = queue.iterator(); iterator.hasNext(); ) {
            Cuadrito next = iterator.next();
            Vector<String> v = new Vector<String>();
            
            if ( ((String) p.getAttribute( next.i + ":" + next.j + ":" + Squares.LEFT )).equals( Squares.TRUE ) ) {
                v.add( Squares.LEFT );
            }

            if ( ((String) p.getAttribute( next.i + ":" + next.j + ":" + Squares.TOP )).equals( Squares.TRUE ) ) {
                v.add( Squares.TOP );
            }
            if ( ((String) p.getAttribute( next.i + ":" + next.j + ":" + Squares.BOTTOM )).equals( Squares.TRUE ) ) {
                v.add( Squares.BOTTOM );
            }
            if ( ((String) p.getAttribute( next.i + ":" + next.j + ":" + Squares.RIGHT )).equals( Squares.TRUE ) ) {
                v.add( Squares.RIGHT );
            }
           // el cuadro está completo  
            if ( v.size() == 4 ) {
                queue.remove( next );
            }else{
                newQueue.add( new Cuadrito( next.i, next.j, v ) );
            }
            
            if ( !next.equals( new Cuadrito( next.i, next.j, v ) ) ) {
                   queue.remove( next );
                   queue.add( new Cuadrito( next.i, next.j, v ) );
            }
        }
       
       
    }

    private static class Cuadrito implements Comparator<Cuadrito> {

        private int i;
        private int j;
        private Vector<String> v = new Vector<String>();
        
        public String available(){
            if ( !v.contains( Squares.TOP)) {
                return Squares.TOP;
            }
            if ( !v.contains( Squares.RIGHT)) {
                return Squares.RIGHT;
            }
            if ( !v.contains( Squares.BOTTOM)) {
                return Squares.BOTTOM;
            }
            if ( !v.contains( Squares.LEFT)) {
                return Squares.LEFT;
            }
            return null;
        }
        
        public Cuadrito( int i, int j, Vector<String> values ) {
            this.i = i;
            this.j = j;
            this.v = values;
        }

        public String toString() {
            String s = i + " " + j;
            for ( Iterator<String> iterator = v.iterator(); iterator.hasNext(); ) {
                String next = iterator.next();
                s = s + next + " ";
            }

            return s;
        }

        public Cuadrito() {

        }

        @Override
        public int compare( Cuadrito c1, Cuadrito c2 ) {
            if ( c1.v.size() > c2.v.size() ) {
                return -1;
            }
            if ( c1.v.size() < c2.v.size() ) {
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
