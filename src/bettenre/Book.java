/**
    Store.java
    Reece Bettencourt
    04-14-2017
    
    main class of the application that emulates a book of spells
 */
package bettenre;

import javafx.application.Application;
import javafx.geometry.*;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;

/**main class of the application that emulates a book of spells
 *
 * @author Reece
 */
public class Book extends Application{
    //declare variables
    private Button nextC, nextP, prevP, prevC, add, edit, dlt, save, cancle;
    private Label title, subtitle, search_label, description, old_name,
     old_level, old_desc, old_chap, errors;
    private VBox main, page, input;
    private HBox top, bot, change_name, change_level, change_chap, 
     change_desc, end_input;
    private int current_chapter = 0;
    private int current_page = 0;
    private Chapter[] main_list = {new Chapter("Homepage"),
        new Chapter("Conjuring"), new Chapter("Elemental"),
        new Chapter("Nature"), new Chapter("Necromancy")};
    private TextField search, input_name, input_level;
    private TextArea input_description;
    private ComboBox input_chapter;
    private int save_modifier = 0;
    private Scene altscene, scene;
    private Stage main_stage;
    
    
    /** main method
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
    /**The start of the application
     *
     * @param primaryStage the primary stage the application will appear on
     */
    @Override
    public void start(Stage primaryStage){
        //initiate variables
        nextC = new Button("Next Chapter");
        nextP = new Button("Next Page");
        prevC = new Button("Previous Chapter");
        prevP = new Button("Previous Page");
        add = new Button("Add");
        edit = new Button("Edit this page");
        dlt = new Button("Delete this page");
        title = new Label("");
        subtitle = new Label("");
        search_label = new Label("Search by Name");
        description = new Label("");
        errors = new Label("");
        old_name = new Label("Name: ");
        old_chap = new Label("Chapter: ");
        old_level = new Label("Level: ");
        old_desc = new Label("Description: ");
        search = new TextField();
        input_name = new TextField();
        input_level = new TextField();
        input_description = new TextArea();
        input_chapter = new ComboBox();
        input_chapter.getItems().addAll("Conjuration", "Elemental", "Nature",
        "Necromancy");
        save = new Button("Save");
        cancle = new Button("Cancel");
        
        
        main = new VBox();
        page = new VBox();
        top = new HBox();
        bot = new HBox();
        input = new VBox();
        change_name = new HBox();
        change_chap = new HBox();
        change_level = new HBox();
        end_input = new HBox();
        
        //adjust properties
        input_chapter.getSelectionModel().selectFirst();
        add.setOnAction(e -> add());
        edit.setOnAction(e -> edit());
        dlt.setOnAction(e -> destroySpell());
        nextP.setOnAction(e -> change_page(1));
        prevP.setOnAction(e -> change_page(-1));
        nextC.setOnAction(e -> change_chapter(1));
        prevC.setOnAction(e -> change_chapter(-1));
        cancle.setOnAction(e -> cancle());
        save.setOnAction(e -> saveData());
        search.textProperty().addListener
            ((observable, oldValue, newValue) -> find_spell()); 
        
        //set children
        main.getChildren().addAll(top, page, bot);
        bot.getChildren().addAll(prevP, prevC, nextC, nextP);
        top.getChildren().addAll(add, edit, dlt, search_label, search);
        page.getChildren().addAll(title, subtitle, description);
        change_name.getChildren().addAll(old_name, input_name);
        change_level.getChildren().addAll(old_level, input_level);
        change_chap.getChildren().addAll(old_chap, input_chapter);
        end_input.getChildren().addAll(save, cancle);
        input.getChildren().addAll(change_name, change_level, change_chap, 
         old_desc, input_description, errors, end_input);
        
        //adjust layout
        description.setWrapText(true);
        errors.setWrapText(true);
        end_input.setAlignment(Pos.CENTER);
        change_name.setAlignment(Pos.CENTER);
        change_level.setAlignment(Pos.CENTER);
        change_chap.setAlignment(Pos.CENTER);
        input.setAlignment(Pos.CENTER);
        bot.setAlignment(Pos.CENTER);
        top.setAlignment(Pos.CENTER);
        page.setAlignment(Pos.CENTER);
        title.setAlignment(Pos.CENTER);
        subtitle.setAlignment(Pos.CENTER);
        description.setAlignment(Pos.CENTER);
        page.setPadding(new Insets(20, 20, 20, 20));
        bot.setPadding(new Insets(0, 10, 0, 10));
        
        
        altscene = new Scene(input);
        scene = new Scene(main);
        
        //set stage
        main_stage = primaryStage;
        primaryStage.setHeight(300);
        primaryStage.setWidth(500);
        primaryStage.setTitle("Book of Spells");
        primaryStage.setScene(scene);
        primaryStage.show();
        update_page();
    }
    
    /** changes the current page 
     *
     * @param direction the direction the user wants to go (1 or -1)
     * 
     */
    public void change_page(int direction){
        if(direction == 1){
            if(((current_page) == main_list[current_chapter].size()) || 
             (main_list[current_chapter].isEmpty())){
                change_chapter(1);
            }
            else{
                current_page++;
                update_page();
            }
        }
        else{
             if((current_page - 1) < 0){
                change_chapter(-1);
                current_page = main_list[current_chapter].size();
                update_page();
            }
            else{
                current_page--;
                update_page();
            }
        }
    }
    
    /**changes the chapter the user is in
     *
     * @param direction the direction the chapter is changing (1 or -1)
     */
    public void change_chapter(int direction){
        current_chapter = current_chapter + direction;
        current_page = 0;
        update_page();
    }
    
    /**displays the current page to the user
     *
     */
    public void update_page(){
        //check to see if current page is the first page of a chapter
        if(current_page == 0){
         show_title();   
         change_buttons();
        }
        else{
            
            Spell target = main_list[current_chapter].get(current_page -1);
            title.setText(target.getName());
            subtitle.setText("Level " + target.getLevel() + " " + 
             target.getChapter() + " Spell");
            description.setText(target.getDescription());
            change_buttons();
        }
    }
    
    /**shows the title page of a chapter
     *
     */
    public void show_title(){
        switch(current_chapter){
            case 0:
                title.setText("Welcome to the Digital Book of Spells");
                subtitle.setText(" ");
                description.setText("This magical tome of knowledge contains"
                 + " a wealth of magic spells that a powerful wizard can "
                 + "take advantage of: on the top and bottom of the pages"
                 + " you will find the books controls.");
                break;
            case 1:
                title.setText("Chapter one: Conjuration");
                subtitle.setText(" ");
                description.setText("The school of spells focuses on "
                 + "manifesting magic into physical forms, allowing the"
                 + " use to create barriers of energy to protect them "
                 + "or weapons to strike down enemys with, and "
                 + "masters of Conjuration can even conjour new life");
                break;
            case 2:
                title.setText("Chapter two: Elemental");
                subtitle.setText(" ");
                description.setText("Elemental magic grants the user the "
                 + "ability to bend the elements to their will, whether "
                 + "thats creating walls of stone, or spewing fire from"
                 + " their hands, elemtalists are the most versitile mages");
                break;
            case 3:
                title.setText("Chapter three: Nature");
                subtitle.setText(" ");
                description.setText("Through nature magic, sorcerers "
                 + "can guide all life around it, calling apon swarms "
                 + "of wildlife, or weaponising plants to even healing "
                 + "wounded beings");
                break;
            case 4:
                title.setText("Chapter four: Necromancy");
                subtitle.setText(" ");
                description.setText("The most common dark magic "
                 + "Necromancy defies the rules of life and death"
                 + " granting users to bring others back from the grave");
                break;
            default:
                title.setText("Error");
                subtitle.setText(" ");
                description.setText("");
                break;
        }
    }
        
    /**enables and disables buttons depending on the page the user is on
     *
     */
    public void change_buttons(){
        edit.setVisible(true);
        dlt.setVisible(true);
        prevC.setVisible(true);
        prevP.setVisible(true);
        nextC.setVisible(true);
        nextP.setVisible(true);
        if(current_page == 0){
            edit.setVisible(false);
            dlt.setVisible(false);
        }
        if(current_chapter == 0){
            prevP.setVisible(false);
            prevC.setVisible(false);
        }
        if(current_chapter == 4 && 
         current_page == main_list[current_chapter].size()){
            nextC.setVisible(false);
            nextP.setVisible(false);
        }
        if(current_chapter == 4){
            nextC.setVisible(false);
        }
    }
    
    /**prepares the screen to add a new spell
     *
     */
    public void add(){
        save_modifier = 1;
        input_name.setText("");
        input_level.setText("");
        input_description.setText("");
        change_chap.setVisible(true);
        main_stage.setScene(altscene);
    }
    
    /**prepares the screen to edit the current spell
     *
     */
    public void edit(){
        save_modifier = -1;
        input_name.setText(main_list[current_chapter].
         get(current_page -1).getName());
        input_level.setText(main_list[current_chapter].
         get(current_page -1).getLevel());
        input_description.setText(main_list[current_chapter].
         get(current_page -1).getDescription());
        change_chap.setVisible(false);
        main_stage.setScene(altscene);
    }
    
    /**returns to main screen without changing any spell data
     *
     */
    public void cancle(){
        save_modifier = 0;
        main_stage.setScene(scene);
        errors.setText("");
    }
    
    /** saves or edits data once input is validated
     *
     */
    public void saveData(){
        int valid = validate();
        //check for validation
        switch(valid){
            case 1:
                errors.setText("A spells name can only contain"
                 + " letters and must be unique");
                break;
            case 2:
                errors.setText("A spells level can only be an"
                 + " integer");
                break;
            case 0:
                errors.setText("A spells name can only contain"
                 + " letters and must be unique, its level must"
                 + " also be an integer");
                break;
                
            case 3:
                errors.setText("");
                
                //check if editing or saving
                if(save_modifier == -1){
                    //edit
                    main_list[current_chapter].editSpell(current_page - 1,
                     input_name.getText(), 
                     main_list[current_chapter].get(current_page - 1).getChapter(),
                     input_level.getText(), 
                     input_description.getText());
                }
                
                else if(save_modifier == 1){
                    //saves to selected chapter
                    switch((String)input_chapter.getValue()){
                        case "Conjuration":
                            main_list[1].createSpell(new Spell(
                             input_name.getText(),
                             "Conjuration",
                             input_level.getText(),
                             input_description.getText()));
                            break;
                        case "Elemental":
                            main_list[2].createSpell(new Spell(
                             input_name.getText(),
                             "Elemental",
                             input_level.getText(),
                             input_description.getText()));
                            break;
                        case "Nature":
                            main_list[3].createSpell(new Spell(
                             input_name.getText(),
                             "Nature",
                             input_level.getText(),
                             input_description.getText()));
                            break;
                        case "Necromancy":
                            main_list[4].createSpell(new Spell(
                             input_name.getText(),
                             "Necromancy",
                             input_level.getText(),
                             input_description.getText()));
                            break;
                    }
                }
                //return to main screen
                save_modifier = 0;
                main_stage.setScene(scene);
                update_page();
                break;
                
            default:
                //default if something goes horribly wrong
                System.out.println("wat");
                break;
                
        }
    }
    
    /** validates inputed data
     *
     * @return what the user inputted correctly  
     */
    public int validate() {
        int valid = 0;
        String new_name = input_name.getText();
        String new_level = input_level.getText();
        //ensures level is a number
        if (!new_level.isEmpty()) {
            if (new_level.matches("\\d+")) {
                valid += 1;
            }
        }
        
        //if user wants to add a new spell, must have a unique name
        //if user wants to edit spell it can have the same name it did
        //before
        if (!new_name.isEmpty()) {
            if(save_modifier == 1){
                if (new_name.matches("[a-zA-Z ]*") &&
                 main_list[1].searchName(new_name) == -1 &&
                 main_list[2].searchName(new_name) == -1 &&
                 main_list[3].searchName(new_name) == -1 &&
                 main_list[4].searchName(new_name) == -1) {
                    valid += 2;
                }
            }
            else{
                if (new_name.matches("[a-zA-Z ]*")) {
                    valid += 2;
                }
            }
        }
        return (valid);
    }
    
    /**deletes a spell
     *
     */
    public void destroySpell(){
        main_list[current_chapter].remove(current_page -1);
        main_list[current_chapter].spell_destroyed();
        change_page(-1);
    }
    
    /**searches for spell
     *
     */
    public void find_spell(){
        String target = search.getText();
        for(int i = 1; i < 5; i++){
            int result = main_list[i].searchName(target);
            if(result != -1){
                current_chapter = i;
                current_page = result + 1;
                update_page();
                search.getStyleClass().remove("notFound");
            }
            else{
                old_name.getStyleClass().add("notFound");
            }
            
        }
    }
}
    
    
    
    
    
