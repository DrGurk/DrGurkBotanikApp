package com.gurk.botanikapp;

import java.util.Vector;

/** Tag
 * defines a family/group of plants
 * name - name
 * question - string of a question, similar to questions in PlantInfo.
 *            it is read from the tag_foo file which has to look as follows:
 *            Tag display name (e.g. Wiesenpflanzen)
 *            Question
 *            Right answer
 *            Wrong answer
 *            Wrong answer
 *            Wrong answer
 *            Optional wrong answer
 *            ...
 *            however, currently only one question per tag is supported
 *  holders - a list of plants (identified by name) holding the tag, useful for question generation
 */
public class Tag {
    public String name;
    public String question;
    public boolean isPrimary;
    public Vector<String> holders = new Vector<String>();
}
