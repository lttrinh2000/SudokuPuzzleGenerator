package GameMechanic;

import java.util.Random;
import javafx.animation.PauseTransition;
import javafx.application.Platform;
import javafx.scene.control.TextField;
import javafx.util.Duration;

public class GeneratePuzzle {
    SudokuBoard obj;
    int[][] board;

    public GeneratePuzzle(SudokuBoard game) {
        obj = game;
        board = game.getBoard();
    }

    public boolean validNum( int val, int row, int col) {

		for (int m=0; m < 9; m++) {
			if ( val == board[row][m] )
				return false;
		}

		for (int n=0; n < 9; n++) {
			if ( val == board[n][col] )
				return false;
		}

		int box_x = 3 * (row / 3);
		int box_y = 3 * (col / 3);

		for ( int p = box_x; p < box_x + 3; p++) {
			for ( int q = box_y; q < box_y + 3; q++) {
				if ( board[p][q] == val ) {
					return false;
				}
			}
		}

		return true;
	}

	public void updateUI(TextField[][] boardUI, int x, int y, String val){	
		Platform.runLater(new Runnable() {
			@Override public void run() {
				PauseTransition pause = new PauseTransition(Duration.millis(30));
				pause.setOnFinished(event -> boardUI[x][y].setText(val));
				pause.play();
			}
		});
    }

	public boolean solve(Random randomVal, TextField[][] boardUI, boolean changeUI) {
		int[] pos = obj.findEmpty();
		if (pos.length == 0)
			return true;

		if (board[pos[0]][pos[1]] == 0) {
				for (int i=1; i<10; i++) {
					int val = randomVal.nextInt(9) + 1;

					if ( validNum( val, pos[0], pos[1]) ) {
						board[pos[0]][pos[1]] = val;
						if (changeUI == true)
							updateUI(boardUI, pos[0], pos[1], String.valueOf(val));
						if ( solve(randomVal, boardUI, changeUI) )
							return true;

					}
					board[pos[0]][pos[1]] = 0;
					//if (changeUI == true)
						//updateUI(boardUI, pos[0], pos[1], "");
				}
		}

		return false;
				
	}

	public void hideSolution() {
        Random randomChance = new Random();

		for (int i=0; i<9; i++) {
			for (int j=0; j<9; j++) {
				int hide = randomChance.nextInt(3) + 1;
				if (hide == 1)
					continue;
                board[i][j] = 0;
			}
		}
    }
}
