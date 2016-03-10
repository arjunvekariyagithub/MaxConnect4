## MaxConnect4

####This is a JAVA Source code for MaxConnect4 game implemented using Minimax AI algorithm.

####Class : Maxconnect4, AiPlayer, GameBoard
	 
###Maxconnect4 :
        APIS : main(), MakeHumanPlay(),  MakeComputerPlay4OneMove(outputFileName), printBoardAndScore(),        
               MakeComputerPlay4Interactive(), printResult().
	

        MakeHumanPlay() -> this function takes input from user and makes the next move for human player.

        MakeComputerPlay4OneMove() -> this function is called when the computer have to make a move for a one-move mode 
        game.

        printBoardAndScore() -> this function display the current score and the board's current state.

        MakeComputerPlay4Interactive() -> this function is called when the computer have to make a move for an interactive  
        mode game.

        printResult() -> this function prints the final score and declares winner or tie.

        
###GameBoard :
        APIS : getscore(), getCurrentTurn(), getGameBoard(), printGameBoard()

        getscore() -> this function takes the current score and send it to printBoardAndScore() to print score.
        
        getCurrentTurn() -> this function traces the current turn.
        
        getGameBoard() -> this function takes the current gameboard state and pass it to printGameBoard() function.
        
        printGameBoard() -> this function prints the current board state.
        

###AiPlayer :
        APIS : findBestPlay(), Calculate_Min_Utility(), Calculate_Max_Utility()

        findBestPlay() -> this function makes the decision to make a move for the computer using the min and max value from                           the below given two functions

        Calculate_Min_Utility() -> this function calculates the min value.
        
        Calculate_Min_Utility() -> this function calculates the max value.


###How to run the Code :

        Compile:
        javac -classpath . Maxconnect4.java
	
        Execute:
        (for interactive mode) :
        java Maxconnect4 interactive [input_file] [computer-next/human-next] [depth]  
        for example: java Maxconnect4 interactive input.txt computer-next 8

        (for one-move mode) :
        time java Maxconnect4 one-move [input_file] [output_file] [depth]  
        for example: java Maxconnect4 one-move input1.txt output.txt 8

        Command to retrieve execution time:
        time java Maxconnect4 one-move [input_file] [output_file] [depth]  
        for example: java Maxconnect4 one-move input1.txt output.txt 8


###Execution time chart :
        
        Depth: 1
        User time : 0m0.129s

        Depth: 2
        User time : 0m0.133s

        Depth: 3
        User time : 0m0.134s

        Depth: 4
        User time : 0m0.152s

        Depth: 5
        User time : 0m0.162s

        Depth: 6
        User time : 0m0.218s

        Depth: 7
        User time : 0m0.389s

        Depth: 8
        User time : 0m0.558s

        Depth: 9
        User time : 0m0.884s

        Depth: 10
        User time : 0m1.929s

