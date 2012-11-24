/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Score;

import javax.ejb.Local;
import nodrawable.Ranking;
import nodrawable.Score;

/**
 *
 * @author davidsantiagobarrera
 */
@Local
public interface ScoreBeanLocal {

    Ranking saveScore(Score score);
    
}
