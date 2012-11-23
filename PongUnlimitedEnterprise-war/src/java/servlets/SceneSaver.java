/**
 *
 */
package servlets;

/**
 * @author David Santiago Barrera
 * @data 14/10/2012
 *
 */
public interface SceneSaver {

    Object load(String name);

    Boolean write(String name, Object obj);
}
