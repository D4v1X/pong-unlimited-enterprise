/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Score;

import nodrawable.Ranking;
import nodrawable.Score;

/**
 *
 * @author davidsantiagobarrera
 */
public interface ScoreManager {

    Ranking loadRanking();

    public void saveScore(Score scoresave);
}
