Reviewer: Siying Qian (u6099927)
Component: <Task5 related codes>
Author: Sulenna Nicholson (u4671448)

Review Comments:

 1. WarringStatesGame.java:248-258 "normaliseLoc" considers the "decimal number representation" of ASCII codes (capital letters and numbers respectively), 
    the location characters on board are transfered into "0-35". 
    Hence, make our later "sameRow" & "sameColumn" check more mathematical, efficient and easily understandable.
 
 2. WarringStatesGame.java:261-299 Helper methods "sameRow" "sameColumn" (built initially for Task5) are very useful throughout our whole assignment, 
    (especially when getting cards between ZhangYi and the destination move position).
    Considering our board size, the two methods are very efficient.
 
 3. WarringStatesGame.java:323 Good to directly use "indexOf".
 
 4. WarringStatesGame.java:326-353 Although correct, I think we can firstly transfer our "String placement" into a "Character array", 
    and then store the destination card in a 3-character array.
    So that our later "location" and "kingdom" found can be more easily,
    do not always need to use "charAt" and "subString".
 
 5. Overall, Task5 codes are well-documented with comments. Its methods and variables are named correctly.
    It has good structure satisfies different check requirements for the Task, and can of course pass all relative tests.