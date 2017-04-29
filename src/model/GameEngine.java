package model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;
import java.util.Scanner;
import java.util.concurrent.ThreadLocalRandom;

public class GameEngine {

	Scanner reader = new Scanner(System.in);

	Player p1;
	Player ai;

	/* current pokemon */
	Pokemon p1Pokemon;
	Pokemon aiPokemon;

	/* need to implement party functionality */
	private ArrayList<Pokemon> allPokemon;
	// private ArrayList<Pokemon> p1Party;
	// private ArrayList<Pokemon> aiParty;

	private String winner = "";

	/* pokemon are still battling boolean */
	private boolean battling;

	/* user selects an attack */
	private boolean moveSelected = false;
	private int selectedAttack = 0;

	/* Default constructor */
	public GameEngine() {

		allPokemon = PokemonFactory.generatePokemon();
//		 int[] test = {3,3,3,3,3,3 };
//		 this.p1 = new Player("Dragon", selectPokemon(test));
		this.p1 = new Player();
		int[] aiTest = { 7, 7, 7, 7, 7, 7 };
		this.ai = new Player("Red", selectPokemon(aiTest));

		this.p1Pokemon = p1.getPokeParty().get(0);
		this.aiPokemon = ai.getPokeParty().get(0);

		// this.p1Party = p1.getPokeParty();
		// this.aiParty = ai.getPokeParty();

		battling = true;
		// beginning phase to init players/pokemon/party
		init();
		startBattleLoop();
	}

	/* SET NAMES // AND INIT PARTIES?! */
	private void init() {
		ai.setPlayerName("Red");
		ai.setPlayerId("AI");
		p1.setPlayerName("Dragon");
	}

	public ArrayList<Pokemon> selectPokemon(int[] test) {
		ArrayList<Pokemon> party = new ArrayList<Pokemon>();
		for (int i = 0; i < 6; i++) {
			Pokemon pp = new Pokemon(this.allPokemon.get(test[i]));
			party.add(pp);
		}
		return party;
	}

	/* init game loop */
	private void startBattleLoop() {
		// counter to keep track of # of turns
		int turnCounter = 0;

		// keep looping while pokemon are still battling
		while (battling) {
			++turnCounter;
			System.out.println("\nTurn " + turnCounter);
			SelectPhase();
			if (battling) {
				AttackPhase();
			}
		}
		System.out.println(winner + " wins!");
	}

	/* Phase to select moves or another pokemon */
	private void SelectPhase() {
		System.out.print(p1.getPlayerName() + " select a move for " + p1Pokemon.getName() + " " + p1Pokemon.getHp()
				+ " " + p1Pokemon.getSpeed() + " \n" + "\t1: " + p1Pokemon.getMoves().get(0).getName() + "\n" + "\t2: "
				+ p1Pokemon.getMoves().get(1).getName() + "\n" + "\t3: " + p1Pokemon.getMoves().get(2).getName() + "\n"
				+ "\t4: " + p1Pokemon.getMoves().get(3).getName());
		System.out.print("\nSelect move number, P to select a new pokemon, or 0 to exit: ");

		String read = reader.nextLine();
		if (read.equals("0")) {
			this.battling = false;
			this.winner = ai.getPlayerName();
		} else if (read.equals("P") || read.equals("p")) {
			SelectPokemon();
			this.moveSelected = false;
		} else if (Integer.parseInt(read) >= 0 && Integer.parseInt(read) <= 4) {
			this.selectedAttack = Integer.parseInt(read) - 1;
			this.moveSelected = true;
		} else{
			this.SelectPhase();
		}

	}

	/* attack phase */
	private void AttackPhase() {
		// randomly picks pokemon to attack if they have the same speed
		boolean random = false;
		if (p1Pokemon.getSpeed() == aiPokemon.getSpeed()) {
			Random r = new Random();
			random = r.nextBoolean();
		}

		// variables to keep track of which player/pokemon
		// is attacking first
		Player first = this.ai;
		Pokemon firstPokemon = this.aiPokemon;
		Player next = this.p1;
		Pokemon nextPokemon = this.p1Pokemon;

		// variables to keep track of damage
		int firstAtk = 0;
		int nextAtk = 0;

		// player's pokemon is slower than AI's pokemon
		if ((p1Pokemon.getSpeed() > aiPokemon.getSpeed() || random) && this.moveSelected) {
			first = this.p1;
			firstPokemon = this.p1Pokemon;
			next = this.ai;
			nextPokemon = this.aiPokemon;
		}

		// calculate and apply first attack damage
		firstAtk = AttackCalc(firstPokemon, nextPokemon, ((first.getPlayerId().equals("AI") ? 2 : selectedAttack)));
		System.out.println(first.getPlayerName() + "'s " + firstPokemon.getName() + " used "
				+ firstPokemon.getMoves().get(((first.getPlayerId().equals("AI") ? 2 : selectedAttack))).getName());
		System.out.println(next.getPlayerName() + "'s " + nextPokemon.getName() + " was hit for " + firstAtk
				+ " and now has " + ((nextPokemon.getHp() >= 0) ? nextPokemon.getHp() : "0") + " HP");

		// if attacked pokemon is still alive
		// AND an attack has been selected then
		// calculate and apply next attack damage
		if (nextPokemon.getHp() > 0 && this.moveSelected) {
			nextAtk = AttackCalc(nextPokemon, firstPokemon, ((next.getPlayerId().equals("AI") ? 2 : selectedAttack)));
			System.out.println(next.getPlayerName() + "'s " + nextPokemon.getName() + " used "
					+ nextPokemon.getMoves().get(((next.getPlayerId().equals("AI") ? 2 : selectedAttack))).getName());
			System.out.println(first.getPlayerName() + "'s " + firstPokemon.getName() + " was hit for " + nextAtk
					+ " and now has " + ((firstPokemon.getHp() >= 0) ? firstPokemon.getHp() : "0") + " HP");
		}

		// temp variables to check if any pokemon have fainted
		boolean pokemonFainted = false;
		Pokemon faintedPokemon = null;
		Player temp = null;
		// if any pokemon have fainted then assign them to temp variables
		if (nextPokemon.getHp() <= 0) {
			pokemonFainted = true;
			faintedPokemon = nextPokemon;
			temp = next;
		} else if (firstPokemon.getHp() <= 0) {
			pokemonFainted = true;
			faintedPokemon = firstPokemon;
			temp = first;
		}

		if (pokemonFainted) {
			pokemonFainted = false;
			System.out.println(temp.getPlayerName() + "'s " + faintedPokemon.getName() + " fainted!");
			// Choose another pokemon if there are some still alive in the party
			if (!this.knockedOut(temp.getPokeParty())) {
				// Select new pokemon for AI
				if (temp.getPlayerName().equals(ai.getPlayerName())) {
					// spawns next pokemon in AI party
					for (int i = 0; i < 6; i++) {
						if (ai.getPokeParty().get(i).getHp() >= 0 && aiPokemon.getHp() <= 0) {
							System.out.print(ai.getPlayerName() + " returned " + aiPokemon.getName());
							aiPokemon = ai.getPokeParty().get(i);
							System.out.println(" and sent out " + aiPokemon.getName());
						}
					}
					// select pokemon function for user
				} else {
					p1.getPokeParty().remove(p1.getPokeParty().indexOf(p1Pokemon));
					if (!p1.getPokeParty().isEmpty()) {
						SelectPokemon();
					}
				}
				// Player with fainted pokemon loses
			} else {
				this.winner = (temp.getPlayerId() == "AI") ? p1.getPlayerName() : ai.getPlayerName();
				battling = false;
			}
		}
	}

	/* Switch pokemon from party or select new pokemon */
	private void SelectPokemon() {
		System.out.println("\nSelect the number associated with the pokemon you want to switch out for"
				+ ((this.p1Pokemon.getHp() <= 0) ? ": " : " (0) to go back to battle phase:"));
		int count = 0;
		for (Pokemon pokemon : p1.getPokeParty()) {
			if (pokemon != p1Pokemon && pokemon.getHp() > 0) {
				System.out.println(
						p1.getPokeParty().indexOf(pokemon) + 1 + ": " + pokemon.getName() + " HP: " + pokemon.getHp());
				count++;
			}
		}

		int tempChosen = Integer.parseInt(reader.nextLine());

		if (tempChosen <= count && tempChosen > 0) {
			// p1.setMoveSelected(false);
			this.moveSelected = false;
			System.out.print(p1.getPlayerName() + " returned " + p1Pokemon.getName());

			p1Pokemon = p1.getPokeParty().get(tempChosen - 1);

			System.out.println(" and sent out " + p1Pokemon.getName() + " " + p1Pokemon.getHp());
		} else if (tempChosen == 0) {
			SelectPhase();
		} else {
			SelectPokemon();
		}
	}

	/* attack calculations have been moved to game engine */
	private int AttackCalc(Pokemon attacker, Pokemon defender, int attack) {
		double a = ((2 * attacker.getLevel()) / 5) + 2;
		double b = attacker.getMoves().get(attack).getBaseDamage();
		double c = attacker.getAttack();
		double d = defender.getDefence();
		double g = c / d;
		double e = ((a * b * g) / 50) + 2;
		double mod = 1 * ThreadLocalRandom.current().nextDouble(0.85, 1)
				* typeEffectiveness(attacker.getMoves().get(attack).getType(), defender.getType());
		defender.setHp(defender.getHp() - (int) (e * mod));
		return (int) (e * mod);
	}

	/* Checks type effectiveness */
	private double typeEffectiveness(Type attack, Type defence) {
		if (Arrays.asList(defence.weak).contains(attack)) {
			System.out.print("Very effective : ");
			return 2.0;
		} else if (Arrays.asList(defence.strong).contains(attack)) {
			System.out.print("Not very effective : ");
			return 0.5;
		} else if (Arrays.asList(defence.noEffect).contains(attack)) {
			System.out.print("No effect : ");
			return 0.0;
		}
		return 1.0;
	}

	/* checks if all pokemon in party are fainted */
	private boolean knockedOut(ArrayList<Pokemon> party) {
		for (Pokemon p : party) {
			if (p.getHp() >= 0) {
				return false;
			}
		}
		return true;
	}
}
