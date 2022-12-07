// ---------------------------------------------

/*
 * Authors: 
 * 			Zaid Awaidah 
 * UIC, Fall 2022
 * CS 342
 * 
 * ThreeCardPokerGame
 * 
 * Implements the GUI for 
 * Three Card Poker
 * 
*/

// ---------------------------------------------

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

import javafx.util.Duration;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

import javafx.animation.PauseTransition;

import javafx.application.Application;

import javafx.geometry.Insets;
import javafx.geometry.Pos;

import javafx.scene.Scene;
import javafx.scene.text.*;
import javafx.scene.image.*;
import javafx.scene.layout.*;
import javafx.scene.control.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.Pair;

//---------------------------------------------

public class ThreeCardPokerGame extends Application {
	// -------------------------

	// Locals
	private int round_counter;
	private boolean activeGame = false;
	private boolean betsPlaced = false;
	private boolean playerOne_wager_set = false;
	private boolean playerTwo_wager_set = false;
	private boolean player1_foldStatus = false;
	private boolean player2_foldStatus = false;

	private HashMap<String, Scene> scenes;

	private Player playerOne = new Player();
	private Player playerTwo = new Player();
	private Dealer dealer = new Dealer();

	private BorderPane Starting_Pane;
	private BorderPane Game_Pane_1;
	private BorderPane current_game_pane;

	private Background current_bckgrnd;
	private ArrayList<Background> backgrounds;

	private PauseTransition pause;

	private Scene starting_scene;
	private Scene scene_1;
	private Scene current_scene;

	private ListView<String> listView;

	private Menu options_menu;
	private MenuItem new_look;
	private MenuItem fresh_start;
	private MenuItem exit;
	private VBox options_MB;

	private Label CurrentMsg;
	private Label DealerLabel;
	private Label Player1Label;
	private Label Player2Label;
	private Label Player1_CurrentBalance;
	private Label Player2_CurrentBalance;
	private TextField Player1_AnteBetTextField;
	private TextField Player2_AnteBetTextField;
	private TextField Player1_PairPlusTextField;
	private TextField Player2_PairPlusTextField;
	private TextField Player1_WagerTextField;
	private TextField Player2_WagerTextField;

	private ArrayList<Card> p1Hand;
	private ArrayList<Card> p2Hand;

	private Image DefaultImage;

	private ImageView Dealer_Card1;
	private ImageView Dealer_Card2;
	private ImageView Dealer_Card3;
	private ImageView Player1_Card1;
	private ImageView Player1_Card2;
	private ImageView Player1_Card3;
	private ImageView Player2_Card1;
	private ImageView Player2_Card2;
	private ImageView Player2_Card3;

	private Button Play;
	private Button Deal;
	private Button Player1_Fold;
	private Button Player2_Fold;
	private Button Player1_ConfirmBets;
	private Button Player1_BetAll;
	private Button Player1_PlayWager;
	private Button Player2_ConfirmBets;
	private Button Player2_BetAll;
	private Button Player2_PlayWager;

	// -------------------------

	/*
	 * bckGrdImg
	 *
	 * returns an image background
	 */
	private Background bckGrdImg(String file) throws Exception {
		// Game backgrounds
		InputStream stream = new FileInputStream(file);
		Image image = new Image(stream);

		BackgroundSize bSize = new BackgroundSize(BackgroundSize.AUTO, BackgroundSize.AUTO, false, false, true, false);
		BackgroundImage bckGImg = new BackgroundImage(image, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT,
				BackgroundPosition.CENTER, bSize);

		Background background = new Background(bckGImg);

		return background;
	}

	// -------------------------

	/*
	 * customize_button
	 *
	 * returns customized buttons
	 */
	private Button customize_button(String button, String font_style, int font_size, int button_width,
			int button_height, Color button_color, Color font_color) {
		Button b1 = new Button(button);
		b1.setFont(Font.font(font_style, FontWeight.BOLD, FontPosture.REGULAR, font_size));
		b1.setTextFill(font_color);
		b1.setPrefSize(button_width, button_height);
		b1.setBackground(new Background(new BackgroundFill(button_color, CornerRadii.EMPTY, Insets.EMPTY)));

		return b1;
	}

	// -------------------------

	/*
	 * get_rand_background
	 * 
	 * get new background image (cannot be DEFAULT)
	 */
	private Background get_rand_background() {
		int rand_index = new Random().nextInt(backgrounds.size());
		Background new_bckgrnd = backgrounds.get(rand_index);

		if (new_bckgrnd == current_bckgrnd) {
			while (new_bckgrnd != current_bckgrnd) {
				rand_index = new Random().nextInt(backgrounds.size());
				new_bckgrnd = backgrounds.get(rand_index);
			}
		}
		return new_bckgrnd;
	}

	// -------------------------

	/*
	 * customize_main_scene
	 * 
	 * customizes the background of starting scene
	 */
	private void customize_main_scene(Text main_scene, Button start) {
		start.setTextFill(Color.WHITE);
		start.setBackground(new Background(new BackgroundFill(randomColor(), CornerRadii.EMPTY, Insets.EMPTY)));
		main_scene.setFill(randomColor());
		current_game_pane.setBackground(current_bckgrnd);
	}

	// -------------------------

	/*
	 * customize_game_scene
	 * 
	 * customizes the background of game scene
	 */
	private void customize_game_scene() {
		Color rand = randomColor();
		DealerLabel.setTextFill(rand);
		Player1Label.setTextFill(rand);
		Player2Label.setTextFill(rand);
		Player1_CurrentBalance.setTextFill(rand);
		Player2_CurrentBalance.setTextFill(rand);

		current_game_pane.setBackground(current_bckgrnd);
	}

	// -------------------------

	/*
	 * resetGame
	 */
	private void resetGame() {
		// clear all hands
		dealer = new Dealer();
		// dealer.clearDealerHand();
		playerOne.clearPlayerHand();
		playerTwo.clearPlayerHand();

		// reset player bets and total
		playerOne.setAnteBet(0);
		playerOne.setPlayBet(0);
		playerOne.setPPBet(0);
		playerOne.setTotalWinnings(0);

		playerTwo.setAnteBet(0);
		playerTwo.setPlayBet(0);
		playerTwo.setPPBet(0);
		playerTwo.setTotalWinnings(0);

		Player1_Card1.setImage(DefaultImage);
		Player1_Card1.setFitHeight(150);
		Player1_Card1.setFitWidth(100);

		Player1_Card2.setImage(DefaultImage);
		Player1_Card2.setFitHeight(150);
		Player1_Card2.setFitWidth(100);

		Player1_Card3.setImage(DefaultImage);
		Player1_Card3.setFitHeight(150);
		Player1_Card3.setFitWidth(100);

		Player2_Card1.setImage(DefaultImage);
		Player2_Card1.setFitHeight(150);
		Player2_Card1.setFitWidth(100);

		Player2_Card2.setImage(DefaultImage);
		Player2_Card2.setFitHeight(150);
		Player2_Card2.setFitWidth(100);

		Player2_Card3.setImage(DefaultImage);
		Player2_Card3.setFitHeight(150);
		Player2_Card3.setFitWidth(100);

		Dealer_Card1.setImage(DefaultImage);
		Dealer_Card1.setFitHeight(150);
		Dealer_Card1.setFitWidth(100);

		Dealer_Card2.setImage(DefaultImage);
		Dealer_Card2.setFitHeight(150);
		Dealer_Card2.setFitWidth(100);

		Dealer_Card3.setImage(DefaultImage);
		Dealer_Card3.setFitHeight(150);
		Dealer_Card3.setFitWidth(100);

		// re-enable buttons for new game
		Player1_ConfirmBets.setDisable(false);
		Player2_ConfirmBets.setDisable(false);

		Player1_CurrentBalance.setText("Current balance: $");
		Player2_CurrentBalance.setText("Current balance: $");

		Player1_AnteBetTextField.clear();
		Player1_PairPlusTextField.clear();
		Player2_AnteBetTextField.clear();
		Player2_PairPlusTextField.clear();
		Player1_WagerTextField.clear();
		Player2_WagerTextField.clear();

		Player1_AnteBetTextField.setPromptText("Enter your ante bet here ($5-$25)");
		Player1_PairPlusTextField.setPromptText("Enter your optional pair plus bet here($5-$25, 0 to skip)");
		Player2_AnteBetTextField.setPromptText("Enter your ante bet here ($5-$25)");
		Player2_PairPlusTextField.setPromptText("Enter your optional pair plus bet here($5-$25, 0 to skip)");
		Player1_WagerTextField.setPromptText("Please enter play wager ($5-$25).");
		Player2_WagerTextField.setPromptText("Please enter play wager ($5-$25).");

		Player1_AnteBetTextField.setDisable(false);
		Player1_PairPlusTextField.setDisable(false);
		Player2_AnteBetTextField.setDisable(false);
		Player2_PairPlusTextField.setDisable(false);
		Player1_WagerTextField.setDisable(true);
		Player2_WagerTextField.setDisable(true);

		playerOne_wager_set = false;
		playerTwo_wager_set = false;

		player1_foldStatus = false;
		player2_foldStatus = false;
		Player1_Fold.setDisable(true);
		Player2_Fold.setDisable(true);
		Player1_PlayWager.setDisable(true);
		Player2_PlayWager.setDisable(true);

		Play.setDisable(false);
		Deal.setDisable(true);

		round_counter = 1;
		betsPlaced = false;
		activeGame = false;
		listView.getItems().clear();
	}

	// -------------------------

	/*
	 * randomColor
	 * 
	 */
	private Color randomColor() {
		Random random = new Random();
		int r = random.nextInt(255);
		int g = random.nextInt(255);
		int b = random.nextInt(255);
		return Color.rgb(r, g, b);
	}

	// -------------------------

	/*
	 * option menu
	 * 
	 * generate option menu bar
	 */
	private VBox option_menu() {
		options_menu.setText("Options");
		new_look.setText("New Look");
		fresh_start.setText("New Game");
		exit.setText("Exit");

		options_menu.setStyle("-fx-font-size: 14;");
		if (options_menu.getItems().size() == 0) {
			options_menu.getItems().add(new_look);
			options_menu.getItems().add(fresh_start);
			options_menu.getItems().add(exit);
		}

		MenuBar options_menuBar = new MenuBar();
		options_menuBar.setStyle("-fx-background-color: lightgray;");
		options_menuBar.getMenus().add(options_menu);

		VBox VB = new VBox(options_menuBar);
		return VB;
	}

	// -------------------------

	/*
	 * initial_game
	 * 
	 * sets up initial game scene
	 */
	private VBox initial_game(HashMap<Pair<Character, Integer>, Image> images) throws FileNotFoundException {
		// Game message
		CurrentMsg = new Label("");

		// Gets default image of an upside down card
		Pair<Character, Integer> defaultImg = new Pair<>('B', 0);
		DefaultImage = images.get(defaultImg);

		// Dealer
		DealerLabel = new Label("Dealer");
		DealerLabel.setFont(new Font("Times New Roman", 26));
		DealerLabel.setStyle("-fx-font-weight: bold");
		DealerLabel.setUnderline(true);
		Dealer_Card1 = new ImageView(DefaultImage);
		Dealer_Card1.setFitHeight(150);
		Dealer_Card1.setFitWidth(100);
		Dealer_Card2 = new ImageView(DefaultImage);
		Dealer_Card2.setFitHeight(150);
		Dealer_Card2.setFitWidth(100);
		Dealer_Card3 = new ImageView(DefaultImage);
		Dealer_Card3.setFitHeight(150);
		Dealer_Card3.setFitWidth(100);

		// Player One
		Player1Label = new Label("Player 1");
		Player1Label.setFont(new Font("Times New Roman", 18));
		Player1Label.setStyle("-fx-font-weight: bold");
		Player1_CurrentBalance = new Label("Current balance: $");
		Player1_CurrentBalance.setFont(new Font("Times New Roman", 18));
		Player1_CurrentBalance.setUnderline(true);
		Player1_ConfirmBets = customize_button("Confirm Both Bets", "Times New Roman", 14, 150, 70, Color.LIGHTGRAY,
				Color.BLACK);
		Player1_BetAll = customize_button("Bet All", "Times New Roman", 14, 100, 70, Color.LIGHTGRAY, Color.BLACK);
		Player1_Fold = customize_button("Fold", "Times New Roman", 14, 100, 70, Color.LIGHTGRAY, Color.BLACK);
		Player1_PlayWager = customize_button("Play Wager", "Times New Roman", 14, 100, 70, Color.LIGHTGRAY,
				Color.BLACK);

		Player1_AnteBetTextField = new TextField();
		Player1_AnteBetTextField.setPromptText("Enter your ante bet here ($5-$25).");
		Player1_PairPlusTextField = new TextField();
		Player1_PairPlusTextField.setPromptText("Enter your optional pair plus bet here($5-$25, 0 to skip).");
		Player1_WagerTextField = new TextField();
		Player1_WagerTextField.setPromptText("Please enter play wager(Must be the same as ante).");
		Player1_WagerTextField.setDisable(true);
		Player1_Card1 = new ImageView(DefaultImage);
		Player1_Card1.setFitHeight(150);
		Player1_Card1.setFitWidth(100);
		Player1_Card2 = new ImageView(DefaultImage);
		Player1_Card2.setFitHeight(150);
		Player1_Card2.setFitWidth(100);
		Player1_Card3 = new ImageView(DefaultImage);
		Player1_Card3.setFitHeight(150);
		Player1_Card3.setFitWidth(100);

		// Player Two
		Player2Label = new Label("Player 2");
		Player2Label.setFont(new Font("Times New Roman", 18));
		Player2Label.setStyle("-fx-font-weight: bold");
		Player2_CurrentBalance = new Label("Current balance: $");
		Player2_CurrentBalance.setFont(new Font("Times New Roman", 18));
		Player2_CurrentBalance.setUnderline(true);
		Player2_ConfirmBets = customize_button("Confirm Both Bets", "Times New Roman", 14, 150, 70, Color.LIGHTGRAY,
				Color.BLACK);
		Player2_BetAll = customize_button("Bet All", "Times New Roman", 14, 100, 70, Color.LIGHTGRAY, Color.BLACK);
		Player2_Fold = customize_button("Fold", "Times New Roman", 14, 100, 70, Color.LIGHTGRAY, Color.BLACK);
		Player2_PlayWager = customize_button("Play Wager", "Times New Roman", 14, 100, 70, Color.LIGHTGRAY,
				Color.BLACK);
		Player2_AnteBetTextField = new TextField();
		Player2_AnteBetTextField.setPromptText("Enter your ante bet here ($5-$25).");
		Player2_PairPlusTextField = new TextField();
		Player2_PairPlusTextField.setPromptText("Enter your optional pair plus bet here($5-$25, 0 to skip).");
		Player2_WagerTextField = new TextField();
		Player2_WagerTextField.setPromptText("Please enter play wager(Must be the same as ante).");
		Player2_WagerTextField.setDisable(true);
		Player2_Card1 = new ImageView(DefaultImage);
		Player2_Card1.setFitHeight(150);
		Player2_Card1.setFitWidth(100);
		Player2_Card2 = new ImageView(DefaultImage);
		Player2_Card2.setFitHeight(150);
		Player2_Card2.setFitWidth(100);
		Player2_Card3 = new ImageView(DefaultImage);
		Player2_Card3.setFitHeight(150);
		Player2_Card3.setFitWidth(100);

		// Players can't fold or bet all at start
		Player1_Fold.setDisable(true);
		Player2_Fold.setDisable(true);
		Player1_BetAll.setDisable(true);
		Player2_BetAll.setDisable(true);
		Player1_PlayWager.setDisable(true);
		Player2_PlayWager.setDisable(true);

		// Dealer component
		listView = new ListView<String>();
		listView.setPrefWidth(175);
		listView.setPrefHeight(175);
		VBox list_view_menu = new VBox(listView);
		list_view_menu.setAlignment(Pos.TOP_LEFT);
		list_view_menu.setMaxHeight(200);
		list_view_menu.setMinWidth(250);
		HBox DealerCardsHBox = new HBox(Dealer_Card1, Dealer_Card2, Dealer_Card3);
		DealerCardsHBox.setSpacing(8);
		DealerCardsHBox.setAlignment(Pos.CENTER);
		VBox DealerVBox = new VBox(DealerLabel, DealerCardsHBox);
		DealerVBox.setAlignment(Pos.CENTER);
		DealerVBox.setSpacing(3);
		HBox Upper_scene_layout = new HBox(list_view_menu, DealerVBox);
		Upper_scene_layout.setSpacing(150);

		// Player one component
		HBox Player1CardsHBox = new HBox(Player1_Card1, Player1_Card2, Player1_Card3);
		Player1CardsHBox.setSpacing(8);
		HBox Player1TopButtons = new HBox(Player1_ConfirmBets);
		Player1TopButtons.setSpacing(3);
		Player1TopButtons.setAlignment(Pos.CENTER);
		HBox Player1BottomButtons = new HBox(Player1_BetAll, Player1_Fold, Player1_PlayWager);
		Player1BottomButtons.setSpacing(3);
		Player1BottomButtons.setAlignment(Pos.CENTER);
		VBox Player1TopAndBottomButtons = new VBox(Player1TopButtons, Player1BottomButtons);
		Player1TopAndBottomButtons.setSpacing(3);
		Player1TopAndBottomButtons.setAlignment(Pos.CENTER);
		VBox Player1VBox = new VBox(Player1Label, Player1_CurrentBalance, Player1CardsHBox, Player1_AnteBetTextField,
				Player1_PairPlusTextField, Player1_WagerTextField, Player1TopAndBottomButtons);
		Player1VBox.setSpacing(3);

		// Player two component
		HBox Player2CardsHBox = new HBox(Player2_Card1, Player2_Card2, Player2_Card3);
		Player2CardsHBox.setSpacing(8);
		HBox Player2TopButtons = new HBox(Player2_ConfirmBets);
		Player2TopButtons.setSpacing(3);
		Player2TopButtons.setAlignment(Pos.CENTER);
		HBox Player2BottomButtons = new HBox(Player2_BetAll, Player2_Fold, Player2_PlayWager);
		Player2BottomButtons.setSpacing(3);
		Player2BottomButtons.setAlignment(Pos.CENTER);
		VBox Player2TopAndBottomButtons = new VBox(Player2TopButtons, Player2BottomButtons);
		Player2TopAndBottomButtons.setSpacing(3);
		Player2TopAndBottomButtons.setAlignment(Pos.CENTER);
		VBox Player2VBox = new VBox(Player2Label, Player2_CurrentBalance, Player2CardsHBox, Player2_AnteBetTextField,
				Player2_PairPlusTextField, Player2_WagerTextField, Player2TopAndBottomButtons);
		Player2VBox.setSpacing(3);

		// Players' component
		HBox Player1AndPlayer2HBox = new HBox(Player1VBox, Player2VBox);
		Player1AndPlayer2HBox.setSpacing(125);
		Player1AndPlayer2HBox.setAlignment(Pos.CENTER);

		CurrentMsg.setText("Welcome to Three Card Poker.");
		CurrentMsg.setFont(new Font("Times New Roman", 18));

		// Play/Deal Component
		Play = customize_button("Play", "Times New Roman", 20, 80, 30, Color.LIGHTGRAY, Color.BLACK);
		Deal = customize_button("Deal", "Times New Roman", 20, 80, 30, Color.LIGHTGRAY, Color.BLACK);
		VBox Play_Or_Deal = new VBox(Play, Deal);
		Deal.setDisable(true);
		Play_Or_Deal.setSpacing(5);
		Play_Or_Deal.setAlignment(Pos.CENTER);

		// initial round
		round_counter = 1;

		// game component
		VBox MasterVBox = new VBox(Upper_scene_layout, CurrentMsg, Play_Or_Deal, Player1AndPlayer2HBox);
		return MasterVBox;
	}

	// -------------------------

	/*
	 * importImagesAndStoreInHashMap
	 * 
	 * Imports images and saves them to be used
	 */
	private void importImagesAndStoreInHashMap(HashMap<Pair<Character, Integer>, Image> images)
			throws FileNotFoundException {
		String card_file_upside_down = ("src/main/resources/images/upside_down_card.png");
		String card_file_2_of_clubs = "src/main/resources/images/2_of_clubs.png";
		String card_file_2_of_diamonds = "src/main/resources/images/2_of_diamonds.png";
		String card_file_2_of_hearts = "src/main/resources/images/2_of_hearts.png";
		String card_file_2_of_spades = "src/main/resources/images/2_of_spades.png";
		String card_file_3_of_clubs = "src/main/resources/images/3_of_clubs.png";
		String card_file_3_of_diamonds = "src/main/resources/images/3_of_diamonds.png";
		String card_file_3_of_hearts = "src/main/resources/images/3_of_hearts.png";
		String card_file_3_of_spades = "src/main/resources/images/3_of_spades.png";
		String card_file_4_of_clubs = "src/main/resources/images/4_of_clubs.png";
		String card_file_4_of_diamonds = "src/main/resources/images/4_of_diamonds.png";
		String card_file_4_of_hearts = "src/main/resources/images/4_of_hearts.png";
		String card_file_4_of_spades = "src/main/resources/images/4_of_spades.png";
		String card_file_5_of_clubs = "src/main/resources/images/5_of_clubs.png";
		String card_file_5_of_diamonds = "src/main/resources/images/5_of_diamonds.png";
		String card_file_5_of_hearts = "src/main/resources/images/5_of_hearts.png";
		String card_file_5_of_spades = "src/main/resources/images/5_of_spades.png";
		String card_file_6_of_clubs = "src/main/resources/images/6_of_clubs.png";
		String card_file_6_of_diamonds = "src/main/resources/images/6_of_diamonds.png";
		String card_file_6_of_hearts = "src/main/resources/images/6_of_hearts.png";
		String card_file_6_of_spades = "src/main/resources/images/6_of_spades.png";
		String card_file_7_of_clubs = "src/main/resources/images/7_of_clubs.png";
		String card_file_7_of_diamonds = "src/main/resources/images/7_of_diamonds.png";
		String card_file_7_of_hearts = "src/main/resources/images/7_of_hearts.png";
		String card_file_7_of_spades = "src/main/resources/images/7_of_spades.png";
		String card_file_8_of_clubs = "src/main/resources/images/8_of_clubs.png";
		String card_file_8_of_diamonds = "src/main/resources/images/8_of_diamonds.png";
		String card_file_8_of_hearts = "src/main/resources/images/8_of_hearts.png";
		String card_file_8_of_spades = "src/main/resources/images/8_of_spades.png";
		String card_file_9_of_clubs = "src/main/resources/images/9_of_clubs.png";
		String card_file_9_of_diamonds = "src/main/resources/images/9_of_diamonds.png";
		String card_file_9_of_hearts = "src/main/resources/images/9_of_hearts.png";
		String card_file_9_of_spades = "src/main/resources/images/9_of_spades.png";
		String card_file_10_of_clubs = "src/main/resources/images/10_of_clubs.png";
		String card_file_10_of_diamonds = "src/main/resources/images/10_of_diamonds.png";
		String card_file_10_of_hearts = "src/main/resources/images/10_of_hearts.png";
		String card_file_10_of_spades = "src/main/resources/images/10_of_spades.png";
		String card_file_jack_of_clubs = "src/main/resources/images/jack_of_clubs.png";
		String card_file_jack_of_diamonds = "src/main/resources/images/jack_of_diamonds.png";
		String card_file_jack_of_hearts = "src/main/resources/images/jack_of_hearts.png";
		String card_file_jack_of_spades = "src/main/resources/images/jack_of_spades.png";
		String card_file_queen_of_clubs = "src/main/resources/images/queen_of_clubs.png";
		String card_file_queen_of_diamonds = "src/main/resources/images/queen_of_diamonds.png";
		String card_file_queen_of_hearts = "src/main/resources/images/queen_of_hearts.png";
		String card_file_queen_of_spades = "src/main/resources/images/queen_of_spades.png";
		String card_file_king_of_clubs = "src/main/resources/images/king_of_clubs.png";
		String card_file_king_of_diamonds = "src/main/resources/images/king_of_diamonds.png";
		String card_file_king_of_hearts = "src/main/resources/images/king_of_hearts.png";
		String card_file_king_of_spades = "src/main/resources/images/king_of_spades.png";
		String card_file_ace_of_clubs = "src/main/resources/images/ace_of_clubs.png";
		String card_file_ace_of_diamonds = "src/main/resources/images/ace_of_diamonds.png";
		String card_file_ace_of_hearts = "src/main/resources/images/ace_of_hearts.png";
		String card_file_ace_of_spades = "src/main/resources/images/ace_of_spades.png";

		InputStream stream = new FileInputStream(card_file_upside_down);
		Image UpsideDownCard = new Image(stream);

		// Note: B 0 will represent the blank card
		Pair<Character, Integer> UpsideDownCardPair = new Pair<Character, Integer>('B', 0);
		images.put(UpsideDownCardPair, UpsideDownCard);

		stream = new FileInputStream(card_file_2_of_clubs);
		Image twoOfClubs = new Image(stream);
		Pair<Character, Integer> twoOfClubsPair = new Pair<Character, Integer>('C', 2);
		images.put(twoOfClubsPair, twoOfClubs);

		stream = new FileInputStream(card_file_2_of_diamonds);
		Image twoOfDiamonds = new Image(stream);
		Pair<Character, Integer> twoOfDiamondsPair = new Pair<Character, Integer>('D', 2);
		images.put(twoOfDiamondsPair, twoOfDiamonds);

		stream = new FileInputStream(card_file_2_of_hearts);
		Image twoOfHearts = new Image(stream);
		Pair<Character, Integer> twoOfHeartsPair = new Pair<Character, Integer>('H', 2);
		images.put(twoOfHeartsPair, twoOfHearts);

		stream = new FileInputStream(card_file_2_of_spades);
		Image twoOfSpades = new Image(stream);
		Pair<Character, Integer> twoOfSpadesPair = new Pair<Character, Integer>('S', 2);
		images.put(twoOfSpadesPair, twoOfSpades);

		stream = new FileInputStream(card_file_3_of_clubs);
		Image threeOfClubs = new Image(stream);
		Pair<Character, Integer> threeOfClubsPair = new Pair<Character, Integer>('C', 3);
		images.put(threeOfClubsPair, threeOfClubs);

		stream = new FileInputStream(card_file_3_of_diamonds);
		Image threeOfDiamonds = new Image(stream);
		Pair<Character, Integer> threeOfDiamondsPair = new Pair<Character, Integer>('D', 3);
		images.put(threeOfDiamondsPair, threeOfDiamonds);

		stream = new FileInputStream(card_file_3_of_hearts);
		Image threeOfHearts = new Image(stream);
		Pair<Character, Integer> threeOfHeartsPair = new Pair<Character, Integer>('H', 3);
		images.put(threeOfHeartsPair, threeOfHearts);

		stream = new FileInputStream(card_file_3_of_spades);
		Image threeOfSpades = new Image(stream);
		Pair<Character, Integer> threeOfSpadesPair = new Pair<Character, Integer>('S', 3);
		images.put(threeOfSpadesPair, threeOfSpades);

		stream = new FileInputStream(card_file_4_of_clubs);
		Image fourOfClubs = new Image(stream);
		Pair<Character, Integer> fourOfClubsPair = new Pair<Character, Integer>('C', 4);
		images.put(fourOfClubsPair, fourOfClubs);

		stream = new FileInputStream(card_file_4_of_diamonds);
		Image fourOfDiamonds = new Image(stream);
		Pair<Character, Integer> fourOfDiamondsPair = new Pair<Character, Integer>('D', 4);
		images.put(fourOfDiamondsPair, fourOfDiamonds);

		stream = new FileInputStream(card_file_4_of_hearts);
		Image fourOfHearts = new Image(stream);
		Pair<Character, Integer> fourOfHeartsPair = new Pair<Character, Integer>('H', 4);
		images.put(fourOfHeartsPair, fourOfHearts);

		stream = new FileInputStream(card_file_4_of_spades);
		Image fourOfSpades = new Image(stream);
		Pair<Character, Integer> fourOfSpadesPair = new Pair<Character, Integer>('S', 4);
		images.put(fourOfSpadesPair, fourOfSpades);

		stream = new FileInputStream(card_file_5_of_clubs);
		Image fiveOfClubs = new Image(stream);
		Pair<Character, Integer> fiveOfClubsPair = new Pair<Character, Integer>('C', 5);
		images.put(fiveOfClubsPair, fiveOfClubs);

		stream = new FileInputStream(card_file_5_of_diamonds);
		Image fiveOfDiamonds = new Image(stream);
		Pair<Character, Integer> fiveOfDiamondsPair = new Pair<Character, Integer>('D', 5);
		images.put(fiveOfDiamondsPair, fiveOfDiamonds);

		stream = new FileInputStream(card_file_5_of_hearts);
		Image fiveOfHearts = new Image(stream);
		Pair<Character, Integer> fiveOfHeartsPair = new Pair<Character, Integer>('H', 5);
		images.put(fiveOfHeartsPair, fiveOfHearts);

		stream = new FileInputStream(card_file_5_of_spades);
		Image fiveOfSpades = new Image(stream);
		Pair<Character, Integer> fiveOfSpadesPair = new Pair<Character, Integer>('S', 5);
		images.put(fiveOfSpadesPair, fiveOfSpades);

		stream = new FileInputStream(card_file_6_of_clubs);
		Image sixOfClubs = new Image(stream);
		Pair<Character, Integer> sixOfClubsPair = new Pair<Character, Integer>('C', 6);
		images.put(sixOfClubsPair, sixOfClubs);

		stream = new FileInputStream(card_file_6_of_diamonds);
		Image sixOfDiamonds = new Image(stream);
		Pair<Character, Integer> sixOfDiamondsPair = new Pair<Character, Integer>('D', 6);
		images.put(sixOfDiamondsPair, sixOfDiamonds);

		stream = new FileInputStream(card_file_6_of_hearts);
		Image sixOfHearts = new Image(stream);
		Pair<Character, Integer> sixOfHeartsPair = new Pair<Character, Integer>('H', 6);
		images.put(sixOfHeartsPair, sixOfHearts);

		stream = new FileInputStream(card_file_6_of_spades);
		Image sixOfSpades = new Image(stream);
		Pair<Character, Integer> sixOfSpadesPair = new Pair<Character, Integer>('S', 6);
		images.put(sixOfSpadesPair, sixOfSpades);

		stream = new FileInputStream(card_file_7_of_clubs);
		Image sevenOfClubs = new Image(stream);
		Pair<Character, Integer> sevenOfClubsPair = new Pair<Character, Integer>('C', 7);
		images.put(sevenOfClubsPair, sevenOfClubs);

		stream = new FileInputStream(card_file_7_of_diamonds);
		Image sevenOfDiamonds = new Image(stream);
		Pair<Character, Integer> sevenOfDiamondsPair = new Pair<Character, Integer>('D', 7);
		images.put(sevenOfDiamondsPair, sevenOfDiamonds);

		stream = new FileInputStream(card_file_7_of_hearts);
		Image sevenOfHearts = new Image(stream);
		Pair<Character, Integer> sevenOfHeartsPair = new Pair<Character, Integer>('H', 7);
		images.put(sevenOfHeartsPair, sevenOfHearts);

		stream = new FileInputStream(card_file_7_of_spades);
		Image sevenOfSpades = new Image(stream);
		Pair<Character, Integer> sevenOfSpadesPair = new Pair<Character, Integer>('S', 7);
		images.put(sevenOfSpadesPair, sevenOfSpades);

		stream = new FileInputStream(card_file_8_of_clubs);
		Image eightOfClubs = new Image(stream);
		Pair<Character, Integer> eightOfClubsPair = new Pair<Character, Integer>('C', 8);
		images.put(eightOfClubsPair, eightOfClubs);

		stream = new FileInputStream(card_file_8_of_diamonds);
		Image eightOfDiamonds = new Image(stream);
		Pair<Character, Integer> eightOfDiamondsPair = new Pair<Character, Integer>('D', 8);
		images.put(eightOfDiamondsPair, eightOfDiamonds);

		stream = new FileInputStream(card_file_8_of_hearts);
		Image eightOfHearts = new Image(stream);
		Pair<Character, Integer> eightOfHeartsPair = new Pair<Character, Integer>('H', 8);
		images.put(eightOfHeartsPair, eightOfHearts);

		stream = new FileInputStream(card_file_8_of_spades);
		Image eightOfSpades = new Image(stream);
		Pair<Character, Integer> eightOfSpadesPair = new Pair<Character, Integer>('S', 8);
		images.put(eightOfSpadesPair, eightOfSpades);

		stream = new FileInputStream(card_file_9_of_clubs);
		Image nineOfClubs = new Image(stream);
		Pair<Character, Integer> nineOfClubsPair = new Pair<Character, Integer>('C', 9);
		images.put(nineOfClubsPair, nineOfClubs);

		stream = new FileInputStream(card_file_9_of_diamonds);
		Image nineOfDiamonds = new Image(stream);
		Pair<Character, Integer> nineOfDiamondsPair = new Pair<Character, Integer>('D', 9);
		images.put(nineOfDiamondsPair, nineOfDiamonds);

		stream = new FileInputStream(card_file_9_of_hearts);
		Image nineOfHearts = new Image(stream);
		Pair<Character, Integer> nineOfHeartsPair = new Pair<Character, Integer>('H', 9);
		images.put(nineOfHeartsPair, nineOfHearts);

		stream = new FileInputStream(card_file_9_of_spades);
		Image nineOfSpades = new Image(stream);
		Pair<Character, Integer> nineOfSpadesPair = new Pair<Character, Integer>('S', 9);
		images.put(nineOfSpadesPair, nineOfSpades);

		stream = new FileInputStream(card_file_10_of_clubs);
		Image tenOfClubs = new Image(stream);
		Pair<Character, Integer> tenOfClubsPair = new Pair<Character, Integer>('C', 10);
		images.put(tenOfClubsPair, tenOfClubs);

		stream = new FileInputStream(card_file_10_of_diamonds);
		Image tenOfDiamonds = new Image(stream);
		Pair<Character, Integer> tenOfDiamondsPair = new Pair<Character, Integer>('D', 10);
		images.put(tenOfDiamondsPair, tenOfDiamonds);

		stream = new FileInputStream(card_file_10_of_hearts);
		Image tenOfHearts = new Image(stream);
		Pair<Character, Integer> tenOfHeartsPair = new Pair<Character, Integer>('H', 10);
		images.put(tenOfHeartsPair, tenOfHearts);

		stream = new FileInputStream(card_file_10_of_spades);
		Image tenOfSpades = new Image(stream);
		Pair<Character, Integer> tenOfSpadesPair = new Pair<Character, Integer>('S', 10);
		images.put(tenOfSpadesPair, tenOfSpades);

		stream = new FileInputStream(card_file_jack_of_clubs);
		Image jackOfClubs = new Image(stream);
		Pair<Character, Integer> jackOfClubsPair = new Pair<Character, Integer>('C', 11);
		images.put(jackOfClubsPair, jackOfClubs);

		stream = new FileInputStream(card_file_jack_of_diamonds);
		Image jackOfDiamonds = new Image(stream);
		Pair<Character, Integer> jackOfDiamondsPair = new Pair<Character, Integer>('D', 11);
		images.put(jackOfDiamondsPair, jackOfDiamonds);

		stream = new FileInputStream(card_file_jack_of_hearts);
		Image jackOfHearts = new Image(stream);
		Pair<Character, Integer> jackOfHeartsPair = new Pair<Character, Integer>('H', 11);
		images.put(jackOfHeartsPair, jackOfHearts);

		stream = new FileInputStream(card_file_jack_of_spades);
		Image jackOfSpades = new Image(stream);
		Pair<Character, Integer> jackOfSpadesPair = new Pair<Character, Integer>('S', 11);
		images.put(jackOfSpadesPair, jackOfSpades);

		stream = new FileInputStream(card_file_queen_of_clubs);
		Image queenOfClubs = new Image(stream);
		Pair<Character, Integer> queenOfClubsPair = new Pair<Character, Integer>('C', 12);
		images.put(queenOfClubsPair, queenOfClubs);

		stream = new FileInputStream(card_file_queen_of_diamonds);
		Image queenOfDiamonds = new Image(stream);
		Pair<Character, Integer> queenOfDiamondsPair = new Pair<Character, Integer>('D', 12);
		images.put(queenOfDiamondsPair, queenOfDiamonds);

		stream = new FileInputStream(card_file_queen_of_hearts);
		Image queenOfHearts = new Image(stream);
		Pair<Character, Integer> queenOfHeartsPair = new Pair<Character, Integer>('H', 12);
		images.put(queenOfHeartsPair, queenOfHearts);

		stream = new FileInputStream(card_file_queen_of_spades);
		Image queenOfSpades = new Image(stream);
		Pair<Character, Integer> queenOfSpadesPair = new Pair<Character, Integer>('S', 12);
		images.put(queenOfSpadesPair, queenOfSpades);

		stream = new FileInputStream(card_file_king_of_clubs);
		Image kingOfClubs = new Image(stream);
		Pair<Character, Integer> kingOfClubsPair = new Pair<Character, Integer>('C', 13);
		images.put(kingOfClubsPair, kingOfClubs);

		stream = new FileInputStream(card_file_king_of_diamonds);
		Image kingOfDiamonds = new Image(stream);
		Pair<Character, Integer> kingOfDiamondsPair = new Pair<Character, Integer>('D', 13);
		images.put(kingOfDiamondsPair, kingOfDiamonds);

		stream = new FileInputStream(card_file_king_of_hearts);
		Image kingOfHearts = new Image(stream);
		Pair<Character, Integer> kingOfHeartsPair = new Pair<Character, Integer>('H', 13);
		images.put(kingOfHeartsPair, kingOfHearts);

		stream = new FileInputStream(card_file_king_of_spades);
		Image kingOfSpades = new Image(stream);
		Pair<Character, Integer> kingOfSpadesPair = new Pair<Character, Integer>('S', 13);
		images.put(kingOfSpadesPair, kingOfSpades);

		stream = new FileInputStream(card_file_ace_of_clubs);
		Image aceOfClubs = new Image(stream);
		Pair<Character, Integer> aceOfClubsPair = new Pair<Character, Integer>('C', 14);
		images.put(aceOfClubsPair, aceOfClubs);

		stream = new FileInputStream(card_file_ace_of_diamonds);
		Image aceOfDiamonds = new Image(stream);
		Pair<Character, Integer> aceOfDiamondsPair = new Pair<Character, Integer>('D', 14);
		images.put(aceOfDiamondsPair, aceOfDiamonds);

		stream = new FileInputStream(card_file_ace_of_hearts);
		Image aceOfHearts = new Image(stream);
		Pair<Character, Integer> aceOfHeartsPair = new Pair<Character, Integer>('H', 14);
		images.put(aceOfHeartsPair, aceOfHearts);

		stream = new FileInputStream(card_file_ace_of_spades);
		Image aceOfSpades = new Image(stream);
		Pair<Character, Integer> aceOfSpadesPair = new Pair<Character, Integer>('S', 14);
		images.put(aceOfSpadesPair, aceOfSpades);
	}

	// -------------------------

	/*
	 * deal_hands
	 * 
	 * deals hands to game players
	 */
	private void deal_hands(HashMap<Pair<Character, Integer>, Image> images) {
		dealer.dealHand();
		p1Hand = dealer.dealHand();
		p2Hand = dealer.dealHand();
		for (int i = 0; i < 3; i++) {
			playerOne.addPlayerHand(p1Hand.get(i));
			playerTwo.addPlayerHand(p2Hand.get(i));
		}
		ArrayList<Card> p1Cards = playerOne.getPlayerHand();
		ArrayList<Card> p2Cards = playerTwo.getPlayerHand();
		ArrayList<Card> dealerCards = dealer.getDealerHand();

		ArrayList<Pair<Character, Integer>> player1CardValues = new ArrayList<>();
		ArrayList<Pair<Character, Integer>> player2CardValues = new ArrayList<>();
		ArrayList<Pair<Character, Integer>> dealerCardValues = new ArrayList<>();

		Pair<Character, Integer> currentCard;
		for (int i = 0; i < 3; i++) {
			currentCard = new Pair<>(p1Cards.get(i).getSuit(), p1Cards.get(i).getValue());
			player1CardValues.add(currentCard);
			currentCard = new Pair<>(p2Cards.get(i).getSuit(), p2Cards.get(i).getValue());
			player2CardValues.add(currentCard);
			currentCard = new Pair<>(dealerCards.get(i).getSuit(), dealerCards.get(i).getValue());
			dealerCardValues.add(currentCard);
		}
		Player1_Card1.setImage(images.get(player1CardValues.get(0)));
		Player1_Card2.setImage(images.get(player1CardValues.get(1)));
		Player1_Card3.setImage(images.get(player1CardValues.get(2)));
		Player2_Card1.setImage(images.get(player2CardValues.get(0)));
		Player2_Card2.setImage(images.get(player2CardValues.get(1)));
		Player2_Card3.setImage(images.get(player2CardValues.get(2)));
	}

	// -------------------------

	/*
	 * player_wagers
	 * 
	 * sets players' wagers
	 */
	private boolean player_wagers() {
		Player1_ConfirmBets.setOnAction(b -> {
			if (Player1_AnteBetTextField.getText() == "" || Player1_PairPlusTextField.getText() == "") {
				CurrentMsg.setText("Please enter bets for player one");
			} else {
				int p1_AB = Integer.parseInt(Player1_AnteBetTextField.getText());
				int p1_PP = Integer.parseInt(Player1_PairPlusTextField.getText());

				playerOne.setAnteBet(p1_AB);
				playerOne.setPPBet(p1_PP);

				CurrentMsg.setText("Player one's bets set.");
				listView.getItems().add("Player one ante bet: $" + String.valueOf(playerOne.getAnteBet()));
				listView.getItems().add("Player one pair-plus bet: $" + String.valueOf(playerOne.getPPBet()));

				// disable bet buttons for current round
				Player1_ConfirmBets.setDisable(true);
				activeGame = true;
			}
		});

		Player2_ConfirmBets.setOnAction(c -> {
			if (Player2_AnteBetTextField.getText() == "" || Player2_PairPlusTextField.getText() == "") {
				CurrentMsg.setText("Please enter bets for player two");
			} else {
				int p2_AB = Integer.parseInt(Player2_AnteBetTextField.getText());
				int p2_PP = Integer.parseInt(Player2_PairPlusTextField.getText());

				playerTwo.setAnteBet(p2_AB);
				playerTwo.setPPBet(p2_PP);

				CurrentMsg.setText("Player two's bets set.");
				listView.getItems().add("Player two ante bet: $" + String.valueOf(playerTwo.getAnteBet()));
				listView.getItems().add("Player two pair-plus bet: $" + String.valueOf(playerTwo.getPPBet()));

				// disable bet buttons for current round
				Player2_ConfirmBets.setDisable(true);
				activeGame = true;
			}
		});
		return activeGame;
	}

	// -------------------------

	/*
	 * initial_game_msg
	 * 
	 * Displays initial game message and sets players' starting total
	 */
	private void initial_game_msg() {
		// Initial game messages
		pause.setOnFinished(a -> {
			CurrentMsg.setText("Each player starts with $100.");
			pause = new PauseTransition(Duration.seconds(3));
			pause.play();
			pause.setOnFinished(b -> {
				CurrentMsg.setText("Please enter initial game bets, then hit confirm for each player.\n");
				Player1_CurrentBalance.setText("Current balance: $100");
				Player2_CurrentBalance.setText("Current balance: $100");

				// Setting players' (object) totals
				playerOne.setTotalWinnings(100);
				playerTwo.setTotalWinnings(100);
				listView.getItems().add("Player One Total: $" + String.valueOf(playerOne.getTotalWinnings()));
				listView.getItems().add("Player Two Total: $" + String.valueOf(playerTwo.getTotalWinnings()));

			});
		});
	}

	// -------------------------

	/*
	 * new_game_window
	 * 
	 * generates a new game
	 */
	private void new_game_window(Stage primaryStage, Scene curr_scene) {
		Button fresh_game = new Button("Starting a New Game.");
		StackPane r = new StackPane();
		r.setBackground(new Background(new BackgroundFill(randomColor(), CornerRadii.EMPTY, Insets.EMPTY)));
		r.getChildren().add(fresh_game);

		Stage game_msg = new Stage();
		game_msg.setTitle("Game Message.");
		Scene sc = new Scene(r, 300, 75);
		game_msg.setScene(sc);
		game_msg.show();

		fresh_game.setOnAction(o -> {
			resetGame();
			game_msg.close();
			listView.getItems().add("Starting round " + round_counter);
			primaryStage.setScene(curr_scene);
		});
	}

	// -------------------------

	/*
	 * player1_fold
	 * 
	 * Player 1 Fold Event Handler
	 */
	private boolean player1_fold() {
		Player1_Fold.setOnAction(b -> {
			// Disable player for current round and subtract bets

			CurrentMsg.setText("Player one choose to fold. ");

			pause = new PauseTransition(Duration.seconds(3));
			pause.play();

			pause.setOnFinished(a -> {
				CurrentMsg.setText("Player one looses current round bets. ");
			});

			player1_foldStatus = true;
			Player1_Fold.setDisable(true);
			Player1_PlayWager.setDisable(true);
			Player1_BetAll.setDisable(true);
			Player1_WagerTextField.setPromptText("Player folded.");
			Player1_WagerTextField.setDisable(true);

			playerOne.setTotalWinnings(playerOne.getTotalWinnings()
					- (playerOne.getAnteBet() + playerOne.getPPBet() + playerOne.getPlayBet()));
			Player1_CurrentBalance.setText("Current balance: $" + playerOne.getTotalWinnings());

			listView.getItems().add("Player one loses: $"
					+ String.valueOf(playerOne.getAnteBet() + playerOne.getPPBet() + playerOne.getPlayBet()));

			// reset all bets
			playerOne.setAnteBet(0);
			playerOne.setPPBet(0);
			playerOne.setPlayBet(0);

			playerOne_wager_set = true;

		});
		return playerOne_wager_set;
	}

	// -------------------------

	/*
	 * player2_fold
	 * 
	 * Player 2 Fold Event Handler
	 */
	private boolean player2_fold() {
		Player2_Fold.setOnAction(c -> {
			// Disable player for current round, and subtract bets
			CurrentMsg.setText("Player two choose to fold. ");

			pause = new PauseTransition(Duration.seconds(3));
			pause.play();

			pause.setOnFinished(a -> {
				CurrentMsg.setText("Player two looses current round bets. ");
			});

			player2_foldStatus = true;
			Player2_Fold.setDisable(true);
			Player2_PlayWager.setDisable(true);
			Player2_BetAll.setDisable(true);

			Player2_WagerTextField.setPromptText("Player folded.");
			Player2_WagerTextField.setDisable(true);

			playerTwo.setTotalWinnings(playerTwo.getTotalWinnings()
					- (playerTwo.getAnteBet() + playerTwo.getPPBet() + playerTwo.getPlayBet()));
			Player2_CurrentBalance.setText("Current balance: $" + playerTwo.getTotalWinnings());

			listView.getItems().add("Player two loses: $"
					+ String.valueOf(playerTwo.getAnteBet() + playerTwo.getPPBet() + playerTwo.getPlayBet()));

			// reset all bets
			playerTwo.setAnteBet(0);
			playerTwo.setPPBet(0);
			playerTwo.setPlayBet(0);
			playerTwo_wager_set = true;
		});
		return playerTwo_wager_set;
	}

	// -------------------------

	/*
	 * calculate_payouts_and_get_winner
	 * 
	 * calculates the pay outs for current round
	 */
	private void calculate_payouts_and_get_winner() {
		// player one folds, otherwise calculate pay out
		if (player1_foldStatus == true) {
			listView.getItems().add("Player 1 vs Dealer: Player folded, dealer wins");
			// dealer wins
		} else if (ThreeCardLogic.compareHands(dealer.getDealerHand(), playerOne.getPlayerHand()) == 1) {
			listView.getItems().add("Player 1 vs Dealer: Dealer Won");
			playerOne.setTotalWinnings(playerOne.getTotalWinnings()
					- (playerOne.getAnteBet() + playerOne.getPPBet() + playerOne.getPlayBet()));
			Player1_CurrentBalance.setText("Current balance: $" + playerOne.getTotalWinnings());
			listView.getItems().add("Player one loses: $"
					+ String.valueOf(playerOne.getAnteBet() + playerOne.getPPBet() + playerOne.getPlayBet()));
			// player won
		} else if ((ThreeCardLogic.compareHands(dealer.getDealerHand(), playerOne.getPlayerHand()) == 2)) {
			listView.getItems().add("Player 1 vs Dealer: Player Won");
			playerOne.setTotalWinnings(playerOne.getTotalWinnings()
					+ ThreeCardLogic.evalPPWinnings(playerOne.getPlayerHand(), playerOne.getAnteBet()));
			Player1_CurrentBalance.setText("Current balance: $" + playerOne.getTotalWinnings());
			listView.getItems().add("Player one wins: $"
					+ String.valueOf(playerOne.getAnteBet() + playerOne.getPPBet() + playerOne.getPlayBet()));
			// tie
		} else {
			listView.getItems().add("Player 1 vs Dealer: Tie");
			Player1_CurrentBalance.setText("Current balance: $" + playerOne.getTotalWinnings());
		}

		// player two folds, otherwise calculate pay out
		if (player2_foldStatus == true) {
			listView.getItems().add("Player 2 vs Dealer: Player folded, dealer wins");
			// dealer wins
		} else if (ThreeCardLogic.compareHands(dealer.getDealerHand(), playerTwo.getPlayerHand()) == 1) {
			listView.getItems().add("Player 2 vs Dealer: Dealer Won");
			playerTwo.setTotalWinnings(playerTwo.getTotalWinnings()
					- (playerTwo.getAnteBet() + playerTwo.getPPBet() + playerTwo.getPlayBet()));
			Player2_CurrentBalance.setText("Current balance: $" + playerTwo.getTotalWinnings());
			listView.getItems().add("Player two loses: $"
					+ String.valueOf(playerTwo.getAnteBet() + playerTwo.getPPBet() + playerTwo.getPlayBet()));
			// player won
		} else if (ThreeCardLogic.compareHands(dealer.getDealerHand(), playerTwo.getPlayerHand()) == 2) {
			listView.getItems().add("Player 2 vs Dealer: Player Won");
			playerTwo.setTotalWinnings(playerTwo.getTotalWinnings()
					+ ThreeCardLogic.evalPPWinnings(playerTwo.getPlayerHand(), playerTwo.getAnteBet()));
			Player2_CurrentBalance.setText("Current balance: $" + playerTwo.getTotalWinnings());
			listView.getItems().add("Player two wins: $"
					+ String.valueOf(playerTwo.getAnteBet() + playerTwo.getPPBet() + playerTwo.getPlayBet()));
		} else {
			listView.getItems().add("Player 2 vs Dealer: Tie");
			Player2_CurrentBalance.setText("Current balance: $" + playerTwo.getTotalWinnings());
		}
		CurrentMsg.setText("Processing complete. Check the results in the left list view!");
	}

	// -------------------------

	/*
	 * reset_round
	 * 
	 * resets all components needed to start next round
	 */
	private void reset_round() {
		pause = new PauseTransition(Duration.seconds(5));
		pause.play();

		pause.setOnFinished(a -> {
			CurrentMsg.setText("Please take your time looking at the results.");
			pause = new PauseTransition(Duration.seconds(5));
			pause.play();
			pause.setOnFinished(b -> {
				CurrentMsg.setText("Whenever you are ready, press play again to start next round.");
				Deal.setDisable(true);
				Play.setDisable(false);

				// clear all hands
				dealer.clearDealerHand();
				playerOne.clearPlayerHand();
				playerTwo.clearPlayerHand();

				playerOne.setAnteBet(0);
				playerOne.setPPBet(0);
				playerOne.setPlayBet(0);

				playerTwo.setAnteBet(0);
				playerTwo.setPPBet(0);
				playerTwo.setPlayBet(0);

				Player1_Card1.setImage(DefaultImage);
				Player1_Card1.setFitHeight(150);
				Player1_Card1.setFitWidth(100);

				Player1_Card2.setImage(DefaultImage);
				Player1_Card2.setFitHeight(150);
				Player1_Card2.setFitWidth(100);

				Player1_Card3.setImage(DefaultImage);
				Player1_Card3.setFitHeight(150);
				Player1_Card3.setFitWidth(100);

				Player2_Card1.setImage(DefaultImage);
				Player2_Card1.setFitHeight(150);
				Player2_Card1.setFitWidth(100);

				Player2_Card2.setImage(DefaultImage);
				Player2_Card2.setFitHeight(150);
				Player2_Card2.setFitWidth(100);

				Player2_Card3.setImage(DefaultImage);
				Player2_Card3.setFitHeight(150);
				Player2_Card3.setFitWidth(100);

				Dealer_Card1.setImage(DefaultImage);
				Dealer_Card1.setFitHeight(150);
				Dealer_Card1.setFitWidth(100);

				Dealer_Card2.setImage(DefaultImage);
				Dealer_Card2.setFitHeight(150);
				Dealer_Card2.setFitWidth(100);

				Dealer_Card3.setImage(DefaultImage);
				Dealer_Card3.setFitHeight(150);
				Dealer_Card3.setFitWidth(100);

				Player1_CurrentBalance.setText("Current balance: $" + playerOne.getTotalWinnings());
				Player2_CurrentBalance.setText("Current balance: $" + playerTwo.getTotalWinnings());

				Player1_AnteBetTextField.clear();
				Player1_PairPlusTextField.clear();
				Player2_AnteBetTextField.clear();
				Player2_PairPlusTextField.clear();
				Player1_WagerTextField.clear();
				Player2_WagerTextField.clear();

				Player1_AnteBetTextField.setPromptText("Enter your ante bet here ($5-$25)");
				Player1_PairPlusTextField.setPromptText("Enter your optional pair plus bet here($5-$25, 0 to skip)");
				Player2_AnteBetTextField.setPromptText("Enter your ante bet here ($5-$25)");
				Player2_PairPlusTextField.setPromptText("Enter your optional pair plus bet here($5-$25, 0 to skip)");
				Player1_WagerTextField.setPromptText("Please enter play wager ($5-$25).");
				Player2_WagerTextField.setPromptText("Please enter play wager ($5-$25).");

				Player1_ConfirmBets.setDisable(false);
				Player2_ConfirmBets.setDisable(false);
				Player1_AnteBetTextField.setDisable(false);
				Player1_PairPlusTextField.setDisable(false);
				Player2_AnteBetTextField.setDisable(false);
				Player2_PairPlusTextField.setDisable(false);
				Player1_WagerTextField.setDisable(true);
				Player2_WagerTextField.setDisable(true);

				playerOne_wager_set = false;
				playerTwo_wager_set = false;

				player1_foldStatus = false;
				player2_foldStatus = false;
				activeGame = false;
				betsPlaced = false;
				Player1_Fold.setDisable(true);
				Player2_Fold.setDisable(true);

				// next round
				round_counter++;
				listView.getItems().add("Starting round: " + round_counter);
			});
		});
	}

	// -------------------------

	/* option_menu_EventHandler */
	private void option_menu_EventHandler(Text main_scene, Button start, Stage primaryStage, Scene curr_scene) {
		current_scene = curr_scene;

		/* Options Event Handler */
		options_menu.setOnAction(a -> {

			// -------------------------

			/* New Look Event Handler */
			new_look.setOnAction(b -> {
				current_bckgrnd = get_rand_background();
				if (!activeGame)
					customize_main_scene(main_scene, start);
				else {
					customize_game_scene();
				}

			});

			// -------------------------

			/* Fresh Start Event Handler */
			fresh_start.setOnAction(c -> {
				new_game_window(primaryStage, current_scene);
				CurrentMsg.setText("Welcome to Three Card Poker.");
				pause = new PauseTransition(Duration.seconds(5));
				pause.play();
				initial_game_msg();
			});

			// -------------------------

			/* Exit Event Handler */
			exit.setOnAction(d -> {
				listView.getItems().add("Exiting Game. Thank you for playing.");
				activeGame = false;
				primaryStage.close();
			});

			// -------------------------

			primaryStage.setScene(current_scene);
		});

		// -------------------------
	}

	// -------------------------

	/* start_EventHandler */
	private void start_EventHandler(Button start, Stage primaryStage, Scene curr_scene, BorderPane Game_pane) {
		start.setOnAction(a -> {
			Game_pane.setBackground(current_bckgrnd);

			pause = new PauseTransition(Duration.seconds(5));
			pause.play();
			listView.getItems().add("Starting round: " + round_counter);
			current_game_pane = Game_pane;
			current_scene = curr_scene;
			initial_game_msg();
			primaryStage.setScene(current_scene);
		});

	}

	// -------------------------

	/* play_EventHandler */
	private void play_EventHandler(HashMap<Pair<Character, Integer>, Image> images) {
		activeGame = player_wagers();

		Play.setOnAction(a -> {
			if (activeGame) {
				if (playerOne.getAnteBet() == 0 || playerTwo.getAnteBet() == 0) {
					// check user inputs bet values before pressing play
					CurrentMsg.setText("Please confirm all bets before pressing play.");
				} else {
					CurrentMsg.setText("Bets placed.");
					betsPlaced = true;
				}
			} else {
				CurrentMsg.setText("Bets must be placed before playing.");
			}

			if (betsPlaced) {
				Player1_AnteBetTextField.setText(String.valueOf("Ante bet: $" + playerOne.getAnteBet()));
				Player1_PairPlusTextField.setText(String.valueOf("Pair plus bet: $" + playerOne.getPPBet()));

				Player2_AnteBetTextField.setText(String.valueOf("Ante bet: $" + playerTwo.getAnteBet()));
				Player2_PairPlusTextField.setText(String.valueOf("Pair plus bet: $" + playerTwo.getPPBet()));

				deal_hands(images);
				CurrentMsg.setText("Please choose to either deal or fold.");
				Play.setDisable(true);
				Deal.setDisable(false);

				String player1Cards = "";
				String player2Cards = "";
				for (int i = 0; i < p1Hand.size(); i++) {
					player1Cards += p1Hand.get(i).getSuit() + ":" + p1Hand.get(i).getValue() + " ";
				}

				for (int i = 0; i < p2Hand.size(); i++) {
					player2Cards += p2Hand.get(i).getSuit() + ":" + p2Hand.get(i).getValue() + " ";
				}

				listView.getItems().add("Player 1 hand: " + player1Cards);
				listView.getItems().add("Player 2 hand: " + player2Cards);
				Player1_AnteBetTextField.setDisable(true);
				Player1_PairPlusTextField.setDisable(true);
				Player2_AnteBetTextField.setDisable(true);
				Player2_PairPlusTextField.setDisable(true);
				Player1_WagerTextField.setDisable(false);
				Player2_WagerTextField.setDisable(false);

				Player1_Fold.setDisable(false);
				Player2_Fold.setDisable(false);
				Player1_PlayWager.setDisable(false);
				Player2_PlayWager.setDisable(false);
			}
		});

	}

	// -------------------------

	/*
	 * deal_or_fold_EventHandler
	 * 
	 * handles whether the players choose to deal or fold
	 */
	private void deal_or_fold_EventHandler(HashMap<Pair<Character, Integer>, Image> images) {
		playerOne_wager_set = player1_fold();
		playerTwo_wager_set = player2_fold();

		// player one wager
		if (!playerOne_wager_set) {
			Player1_PlayWager.setOnAction(b -> {
				if (Player1_WagerTextField.getText() == "") {
					CurrentMsg.setText("Please enter your play bet before confirming.");
				} else {
					int p1_PW = Integer.parseInt(Player1_WagerTextField.getText());
					playerOne.setPlayBet(p1_PW);
					Player1_WagerTextField.setPromptText("Play Wager: $" + Player1_WagerTextField.getText());
					CurrentMsg.setText("Player one play wager set.");
					listView.getItems().add("Player one play wager: $" + String.valueOf(playerOne.getPlayBet()));
					playerOne_wager_set = true;
					Player1_PlayWager.setDisable(true);
					Player1_Fold.setDisable(true);
				}
			});
		}

		// player two wager
		if (!playerTwo_wager_set) {
			Player2_PlayWager.setOnAction(b -> {
				if (Player2_WagerTextField.getText() == "") {
					CurrentMsg.setText("Please enter your play bet before confirming.");
				} else {
					int p2_PW = Integer.parseInt(Player2_WagerTextField.getText());
					playerTwo.setPlayBet(p2_PW);
					Player2_WagerTextField.setPromptText("Play Wager: $" + Player2_WagerTextField.getText());
					CurrentMsg.setText("Player two play wager set.");
					listView.getItems().add("Player two play wager: $" + String.valueOf(playerTwo.getPlayBet()));
					playerTwo_wager_set = true;
					Player2_PlayWager.setDisable(true);
					Player2_Fold.setDisable(true);
				}
			});
		}

		Deal.setOnAction(a -> {
			if (playerOne_wager_set && playerTwo_wager_set) {
				Player1_Fold.setDisable(true);
				Player2_Fold.setDisable(true);
				Player1_BetAll.setDisable(true);
				Player2_BetAll.setDisable(true);

				Player1_WagerTextField.setText("Play bet: $" + String.valueOf(playerOne.getPlayBet()));
				Player1_WagerTextField.setDisable(true);
				Player2_WagerTextField.setText("Play bet: $" + String.valueOf(playerTwo.getPlayBet()));
				Player2_WagerTextField.setDisable(true);

				Pair<Character, Integer> currentCard;
				ArrayList<Card> dealerCards = dealer.getDealerHand();
				ArrayList<Pair<Character, Integer>> dealerCardValues = new ArrayList<>();

				for (int i = 0; i < 3; i++) {
					currentCard = new Pair<>(dealerCards.get(i).getSuit(), dealerCards.get(i).getValue());
					dealerCardValues.add(currentCard);
				}

				Dealer_Card1.setImage(images.get(dealerCardValues.get(0)));
				Dealer_Card2.setImage(images.get(dealerCardValues.get(1)));
				Dealer_Card3.setImage(images.get(dealerCardValues.get(2)));

				String dealer_cards = "";
				for (int i = 0; i < dealer.getDealerHand().size(); i++) {
					dealer_cards += dealer.getDealerHand().get(i).getSuit() + ":"
							+ dealer.getDealerHand().get(i).getValue() + " ";
				}

				listView.getItems().add("Dealer hand: " + dealer_cards);
				CurrentMsg.setText("Please wait while we check who won.");
				pause = new PauseTransition(Duration.seconds(3));
				pause.play();

				pause.setOnFinished(b -> {
					calculate_payouts_and_get_winner();
				});

				// start next round
				reset_round();
			} else {
				CurrentMsg.setText("Please enter both players' play wager to start round. ");
			}
		});
	}

	// -------------------------

	/* main */
	public static void main(String[] args) {
		launch(args);
	}

	// -------------------------

	/*
	 * start Implements GUI for application
	 */
	@Override
	public void start(Stage primaryStage) throws Exception {
		// -------------------------

		primaryStage.setTitle("Welcome to Project #2");

		// -------------------------

		/* Start scene text */
		Text main_scene_text = new Text("      Welcome to \nThree Card Poker");
		main_scene_text.setFont(Font.font("Times New Roman", FontWeight.BOLD, FontPosture.ITALIC, 50));
		main_scene_text.setFill(Color.DEEPSKYBLUE);

		// -------------------------

		/* Application Scenes */
		scenes = new HashMap<String, Scene>();

		// -------------------------

		/* Card Images */
		HashMap<Pair<Character, Integer>, Image> images = new HashMap<Pair<Character, Integer>, Image>();
		importImagesAndStoreInHashMap(images);

		// -------------------------

		/* Backgrounds */

		// Background files
		backgrounds = new ArrayList<Background>();
		String file_1 = "src/main/resources/images/pokerBackground1.jpg";
		String file_2 = "src/main/resources/images/pokerBackground2.jpg";
		String file_3 = "src/main/resources/images/pokerBackground3.jpg";
		String file_4 = "src/main/resources/images/pokerBackground4.jpg";
		String file_5 = "src/main/resources/images/pokerBackground5.jpg";

		Background DEFAULT = bckGrdImg(file_1); // DEFAULT
		Background COOL_GREEN = bckGrdImg(file_2); // COOL_GREEN
		Background BASIC_GREEN = bckGrdImg(file_3); // BASIC_GREEN
		Background BLUE = bckGrdImg(file_4); // BLUE
		Background NEW_GREEN = bckGrdImg(file_5); // NEW_GREEN

		// save all backgrounds
		backgrounds.add(DEFAULT);
		backgrounds.add(COOL_GREEN);
		backgrounds.add(BASIC_GREEN);
		backgrounds.add(BLUE);
		backgrounds.add(NEW_GREEN);

		// Game scenes
		Starting_Pane = new BorderPane();
		Game_Pane_1 = new BorderPane();

		// setting backgrounds
		Starting_Pane.setBackground(DEFAULT);
		Game_Pane_1.setBackground(DEFAULT);

		// -------------------------

		/* Main Scene */
		Button Start = customize_button("START", "Times New Roman", 20, 150, 50, Color.LIGHTGRAY, Color.BLACK);

		HBox game_intro = new HBox(main_scene_text);
		VBox start_menu = new VBox(game_intro, Start);

		start_menu.setSpacing(10);
		game_intro.setAlignment(Pos.CENTER);
		start_menu.setAlignment(Pos.CENTER);
		Starting_Pane.setCenter(start_menu);

		// -------------------------

		/* Options Menu Bar */
		options_menu = new Menu();
		new_look = new MenuItem();
		fresh_start = new MenuItem();
		exit = new MenuItem();

		this.options_MB = option_menu();
		Starting_Pane.setRight(options_MB);
		options_MB.setAlignment(Pos.TOP_RIGHT);

		// -------------------------

		/* Initial Game Scene */

		VBox MasterVBox = initial_game(images);
		VBox options_MB_copy_2 = option_menu();

		MasterVBox.setSpacing(20);
		MasterVBox.setAlignment(Pos.CENTER);
		this.Game_Pane_1.setCenter(MasterVBox);
		this.Game_Pane_1.setRight(options_MB_copy_2);
		options_MB_copy_2.setAlignment(Pos.TOP_RIGHT);

		// -------------------------

		/* Application Scenes */
		starting_scene = new Scene(Starting_Pane, 1200, 798);
		scene_1 = new Scene(Game_Pane_1, 1200, 798);

		// Save scenes
		scenes.put("START", starting_scene);
		scenes.put("ACTIVE_GAME", scene_1);

		// -------------------------

		/* Options Event Handler */
		option_menu_EventHandler(main_scene_text, Start, primaryStage, current_scene);

		// -------------------------

		/* Start Event Handler */
		start_EventHandler(Start, primaryStage, scene_1, Game_Pane_1);

		// -------------------------

		/* Play Event Handler */
		play_EventHandler(images);

		// -------------------------

		/* Deal or fold event handler */
		deal_or_fold_EventHandler(images);

		// -------------------------

		/* Show scene */
		if (primaryStage.getScene() == null) {
			current_game_pane = Starting_Pane;
			current_scene = starting_scene;
			current_bckgrnd = DEFAULT;
			primaryStage.setScene(current_scene);
		}
		primaryStage.setResizable(true);
		primaryStage.show();

		// -------------------------
	}

	// -------------------------
}

//---------------------------------------------
