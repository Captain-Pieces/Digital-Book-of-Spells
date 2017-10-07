/**
    Chapter.java
    Reece Bettencourt
    04-13-2017
    
    class that adds new magic
    functionality to arraylists to emulate a chapter of a book
 */

package bettenre;

import java.util.ArrayList;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;
import java.io.PrintWriter;
import java.util.Collections;
import java.util.logging.Level;
import java.util.logging.Logger;
/**class that adds new magic
 * functionality to arraylists to emulate a chapter of a book
 *
 * @author Reece
 */
public class Chapter extends ArrayList<Spell> {

    private File file;
    private Scanner input;
    
    /**constructor 
     *
     * @param name name of the chapter
     */
    public Chapter(String name){
        this.file = new File(name +".txt");
        try {
            this.input = new Scanner(file).
             useDelimiter("(end_of_input)+");
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Chapter.class.getName()).log(Level.SEVERE, null, ex);
        }
        readFile();
       
    }

    /**reads a file to make list of spells
     *
     */
    public void readFile() {
        String chap;
        String level;
        String name;
        String desc;
        while (input.hasNext()) {
            chap = input.next();
            level = input.next();
            name = input.next();
            desc = input.next();
            this.add(new Spell(name, chap, level, desc));
          
        }
        //sorts spells alphabeticaly
        Collections.sort(this);
        input.close();
        
    }
    
    /**adds a new spell
     * @param spell new spell
     */
    public void createSpell(Spell spell) {
        this.add(spell);
        //append to file
        PrintWriter output = null;
        try {
            output = new PrintWriter(new FileWriter(file, true));
        } catch (IOException ex) {
            Logger.getLogger(Chapter.class.getName()).log(Level.SEVERE, null, ex);
        }
        output.print(spell.getChapter() + "end_of_input"
         + spell.getLevel() + "end_of_input" + spell.getName()
         + "end_of_input" + spell.getDescription()
         + "end_of_input");
        output.close();
        Collections.sort(this);

    }
    
    /**edits a pre existing spell
     *
     * @param location the page the spell is on
     * @param name new name
     * @param chap new chapter
     * @param level new level
     * @param desc new description
     */
    public void editSpell(int location, String name, String chap, 
     String level, String desc){
     
        this.get(location).setName(name);
        this.get(location).setChapter(chap);
        this.get(location).setLevel(level);
        this.get(location).setDescription(desc);
       
        //rewrite file with new data
        PrintWriter output = null;
        try {
            output = new PrintWriter(file);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Chapter.class.getName()).log(
             Level.SEVERE, null, ex);
        }
        
        for(int i = 0; i < this.size(); i++) {
            output.print(this.get(i).getChapter() + "end_of_input"
             + this.get(i).getLevel() + "end_of_input" + this.get(i).
              getName() + "end_of_input" + 
             this.get(i).getDescription() +"end_of_input");
        }
        output.close();
        Collections.sort(this);

    }
    
    /**search to see if a spell already exists
     * @param target name of the spell thats being looked for
     * @return the location of the spell, or -1 if it does not exist
     */
    public int searchName(String target){
        int valid = -1;
        for(int i = 0; i < this.size(); i++){
            if(this.get(i).getName().equals(target)){
                valid = i;
            }
        }
        return(valid);
    }

    /**Method thats called when a spell is removed
     **from the chapter; this method updates the text files
     **with the new information
     */
    public void spell_destroyed(){
        PrintWriter output = null;
        try {
            output = new PrintWriter(file);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Chapter.class.getName()).log(
             Level.SEVERE, null, ex);
        }
        
        for(int i = 0; i < this.size(); i++) {
            output.print(this.get(i).getChapter() + "end_of_input"
             + this.get(i).getLevel() + "end_of_input" + this.get(i).
              getName() + "end_of_input" + 
             this.get(i).getDescription() +"end_of_input");
        }
        output.close();
        Collections.sort(this);
    }
    
}
