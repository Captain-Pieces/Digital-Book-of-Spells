/**
    Spell.java
    Reece Bettencourt
    04-14-2017
    
    class that defines a magic spell
 */
package bettenre;

/**class that defines a magic spell.
 *
 * @author Reece
 */
public class Spell implements Comparable<Spell> {
    public String name;
    private String chapter;
    private String level;
    private String description;
    
     /**
     * Constructs an empty Spell
     */
    public Spell() {
        name = " ";
        chapter = " ";
        level = "0";
        description = " ";
    }

    /**Main Constructor
     *
     * @param level the power level of the spell
     * @param chapter the chapter the spell belongs to
     * @param name the spells name
     * @param description an in depth description of how the spell works
     */
    public Spell(String name, String chapter, String level, 
     String description) {
        this.name = name;
        this.chapter = chapter;
        this.level = level;
        this.description = description;
    }

    /**get the spells name
     *
     * @return the spells name 
     */
    public String getName() {
        return name;
    }

    /**set a spells name
     *
     * @param name the Spells new name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**get the spells chapter
     *
     * @return the spells chapter 
     */
    public String getChapter() {
        return chapter;
    }

    /**set a spells chapter
     *
     * @param chapter the Spells new chapter
     */
    public void setChapter(String chapter) {
        this.chapter = chapter;
    }

    
    /**get the spells level
     *
     * @return the spells level 
     */
    public String getLevel() {
        return level;
    }

    /**set a spells level
     *
     * @param level the Spells new level
     */
    public void setLevel(String level) {
        this.level = level;
    }

    /**get the spells description
     *
     * @return the spells description 
     */
    public String getDescription() {
        return description;
    }

    /**set a spells Description
     *
     * @param description the Spells new description
     */
    public void setDescription(String description) {
        this.description = description;
    }
    
    @Override
    public int compareTo(Spell other) {
        return name.compareTo(other.name);
    }
    
    
}


