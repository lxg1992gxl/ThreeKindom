Reviewer: Lyu, Xiaoguang (u6464313)
Component: <Task6>
Author: Siying Qian (u6099927)

Review Comments:
About Task6:

1.Generally view, this method collect two strings, comparing if move sequence is 
valid for current setup.

2.Details:
    a.check if the input sequence is well formed
    b.spilt move sequence and put it into a char[]
    c.check no false movement in move sequence
    d.check every move is valid
        i.If valid, change zhangyi's posision and change the sequence of setup
        ii.If invalid, return false
    e.output a sequence of borad when all valid moves done

3.improvment:
    a.use i=-1 as a marks to jump out of the loop
    b.should name the variable with a better name(e.g: D is the destination of zhangyi)
    

