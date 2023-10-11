package carro;



import com.jogamp.opengl.GL2;
import com.jogamp.opengl.GLAutoDrawable;
import com.jogamp.opengl.GLEventListener;
import com.jogamp.opengl.glu.GLU;
import com.jogamp.opengl.util.awt.TextRenderer;
import com.jogamp.opengl.util.gl2.GLUT;

import java.awt.*;

/**
 *
 * @author Kakugawa
 */
public class Cena implements GLEventListener{    
    private float xMin, xMax, yMin, yMax, zMin, zMax;    
    private GL2 gl;
    private GLUT glut;
    public int horizontal, vertical;
    
    @Override
    public void init(GLAutoDrawable drawable) {
        gl = drawable.getGL().getGL2();
        glut = new GLUT();
        //Estabelece as coordenadas do SRU (Sistema de Referencia do Universo)
        xMin = yMin = zMin = -100;
        xMax = yMax = zMax = 100;  
        horizontal = vertical = 0;
        //Habilita o buffer de profundidade
        gl.glEnable(GL2.GL_DEPTH_TEST);
    }

    @Override
    public void display(GLAutoDrawable drawable) {  
        gl.glClearColor(0.95f, 0.95f, 0.95f, 1);
        gl.glClear(GL2.GL_COLOR_BUFFER_BIT | GL2.GL_DEPTH_BUFFER_BIT);       
        gl.glLoadIdentity(); //ler a matriz identidade
        
        gl.glRotatef(horizontal, 0, 1, 0);
        gl.glRotatef(vertical, 1, 0, 0);
        
        drawCar();

        gl.glFlush();      
    }

    @Override
    public void reshape(GLAutoDrawable drawable, int x, int y, int width, int height) {    
               
        //evita a divisao por zero
        if(height == 0) height = 1;
                
        //ativa a matriz de projecao
        gl.glMatrixMode(GL2.GL_PROJECTION);      
        gl.glLoadIdentity(); //ler a matriz identidade

        //projecao ortogonal sem a correcao do aspecto
        gl.glOrtho(xMin, xMax, yMin, yMax, zMin, zMax);
        
        //ativa a matriz de modelagem
        gl.glMatrixMode(GL2.GL_MODELVIEW);
        gl.glLoadIdentity(); //ler a matriz identidade
        System.out.println("Reshape: " + width + ", " + height);
    }    
       
    @Override
    public void dispose(GLAutoDrawable drawable) {}
    
    public void drawCar() {
        Color colorCarcaca = new Color(100, 100, 100);
        Color colorCapota = new Color(150, 150, 150);
        Color colorFarol = Color.RED;
        Color colorWheels = Color.BLACK;
        
        drawBody(colorCarcaca, 0, -25, 0, 50, 2, 1, 3);
        drawBody(colorCapota, 0, 20, -45, 40, 2.5f, 1, 1.5f);
        // FAROIS DIANTEIROS
        drawHeadlight(colorFarol, 8, 5, -25,-15, 75);
        drawHeadlight(colorFarol, 8, 5, 25, -15, 75);
        // FAROIS TRASEIROS
        drawHeadlight(colorFarol, 8, 5, -25,-15, -80);
        drawHeadlight(colorFarol, 8, 5, 25, -15, -80);
        // RODAS LATERAL DIREITA
        drawWheels(colorWheels, 5, 15, -50, -50, 75);
        drawWheels(colorWheels, 5, 15, -50, -50, -75);
        // RODAS LATERAL ESQUERDA
        drawWheels(colorWheels, 5, 15, 50, -50, 75);
        drawWheels(colorWheels, 5, 15, 50, -50, -75);
        
        
        
    }

    public void drawBody(Color color, float x, float y, float z, float size, float scaleX, float scaleY, float scaleZ) {    
        gl.glColor3f(convertColor(color.getRed()), convertColor(color.getGreen()), convertColor(color.getBlue()));
        gl.glPushMatrix();
        gl.glTranslatef(x, y, z);
        gl.glScalef(scaleX, scaleY, scaleZ);
        glut.glutSolidCube(size);
        gl.glPopMatrix();
    }
    
    public void drawHeadlight(Color color, double radius, double height, float x, float y, float z) {
        gl.glColor3f(convertColor(color.getRed()), convertColor(color.getGreen()), convertColor(color.getBlue()));
        gl.glPushMatrix();
        gl.glTranslatef(x, y, z);
        glut.glutSolidCylinder(radius, height, 60, 20);
        gl.glPopMatrix();
    }
    
    public void drawWheels(Color color, double innerRadius, double outterRadius, float x, float y, float z) {
        gl.glColor3f(convertColor(color.getRed()), convertColor(color.getGreen()), convertColor(color.getBlue()));
        gl.glPushMatrix();
        gl.glTranslatef(x, y, z);
        gl.glRotatef(90, 0, 1, 0);
        glut.glutSolidTorus(innerRadius, outterRadius, 20, 40);
        gl.glPopMatrix();
    }

    public void iluminacaoAmbiente(GL2 gl) {
        float luzAmbiente[] = {0.5f, 0.5f, 0.5f, 1f}; //cor
        float posicaoLuz[] = {50.0f, 100.0f, 100.0f, 1.0f}; //pontual

        // define parametros de luz de n�mero 0 (zero)
        gl.glLightfv(GL2.GL_LIGHT0, GL2.GL_AMBIENT, luzAmbiente, 0);
        gl.glLightfv(GL2.GL_LIGHT0, GL2.GL_POSITION, posicaoLuz, 0);
    }

    public void ligaLuz(GL2 gl) {
        // habilita a defini��o da cor do material a partir da cor corrente
        gl.glEnable(GL2.GL_COLOR_MATERIAL);

        // habilita o uso da ilumina��o na cena
        gl.glEnable(GL2.GL_LIGHTING);
        // habilita a luz de n�mero 0
        gl.glEnable(GL2.GL_LIGHT0);
        //Especifica o Modelo de tonalizacao a ser utilizado
        //GL_FLAT -> modelo de tonalizacao flat
        //GL_SMOOTH -> modelo de tonaliza��o GOURAUD (default)
        gl.glShadeModel(GL2.GL_SMOOTH);
    }
    public void desligaluz(GL2 gl) {
        //desabilita o ponto de luz
        gl.glDisable(GL2.GL_LIGHT0);
        //desliga a iluminacao
        gl.glDisable(GL2.GL_LIGHTING);
    }
    
    public float convertColor(int color) {
        return (float) color/255;
    }
}
