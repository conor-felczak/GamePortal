

import java.io.File;
import java.util.HashMap;
import java.util.Scanner;


public class Quiz implements GameWriteable{
        String winner;

        static Scanner sc = new Scanner(System.in);

        static HashMap<String, Integer> gameStats = new HashMap<>() {{
                put("Pirhanna Plant", 0);
                put("Chain Chomp", 0);
                put("Bullet Bill", 0);
                put("goomba", 0);
        }};

        public static void main(String[] args) throws Exception {
                // Create Categories
                Quiz q = new Quiz();

                q.play();

                System.out.println("press 'y' to play another game.");

                while (sc.hasNext() && sc.next().equals("y")) {
                        q.play();
                        System.out.println("press 'y' to play another game.");
                    }


        }

        @Override
        public void play() {
                Category pirhannaPlant = new Category("Pirhanna Plant",
                                "you are pretty lazy, but very perceptive");
                Category chainChomp = new Category("Chain Chomp", "You are eager and energetic "
                                + "but sometimes feel trapped by your own plans");
                Category bulletBill = new Category("Bullet Bill",
                                "You get angry easily and often pick fights");
                Category goomba = new Category("goomba", "You always chase your goal despite any obstacle");
                // Create Questions
                Question q1 = new Question("What's your favorite sport");
                // Attach Answers to Questions
                q1.possibleAnswers[0] = new Answer("Archery", bulletBill);
                q1.possibleAnswers[1] = new Answer("Sleeping",
                                pirhannaPlant);
                q1.possibleAnswers[2] = new Answer("Ultamite Frisbee", chainChomp);
                q1.possibleAnswers[3] = new Answer("Track", goomba);

                Question q2 = new Question("What's your favorite pajama sam quote");
                q2.possibleAnswers[0] = new Answer("Thunder and lightning aren’t so frightening", bulletBill);
                q2.possibleAnswers[1] = new Answer("No need to hide when it’s dark outside", pirhannaPlant);
                q2.possibleAnswers[2] = new Answer("Life’s rough when you lose your stuff", chainChomp);
                q2.possibleAnswers[3] = new Answer("You are what you eat from your head to your feet",
                                goomba);

                Question q3 = new Question("What's your favorite animal?");
                q3.possibleAnswers[0] = new Answer("Pistol Shrimp", bulletBill);
                q3.possibleAnswers[1] = new Answer("Pirhanna", pirhannaPlant);
                q3.possibleAnswers[2] = new Answer("Dog", chainChomp);
                q3.possibleAnswers[3] = new Answer("Pit Bull",
                                goomba);

                Question q4 = new Question("What’s your favorite music genre?");
                q4.possibleAnswers[0] = new Answer("Country", bulletBill);
                q4.possibleAnswers[1] = new Answer("Jazz/Lofi", pirhannaPlant);
                q4.possibleAnswers[2] = new Answer("None", chainChomp);
                q4.possibleAnswers[3] = new Answer("Rock",
                                goomba);

                Question q5 = new Question("What's your favorite ancient Greek punishment");
                q5.possibleAnswers[0] = new Answer("Prometheus and the eagle", bulletBill);
                q5.possibleAnswers[1] = new Answer("Tantalus and the fruit", pirhannaPlant);
                q5.possibleAnswers[2] = new Answer("Ripped apart by chariots", chainChomp);
                q5.possibleAnswers[3] = new Answer("Sisyphus and the boulder",
                                goomba);

                Question q6 = new Question("What's your favorite color");
                q6.possibleAnswers[0] = new Answer("White", bulletBill);
                q6.possibleAnswers[1] = new Answer("Red", pirhannaPlant);
                q6.possibleAnswers[2] = new Answer("Black", chainChomp);
                q6.possibleAnswers[3] = new Answer("Brown",
                                goomba);

                Question q7 = new Question("If a thousand dogs were running at you, what would you do");
                q7.possibleAnswers[0] = new Answer("fight them", bulletBill);
                q7.possibleAnswers[1] = new Answer("fall asleep", pirhannaPlant);
                q7.possibleAnswers[2] = new Answer("play with them", chainChomp);
                q7.possibleAnswers[3] = new Answer("run away",
                                goomba);

                Question q8 = new Question("What do you often dream about");
                q8.possibleAnswers[0] = new Answer("Living in a western movie", bulletBill);
                q8.possibleAnswers[1] = new Answer("falling asleep in the dream", pirhannaPlant);
                q8.possibleAnswers[2] = new Answer("flying", chainChomp);
                q8.possibleAnswers[3] = new Answer(
                                "building like a giant human pyramid like how those sports teams do but then like walking around as the pyramid and  also attacking any plumber you see",
                                goomba);

                // ... more questions here

                // For each question, ask, read input, store answer.
                gameIntro();
                Question[] qList = { q1, q2, q3, q4, q5, q6, q7, q8 };
                for (Question q : qList) {
                        Category c = q.ask(sc);
                        c.points++;
                }
                // Get most common category from the questions asked
                // Return Category
                Category[] cList = { bulletBill,  pirhannaPlant,  chainChomp, goomba };
                // these need to be in the same order or the points will be incorrect!
                int index = getMostPopularCatIndex(cList);

                //System.out.println(getMostPopularCatIndex(cList));
                System.out.println("If you were a Mario enemy game, you would be " + cList[index].label + ". ");
                System.out.println(cList[index].description);
                winner = cList[index].label;



                gameStats.put(cList[index].label, gameStats.get(cList[index].label) + 1);

                String statsString = gameStats.toString();
                String[] statsStrings = statsString.split(", ");
                
                System.out.println("Your most chosen enemies are:");


                for (String stats : statsStrings) {
                        String statsNumberTwo = stats.replace("=", ",");
                        String statsNumberThree = statsNumberTwo.replaceAll("[{}]", "");
                        System.out.println(statsNumberThree);
                }
        }

        public static void gameIntro() {
                // requires 1 to keep going
                System.out.println("Which Mario Enemy are you?");
                System.out.println("You get to choose numbers 1-4 for every question. Enter '1' to play!");

                if (sc.hasNextInt() != true) {
                        System.err.println("UNDEFINED");
                        sc.next();
                        gameIntro();
                } else {
                        int play = sc.nextInt();

                        if (play != 1) {
                                System.out.println("Unidentifiable input. Please enter '1' to play");
                                sc.next();
                                gameIntro();
                        }
                }

        }

        // returns the index that is the max
        // the tie breaker is the first Category that has the count is the "max" :/
        public static int getMostPopularCatIndex(Category[] counts) {
                int maxCount = 0;
                int maxIndex = 0;
                for (int i = 0; i < counts.length; i++) {
                        if (counts[i].points > maxCount) {
                                maxCount = counts[i].points;
                                maxIndex = i;
                        }
                }
                return maxIndex;
        }


        @Override
        public String getGameName() {
                // TODO Auto-generated method stub
                return "Buzzfeed Quiz";
        }


        @Override
        public void writeHighScore(File f)  {
                // TODO Auto-generated method stub
                
        }




        @Override
        public boolean isHighScore(String score, String currentHighScore) {
                // TODO Auto-generated method stub
                score = winner;

                return true;
        }


        @Override
        public String getScore() {
                // TODO Auto-generated method stub
                return winner;
        }
}
