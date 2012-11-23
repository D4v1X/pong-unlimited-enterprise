/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package servlets;

import java.io.IOException;
import java.io.InputStream;
import java.io.InvalidClassException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.StreamCorruptedException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import servlets.db.BDSaver;
import servlets.file.Filesaver;

/**
 *
 * @author davidsantiagobarrera
 */
public class SceneServlet extends HttpServlet {

    /**
     * Processes requests for both HTTP
     * <code>GET</code> and
     * <code>POST</code> methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
// Declaraciones:
        SceneSaver scenesaverFile;
        SceneSaver scenesaverBD;
        scenesaverFile = new Filesaver();
        scenesaverBD = new BDSaver();
        Object resultado = null;

        // establecemos el formato de la respuesta
        String contentType = "application/x-java-serialized-object";
        response.setContentType(contentType);

        try {
            // recuperamos el stream de entrada POST
            InputStream instream = request.getInputStream();
            ObjectInputStream bufferentrada = new ObjectInputStream(instream);

            // (R1)Mensaje Cliente (Usuario)
            String usuario = (String) bufferentrada.readObject();

            // (R2)Mensaje Cliente (Operacion)
            String operacion = (String) bufferentrada.readObject();
            System.out.println("OPERACION: " + operacion);

            //Gestionar los Datos en Ficheros --------------
            if (operacion.equals("GUARDARFL")) {

                // (R3.1)Guarda Fichero
                Object Obj = bufferentrada.readObject();
                scenesaverFile.write(usuario, Obj);
                System.out.println("Stream Guardado en Fichero");
                resultado = (Object) "Stream Guardado en Fichero";

                System.out.println("Servlet Guardando en Fichero");
            }
            if (operacion.equals("CARGARFL")) {

                // (R3.2)Cargar Fichero
                resultado = scenesaverFile.load(usuario);

                System.out.println("Servlet Cargando de Fichero");
            }
            //Gestionar los Datos en Base de Datos ----------
            if (operacion.equals("GUARDARBD")) {

                // (R3.3)Guarda Fichero
                Object Obj = bufferentrada.readObject();
                scenesaverBD.write(usuario, Obj);
                System.out.println("Stream Guardado en BD");
                resultado = (Object) "Stream Guardado en BD";

                System.out.println("Servlet Guardando en BD");

            }
            if (operacion.equals("CARGARBD")) {

                // (R3.4)Cargar Fichero
                resultado = scenesaverBD.load(usuario);

                System.out.println("Servlet Cargando de BD");
            }
            //---------------------------------------------
            // Configurarmos un Stream de Salida GET
            OutputStream outstream = response.getOutputStream();
            ObjectOutputStream buffersalida = new ObjectOutputStream(outstream);

            // (W1)escribimos los datos
            buffersalida.writeObject(resultado);

            // y los enviamos
            buffersalida.flush();



        } catch (ClassNotFoundException e) {
            System.out.println("Error ClassNotFoundException");
            System.out.println(e.getMessage());
        } catch (InvalidClassException e) {
            System.out.println("Error InvalidClassException");
            System.out.println(e.getMessage());
        } catch (StreamCorruptedException e) {
            System.out.println("Error aStreamCorruptedException");
            System.out.println(e.getMessage());
        } catch (IOException e) {
            System.out.println("Error IOException");
            System.out.println(e.getMessage());
        } catch (Exception e) {
            System.out.println("Error al recuperar datos");
            System.out.println(e.getMessage());
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP
     * <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP
     * <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>
}
