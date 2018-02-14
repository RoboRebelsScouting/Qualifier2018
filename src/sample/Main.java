package sample;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.*;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.input.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Box;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.stage.Modality;
import javafx.stage.Stage;

import javax.swing.*;
import javax.swing.text.html.ImageView;
import java.awt.*;
import java.io.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import static java.awt.SystemColor.text;
import static java.awt.SystemColor.window;
import static javafx.application.Platform.exit;
import static javafx.scene.paint.Color.MEDIUMBLUE;
import static javafx.scene.paint.Color.RED;

public class Main extends Application {

    public static String userDir = System.getProperty("user.home");
    //public static String dataSheetDir = "C:\\Users\\1153\\Documents\\Datasheets";
    public static String dataSheetDir = userDir + "\\Documents\\Datasheets";
    public Writer writer = null;

    public ArrayList<RobotData> robotList = new ArrayList<RobotData>();
    public List<AllianceData> adListRed = new ArrayList<AllianceData>();
    public List<AllianceData> adListBlue = new ArrayList<AllianceData>();
    //Do I still need a teamlist to auto-populate?
    //public List<Integer> teamList = new ArrayList<Integer>();

    public AllianceData allianceRed = new AllianceData();
    public AllianceData allianceBlue = new AllianceData();

    //public List<Text> AllianceRedTextList = new ArrayList<Text>();
    //public List<Text> AllianceBlueTextList = new ArrayList<Text>();

    public Text predictedScoreRed;
    public Text predictedScoreBlue;

    public Text avgTeleSwitchTextRed; //avgTeleGear
    public Text avgAutoSwitchTextRed; //avgAutoGear
    public Text avgTeleScaleTextRed; //avgTeleFuel
    public Text avgAutoScaleTextRed; //avgAutoFuel
    public Text avgClimbsTextRed;

    public Text avgTeleSwitchTextBlue;
    public Text avgAutoSwitchTextBlue;
    public Text avgTeleScaleTextBlue;
    public Text avgAutoScaleTextBlue;
    public Text avgClimbsTextBlue;

    public int avgAutoScaleRedX = 462;
    public int avgAutoScaleRedY = 73;
    public int avgTeleScaleRedX = 556;
    public int avgTeleScaleRedY = 73;
    public int avgAutoSwitchRedX = 650;
    public int avgAutoSwitchRedY = 73;
    public int avgTeleSwitchRedX = 753;
    public int avgTeleSwitchRedY = 73;
    public int avgClimbsRedX = 838;
    public int avgClimbsRedY = 73;

    public int avgAutoScaleBlueX = 462;
    public int avgAutoScaleBlueY = 140;
    public int avgTeleScaleBlueX = 556;
    public int avgTeleScaleBlueY = 140;
    public int avgAutoSwitchBlueX = 650;
    public int avgAutoSwitchBlueY = 140;
    public int avgTeleSwitchBlueX = 753;
    public int avgTeleSwitchBlueY = 140;
    public int avgClimbsBlueX = 838;
    public int avgClimbsBlueY = 140;

    public int predictedScoreRedX = 353;
    public int predictedScoreRedY = 73;

    public int predictedScoreBlueX = 353;
    public int predictedScoreBlueY = 140;

    public int predictedScoreLabelX = 315;
    public int predictedScoreLabelY = 25;
    public int avgTeleScaleLabelX = 540;
    public int avgTeleScaleLabelY = 25;
    public int avgAutoScaleLabelX = 445;
    public int avgAutoScaleLabelY = 25;
    public int avgTeleSwitchLabelX = 735;
    public int avgTeleSwitchLabelY = 25;
    public int avgAutoSwitchLabelX = 630;
    public int avgAutoSwitchLabelY = 25;
    public int avgClimbsLabelX = 830;
    public int avgClimbsLabelY = 25;


    public int allianceRedTextY = 50;
    public int allianceRedTextX = 150;
    public int allianceBlueTextY = 115;
    public int allianceBlueTextX = 150;
    //Maybe
    public int robotTextY = 50;
    public int robotTextX = 5;

    public int startX = 5;
    public int startY = 38;
    public int incrX = 30;
    public int allianceIncrX = 55;
    public int incrY = 25;
    public int currX = startX;
    public int currY = startY;
    public int columns = 4;
    public int robotColumns = 4;
    public int robotYOffset = 30;
    public int allianceRedYOffset = 30;
    public int allianceBlueYOffset = 30;

    @Override
    public void start(Stage primaryStage) throws Exception{
        getDataFromDB();
        Group root = new Group();
        primaryStage.setTitle("Match Analysis");
        primaryStage.setScene(new Scene(root, 900, 650));
       //MAKE THIS THE AUTO BUTTON
        Button auto = new Button();
        auto.setLayoutX(105);
        auto.setLayoutY(5);
        auto.setPrefSize(85, 10);
        auto.setText("AUTO");
        root.getChildren().add(auto);
        auto.setOnAction(
                new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent event) {
                        final Stage dialog = new Stage();
                        dialog.initModality(Modality.APPLICATION_MODAL);
                        dialog.initOwner(primaryStage);
                        GridPane auto = new GridPane();
                        auto.setHgap(10);
                        auto.setVgap(10);
                        auto.setPadding(new Insets(0, 10, 0, 10));
                        Scene dialogScene = new Scene(auto, 900, 650);

                        Text allianceRedLabel = new Text("Red Alliance");
                        allianceRedLabel.setStyle("-fx-font: 24 cambria");
                        allianceRedLabel.setFill(Color.RED);
                        auto.add(allianceRedLabel, 0, 1);

                        Text allianceBlueLabel = new Text("Blue Alliance");
                        allianceBlueLabel.setStyle("-fx-font: 24 cambria");
                        allianceBlueLabel.setFill(Color.MEDIUMBLUE);
                        auto.add(allianceBlueLabel, 0, 2);

                        dialog.setScene(dialogScene);
                        dialog.show();
                    }});

        //MAKE TELEOP BUTTON
        Button teleop = new Button();
        teleop.setLayoutX(195);
        teleop.setLayoutY(5);
        teleop.setPrefSize(85, 10);
        teleop.setText("TELEOP");
        root.getChildren().add(teleop);
        teleop.setOnAction(
                new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent event) {
                        final Stage dialog = new Stage();
                        dialog.initModality(Modality.APPLICATION_MODAL);
                        dialog.initOwner(primaryStage);
                        GridPane teleop = new GridPane();
                        teleop.setHgap(10);
                        teleop.setVgap(10);
                        teleop.setPadding(new Insets(0, 10, 0, 10));
                        Scene dialogScene = new Scene(teleop, 900, 650);

                        Text allianceRedLabel = new Text("Alliance 1");
                        allianceRedLabel.setStyle("-fx-font: 24 cambria");
                        allianceRedLabel.setFill(Color.RED);
                        teleop.add(allianceRedLabel, 0, 1);

                        Text allianceBlueLabel = new Text("Alliance 2");
                        allianceBlueLabel.setStyle("-fx-font: 24 cambria");
                        allianceBlueLabel.setFill(Color.MEDIUMBLUE);
                        teleop.add(allianceBlueLabel, 0, 2);

                        dialog.setScene(dialogScene);
                        dialog.show();
                    }});

        int currNum = 1;

        final Text allianceRedText = new Text (allianceRedTextX,allianceRedTextY-3,"Red Alliance");
        allianceRedText.setFill(Color.RED);
        allianceRedText.setStyle("-fx-font: 20 cambria");
        final Text allianceBlueText = new Text (allianceBlueTextX,allianceBlueTextY-3,"Blue Alliance");
        allianceBlueText.setFill(Color.MEDIUMBLUE);
        allianceBlueText.setStyle("-fx-font: 20 cambria");

        predictedScoreRed = new Text(predictedScoreRedX,predictedScoreRedY,"");
        predictedScoreRed.setFill(RED);
        predictedScoreRed.setStyle("-fx-font: 25 cambria");
        predictedScoreBlue = new Text(predictedScoreBlueX,predictedScoreBlueY,"");
        predictedScoreBlue.setFill(MEDIUMBLUE);

        avgTeleScaleTextRed = new Text (avgTeleScaleRedX,avgTeleScaleRedY, "");
        avgTeleScaleTextRed.setFill(RED);
        avgTeleScaleTextRed.setStyle("-fx-font: 25 cambria");
        avgAutoScaleTextRed = new Text (avgAutoScaleRedX,avgAutoScaleRedY, "");
        avgAutoScaleTextRed.setFill(RED) ;
        avgAutoScaleTextRed.setStyle("-fx-font: 25 cambria");
        avgTeleSwitchTextRed = new Text (avgTeleSwitchRedX,avgTeleSwitchRedY, "");
        avgTeleSwitchTextRed.setFill(RED);
        avgTeleSwitchTextRed.setStyle("-fx-font: 25 cambria");
        avgAutoSwitchTextRed = new Text (avgAutoSwitchRedX,avgAutoSwitchRedY, "") ;
        avgAutoSwitchTextRed.setFill(RED);
        avgAutoSwitchTextRed.setStyle("-fx-font: 25 cambria");
        avgClimbsTextRed = new Text (avgClimbsRedX,avgClimbsRedY, "");
        avgClimbsTextRed.setFill(RED);
        avgClimbsTextRed.setStyle("-fx-font: 25 cambria");

        avgTeleScaleTextBlue = new Text (avgTeleScaleBlueX,avgTeleScaleBlueY, "");
        avgTeleScaleTextBlue.setFill(MEDIUMBLUE);
        avgTeleScaleTextBlue.setStyle("-fx-font: 25 cambria");
        avgAutoScaleTextBlue = new Text (avgAutoScaleBlueX,avgAutoScaleBlueY, "");
        avgAutoScaleTextBlue.setFill(MEDIUMBLUE) ;
        avgAutoScaleTextBlue.setStyle("-fx-font: 25 cambria");
        avgTeleSwitchTextBlue = new Text (avgTeleSwitchBlueX,avgTeleSwitchBlueY, "");
        avgTeleSwitchTextBlue.setFill(MEDIUMBLUE);
        avgTeleSwitchTextBlue.setStyle("-fx-font: 25 cambria");
        avgAutoSwitchTextBlue = new Text (avgAutoSwitchBlueX,avgAutoSwitchBlueY, "") ;
        avgAutoSwitchTextBlue.setFill(MEDIUMBLUE);
        avgAutoSwitchTextBlue.setStyle("-fx-font: 25 cambria");
        avgClimbsTextBlue = new Text (avgClimbsBlueX,avgClimbsBlueY, "");
        avgClimbsTextBlue.setFill(MEDIUMBLUE);
        avgClimbsTextBlue.setStyle("-fx-font: 25 cambria");

        root.getChildren().add(predictedScoreRed);
        root.getChildren().add(predictedScoreBlue);

        root.getChildren().add(avgTeleScaleTextRed);
        root.getChildren().add(avgAutoScaleTextRed);
        root.getChildren().add(avgTeleSwitchTextRed);
        root.getChildren().add(avgAutoSwitchTextRed);
        root.getChildren().add(avgClimbsTextRed);

        root.getChildren().add(avgTeleScaleTextBlue);
        root.getChildren().add(avgAutoScaleTextBlue);
        root.getChildren().add(avgTeleSwitchTextBlue);
        root.getChildren().add(avgAutoSwitchTextBlue);
        root.getChildren().add(avgClimbsTextBlue);

        Text predictedScoreLabel = new Text (predictedScoreLabelX, predictedScoreLabelY, "Predicted Score");
        predictedScoreLabel.setFill(Color.MEDIUMBLUE);
        predictedScoreLabel.setStyle("-fx-font: 16 cambria");
        Text avgTeleScaleLabel = new Text (avgTeleScaleLabelX, avgTeleScaleLabelY, "Tele Fuel");
        avgTeleScaleLabel.setFill(Color.MEDIUMBLUE);
        avgTeleScaleLabel.setStyle("-fx-font: 16 cambria");
        Text avgAutoScaleLabel = new Text (avgAutoScaleLabelX, avgAutoScaleLabelY, "Auto Fuel");
        avgAutoScaleLabel.setFill(Color.MEDIUMBLUE);
        avgAutoScaleLabel.setStyle("-fx-font: 16 cambria");
        Text avgAutoSwitchLabel = new Text (avgAutoSwitchLabelX, avgAutoSwitchLabelY, "Auto Gears");
        avgAutoSwitchLabel.setFill(Color.MEDIUMBLUE);
        avgAutoSwitchLabel.setStyle("-fx-font: 16 cambria");
        Text avgTeleSwitchLabel = new Text (avgTeleSwitchLabelX, avgTeleSwitchLabelY, "Tele Gears");
        avgTeleSwitchLabel.setFill(Color.MEDIUMBLUE);
        avgTeleSwitchLabel.setStyle("-fx-font: 16 cambria");
        Text avgClimbsLabel = new Text (avgClimbsLabelX, avgClimbsLabelY, "Climbs");
        avgClimbsLabel.setFill(Color.MEDIUMBLUE);
        avgClimbsLabel.setStyle("-fx-font: 16 cambria");

        root.getChildren().add(predictedScoreLabel);
        root.getChildren().add(avgTeleScaleLabel);
        root.getChildren().add(avgAutoScaleLabel);
        root.getChildren().add(avgAutoSwitchLabel);
        root.getChildren().add(avgTeleSwitchLabel);
        root.getChildren().add(avgClimbsLabel);
        root.getChildren().add(allianceRedText);
        root.getChildren().add(allianceBlueText);

        if (adListRed.size() == 0) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Database is empty");
            alert.setHeaderText("Check the SQL database");
            alert.showAndWait();
            exit();
        }
        if (adListBlue.size() == 0) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Database is empty");
            alert.setHeaderText("Check the SQL database");
            alert.showAndWait();
            exit();
        }
      final Rectangle allianceRedRect = new Rectangle(allianceRedTextX, allianceRedTextY, 160, 40);
        allianceRedRect.setFill(null);
        allianceRedRect.setStroke(Color.RED);
        allianceRedRect.toBack();
        root.getChildren().add(allianceRedRect);

        final TextFlow allianceRedTarget = new TextFlow(
        );
        allianceRedTarget.setLayoutX(allianceRedTextX);
        allianceRedTarget.setLayoutY(allianceRedTextY);
        allianceRedTarget.setPrefSize(160,40);
        allianceRedTarget.toBack();

        final Rectangle allianceBlueRect = new Rectangle(allianceBlueTextX, allianceBlueTextY, 160, 40);
        allianceBlueRect.setFill(null);
        allianceBlueRect.setStroke(Color.MEDIUMBLUE);
        allianceBlueRect.toBack();
        root.getChildren().add(allianceBlueRect);

        final TextFlow allianceBlueTarget = new TextFlow(
        );
        allianceBlueTarget.setLayoutX(allianceBlueTextX);
        allianceBlueTarget.setLayoutY(allianceBlueTextY);
        allianceBlueTarget.setPrefSize(160,40);
        allianceBlueTarget.toBack();

        placeTeams(); }
    // Display Red Alliance  Stats
    public void getStrongestAllianceRed() {
        // first sort alliances by strength
        Collections.sort(adListRed, new Comparator<AllianceData>() {
            public int compare(AllianceData o1, AllianceData o2) {
                if (o1.allianceLowStrength == o2.allianceLowStrength)
                    return 0;
                return o1.allianceLowStrength > o2.allianceLowStrength ? -1 : 1;
            }
        });

        int r1 = 0;
        int r2 = 0;
        int r3 = 0;
        double strength = 0.0;
        double avgAutoScale = 0;
        double avgTeleScale = 0;
        double avgAutoSwitch = 0;
        double avgTeleSwitch = 0;
        double avgClimbs = 0;
        // if there's three robots, show the score
          if (allianceRedTextList.size() == 3) {
            // find highest matching alliance with all 3 robots in it
            // find highest matching alliance with robots 1 and 2 in it
            r1 = Integer.parseInt(allianceRedTextList.get(0).getText().toString());
            r2 = Integer.parseInt(allianceRedTextList.get(1).getText().toString());
            r3 = Integer.parseInt(allianceRedTextList.get(2).getText().toString());


            // find highest matching alliance with robot 1 in it
            int c =0;
            boolean keepSearching = true;
            while (keepSearching == true) {
                if ((adListRed.get(c).robot1 == r1 || adListRed.get(c).robot2 == r1 || adListRed.get(c).robot3 == r1) &&
                        (adListRed.get(c).robot1 == r2 || adListRed.get(c).robot2 == r2 || adListRed.get(c).robot3 == r2) &&
                        (adListRed.get(c).robot1 == r3 || adListRed.get(c).robot2 == r3 || adListRed.get(c).robot3 == r3)) {
                    keepSearching = false;
                    // set all of the robot numbers
                    r1 = adListRed.get(c).robot1;
                    r2 = adListRed.get(c).robot2;
                    r3 = adListRed.get(c).robot3;
                    strength = adListRed.get(c).allianceLowStrength;
                    avgAutoScale = adListRed.get(c).avgAutoScale;
                    avgTeleScale = adListRed.get(c).avgTeleScale;
                    avgAutoSwitch = adListRed.get(c).avgAutoSwitch;
                    avgTeleSwitch = adListRed.get(c).avgTeleSwitch;
                    avgClimbs = adListRed.get(c).avgTeleClimb;
                    allianceRed.allianceHighStrength = adListRed.get(c).allianceHighStrength;
                    allianceRed.allianceLowStrength = adListRed.get(c).allianceLowStrength;
                    allianceRed.allianceRawStrength = adListRed.get(c).allianceRawStrength;
                    allianceRed.avgAutoSwitch = adListRed.get(c).avgAutoSwitch;
                    allianceRed.avgTeleSwitch = adListRed.get(c).avgTeleSwitch;
                    allianceRed.avgAutoScale = adListRed.get(c).avgAutoScale;
                    allianceRed.avgTeleScale = adListRed.get(c).avgTeleScale;
                    allianceRed.avgTeleClimb = adListRed.get(c).avgTeleClimb;
                    allianceRed.allianceNumber = 1;
                }

                c++;
                if (c >= adListRed.size()) {
                    keepSearching = false;
                }
            }
        }
        predictedScoreRed.setText(String.format("%.1f",strength));
        avgAutoScaleTextRed.setText(String.format("%.1f",avgAutoScale));
        avgTeleScaleTextRed.setText(String.format("%.1f", avgTeleScale));
        avgAutoSwitchTextRed.setText(String.format("%.1f", avgAutoSwitch));
        avgTeleSwitchTextRed.setText(String.format("%.1f", avgTeleSwitch));
        avgClimbsTextRed.setText(String.format("%.1f", avgClimbs));

    }

    // Display Alliance Blue Stats
    public void getStrongestAllianceBlue() {
        // first sort alliances by strength
        Collections.sort(adListBlue, new Comparator<AllianceData>() {
            public int compare(AllianceData o1, AllianceData o2) {
                if (o1.allianceLowStrength == o2.allianceLowStrength)
                    return 0;
                return o1.allianceLowStrength > o2.allianceLowStrength ? -1 : 1;
            }
        });

        int r1 = 0;
        int r2 = 0;
        int r3 = 0;
        double strength = 0.0;
        double avgAutoScale = 0;
        double avgTeleScale = 0;
        double avgAutoSwitch = 0;
        double avgTeleSwitch = 0;
        double avgClimbs = 0;
        // if there's three robots, show the score
        if (allianceBlueTextList.size() == 3) {
            // find highest matching alliance with all 3 robots in it
            // find highest matching alliance with robots 1 and 2 in it
            r1 = Integer.parseInt(allianceBlueTextList.get(0).getText().toString());
            r2 = Integer.parseInt(allianceBlueTextList.get(1).getText().toString());
            r3 = Integer.parseInt(allianceBlueTextList.get(2).getText().toString());

            // find highest matching alliance with robot 1 in it
            int c =0;
            boolean keepSearching = true;
            while (keepSearching == true) {
                if ((adListBlue.get(c).robot1 == r1 || adListBlue.get(c).robot2 == r1 || adListBlue.get(c).robot3 == r1) &&
                        (adListBlue.get(c).robot1 == r2 || adListBlue.get(c).robot2 == r2 || adListBlue.get(c).robot3 == r2) &&
                        (adListBlue.get(c).robot1 == r3 || adListBlue.get(c).robot2 == r3 || adListBlue.get(c).robot3 == r3)) {
                    keepSearching = false;
                    // set all of the robot numbers
                    r1 = adListBlue.get(c).robot1;
                    r2 = adListBlue.get(c).robot2;
                    r3 = adListBlue.get(c).robot3;
                    strength = adListBlue.get(c).allianceLowStrength;
                    avgAutoScale = adListBlue.get(c).avgAutoScale;
                    avgTeleScale = adListBlue.get(c).avgTeleScale;
                    avgAutoSwitch = adListBlue.get(c).avgAutoSwitch;
                    avgTeleSwitch = adListBlue.get(c).avgTeleSwitch;
                    avgClimbs = adListBlue.get(c).avgTeleClimb;
                    allianceBlue.allianceHighStrength = adListBlue.get(c).allianceHighStrength;
                    allianceBlue.allianceLowStrength = adListBlue.get(c).allianceLowStrength;
                    allianceBlue.allianceRawStrength = adListBlue.get(c).allianceRawStrength;
                    allianceBlue.avgAutoSwitch = adListBlue.get(c).avgAutoSwitch;
                    allianceBlue.avgTeleSwitch = adListBlue.get(c).avgTeleSwitch;
                    allianceBlue.avgAutoScale = adListBlue.get(c).avgAutoScale;
                    allianceBlue.avgTeleScale = adListBlue.get(c).avgTeleScale;
                    allianceBlue.avgTeleClimb = adListBlue.get(c).avgTeleClimb;
                    allianceBlue.allianceNumber = 2;

                }
                c++;
                if (c >= adListBlue.size()) {
                    keepSearching = false;
                }
            }
        }
        predictedScoreBlue.setText(String.format("%.1f",strength));
        avgAutoScaleTextBlue.setText(String.format("%.1f",avgAutoScale));
        avgTeleScaleTextBlue.setText(String.format("%.1f", avgTeleScale));
        avgAutoSwitchTextBlue.setText(String.format("%.1f", avgAutoSwitch));
        avgTeleSwitchTextBlue.setText(String.format("%.1f", avgTeleSwitch));
        avgClimbsTextBlue.setText(String.format("%.1f", avgClimbs));
    }
       // loop through available team list and place teams
    public void placeTeams() {
        int currNum = 0;
        currY = startY;
        //System.out.println("size of available team list = " + availableTeamTextList.size());
        Collections.sort(robotTextList, new Comparator<Text>() {
            public int compare(Text o1, Text o2) {
                if (Integer.parseInt(o1.getText()) == Integer.parseInt(o2.getText()))
                    return 0;
                return  Integer.parseInt(o1.getText())> Integer.parseInt(o2.getText()) ? 1 : -1;
            }
        });
        for (Text t : robotTextList) {
            currX = (currNum % columns)*incrX + startX;
            if (currNum % columns == 0) {
                currX = startX;
                currY += incrY;
            }
            t.setX(currX);
            t.setY(currY);
            currNum++;
        }
    }
    public void resetData(int allianceNumber) {
        if (allianceNumber == 1) {
            avgAutoSwitchTextRed.setText("");
            avgTeleSwitchTextRed.setText("");
            avgAutoScaleTextRed.setText("");
            avgTeleScaleTextRed.setText("");
            avgClimbsTextRed.setText("");
            predictedScoreRed.setText("");
        }
        if (allianceNumber == 2) {
            avgAutoSwitchTextBlue.setText("");
            avgTeleSwitchTextBlue.setText("");
            avgAutoScaleTextBlue.setText("");
            avgTeleScaleTextBlue.setText("");
            avgClimbsTextBlue.setText("");
            predictedScoreBlue.setText("");
        }

    }

    public void placeRobots() {
        int currNum = 0;
        currY = robotTextY + robotYOffset;
        //System.out.println("size of picked team list = " + pickedTeamTextList.size());
        Collections.sort(robotTextList, new Comparator<Text>() {
            public int compare(Text o1, Text o2) {
                if (Integer.parseInt(o1.getText()) == Integer.parseInt(o2.getText()))
                    return 0;
                return  Integer.parseInt(o1.getText())> Integer.parseInt(o2.getText()) ? 1 : -1;
            }
        });
        for (Text t : robotTextList) {
            currX = (currNum % robotColumns)*incrX + robotTextX;
            if (currNum % robotColumns == 0) {
                currX = robotTextX;
                currY += incrY;
            }
            t.setX(currX);
            t.setY(currY);
            currNum++;
        }
    }
    public void placeAllianceRed() {
        int currNum = 0;
        currY = allianceRedTextY + allianceRedYOffset - 5;
        //System.out.println("size of picked team list = " + pickedTeamTextList.size());
         /* Collections.sort(allianceRedTextList, new Comparator<Text>() {
            public int compare(Text o1, Text o2) {
                if (Integer.parseInt(o1.getText()) == Integer.parseInt(o2.getText()))
                    return 0;
                return  Integer.parseInt(o1.getText())> Integer.parseInt(o2.getText()) ? 1 : -1;
            }
        }); */
        for (Text t : allianceRedTextList) {
            currX = currNum*allianceIncrX + allianceRedTextX + 18;
            t.setX(currX);
            t.setY(currY);
            currNum++;
        }
    }
    public void placeAllianceBlue() {
        int currNum = 0;
        currY = allianceBlueTextY + allianceBlueYOffset - 5;
        for (Text t : allianceBlueTextList) {
            currX = currNum*allianceIncrX + allianceBlueTextX + 18;
            t.setX(currX);
            t.setY(currY);
            currNum++;
        }
    }

    // given a robot number string, return the text
    public Text getTextObject (String robotNumber) {
        // check all three lists
        for (Text t : allianceRedTextList) {
            if (t.getText().toString().equalsIgnoreCase(robotNumber)) {
                return t;
            }
        }
        // check all three lists
        for (Text t : allianceBlueTextList) {
            if (t.getText().toString().equalsIgnoreCase(robotNumber)) {
                return t;
            }
        }
       //WHY DO YOU EXIST
        for (Text t : robotTextList) {
            if (t.getText().toString().equalsIgnoreCase(robotNumber)) {
                return t;
            }
        }
        return null;
    }
    public boolean isInRobotTextList (String robotNumberString) {
        for (Text t : robotTextList) {
            if (t.getText().toString().equalsIgnoreCase(robotNumberString)) {
                return true;
            }
        }

        return false;
    }
    public boolean isInAllianceRed (String robotNumberString) {
        // check all three lists
        // check all three lists
        for (Text t : allianceRedTextList) {
            if (t.getText().toString().equalsIgnoreCase(robotNumberString)) {
                return true;
            }
        }

        return false;
    }

    public boolean isInAllianceBlue (String robotNumberString) {
        // check all three lists
        // check all three lists
        for (Text t : allianceBlueTextList) {
            if (t.getText().toString().equalsIgnoreCase(robotNumberString)) {
                return true;
            }
        }

        return false;
    }

    // check the robot list to see if we have a robot already with the given number
    public boolean haveRobot(int robotNumber) {
        for (RobotData r : robotList) {
            if (r.robotNumber == robotNumber) {
                return true;
            }
        }
        return false;
    }

    // get the robot with the given robotNumber from the list
    // or return null
    public RobotData getRobot(int robotNumber) {

        for (RobotData r : robotList) {
            if (r.robotNumber == robotNumber) {
                return r;
            }
        }
        return null;
    }

    public void getRanks() {
        // rank the robots based on average alliance score
        ArrayList<RobotData> rankList = new ArrayList<RobotData>();
        for (RobotData r : robotList) {
            rankList.add(r);
        }

        Collections.sort(rankList, new Comparator<RobotData>() {
            public int compare(RobotData o1, RobotData o2) {
                if (o1.lowShots.avg == o2.lowShots.avg)
                    return 0;
                return o1.lowShots.avg > o2.lowShots.avg ? -1 : 1;
            }
        });
        // now loop through the lists and set the rank based on avg score
        for (int c = 0; c < rankList.size(); c++) {
            if (c > 0) {
                int prev_rank = getRobot(rankList.get(c-1).robotNumber).autoLowShots.rank;
                if (getRobot(rankList.get(c).robotNumber).autoLowShots.avg < getRobot(rankList.get(c-1).robotNumber).autoLowShots.avg) {
                    getRobot(rankList.get(c).robotNumber).autoLowShots.rank = prev_rank + 1;
                } else {
                    getRobot(rankList.get(c).robotNumber).autoLowShots.rank = prev_rank;
                }
            } else {
                getRobot(rankList.get(c).robotNumber).autoLowShots.rank = 1;
            }
        }

        for (int c = 0; c < rankList.size(); c++) {
            if (c > 0) {
                int prev_rank = getRobot(rankList.get(c-1).robotNumber).autoHighShots.rank;
                if (getRobot(rankList.get(c).robotNumber).autoHighShots.avg < getRobot(rankList.get(c-1).robotNumber).autoHighShots.avg) {
                    getRobot(rankList.get(c).robotNumber).autoHighShots.rank = prev_rank + 1;
                } else {
                    getRobot(rankList.get(c).robotNumber).autoHighShots.rank = prev_rank;
                }
            } else {
                getRobot(rankList.get(c).robotNumber).autoHighShots.rank = 1;
            }
        }

        for (int c = 0; c < rankList.size(); c++) {
            if (c > 0) {
                int prev_rank = getRobot(rankList.get(c-1).robotNumber).lowShots.rank;
                if (getRobot(rankList.get(c).robotNumber).lowShots.avg < getRobot(rankList.get(c-1).robotNumber).lowShots.avg) {
                    getRobot(rankList.get(c).robotNumber).lowShots.rank = prev_rank + 1;
                } else {
                    getRobot(rankList.get(c).robotNumber).lowShots.rank = prev_rank;
                }
            } else {
                getRobot(rankList.get(c).robotNumber).lowShots.rank = 1;
            }
        }

        Collections.sort(rankList, new Comparator<RobotData>() {
            public int compare(RobotData o1, RobotData o2) {
                if (o1.highShots.avg == o2.highShots.avg)
                    return 0;
                return o1.highShots.avg > o2.highShots.avg ? -1 : 1;
            }
        });
        // now loop through the lists and set the rank based on avg score
        for (int c = 0; c < rankList.size(); c++) {
            if (c > 0) {
                int prev_rank = getRobot(rankList.get(c-1).robotNumber).highShots.rank;
                if (getRobot(rankList.get(c).robotNumber).highShots.avg < getRobot(rankList.get(c-1).robotNumber).highShots.avg) {
                    getRobot(rankList.get(c).robotNumber).highShots.rank = prev_rank + 1;
                } else {
                    getRobot(rankList.get(c).robotNumber).highShots.rank = prev_rank;
                }
            } else {
                getRobot(rankList.get(c).robotNumber).highShots.rank = 1;
            }
        }
        Collections.sort(rankList, new Comparator<RobotData>() {
            public int compare(RobotData o1, RobotData o2) {
                if (o1.autoCross.avg == o2.autoCross.avg)
                    return 0;
                return o1.autoCross.avg > o2.autoCross.avg ? -1 : 1;
            }
        });
        // now loop through the lists and set the rank based on avg score
        for (int c = 0; c < rankList.size(); c++) {
            getRobot(rankList.get(c).robotNumber).autoCross.rank = c + 1;
        }
        for (int c = 0; c < rankList.size(); c++) {
            if (c > 0) {
                int prev_rank = getRobot(rankList.get(c-1).robotNumber).autoCross.rank;
                if (getRobot(rankList.get(c).robotNumber).autoCross.avg < getRobot(rankList.get(c-1).robotNumber).autoCross.avg) {
                    getRobot(rankList.get(c).robotNumber).autoCross.rank = prev_rank + 1;
                } else {
                    getRobot(rankList.get(c).robotNumber).autoCross.rank = prev_rank;
                }
            } else {
                getRobot(rankList.get(c).robotNumber).autoCross.rank = 1;
            }
        }

        Collections.sort(rankList, new Comparator<RobotData>() {
            public int compare(RobotData o1, RobotData o2) {
                if (o1.autoGears.avg == o2.autoGears.avg)
                    return 0;
                return o1.autoGears.avg > o2.autoGears.avg ? -1 : 1;
            }
        });
        // now loop through the lists and set the rank based on avg score
        for (int c = 0; c < rankList.size(); c++) {
            if (c > 0) {
                int prev_rank = getRobot(rankList.get(c-1).robotNumber).autoGears.rank;
                if (getRobot(rankList.get(c).robotNumber).autoGears.avg < getRobot(rankList.get(c-1).robotNumber).autoGears.avg) {
                    getRobot(rankList.get(c).robotNumber).autoGears.rank = prev_rank + 1;
                } else {
                    getRobot(rankList.get(c).robotNumber).autoGears.rank = prev_rank;
                }
            } else {
                getRobot(rankList.get(c).robotNumber).autoGears.rank = 1;
            }
        }
        Collections.sort(rankList, new Comparator<RobotData>() {
            public int compare(RobotData o1, RobotData o2) {
                if (o1.gears.avg == o2.gears.avg)
                    return 0;
                return o1.gears.avg > o2.gears.avg ? -1 : 1;
            }
        });
        // now loop through the lists and set the rank based on avg score
        for (int c = 0; c < rankList.size(); c++) {
            if (c > 0) {
                int prev_rank = getRobot(rankList.get(c-1).robotNumber).gears.rank;
                if (getRobot(rankList.get(c).robotNumber).gears.avg < getRobot(rankList.get(c-1).robotNumber).gears.avg) {
                    getRobot(rankList.get(c).robotNumber).gears.rank = prev_rank + 1;
                } else {
                    getRobot(rankList.get(c).robotNumber).gears.rank = prev_rank;
                }
            } else {
                getRobot(rankList.get(c).robotNumber).gears.rank = 1;
            }
        }

        Collections.sort(rankList, new Comparator<RobotData>() {
            public int compare(RobotData o1, RobotData o2) {
                if (o1.climb.avg == o2.climb.avg)
                    return 0;
                return o1.climb.avg > o2.climb.avg ? -1 : 1;
            }
        });
        // now loop through the lists and set the rank based on avg score
        for (int c = 0; c < rankList.size(); c++) {
            if (c > 0) {
                int prev_rank = getRobot(rankList.get(c-1).robotNumber).climb.rank;
                if (getRobot(rankList.get(c).robotNumber).climb.avg < getRobot(rankList.get(c-1).robotNumber).climb.avg) {
                    getRobot(rankList.get(c).robotNumber).climb.rank = prev_rank + 1;
                } else {
                    getRobot(rankList.get(c).robotNumber).climb.rank = prev_rank;
                }
            } else {
                getRobot(rankList.get(c).robotNumber).climb.rank = 1;
            }
        }
    }

    public void getDataFromDB() {

        // make directory if not found
        File dataSheetDirFile = new File(dataSheetDir);
        if (dataSheetDirFile.exists() == false) {
            dataSheetDirFile.mkdir();
        }

        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            System.out.println("My SQL JDBC Driver Not Registered?");
            e.printStackTrace();
            return;
        }
        System.out.println("Getting Data from SQL Database");

        try {
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/roborebels", "root", "roborebels1153");
            Statement stmt;
            ResultSet rs;

            //create hashmap of data
            stmt = conn.createStatement();

            //get match table, then create robots
            rs = stmt.executeQuery("SELECT * from matchdata");

            //process Data
            RobotData rd;
            while (rs.next()) {
                int rn = rs.getInt("RobotNumber");
                String matchName = rs.getString("matchNumber");
                Integer matchNumber = Integer.parseInt(matchName);

                if (haveRobot(rn)) {
                    // check to see if we have this match number already, if not, add it to the list and increase
                    // number of matches
                    if (getRobot(rn).matchList.contains(matchNumber) == false) {
                        getRobot(rn).matches++;
                        getRobot(rn).matchList.add(matchNumber);
                    }
                } else {
                    rd = new RobotData();
                    rd.robotNumber = rn;
                    rd.matches = 1;
                    rd.matchList.add(matchNumber);
                    robotList.add(rd);

                }
            }
            rs.close();
            rs = stmt.executeQuery("SELECT * from matchdata");
            while (rs.next()) {
                int rn = rs.getInt("RobotNumber");
                if (haveRobot(rn)) {
                    String gameEvent = rs.getString("gameEvent");
                    if (gameEvent.equals("crossBaselineAuto")){getRobot(rn).autoCross.total++;}
                    if (gameEvent.equals("climbed")){getRobot(rn).climb.total++;}
                    if (gameEvent.equals("gearPlacedAuto")){getRobot(rn).autoGears.total++;}
                    if (gameEvent.equals("gearPlacedTeleop")){getRobot(rn).teleGears.total++;}
                    if (gameEvent.equals("lowGoal")){getRobot(rn).lowShots.total++;}
                    if (gameEvent.equals("highGoal")){getRobot(rn).highShots.total++;}
                    if (gameEvent.equals("lowGoalAuto")){getRobot(rn).autoLowShots.total++;}
                    if (gameEvent.equals("highGoalAuto")){getRobot(rn).autoHighShots.total++;}
                    if (gameEvent.equals("gearPlacedTeleop") || gameEvent.equals("gearPlacedAuto")){getRobot(rn).gears.total++;}

                }
            }
            rs.close();
            stmt.close();

            //averages
            for (RobotData r : robotList) {
                r.autoLowShots.avg = (double) r.autoLowShots.total / r.matches;
                r.autoHighShots.avg = (double) r.autoHighShots.total / r.matches;
                r.autoCross.avg = (double) r.autoCross.total / r.matches;
                r.autoGears.avg = (double) r.autoGears.total / r.matches;
                r.teleGears.avg = (double) r.teleGears.total / r.matches;
                r.climb.avg = (double) r.climb.total / r.matches;
                r.gears.avg = (double) r.gears.total / r.matches;
                r.lowShots.avg = (double) r.lowShots.total / r.matches;
                r.highShots.avg = (double) r.highShots.total / r.matches;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        getRanks();



        for (RobotData r : robotList) {
            teamList.add(r.robotNumber);
        }

        //System.out.println("Got " + teamList.size() + " robots");

        for (Integer t1 : teamList) {
            for (Integer t2 : teamList) {
                for (Integer t3 : teamList) {
                    if (t1 != t3 && t3 != t2 && t1 != t2) {
                        AllianceData ad = new AllianceData();
                        ad.robot1 = t1;
                        ad.robot2 = t2;
                        ad.robot3 = t3;

                        // create the combined averages
                        // in auto low shots are worth 1/3 point, high shots = 1pt
                        ad.avgAutoScale = (getRobot(t1).autoLowShots.avg + getRobot(t2).autoLowShots.avg + getRobot(t3).autoLowShots.avg) +
                                (getRobot(t1).autoHighShots.avg + getRobot(t2).autoHighShots.avg + getRobot(t3).autoHighShots.avg);
                        ad.avgTeleScale = (getRobot(t1).lowShots.avg + getRobot(t2).lowShots.avg + getRobot(t3).lowShots.avg) +
                                (getRobot(t1).highShots.avg + getRobot(t2).highShots.avg + getRobot(t3).highShots.avg);
                        ad.avgAutoSwitch = getRobot(t1).autoGears.avg + getRobot(t2).autoGears.avg + getRobot(t3).autoGears.avg;
                        ad.avgTeleSwitch = getRobot(t1).teleGears.avg + getRobot(t2).teleGears.avg + getRobot(t3).teleGears.avg;
                        ad.avgTeleClimb = getRobot(t1).climb.avg + getRobot(t2).climb.avg + getRobot(t3).climb.avg;

                        ad.calcStrength();
                        adListRed.add(ad);
                    }
                }
            }
        }

        for (Integer t1 : teamList) {
            for (Integer t2 : teamList) {
                for (Integer t3 : teamList) {
                    if (t1 != t3 && t3 != t2 && t1 != t2) {
                        AllianceData ad = new AllianceData();
                        ad.robot1 = t1;
                        ad.robot2 = t2;
                        ad.robot3 = t3;

                        // create the combined averages
                        // in auto low shots are worth 1/3 point, high shots = 1pt
                        ad.avgAutoScale = (getRobot(t1).autoLowShots.avg + getRobot(t2).autoLowShots.avg + getRobot(t3).autoLowShots.avg) / 3 +
                                (getRobot(t1).autoHighShots.avg + getRobot(t2).autoHighShots.avg + getRobot(t3).autoHighShots.avg);
                        ad.avgTeleScale = (getRobot(t1).lowShots.avg + getRobot(t2).lowShots.avg + getRobot(t3).lowShots.avg) / 9 +
                                (getRobot(t1).highShots.avg + getRobot(t2).highShots.avg + getRobot(t3).highShots.avg) / 3;
                        ad.avgAutoSwitch = getRobot(t1).autoGears.avg + getRobot(t2).autoGears.avg + getRobot(t3).autoGears.avg;
                        ad.avgTeleSwitch = getRobot(t1).teleGears.avg + getRobot(t2).teleGears.avg + getRobot(t3).teleGears.avg;
                        ad.avgTeleClimb = getRobot(t1).climb.avg + getRobot(t2).climb.avg + getRobot(t3).climb.avg;

                        ad.calcStrength();
                        adListBlue.add(ad);
                    }
                }
            }
        } }

    public static void main(String[] args) {
        launch(args);
    }
}
