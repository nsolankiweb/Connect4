package application;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Group;
import javafx.scene.control.Button;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import java.util.HashMap; 
import java.util.Map;
import java.util.Random;

import javafx.scene.text.Text;
public class SampleController {
	
	@FXML public Button reset;
	
	@FXML public Group GameBoard; 
	@FXML public Group resultBox; 
	@FXML public Group startMenu; 
	
	@FXML public Text resultStatement; 
	@FXML public Text playerScore; 
	@FXML public Text computerScore;
	
	@FXML public Button easy; 
	@FXML public Button hard; 
	
	@FXML public Button area01; 
	@FXML public Button area02; 
    @FXML public Button area03; 
    @FXML public Button area04;
    @FXML public Button area05; 
    
    @FXML public Button area10; 
    @FXML public Button area11; 
    @FXML public Button area12; 
    @FXML public Button area13;
    @FXML public Button area14; 
    @FXML public Button area15;
    
    @FXML public Button area20; 
    @FXML public Button area21; 
    @FXML public Button area22;
    @FXML public Button area23; 
    @FXML public Button area24; 
    @FXML public Button area25;
    
    @FXML public Button area30; 
    @FXML public Button area31; 
    @FXML public Button area32; 
    @FXML public Button area33; 
    @FXML public Button area34; 
    @FXML public Button area35; 
    
    @FXML public Button area40; 
    @FXML public Button area41; 
    @FXML public Button area42;
    @FXML public Button area43; 
    @FXML public Button area44; 
    @FXML public Button area45; 
    
    @FXML public Button area50; 
    @FXML public Button area51; 
    @FXML public Button area52; 
    @FXML public Button area53; 
    @FXML public Button area54; 
    @FXML public Button area55; 
    
    @FXML public Circle slot00; 
	@FXML public Circle slot01; 
	@FXML public Circle slot02; 
    @FXML public Circle slot03; 
    @FXML public Circle slot04;
    @FXML public Circle slot05; 
    
    @FXML public Circle slot10; 
    @FXML public Circle slot11; 
    @FXML public Circle slot12; 
    @FXML public Circle slot13;
    @FXML public Circle slot14; 
    @FXML public Circle slot15;
    
    @FXML public Circle slot20; 
    @FXML public Circle slot21; 
    @FXML public Circle slot22;
    @FXML public Circle slot23; 
    @FXML public Circle slot24; 
    @FXML public Circle slot25;
    
    @FXML public Circle slot30; 
    @FXML public Circle slot31; 
    @FXML public Circle slot32; 
    @FXML public Circle slot33; 
    @FXML public Circle slot34; 
    @FXML public Circle slot35; 
    
    @FXML public Circle slot40; 
    @FXML public Circle slot41; 
    @FXML public Circle slot42;
    @FXML public Circle slot43; 
    @FXML public Circle slot44; 
    @FXML public Circle slot45; 
    
    @FXML public Circle slot50; 
    @FXML public Circle slot51; 
    @FXML public Circle slot52; 
    @FXML public Circle slot53; 
    @FXML public Circle slot54; 
    @FXML public Circle slot55;
    
    boolean initialized = false; 
    
    public boolean diff = true; 

    
    int numRows = 6; 
    int numCols = 6; 
    
    public Map<String, Circle> moves = new HashMap<>();
    public Map<String, Integer> shadowData = new HashMap<>();
    
    public String lastPiece = ""; 
    public String lastCPiece = ""; 
    
    Random rand = new Random(); 
    
    int player_score = 0; 
    int comp_score = 0; 
   
    //#6dc9b1 - player color
    //#e9717d - computer color 
    
    @FXML
	public void menu(ActionEvent actionEvent) {
		Button b = (Button) actionEvent.getSource();
		if(b == easy) {
			diff = false; 
			
		}else {
			diff = true; 
		}
		
		startMenu.setDisable(true); 
		startMenu.setVisible(false);
		enable();
	}
	
	@FXML 
	public void player_controller(ActionEvent actionEvent) {
		if(!initialized) {
			initialSetup(); 
			initialized = true; 
		}
		
		int x = player_turn(actionEvent); 
		if(x == 0) {
			computer_turn(); 
		}
		
		
		
	}
	
	@FXML
	public int player_turn(ActionEvent actionEvent) {
		Button b = (Button) actionEvent.getSource();
		String tc = b.getText(); 
		tc = tc.substring(0, tc.length() - 1);  
		int row = emptyRow(tc);
		String k = tc + String.valueOf(row);
		if (row != -1) {
			Circle c = moves.get(k); 
			c.setFill(Color.valueOf("#6dc9b1"));
			shadowData.put(k, 1);
			lastPiece = k; 
		}
		int cw = check_win(); 
		if(cw == 1) {
			player_score++; 
			setWin("player");
			return -1; 
		}
		
		return 0; 
	}
	
	@FXML 
	public void computer_turn() {
		String lp = lastPiece; 
		
		if(diff) {
		//check for win an place move
		for(int i = 0; i < numCols; i++) {
			int row = emptyRow(String.valueOf(i));
			if(row != -1) {
				lastPiece = String.valueOf(i) + String.valueOf(row); 
				shadowData.put(lastPiece, 2);
				int w = check_win(); 
				
				//if win
				if(w == 2) {
					Circle c = moves.get(lastPiece); 
					c.setFill(Color.valueOf("#e9717d"));
					shadowData.put(lastPiece, 2);
					comp_score++; 
					setWin("comp"); 
					lastCPiece = lastPiece; 
					return; 
				} else {
					shadowData.put(lastPiece, 0);
				}
				
			}
		}
			
		
		//check for block
		for(int i = 0; i < numCols; i++) {
			int row = emptyRow(String.valueOf(i)); 
			if(row != -1) {
				lastPiece = String.valueOf(i) + String.valueOf(row); 
				shadowData.put(lastPiece, 1);
				int w = check_win(); 
				
				if(w == 1) {
					Circle c = moves.get(lastPiece); 
					c.setFill(Color.valueOf("#e9717d"));
					shadowData.put(lastPiece, 2);
					lastCPiece = lastPiece; 
					return;
				} else {
					shadowData.put(lastPiece, 0);
				}
				 
			}
		}
		
		
		//place next chip close to last chip
		
		
	
		
		if(lastCPiece != "") {
			String cl = lastCPiece.substring(0, lastCPiece.length() - 1); 

			String rw = lastCPiece.substring(1); 

			int col = Integer.parseInt(cl); 
		
			int row = Integer.parseInt(rw);
			
			if(row < 5) {
				int e = emptyRow(cl);  
				if(e == row + 1) {
					lastPiece = String.valueOf(col) + String.valueOf(row + 1); 
					Circle c = moves.get(lastPiece); 
					c.setFill(Color.valueOf("#e9717d"));
					shadowData.put(lastPiece, 2);
					lastCPiece = lastPiece; 
					int cw = check_win(); 
					if(cw == 2) {
						comp_score++; 
						setWin("comp");
					}
					return;
				}
				 
			} 
			
			if(col < 5) {
				int e = emptyRow(String.valueOf(col + 1)); 
				if((e + 1 == row || e - 1 == row) && e != -1) {
					
						lastPiece = String.valueOf(col + 1) + String.valueOf(e); 
						Circle c = moves.get(lastPiece); 
						c.setFill(Color.valueOf("#e9717d"));
						shadowData.put(lastPiece, 2);
						lastCPiece = lastPiece; 
						int cw = check_win(); 
						if(cw == 2) {
							comp_score++; 
							setWin("comp");
						
						}
					return; 
				} 
			}
			
			
			if(col > 0) {
				int e = emptyRow(String.valueOf(col - 1)); 
				if((e + 1 == row || e - 1 == row) && e != -1) {
						lastPiece = String.valueOf(col - 1) + String.valueOf(e); 
						Circle c = moves.get(lastPiece); 
						c.setFill(Color.valueOf("#e9717d"));
						shadowData.put(lastPiece, 2);
						lastCPiece = lastPiece; 
						int cw = check_win(); 
						if(cw == 2) {
							comp_score++; 
							setWin("comp");
						}
						return; 
				} 
			}	
		}
			
	
		}
		//random place
		
		for(int i = 0; i < numCols; i++) {
			int p = rand.nextInt(numCols);
			int e = emptyRow(String.valueOf(p)); 
			if(e != -1) {
				lastPiece = String.valueOf(p) + String.valueOf(e); 
				Circle c = moves.get(lastPiece); 
				c.setFill(Color.valueOf("#e9717d"));
				shadowData.put(lastPiece, 2);
				lastCPiece = lastPiece; 
				int cw = check_win(); 
				if(cw == 2) {
					comp_score++; 
					setWin("comp");
				}
				return; 
			}
		}
		
	}
	
	@FXML
	public int check_win() {
		// Check horizontally
		
		String tc = lastPiece.substring(0, lastPiece.length() - 1); 
		int col = Integer.parseInt(tc);
		String tr = lastPiece.substring(1); 
		int row = Integer.parseInt(tr);
		
		//check horizontal
	    for (int c = 0; c < 3; c++) {
	    	String k = String.valueOf(c) + tr; 
	    	String k1 = String.valueOf(c + 1) + tr;
	    	String k2 = String.valueOf(c + 2) + tr;
	    	String k3 = String.valueOf(c + 3) + tr;
	    	
	    	if(shadowData.get(k)== shadowData.get(k1) && shadowData.get(k1) == shadowData.get(k2) 
	    						&& shadowData.get(k2) == shadowData.get(k3) && shadowData.get(k) != 0) {
	    		return shadowData.get(k); 
	    	}
	    }

	    // Check vertically
	    for (int r = 0; r < 3; r++) {
	    	String k = tc + String.valueOf(r); 
	    	String k1 = tc + String.valueOf(r + 1); 
	    	String k2 = tc + String.valueOf(r + 2); 
	    	String k3 = tc + String.valueOf(r + 3); 
	    	
	    	if(shadowData.get(k)== shadowData.get(k1) && shadowData.get(k1) == shadowData.get(k2) 
	    						&& shadowData.get(k2) == shadowData.get(k3) && shadowData.get(k) != 0) {
	    		return shadowData.get(k); 
	    	}
	    }

	    // Check diagonally (down-left)
	    for (int r = 5; r > 2; r--) {
	        for (int c = 0; c < 3; c++) {
	        	String k = String.valueOf(c) + String.valueOf(r); 
		    	String k1 = String.valueOf(c + 1) + String.valueOf(r - 1);
		    	String k2 = String.valueOf(c + 2) + String.valueOf(r - 2);
		    	String k3 = String.valueOf(c + 3) + String.valueOf(r - 3);
		    	
		    	if(shadowData.get(k)== shadowData.get(k1) && shadowData.get(k1) == shadowData.get(k2) 
						&& shadowData.get(k2) == shadowData.get(k3) && shadowData.get(k) != 0) {
		    		return shadowData.get(k); 
		    	}
	        }
	    }
	    
	    for (int r = 4; r > 2; r--) {
	        for (int c = 0; c < 2; c++) {
	        	String k = String.valueOf(c) + String.valueOf(r); 
		    	String k1 = String.valueOf(c + 1) + String.valueOf(r - 1);
		    	String k2 = String.valueOf(c + 2) + String.valueOf(r - 2);
		    	String k3 = String.valueOf(c + 3) + String.valueOf(r - 3);
		    	
		    	if(shadowData.get(k)== shadowData.get(k1) && shadowData.get(k1) == shadowData.get(k2) 
						&& shadowData.get(k2) == shadowData.get(k3) && shadowData.get(k) != 0) {
		    		return shadowData.get(k); 
		    	}
	        }
	    }
	    
	    for (int r = 5; r > 3; r--) {
	        for (int c = 1; c < 3; c++) {
	        	String k = String.valueOf(c) + String.valueOf(r); 
		    	String k1 = String.valueOf(c + 1) + String.valueOf(r - 1);
		    	String k2 = String.valueOf(c + 2) + String.valueOf(r - 2);
		    	String k3 = String.valueOf(c + 3) + String.valueOf(r - 3);
		    	
		    	if(shadowData.get(k)== shadowData.get(k1) && shadowData.get(k1) == shadowData.get(k2) 
						&& shadowData.get(k2) == shadowData.get(k3) && shadowData.get(k) != 0) {
		    		return shadowData.get(k); 
		    	}
	        }
	    }
	    
	    if(shadowData.get("03")== shadowData.get("12") && shadowData.get("12") == shadowData.get("21") 
				&& shadowData.get("21") == shadowData.get("30") && shadowData.get("03") != 0) {
    		return shadowData.get("03"); 
    	}
	    
	    if(shadowData.get("25")== shadowData.get("34") && shadowData.get("34") == shadowData.get("43") 
				&& shadowData.get("43") == shadowData.get("52") && shadowData.get("25") != 0) {
    		return shadowData.get("25"); 
    	}
	    
	    
	 // Check diagonally (up-left)
	    for (int r = 0; r < 3; r++) {
	        for (int c = 0; c < 3; c++) {
	        	String k = String.valueOf(c) + String.valueOf(r); 
		    	String k1 = String.valueOf(c + 1) + String.valueOf(r + 1);
		    	String k2 = String.valueOf(c + 2) + String.valueOf(r + 2);
		    	String k3 = String.valueOf(c + 3) + String.valueOf(r + 3);
		    	
		    	if(shadowData.get(k)== shadowData.get(k1) && shadowData.get(k1) == shadowData.get(k2) 
						&& shadowData.get(k2) == shadowData.get(k3) && shadowData.get(k) != 0) {
		    		return shadowData.get(k); 
		    	}
	        }
	    }
	    
	    for (int r = 0; r < 2; r++) {
	        for (int c = 1; c < 3; c++) {
	        	String k = String.valueOf(c) + String.valueOf(r); 
		    	String k1 = String.valueOf(c + 1) + String.valueOf(r + 1);
		    	String k2 = String.valueOf(c + 2) + String.valueOf(r + 2);
		    	String k3 = String.valueOf(c + 3) + String.valueOf(r + 3);
		    	
		    	if(shadowData.get(k)== shadowData.get(k1) && shadowData.get(k1) == shadowData.get(k2) 
						&& shadowData.get(k2) == shadowData.get(k3) && shadowData.get(k) != 0) {
		    		return shadowData.get(k); 
		    	}
	        }
	    }
	    
	    for (int r = 1; r < 3; r++) {
	        for (int c = 0; c < 2; c++) {
	        	String k = String.valueOf(c) + String.valueOf(r); 
		    	String k1 = String.valueOf(c + 1) + String.valueOf(r + 1);
		    	String k2 = String.valueOf(c + 2) + String.valueOf(r + 2);
		    	String k3 = String.valueOf(c + 3) + String.valueOf(r + 3);
		    	
		    	if(shadowData.get(k)== shadowData.get(k1) && shadowData.get(k1) == shadowData.get(k2) 
						&& shadowData.get(k2) == shadowData.get(k3) && shadowData.get(k) != 0) {
		    		return shadowData.get(k); 
		    	}
	        }
	    }
	    
	    if(shadowData.get("02")== shadowData.get("13") && shadowData.get("13") == shadowData.get("24") 
				&& shadowData.get("24") == shadowData.get("35") && shadowData.get("02") != 0) {
    		return shadowData.get("02"); 
    	}
	    
	    if(shadowData.get("20")== shadowData.get("31") && shadowData.get("31") == shadowData.get("42") 
				&& shadowData.get("42") == shadowData.get("53") && shadowData.get("20") != 0) {
    		return shadowData.get("20"); 
    	}
	    
	    return 0;  
	}
	
	@FXML
	public void setWin(String s) {
		disable(); 
		if(s == "player") {
			resultStatement.setText("Yay! You Win");
			resultStatement.setFill(Color.valueOf("#6dc9b1"));
			playerScore.setText(String.valueOf(player_score));
			resultBox.setVisible(true);
		} else {
			resultStatement.setText("No! You Lose");
			resultStatement.setFill(Color.valueOf("#e9717d"));
			computerScore.setText(String.valueOf(comp_score));
			resultBox.setVisible(true);
		}
	}
	
	public void reset() {
		resultBox.setVisible(false);
		easy.setDisable(false);
		hard.setDisable(false);
		startMenu.setDisable(false); 
		startMenu.setVisible(true);
		
		for(int i = 0; i < numCols; i++) {
			for(int j = 0; j < numRows; j++) {
				String c = String.valueOf(i) + String.valueOf(j);
				Circle l = moves.get(c);
				l.setFill(Color.valueOf("#fffafc"));
				shadowData.put(c, 0);
			}
		}
	}
	
	@FXML
	public int emptyRow(String col) {
		for(int row = 0; row < numRows; row++) {
			String c = col + String.valueOf(row); 
			if(shadowData.get(c) == 0) {
				return row; 
			}
		}
		
		return -1; 
	}
	
	@FXML
	public void disable() {
		 area01.setDisable(true); 
		 area02.setDisable(true); 
	     area03.setDisable(true); 
	     area04.setDisable(true);
	     area05.setDisable(true); 
	    
	     area10.setDisable(true); 
	     area11.setDisable(true); 
	     area12.setDisable(true); 
	     area13.setDisable(true);
	     area14.setDisable(true); 
	     area15.setDisable(true);
	    
	     area20.setDisable(true); 
	     area21.setDisable(true); 
	     area22.setDisable(true);
	     area23.setDisable(true); 
	     area24.setDisable(true); 
	     area25.setDisable(true);
	    
	     area30.setDisable(true); 
	     area31.setDisable(true); 
	     area32.setDisable(true); 
	     area33.setDisable(true); 
	     area34.setDisable(true); 
	     area35.setDisable(true); 
	    
	     area40.setDisable(true); 
	     area41.setDisable(true); 
	     area42.setDisable(true);
	     area43.setDisable(true); 
	     area44.setDisable(true); 
	     area45.setDisable(true);
	     
	      area50.setDisable(true); 
	      area51.setDisable(true); 
	      area52.setDisable(true); 
	      area53.setDisable(true); 
	      area54.setDisable(true); 
	      area55.setDisable(true); 
	}

	
	@FXML
	public void enable() {
		 area01.setDisable(false); 
		 area02.setDisable(false); 
	     area03.setDisable(false); 
	     area04.setDisable(false);
	     area05.setDisable(false); 
	    
	     area10.setDisable(false); 
	     area11.setDisable(false); 
	     area12.setDisable(false); 
	     area13.setDisable(false);
	     area14.setDisable(false); 
	     area15.setDisable(false);
	    
	     area20.setDisable(false); 
	     area21.setDisable(false); 
	     area22.setDisable(false);
	     area23.setDisable(false); 
	     area24.setDisable(false); 
	     area25.setDisable(false);
	    
	     area30.setDisable(false); 
	     area31.setDisable(false); 
	     area32.setDisable(false); 
	     area33.setDisable(false); 
	     area34.setDisable(false); 
	     area35.setDisable(false); 
	    
	     area40.setDisable(false); 
	     area41.setDisable(false); 
	     area42.setDisable(false);
	     area43.setDisable(false); 
	     area44.setDisable(false); 
	     area45.setDisable(false);
	     
	      area50.setDisable(false); 
	      area51.setDisable(false); 
	      area52.setDisable(false); 
	      area53.setDisable(false); 
	      area54.setDisable(false); 
	      area55.setDisable(false); 
	}
	
	
	
	public void initialSetup() {
 
		moves.put("00", slot00); 
		moves.put("01", slot01); 
		moves.put("02", slot02); 
		moves.put("03", slot03);
		moves.put("04", slot04); 
		moves.put("05", slot05);
		
		moves.put("10", slot10); 
		moves.put("11", slot11); 
		moves.put("12", slot12); 
		moves.put("13", slot13);
		moves.put("14", slot14); 
		moves.put("15", slot15);
		
		moves.put("20", slot20); 
		moves.put("21", slot21); 
		moves.put("22", slot22); 
		moves.put("23", slot23);
		moves.put("24", slot24); 
		moves.put("25", slot25);
		
		moves.put("30", slot30); 
		moves.put("31", slot31); 
		moves.put("32", slot32); 
		moves.put("33", slot33);
		moves.put("34", slot34); 
		moves.put("35", slot35);
		
		moves.put("40", slot40); 
		moves.put("41", slot41); 
		moves.put("42", slot42); 
		moves.put("43", slot43);
		moves.put("44", slot44); 
		moves.put("45", slot45);
		
		moves.put("50", slot50); 
		moves.put("51", slot51); 
		moves.put("52", slot52); 
		moves.put("53", slot53);
		moves.put("54", slot54); 
		moves.put("55", slot55);
		
		shadowData.put("00", 0); 
		shadowData.put("01", 0); 
		shadowData.put("02", 0); 
		shadowData.put("03", 0);
		shadowData.put("04", 0); 
		shadowData.put("05", 0);
		
		shadowData.put("10", 0); 
		shadowData.put("11", 0); 
		shadowData.put("12", 0); 
		shadowData.put("13", 0);
		shadowData.put("14", 0); 
		shadowData.put("15", 0);
		
		shadowData.put("20", 0); 
		shadowData.put("21", 0); 
		shadowData.put("22", 0); 
		shadowData.put("23", 0);
		shadowData.put("24", 0); 
		shadowData.put("25", 0);
		
		shadowData.put("30", 0); 
		shadowData.put("31", 0); 
		shadowData.put("32", 0); 
		shadowData.put("33", 0);
		shadowData.put("34", 0); 
		shadowData.put("35", 0);
		
		shadowData.put("40", 0); 
		shadowData.put("41", 0); 
		shadowData.put("42", 0); 
		shadowData.put("43", 0);
		shadowData.put("44", 0); 
		shadowData.put("45", 0);
		
		shadowData.put("50", 0); 
		shadowData.put("51", 0); 
		shadowData.put("52", 0); 
		shadowData.put("53", 0);
		shadowData.put("54", 0); 
		shadowData.put("55", 0);
		
		
	}
}
