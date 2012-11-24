/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Score;

import javax.ejb.Stateless;
import nodrawable.Ranking;
import nodrawable.Score;

/**
 *
 * @author davidsantiagobarrera
 */
@Stateless
public class ScoreBean implements ScoreBeanRemote, ScoreBeanLocal {

    @Override
    public Ranking saveScore(Score score) {
        return null;
    }
    
    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")
    
    
}
