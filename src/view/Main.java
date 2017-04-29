package view;

import java.io.InputStream;
import java.util.Arrays;
import java.util.List;

import javafx.animation.FadeTransition;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.PathTransition;
import javafx.animation.PauseTransition;
import javafx.animation.ScaleTransition;
import javafx.animation.Timeline;
import javafx.animation.TranslateTransition;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.stage.Stage;
import javafx.util.Duration;
import javafx.util.Pair;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.effect.DropShadow;
import javafx.scene.effect.InnerShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.QuadCurveTo;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;


public class Main extends Application {
	
	MediaPlayer mediaPlayer;
	
	Button newGameButton;
	Button continueButton;
	Button optionsButton;
	Button controlsButton;
	Button creditsButton;
	Button exitButton;
	
	Stage theStage;
	
	int[] selectionList = new int[7];
	int globalCounter = 0;
	
	
	private final String buttonStyle =  "-fx-background-color: linear-gradient(#686868 0%, #232723 25%, #373837 75%, #757575 100%), "
			+ "linear-gradient(#020b02, #3a3a3a), " 
			+ "linear-gradient(#9d9e9d 0%, #6b6a6b 20%, #343534 80%, #242424 100%), " 
			+ "linear-gradient(#8a8a8a 0%, #6b6a6b 20%, #343534 80%, #262626 100%), " 
			+ "linear-gradient(#777777 0%, #606060 50%, #505250 51%, #2a2b2a 100%);"
			+ " -fx-background-insets: 0,1,4,5,6;" 
			+ " -fx-background-radius: 9,8,5,4,3; "
			+ " -fx-padding: 15 30 15 30; "
			+ " -fx-font-family: \"PKMN RBYGSC\"; "
			+ " -fx-font-size: 8px;"
			+ " -fx-font-weight: bold;"
			+ " -fx-text-fill: white;"
			+ " -fx-effect: dropshadow( three-pass-box , rgba(255,255,255,0.2) , 1, 0.0 , 0 , 1);"
			+ " -fx-effect: dropshadow( one-pass-box , black , 0, 0.0 , 0 , -1 );";
	
	private static final int WIDTH = 1280;
    private static final int HEIGHT = 720;
    
	@Override
	public void start(Stage primaryStage) {
		try {
			theStage = primaryStage;
	    	
	        Scene scene = new Scene(createContent());
	        primaryStage.setTitle("Pokemon Battle Sim Menu");
	        primaryStage.setScene(scene);
	        primaryStage.show();

		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	 private List<Pair<String, Button>> menuData = Arrays.asList(
	            new Pair<String, Button>("New Game", newGameButton),
	            new Pair<String, Button>("Continue", continueButton),
	            new Pair<String, Button>("Game Options", optionsButton),
	            new Pair<String, Button>("Controls", controlsButton),
	            new Pair<String, Button>("Credits", creditsButton),
	            new Pair<String, Button>("Exit", exitButton)
	    );

	    private Pane root = new Pane();
	    private VBox menuBox = new VBox(-5);
	    private Line line;

	    private Parent createContent() {
	        addBackground();
	        addTitle();

	        double lineX = WIDTH / 2 - 100;
	        double lineY = HEIGHT / 3 + 50;

	        addLine(lineX, lineY);
	        addMenu(lineX + 5, lineY + 5);

	        startAnimation();

	        return root;
	    }
	    
	    public int[] getSelectionList(){
	    	return selectionList;
	    }

	    private void addBackground() {
	    	Image image = new Image(getClass().getResourceAsStream("/res/gengar.jpg"));
	        ImageView imageView = new ImageView(image);
	        imageView.setFitWidth(WIDTH);
	        imageView.setFitHeight(HEIGHT);

	        root.getChildren().add(imageView);
	    }

	    private void addTitle() {
	        PokemonTitle title = new PokemonTitle("POKEMON");
	        title.setTranslateX(WIDTH / 2 - title.getTitleWidth() / 2);
	        title.setTranslateY(HEIGHT / 3);

	        root.getChildren().add(title);
	    }

	    private void addLine(double x, double y) {
	        line = new Line(x, y, x, y + 300);
	        line.setStrokeWidth(3);
	        line.setStroke(Color.color(1, 1, 1, 0.75));
	        line.setEffect(new DropShadow(5, Color.BLACK));
	        line.setScaleY(0);

	        root.getChildren().add(line);
	    }

	    private void startAnimation() {
	        ScaleTransition st = new ScaleTransition(Duration.seconds(1), line);
	        st.setToY(1);
	        st.setOnFinished(e -> {

	            for (int i = 0; i < menuBox.getChildren().size(); i++) {
	                Node n = menuBox.getChildren().get(i);

	                TranslateTransition tt = new TranslateTransition(Duration.seconds(1 + i * 0.15), n);
	                tt.setToX(0);
	                tt.setOnFinished(e2 -> n.setClip(null));
	                tt.play();
	            }
	        });
	        st.play();
	    }

	    private void addMenu(double x, double y) {
	        menuBox.setTranslateX(x);
	        menuBox.setTranslateY(y);
	        
	        Rectangle clip = new Rectangle(300, 30);
	        Rectangle clip2 = new Rectangle(300, 30);
	        Rectangle clip3 = new Rectangle(300, 30);
	        Rectangle clip4 = new Rectangle(300, 30);
	        Rectangle clip5 = new Rectangle(300, 30);
	        Rectangle clip6 = new Rectangle(300, 30);
	      
	        PokemonMenuItem item1 = new PokemonMenuItem(menuData.get(0).getKey());
	        item1.setTranslateX(-300);
	        item1.setId(menuData.get(0).getKey());
	        item1.setOnMouseClicked(new EventHandler<MouseEvent>() {
				 
	            public void handle(MouseEvent event) {
	            	intro(theStage);
	            }
	            	
	           	});
	        clip.translateXProperty().bind(item1.translateXProperty().negate());
	        item1.setClip(clip);

	        PokemonMenuItem item2 = new PokemonMenuItem(menuData.get(1).getKey());
	        item2.setTranslateX(-300);
	        item2.setId(menuData.get(1).getKey());
	        item2.setOnMouseClicked(new EventHandler<MouseEvent>() {
					 
		            public void handle(MouseEvent event) {
		            	
		            	selectScreen(theStage);
		            }
		            	
		           	});
	        clip2.translateXProperty().bind(item2.translateXProperty().negate());
	        item2.setClip(clip2);
	        
	        PokemonMenuItem item3 = new PokemonMenuItem(menuData.get(2).getKey());
	        item3.setTranslateX(-300);
	        item3.setId(menuData.get(2).getKey());
	        item3.setOnMouseClicked(new EventHandler<MouseEvent>() {
				 
	            public void handle(MouseEvent event) {
	            	Platform.exit();
	            }
	            	
	           	});
	        clip3.translateXProperty().bind(item3.translateXProperty().negate());
	        item3.setClip(clip3);
	        
	        PokemonMenuItem item4 = new PokemonMenuItem(menuData.get(3).getKey());
	        item4.setTranslateX(-300);
	        item4.setId(menuData.get(3).getKey());
	        item4.setOnMouseClicked(new EventHandler<MouseEvent>() {
				 
	            public void handle(MouseEvent event) {
	            	Platform.exit();
	            }
	            	
	           	});
	        clip4.translateXProperty().bind(item4.translateXProperty().negate());
	        item4.setClip(clip4);
	        
	        PokemonMenuItem item5 = new PokemonMenuItem(menuData.get(4).getKey());
	        item5.setTranslateX(-300);
	        item5.setId(menuData.get(4).getKey());
	        item5.setOnMouseClicked(new EventHandler<MouseEvent>() {
				 
	            public void handle(MouseEvent event) {
	            	Platform.exit();
	            }
	            	
	           	});
	        clip5.translateXProperty().bind(item5.translateXProperty().negate());
	        item5.setClip(clip5);
	        
	        PokemonMenuItem item6 = new PokemonMenuItem(menuData.get(5).getKey());
	        item6.setTranslateX(-300);
	        item6.setId(menuData.get(5).getKey());
	        item6.setOnMouseClicked(new EventHandler<MouseEvent>() {
				 
	            public void handle(MouseEvent event) {
	            	Platform.exit();
	            }
	            	
	           	});
	        clip6.translateXProperty().bind(item6.translateXProperty().negate());
	        item6.setClip(clip6);
	        
	        menuBox.getChildren().addAll(item1, item2, item3, item4, item5, item6);
	        
	        root.getChildren().add(menuBox);
	    }
	    
	    public void selectScreen(Stage theStage){
	    	/*
        	 * 1 = Venasaur
        	 * 2 = Meganium
        	 * 3 = Sceptile
        	 * 4 = Torterra
        	 * 5 = Charizard
        	 * 6 = Typhlosion
        	 * 7 = Blaziken
        	 * 8 = Infernape
        	 * 9 = Blastoise
        	 * 10 = Feraligatr
        	 * 11 = Swampert
        	 * 12 = Empoleon*/
        	String[] buttonIds = {"1", "2", "3", "4",
					"5", "6", "7", "8",
					"9", "10", "11", "12"};
        	
        	
			InnerShadow shadow = new InnerShadow();

			Button pokemon1 = new Button();
			pokemon1.setShape(new Polygon(new Hexagon(100d).getPoints()));
			pokemon1.setMinSize(100, 90);
			pokemon1.setId(buttonIds[0]);
			pokemon1.setOnMouseClicked(new EventHandler<MouseEvent>() {
				 
	            public void handle(MouseEvent event) {
	            	pokemon1.setEffect(shadow);
	            	selectionList[globalCounter] = Integer.parseInt(pokemon1.getId());
	            	globalCounter++;
	            }
	            	
	           	});

			Button pokemon2 = new Button();
			pokemon2.setShape(new Polygon(new Hexagon(100d).getPoints()));
			pokemon2.setMinSize(100, 90);
			pokemon2.setId(buttonIds[2]);
			pokemon2.setOnMouseClicked(new EventHandler<MouseEvent>() {
				 
	            public void handle(MouseEvent event) {
	            	pokemon2.setEffect(shadow);
	            	selectionList[globalCounter] = Integer.parseInt(pokemon2.getId());
	            	globalCounter++;
	            }
	            	
	           	});
			
			Button pokemon3 = new Button();
			pokemon3.setShape(new Polygon(new Hexagon(100d).getPoints()));
			pokemon3.setMinSize(100, 90);
			pokemon3.setId(buttonIds[4]);
			pokemon3.setOnMouseClicked(new EventHandler<MouseEvent>() {
				 
	            public void handle(MouseEvent event) {
	            	pokemon3.setEffect(shadow);
	            	selectionList[globalCounter] = Integer.parseInt(pokemon3.getId());
	            	globalCounter++;
	            }
	            	
	           	});
			
			Button pokemon4 = new Button();
			pokemon4.setShape(new Polygon(new Hexagon(100d).getPoints()));
			pokemon4.setMinSize(100, 90);
			pokemon4.setId(buttonIds[6]);
			pokemon4.setOnMouseClicked(new EventHandler<MouseEvent>() {
				 
	            public void handle(MouseEvent event) {
	            	pokemon4.setEffect(shadow);
	            	selectionList[globalCounter] = Integer.parseInt(pokemon4.getId());
	            	globalCounter++;
	            }
	            	
	           	});

			Button pokemon5 = new Button();
			pokemon5.setShape(new Polygon(new Hexagon(100d).getPoints()));
			pokemon5.setMinSize(100, 90);
			pokemon5.setId(buttonIds[8]);
			pokemon5.setOnMouseClicked(new EventHandler<MouseEvent>() {
				 
	            public void handle(MouseEvent event) {
	            	pokemon5.setEffect(shadow);
	            	selectionList[globalCounter] = Integer.parseInt(pokemon5.getId());
	            	globalCounter++;
	            }
	            	
	           	});
			
			Button pokemon6 = new Button();
			pokemon6.setShape(new Polygon(new Hexagon(100d).getPoints()));
			pokemon6.setMinSize(100, 90);
			pokemon6.setId(buttonIds[10]);
			pokemon6.setOnMouseClicked(new EventHandler<MouseEvent>() {
				 
	            public void handle(MouseEvent event) {
	            	pokemon6.setEffect(shadow);
	            	selectionList[globalCounter] = Integer.parseInt(pokemon6.getId());
	            	globalCounter++;
	            }
	            	
	           	});
			
			Button pokemon7 = new Button();
			pokemon7.setShape(new Polygon(new Hexagon(100d).getPoints()));
			pokemon7.setMinSize(100, 90);
			pokemon7.setId(buttonIds[1]);
			pokemon7.setOnMouseClicked(new EventHandler<MouseEvent>() {
				 
	            public void handle(MouseEvent event) {
	            	pokemon7.setEffect(shadow);
	            	selectionList[globalCounter] = Integer.parseInt(pokemon7.getId());
	            	globalCounter++;
	            }
	            	
	           	});
			
			Button pokemon8 = new Button();
			pokemon8.setShape(new Polygon(new Hexagon(100d).getPoints()));
			pokemon8.setMinSize(100, 90);
			pokemon8.setId(buttonIds[3]);
			pokemon8.setOnMouseClicked(new EventHandler<MouseEvent>() {
				 
	            public void handle(MouseEvent event) {
	            	pokemon8.setEffect(shadow);
	            	selectionList[globalCounter] = Integer.parseInt(pokemon8.getId());
	            	globalCounter++;
	            }
	            	
	           	});
			
			Button pokemon9 = new Button();
			pokemon9.setShape(new Polygon(new Hexagon(100d).getPoints()));
			pokemon9.setMinSize(100, 90);
			pokemon9.setId(buttonIds[5]);
			pokemon9.setOnMouseClicked(new EventHandler<MouseEvent>() {
				 
	            public void handle(MouseEvent event) {
	            	pokemon9.setEffect(shadow);
	            	selectionList[globalCounter] = Integer.parseInt(pokemon9.getId());
	            	globalCounter++;
	            }
	            	
	           	});

			Button pokemon10 = new Button();
			pokemon10.setShape(new Polygon(new Hexagon(100d).getPoints()));
			pokemon10.setMinSize(100, 90);
			pokemon10.setId(buttonIds[7]);
			pokemon10.setOnMouseClicked(new EventHandler<MouseEvent>() {
				 
	            public void handle(MouseEvent event) {
	            	pokemon10.setEffect(shadow);
	            	selectionList[globalCounter] = Integer.parseInt(pokemon10.getId());
	            	globalCounter++;
	            }
	            	
	           	});
			
			Button pokemon11 = new Button();
			pokemon11.setShape(new Polygon(new Hexagon(100d).getPoints()));
			pokemon11.setMinSize(100, 90);
			pokemon11.setId(buttonIds[9]);
			pokemon11.setOnMouseClicked(new EventHandler<MouseEvent>() {
				 
	            public void handle(MouseEvent event) {
	            	pokemon11.setEffect(shadow);
	            	selectionList[globalCounter] = Integer.parseInt(pokemon11.getId());
	            	globalCounter++;
	            }
	            	
	           	});
			
			Button pokemon12 = new Button();
			pokemon12.setShape(new Polygon(new Hexagon(100d).getPoints()));
			pokemon12.setMinSize(100, 90);
			pokemon12.setId(buttonIds[11]);
			pokemon12.setOnMouseClicked(new EventHandler<MouseEvent>() {
				 
	            public void handle(MouseEvent event) {
	            	pokemon12.setEffect(shadow);
	            	selectionList[globalCounter] = Integer.parseInt(pokemon12.getId());
	            	globalCounter++;
	            }
	            	
	           	});
			
			Button battle = new Button("BATTLE!");
			battle.setMinSize(500, 180);
			battle.setOnMouseClicked(new EventHandler<MouseEvent>() {
				 
	            public void handle(MouseEvent event) {
	            	System.out.println(selectionList[0]);
	            	System.out.println(selectionList[1]);
	            	System.out.println(selectionList[2]);
	            	System.out.println(selectionList[3]);
	            	System.out.println(selectionList[4]);
	            	System.out.println(selectionList[5]);
	            }
	            	
	           	});
			
			Button clear = new Button("CLEAR");
			clear.setShape(new Polygon(new Hexagon(100d).getPoints()));
			clear.setMinSize(100, 90);
			clear.setOnMouseClicked(new EventHandler<MouseEvent>() {
				 
	            public void handle(MouseEvent event) {
	            	for(int i = 0; i < selectionList.length; i++){
	            		selectionList[i] = 0;
	            	}
	            	globalCounter = 0;
	            	
	            	pokemon1.setEffect(null);
	            	pokemon2.setEffect(null);
	            	pokemon3.setEffect(null);
	            	pokemon4.setEffect(null);
	            	pokemon5.setEffect(null);
	            	pokemon6.setEffect(null);
	            	pokemon7.setEffect(null);
	            	pokemon8.setEffect(null);
	            	pokemon9.setEffect(null);
	            	pokemon10.setEffect(null);
	            	pokemon11.setEffect(null);
	            	pokemon12.setEffect(null);
	            }
	            	
	           	});

			Media videoFile = new Media("file:///C:/Users/Ramon/workspace/SelectScreen/src/res/selectscreen.mp3");
			mediaPlayer = new MediaPlayer(videoFile);
			//mediaPlayer.setAutoPlay(true);
			mediaPlayer.setVolume(0.1);
			mediaPlayer.setOnEndOfMedia(new Runnable() {
			       public void run() {
			         mediaPlayer.seek(Duration.ZERO);
			       }
			   });
			  mediaPlayer.play();
			
			Image pokemonImage1 = new Image(getClass().getResourceAsStream("/res/venasaur.png"));
			ImageView imageView1 = new ImageView(pokemonImage1);
			pokemon1.setGraphic(imageView1);
			imageView1.setFitHeight(90);
			imageView1.setFitWidth(100);
			
			Image pokemonImage2 = new Image(getClass().getResourceAsStream("/res/sceptile.png"));
			ImageView imageView2 = new ImageView(pokemonImage2);
			pokemon2.setGraphic(imageView2);
			imageView2.setFitHeight(90);
			imageView2.setFitWidth(100);

			Image pokemonImage3 = new Image(getClass().getResourceAsStream("/res/charizard.png"));
			ImageView imageView3 = new ImageView(pokemonImage3);
			pokemon3.setGraphic(imageView3);
			imageView3.setFitHeight(90);
			imageView3.setFitWidth(100);
			
			Image pokemonImage4 = new Image(getClass().getResourceAsStream("/res/blaziken.png"));
			ImageView imageView4 = new ImageView(pokemonImage4);
			pokemon4.setGraphic(imageView4);
			imageView4.setFitHeight(90);
			imageView4.setFitWidth(100);
			
			Image pokemonImage5 = new Image(getClass().getResourceAsStream("/res/blastoise.png"));
			ImageView imageView5 = new ImageView(pokemonImage5);
			pokemon5.setGraphic(imageView5);
			imageView5.setFitHeight(90);
			imageView5.setFitWidth(100);
			
			Image pokemonImage6 = new Image(getClass().getResourceAsStream("/res/swampert.png"));
			ImageView imageView6 = new ImageView(pokemonImage6);
			pokemon6.setGraphic(imageView6);
			imageView6.setFitHeight(90);
			imageView6.setFitWidth(100);

			Image pokemonImage7 = new Image(getClass().getResourceAsStream("/res/meganium.png"));
			ImageView imageView7 = new ImageView(pokemonImage7);
			pokemon7.setGraphic(imageView7);
			imageView7.setFitHeight(90);
			imageView7.setFitWidth(100);
			
			Image pokemonImage8 = new Image(getClass().getResourceAsStream("/res/torterra.png"));
			ImageView imageView8 = new ImageView(pokemonImage8);
			pokemon8.setGraphic(imageView8);
			imageView8.setFitHeight(90);
			imageView8.setFitWidth(100);
			
			Image pokemonImage9 = new Image(getClass().getResourceAsStream("/res/typhlosion.png"));
			ImageView imageView9 = new ImageView(pokemonImage9);
			pokemon9.setGraphic(imageView9);
			imageView9.setFitHeight(90);
			imageView9.setFitWidth(100);
			
			Image pokemonImage10 = new Image(getClass().getResourceAsStream("/res/infernape.png"));
			ImageView imageView10 = new ImageView(pokemonImage10);
			pokemon10.setGraphic(imageView10);
			imageView10.setFitHeight(90);
			imageView10.setFitWidth(100);

			Image pokemonImage11 = new Image(getClass().getResourceAsStream("/res/gator.png"));
			ImageView imageView11 = new ImageView(pokemonImage11);
			pokemon11.setGraphic(imageView11);
			imageView11.setFitHeight(90);
			imageView11.setFitWidth(100);
			
			Image pokemonImage12 = new Image(getClass().getResourceAsStream("/res/empoleon.png"));
			ImageView imageView12 = new ImageView(pokemonImage12);
			pokemon12.setGraphic(imageView12);
			imageView12.setFitHeight(90);
			imageView12.setFitWidth(100);
		    
		    GridPane gridPane = new GridPane();
		    
		    GridPane.setConstraints(battle, 2, 4, 2, 2);
		    
		    GridPane.setRowIndex(pokemon1, 0);
		    GridPane.setColumnIndex(pokemon1, 0);
		    
		    GridPane.setRowIndex(pokemon2, 1);
		    GridPane.setColumnIndex(pokemon2, 0);
		    
		    GridPane.setRowIndex(pokemon3, 2);
		    GridPane.setColumnIndex(pokemon3, 0);
		    
		    GridPane.setRowIndex(pokemon4, 3);
		    GridPane.setColumnIndex(pokemon4, 0);
		    
		    GridPane.setRowIndex(pokemon5, 4);
		    GridPane.setColumnIndex(pokemon5, 0);
		    
		    GridPane.setRowIndex(pokemon6, 5);
		    GridPane.setColumnIndex(pokemon6, 0);
		    
		    GridPane.setRowIndex(pokemon7, 0);
		    GridPane.setColumnIndex(pokemon7, 1);
		    
		    GridPane.setRowIndex(pokemon8, 1);
		    GridPane.setColumnIndex(pokemon8, 1);

		    GridPane.setRowIndex(pokemon9, 2);
		    GridPane.setColumnIndex(pokemon9, 1);
		    
		    GridPane.setRowIndex(pokemon10, 3);
		    GridPane.setColumnIndex(pokemon10, 1);
		    
		    GridPane.setRowIndex(pokemon11, 4);
		    GridPane.setColumnIndex(pokemon11, 1);
		    
		    GridPane.setRowIndex(pokemon12, 5);
		    GridPane.setColumnIndex(pokemon12, 1);
    
		    GridPane.setRowIndex(clear, 1);
		    GridPane.setColumnIndex(clear, 3);
		    
		    gridPane.getChildren().addAll(pokemon1, pokemon2, pokemon3, 
		    		pokemon4, pokemon5, pokemon6, pokemon7, pokemon8, pokemon9, 
		    		pokemon10, pokemon11, pokemon12, battle, clear);
		
		    Scene scene = new Scene(gridPane);
		    theStage.setScene(scene);
		    theStage.setTitle("Pokemon Selector");
		    theStage.show();
	    }

	//not mvc bc lazy/testing, will change
	public void intro(Stage primaryStage){
		final AnchorPane root = new AnchorPane();
		Scene scene = new Scene(root,700,700);

		//background
		Image background = new Image(getClass().getResourceAsStream("/res/introbackground.png"));
		ImageView backgroundIv = new ImageView(background);
		backgroundIv.setFitHeight(700);
		backgroundIv.setFitWidth(700);
		root.getChildren().add(backgroundIv);

		//prof oak
		Image profImage = new Image(getClass().getResourceAsStream("/res/profoak.png"));
		ImageView profImageView = new ImageView(profImage);
		profImageView.setX(-100);
		profImageView.setY(100);
		root.getChildren().add(profImageView);	

		//arbok
		Image pkmnImage = new Image(getClass().getResourceAsStream("/res/arbok.png"));
		final ImageView pkmnImageView = new ImageView(pkmnImage);
		pkmnImageView.setX(250);
		pkmnImageView.setY(200);

		//prof slides in from left
		final Timeline profTimeline = new Timeline();
		profTimeline.setCycleCount(1);
		final KeyValue profKv = new KeyValue(profImageView.xProperty(), 250);
		final KeyFrame profKf = new KeyFrame(Duration.millis(2000), profKv);
		profTimeline.getKeyFrames().add(profKf);
		profTimeline.play();

		//prof moves over for pkmn
		final Timeline pkmnTimeline = new Timeline();
		pkmnTimeline.setCycleCount(1);
		final KeyValue pkmnKv = new KeyValue(profImageView.xProperty(), 350);
		final KeyFrame pkmnKf = new KeyFrame(Duration.millis(500), pkmnKv);
		pkmnTimeline.getKeyFrames().add(pkmnKf);

		//arbok moving out
		Path pkmnPath = new Path();
		pkmnPath.getElements().add(new MoveTo(pkmnImageView.getX(), pkmnImageView.getY()));
		pkmnPath.getElements().add(new QuadCurveTo(200, 200, 250, 350));
		final PathTransition pkmnPathTransition = new PathTransition();
		pkmnPathTransition.setDuration(Duration.millis(500));
		pkmnPathTransition.setPath(pkmnPath);
		pkmnPathTransition.setNode(pkmnImageView);
		pkmnPathTransition.setOrientation(PathTransition.OrientationType.NONE);
		pkmnPathTransition.setCycleCount(1);
		pkmnPathTransition.setAutoReverse(false);

		//pokemon text label
		final Label textLabel = new Label();
		changeIntroText(textLabel);
		textLabel.setWrapText(true);
		textLabel.setMaxSize(400, 100);
		textLabel.setLayoutX(200);
		textLabel.setLayoutY(600);
		textLabel.setFont(Font.loadFont(getClass().getResourceAsStream("/res/font.ttf"), 12));
		root.getChildren().add(textLabel);

		//fading out of arbok and prof
		final FadeTransition pkmnFt = new FadeTransition(Duration.millis(1000),pkmnImageView);
		pkmnFt.setFromValue(1.0);
		pkmnFt.setToValue(0.0);
		pkmnFt.setCycleCount(1);
		final FadeTransition profFt = new FadeTransition(Duration.millis(1000), profImageView);
		profFt.setFromValue(1.0);
		profFt.setToValue(0.0);
		profFt.setCycleCount(1);

		//boy button
		final Button boyButton = new Button("Boy");
		boyButton.setLayoutX(250);
		boyButton.setLayoutY(500);
		boyButton.setPrefWidth(100);
		boyButton.setPrefHeight(50);
		boyButton.setStyle(buttonStyle);

		//boy image
		Image boyImage = new Image(getClass().getResourceAsStream("/res/boy.png"));
		final ImageView boyImageView = new ImageView(boyImage);
		boyImageView.setX(250);
		boyImageView.setY(200);

		//moves boy for name entry
		final Timeline boyTimeline = new Timeline();
		boyTimeline.setCycleCount(1);
		final KeyValue boyKv = new KeyValue(boyImageView.xProperty(), 375);
		final KeyFrame boyKf = new KeyFrame(Duration.millis(500), boyKv);
		boyTimeline.getKeyFrames().add(boyKf);

		//boy fading in
		final FadeTransition boyFt = new FadeTransition(Duration.millis(1000),boyImageView);
		boyFt.setFromValue(0.0);
		boyFt.setToValue(1.0);
		boyFt.setCycleCount(1);

		//boy shrinking
		final ScaleTransition boySt = new ScaleTransition(Duration.millis(500), boyImageView);
		boySt.setFromX(1.0);
		boySt.setFromY(1.0);
		boySt.setToX(0.1);
		boySt.setToY(0.1);
		boySt.setCycleCount(1);

		//girl button
		final Button girlButton = new Button("Girl");
		girlButton.setLayoutX(350);
		girlButton.setLayoutY(500);
		girlButton.setPrefWidth(100);
		girlButton.setPrefHeight(50);
		girlButton.setStyle(buttonStyle);

		//girl image
		Image girlImage = new Image(getClass().getResourceAsStream("/res/girl.png"));
		final ImageView girlImageView = new ImageView(girlImage);
		girlImageView.setX(250);
		girlImageView.setY(200);

		//moves girl for name entry
		final Timeline girlTimeline = new Timeline();
		girlTimeline.setCycleCount(1);
		final KeyValue girlKv = new KeyValue(girlImageView.xProperty(), 375);
		final KeyFrame girlKf = new KeyFrame(Duration.millis(500), girlKv);
		girlTimeline.getKeyFrames().add(girlKf);

		//girl fading in
		final FadeTransition girlFt = new FadeTransition(Duration.millis(1000), girlImageView);
		girlFt.setFromValue(0.0);
		girlFt.setToValue(1.0);
		girlFt.setCycleCount(1);

		//girl shrinking
		final ScaleTransition girlSt = new ScaleTransition(Duration.millis(500), girlImageView);
		girlSt.setFromX(1.0);
		girlSt.setFromY(1.0);
		girlSt.setToX(0.1);
		girlSt.setToY(0.1);
		girlSt.setCycleCount(1);

		//TODO added
		//boyButton.setOnAction(controller);

		//change text to include boy
		boyButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
			public void handle(MouseEvent e){
				root.getChildren().remove(boyButton);
				root.getChildren().remove(girlButton);
				boyFt.play();
				root.getChildren().add(boyImageView);
				textLabel.setText(introText[introIndex] + "boy!");
				introIndex++;
			}
		});

		//TODO added
		//girlButton.setOnAction(controller);

		//change text to include girl
		girlButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
			public void handle(MouseEvent e){
				root.getChildren().remove(boyButton);
				root.getChildren().remove(girlButton);
				girlFt.play();
				root.getChildren().add(girlImageView);
				textLabel.setText(introText[introIndex] + "girl!");
				introIndex++;
			}
		});

		//done button
		final Button doneButton = new Button("Done");
		doneButton.setLayoutX(200);
		doneButton.setLayoutY(550);
		doneButton.setPrefWidth(170);
		doneButton.setPrefHeight(20);
		doneButton.setStyle(buttonStyle);

		//name text field
		final TextField nameTextField = new TextField();
		nameTextField.setLayoutX(200);
		nameTextField.setLayoutY(500);

		//TODO added
		//doneButton.setOnAction(controller);

		//update name
		doneButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
			public void handle(MouseEvent e){
				name = nameTextField.getText();
				root.getChildren().remove(doneButton);
				root.getChildren().remove(nameTextField);
				if (name.equalsIgnoreCase("")){
					if (root.getChildren().contains(boyImageView)){
						name = "Ash";
					} else {
						name = "Misty";
					}
				}
				textLabel.setText(introText[introIndex] + name + '!');
				introIndex++;
			}
		});

		//wait ~1 second before removing (to show shrink) then call pokemon selection method
		final PauseTransition pt = new PauseTransition(Duration.millis(750));
		pt.setOnFinished( event -> {
			if (root.getChildren().contains(boyImageView)){
				root.getChildren().remove(boyImageView);
			}
			if (root.getChildren().contains(girlImageView)){
				root.getChildren().remove(girlImageView);
			}
			//TODO change to pkmn selection
			selectScreen(primaryStage);
		});

		root.setOnMouseClicked(new EventHandler<MouseEvent>(){
			public void handle(MouseEvent e){
				//prof moves over, arbok comes out
				if (introIndex == 3){
					pkmnTimeline.play();
					root.getChildren().add(pkmnImageView);
					pkmnPathTransition.play();
				}
				//arbok and prof fade out, add buttons
				if (introIndex == 6 && (!root.getChildren().contains(girlButton) || !root.getChildren().contains(boyButton))){
					pkmnFt.play();
					profFt.play();
					root.getChildren().add(boyButton);
					root.getChildren().add(girlButton);
					changeIntroText(textLabel);
				}
				//character moves over, add button/textfield
				if (introIndex == 8 && (!root.getChildren().contains(doneButton))){
					if (root.getChildren().contains(boyImageView)){
						boyTimeline.play();
					} else {
						girlTimeline.play();
					}
					root.getChildren().add(nameTextField);
					root.getChildren().add(doneButton);
					changeIntroText(textLabel);
				}

				//start pokemon selection
				if (introIndex == introText.length){
					if (root.getChildren().contains(textLabel)){
						root.getChildren().remove(textLabel);
					}
					if (root.getChildren().contains(boyImageView)){
						boySt.play();
						pt.play();
					}
					if (root.getChildren().contains(girlImageView)){
						girlSt.play();
						pt.play();
					}
				}
				//don't change text if buttons are on the screen - waiting for them to be pressed instead
				if ((!root.getChildren().contains(girlButton) || !root.getChildren().contains(boyButton)) && !root.getChildren().contains(doneButton)){
					changeIntroText(textLabel);
				}
			}
		});

		//root.setOnMouseClicked(controller);

		primaryStage.setScene( scene ); 
		primaryStage.setResizable(false);
		primaryStage.setTitle( "Pokémon" );
		primaryStage.show();
	}
	
	public void changeIntroText(Label label){
		if (!(introIndex >= introText.length)){
			label.setText(introText[introIndex]);
			introIndex++;
		}
	}

	//For intro use
		private int introIndex = 0; 
		private final String[] introText = {"Welcome to the world of Pokémon!","My name is Professor Oak.","People affectionately refer to me as the Pokémon professor!",
				"This world is inhabited far and wide by creatures called Pokémon!","For some people pokemon are pets, others use them for battling!",
				"That is what you will be doing, but first tell me a little about yourself!","Now tell me, are you a boy or are you a girl?",
				"Oh so you're a ", "Lets begin with your name! What is it?", "Right so your name is ","You will now choose your team for battle!"};
		private String name;



	public static void main(String[] args) {
		launch(args);
	}
}

