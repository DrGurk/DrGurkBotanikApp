package com.gurk.botanikapp;

import android.content.Context;

import java.util.Collections;
import java.util.Random;
import java.util.Vector;

/** QuizMaster
 *  the big one
 *  this class handles most of the stuff
 *
 */
public class QuizMaster {

    static Random rand = new Random();
    static boolean initialized = false;
    static Vector<Tag> tags = new Vector<Tag>();
    public static Vector<PlantInfo> plantInfos = new Vector<PlantInfo>();
    public static Vector<PlantInfo> plantsWithQuestions = new Vector<PlantInfo>();
    static Vector<Integer> questionTypes = new Vector<Integer>();


    static int gameMode = 0; //0 = normal, 1 = blitz, 2 = survival

    public static int score = 0;
    public static int correctQuestions = 0;
    public static int numQuestionsSurvival = 0;

    /** prepareQuestion
     * Prepares question types according to the specification in fragetypen
     *  boundary checks for 0-3
     * @param in
     */
    public static void prepareQuestions(Vector<Integer> in){
        for(Integer i : in){
            int tmp = i.intValue();
            tmp = tmp < 0 ? 0: tmp > 3 ? 3 : tmp;
            questionTypes.add(tmp);
        }
    }
    //public static void newGame(Context ctx){
     //   newGame(10, ctx);
    //}

    /** initializeTriviaQuestions
     *   initialization process for trivia questions, these are picked from a separate array
     * @param ctx
     */
    public static void initializeTriviaQuestions(final Context ctx){
        for(PlantInfo pi : plantInfos){
            if (pi.triviaQuestions.size() > 0){
                plantsWithQuestions.add(pi);
            }
        }
    }

    /** newGame
     * starts new game
     * @param ctx
     */
    public static void newGame( Context ctx){
        score = 0;
        correctQuestions = 0;
        questionTypes.clear();
        Vector<String> questionTypes = Utility.readFile("fragentypen", "raw",  ctx);
        Vector<Integer> ints = new Vector<Integer>();
            for (String s : questionTypes) {
                    ints.add(Integer.parseInt(s));
            }

        prepareQuestions(ints);
    }

    /** initialize
     *  reads tags, plants and questions from the raw folder and puts them into their respective
     *  fields
     * @param ctx
     */
    public static void initialize(final Context ctx){
        initialized = true;

        insertTags(Utility.readTags(ctx));
        System.out.println("Plant read");
        PlantInfo.getPlantInfos(Utility.readFile("tags", "raw",ctx), ctx);
        initializeTriviaQuestions(ctx);
    }

    /** inserts the name(id=0), question (id=1) and tag holders (id=2+, answers for multianswers)
     * into the holders vector
     * @param in
     */
    private static void insertTags(Vector<Vector<String>> in){
        for(Vector<String> vs : in){
            Tag t = new Tag();
            t.name = vs.elementAt(0);
            t.question = vs.elementAt(1);
            for(int i = 2; i < vs.size(); i++){
                t.holders.add(vs.elementAt(i));
            }
            tags.add(t);
        }
    }

    /** getFourChoiceQuestion
     * constructs a random FourChoiceQuestion
     * it always tries to find 4 possible names in the same tag,if there are not enough plants
     * in a tag, a random second tag will be added
     * @param ctx
     * @return
     */
    public static FourChoiceQuestion getFourChoiceQuestion(final Context ctx){
        int[] answers = new int[4];
        FourChoiceQuestion out = new FourChoiceQuestion();
        String correct; //tmp variable to find which one was the right one after shuffling
        String strtag; //tmp string name
        String sectag;
        int rng = -1;
        rng = rand.nextInt(plantInfos.size());
        PlantInfo pi;
        do{
            pi = plantInfos.elementAt(rng);
            out.numImages = pi.numImages;}
        while(out.numImages < 1); //loop until we find something with images
        correct = pi.name;
        strtag = pi.tags.elementAt(rand.nextInt(pi.tags.size()));

        out.answers.add(correct);

        Tag tag = new Tag();

        //look for our tag

        for(Tag t: tags){
            if(strtag.equals(t.name)){
                tag = t;
                break;
            }
        }
        if(tag.holders.size() < 4){ // if we have less than 4 holders we fill with different random images
            Tag secondtag = tags.elementAt(rand.nextInt(tags.size()));
            for(String s: tag.holders){
                if(!out.answers.contains(s)){
                    out.answers.add(s);
                }
            }
            int remainingQuestions = 4 - out.answers.size();
            for(int i = 0; i < remainingQuestions; i++){
                out.answers.add(secondtag.holders.elementAt(rand.nextInt(secondtag.holders.size())));
            }


        }
        else{
            for(int i = 1; i < 4; i++){
                while(true) {//repeatedly add random plant from tag
                    rng = rand.nextInt(tag.holders.size());
                    String s = tag.holders.elementAt(rng);
                    //String[] strs = s.split("\\(");
                    //s = strs[0];
                    if(!out.answers.contains(s)){//check if we already have this one
                        out.answers.add(s);
                        break;
                    }

                }
            }
            while(out.answers.size() < 4){
                out.answers.add("Error");
            }
        }
        Collections.shuffle(out.answers);
        out.correct = out.answers.indexOf(correct);
        return out;
    }

    /** getRandomTriviaQuestion(){
     * returns random trivia question from plantsWithQuestions
     * @return
     */
    public static TriviaQuestion getRandomTriviaQuestion(){

        PlantInfo plantInfo = plantsWithQuestions.elementAt(rand.nextInt(plantsWithQuestions.size()));

        int rng = rand.nextInt(plantInfo.triviaQuestions.size());
        TriviaQuestionData triviaQuestionData = plantInfo.triviaQuestions.elementAt(rng);
        TriviaQuestion out = new TriviaQuestion(triviaQuestionData);
        out.shuffle();
        return out;
    }

    /** getPlant
     *  search for plant by name
     * @param name
     */
    public static PlantInfo getPlant(String name){
        for (PlantInfo pi: plantInfos){
            if(pi.name.equals(name)){
                return pi;
            }
        }
        return new PlantInfo();
    }

    /** getMultiAnswerQuestion
     * constructs a Multianswerquestion
     * see MultiAnswer for how they work
     *
     * you can change numTrueAnswers and numFalseAnswers to suit the situation (used to be random)
     * @param ctx
     * @return
     */
    public static MultiAnswerQuestion getMultiAnswerQuestion(final Context ctx){
        MultiAnswerQuestion out = new MultiAnswerQuestion();
        //int numTrueAnswers = rand.nextInt(2) + 1;
        int numTrueAnswers = 1;
        int numFalseAnswers = 1;
        int rng = rand.nextInt(QuizMaster.tags.size());

        Vector<Integer> added = new Vector<Integer>();
        Tag tag = tags.elementAt(rng);
        while(tag.holders.size() < 4 || tag.question.equals("null")){
            tag = tags.elementAt(rand.nextInt(QuizMaster.tags.size()));
        }
        Vector<MultiAnswerData> dataVector = new Vector<MultiAnswerData>();
        int timeout = 0;
        for(int i = 0; i < numTrueAnswers; i++){
            rng = rand.nextInt(tag.holders.size());
            if(!added.contains(rng)) {
                added.add(rng);
                MultiAnswerData d = new MultiAnswerData(tag.holders.elementAt(rng), true);
                dataVector.add(d);
            } else{
                i--;
                if(timeout++ > 100){
                    for(int j = dataVector.size(); j < 8; j++){
                        MultiAnswerData to = new MultiAnswerData("Timeout", true);
                        dataVector.add(to);
                    }
                    break;
                }

            }
        }
        out.question = tag.question;

        //int numFalseAnswers = 6 - numTrueAnswers;

        timeout = 0;
        for(int i = 0; i < numFalseAnswers; i++){
            rng = rand.nextInt(QuizMaster.plantInfos.size());
            MultiAnswerData d = new MultiAnswerData(QuizMaster.plantInfos.elementAt(rng).name, false);
            if(!dataVector.contains(d) && !tag.holders.contains(d.str)){
                dataVector.add(d);
            } else{
                i--;
                if(timeout++ > 100){
                    for(int j = dataVector.size(); j < 8; j++){
                        MultiAnswerData to = new MultiAnswerData("Timeout", true);
                        dataVector.add(to);
                    }
                    break;
                }
            }
        }

        Collections.shuffle(dataVector);
        out.data = dataVector;
        return out;

    }
    private static void processTimeout(){

    }
}
