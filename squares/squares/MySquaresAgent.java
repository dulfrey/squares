/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package squares;
import unalcol.agents.Action;
import unalcol.agents.AgentProgram;
import unalcol.agents.Percept;
import unalcol.agents.examples.reversi.Clock;
import unalcol.types.collection.vector.Vector;

/**
 *
 * @author dulfrey
 */
public class MySquaresAgent implements AgentProgram {

    
    protected String myColor;
    protected Clock myClock;
    protected Clock oponentClock;
    
    
    public MySquaresAgent( String color ){
        this.myColor = color;        
    }
    //agregar paquete .si2.dulfreyhernandez
    //recordar ver el reloj 
    // mi tiempo y el de mi oponente
    @Override
    public Action compute(Percept p) {
        long time = (long)(200 * Math.random());
        try{
           Thread.sleep(time);
        }catch(Exception e){}
        
        //si es mi turno
        if( p.getAttribute(Squares.TURN).equals(myColor) ){
            
//            int size = Integer.parseInt((String)p.getAttribute(Squares.SIZE));
//            int i = 0;
//            int j = 0;
//            Vector<String> v = new Vector<String>();
//            while(v.size()==0){
//              i = (int)(size*Math.random());
//              j = (int)(size*Math.random());
//              if(((String)p.getAttribute(i+":"+j+":"+Squares.LEFT)).equals(Squares.FALSE))
//                v.add(Squares.LEFT);
//              if(((String)p.getAttribute(i+":"+j+":"+Squares.TOP)).equals(Squares.FALSE))
//                v.add(Squares.TOP);
//              if(((String)p.getAttribute(i+":"+j+":"+Squares.BOTTOM)).equals(Squares.FALSE))
//                v.add(Squares.BOTTOM);
//              if(((String)p.getAttribute(i+":"+j+":"+Squares.RIGHT)).equals(Squares.FALSE))
//                v.add(Squares.RIGHT);
//            }
//            return new Action( i+":"+j+":"+v.get((int)(Math.random()*v.size())) );
        }
        
        System.out.println( p.getAttribute( Squares.WHITE + "_" + Squares.TIME));
        return new Action(Squares.PASS);
    }
    
    
    //devuelve el myColor del oponente
    public String getOponentColor(){
        if ( this.myColor.equals( Squares.BLACK)) {
            return Squares.WHITE;
        }else 
            return Squares.BLACK;
    }
    
    @Override
    public void init() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}