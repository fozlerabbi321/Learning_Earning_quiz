package com.learnearning.learnnnge;


public class Question {

    public String questions[] = {
            "The nation animal of bangladesh is",
            "bd got independence is ",
            "which language android use",
            "Who is the computer detector",
            "Who is The father of computer",
            "Which is the input device ? ",
            "How much is discovered Facebook ? ",
            "Which is the largest continent ? ",
            "Which is the largest Country ? ",
            "Which is the largest City? ",
            "Which is the largest Sea Beach?",
            "Which is the largest ocean? ",
            "Which is the largest desert?",
            "Which is the largest island country? ",
            "Which is the largest country in the population?",
            "Which is the sunrise country?",
            "What is the thunderbolt country?",
            "Which country is Known for kangaroor?",
            "Who is The father of computer?",
            "Where is the UN headquarters?"
    };
    public String choices[][] = {
            {"tiger", "goat", "cow", "dear"},
            {"1952", "1947", "1971", "1999"},
            {"java","flutter","kotlin","all"},
            {"Charles Babbage", "Howard Eckin", "Ray Tomlinson", "Gareth Bell"},
            {"Charles Babbage", "Howard Eckin", "Ray Tomlinson", "Gareth Bell"},
            {"Keyboard", "Monitor", "Printer", "Flopy Disk"},
            {"2005", "2003", "2004", "2001"},
            {"Australia", "Asia", "South America", "Africa"},
            {"China", "America", "Russia", "India"},
            {"London", "Beijing", "Tokyo", "Sydney"},
            {"Cape Town", "Cox's Bazar", "Hanley Bay", "Robinhood's Bay"},
            {"Northern Ocean", "The Atlantic Ocean", "The Pacific Ocean", "Indian ocean"},
            {"Sahara", "Thor", "Gobi", "Kalahari"},
            {"Singapore", "Australia", "Thailand ", "Indonesia"},
            { "China", "Russia", "India", "America"},
            {"Japan", "New Zealand", "North Korea", "Thailand"},
            {"Nepal", "Thailand", "Bhutan", "Myanmar"},
            {"Turkey", "Greenland", "New Zealand", "Australia"},
            {"Iceland", "Norwey", "France", "Italy"},
            {"Venice", "Moscow", "New York", "Beijing"}

    };

    public String correctAnswer[] = {
            "tiger",
            "1971",
            "all",
            "Howard Eckin",
            "Charles Babbage",
            "Keyboard",
            "2004",
            "Asia",
            "Russia",
            "London",
            "Cox's Bazar",
            "The Pacific Ocean",
            "Sahara",
            "Indonesia",
            "China",
            "Japan",
            "Bhutan",
            "Australia",
            "Norwey",
            "New York"
    };

    public String getQuestion(int a){
        String question = questions[a];
        return question;
    }

    public String getchoice1(int a){
        String choice = choices[a][0];
        return choice;
    }

    public String getchoice2(int a){
        String choice = choices[a][1];
        return choice;
    }

    public String getchoice3(int a){
        String choice = choices[a][2];
        return choice;
    }

    public String getchoice4(int a){
        String choice = choices[a][3];
        return choice;
    }

    public String getCorrectAnswer(int a){
        String answer = correctAnswer[a];
        return answer;
    }
}
